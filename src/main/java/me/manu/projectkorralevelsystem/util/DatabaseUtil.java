package me.manu.projectkorralevelsystem.util;

import com.projectkorra.projectkorra.ProjectKorra;
import com.projectkorra.projectkorra.ability.CoreAbility;
import com.projectkorra.projectkorra.storage.DBConnection;
import com.projectkorra.projectkorra.storage.MySQL;
import me.manu.projectkorralevelsystem.ProjectKorraLevelSystem;
import me.manu.projectkorralevelsystem.rpplayer.RpPlayer;

import java.sql.*;
import java.util.*;

public class DatabaseUtil {

    public static String databaseType = ProjectKorra.plugin.getConfig().getString("Storage.engine");
    public static List<CoreAbility> abilities = new ArrayList<>();


    public static void createDB() {
        abilities = CoreAbility.getAbilities();
        if (!DBConnection.sql.tableExists("pk_rpplayer")) {
            ProjectKorraLevelSystem.getInstance().getLogger().info("Creating database to store RpPlayer");

            if (DBConnection.sql instanceof MySQL) {
                StringBuilder queryBuilder = new StringBuilder("CREATE TABLE `pk_rpplayer` (");
                queryBuilder.append("uuid varchar(36) NOT NULL,");
                queryBuilder.append("xp DOUBLE DEFAULT 0,");
                queryBuilder.append("level INT DEFAULT 1,");

                Set<String> addedAbilities = new HashSet<>();

                for (CoreAbility ability : abilities) {
                    String abilityName = ability.getName().toLowerCase();

                    if (!addedAbilities.contains(abilityName)) {
                        ProjectKorraLevelSystem.getInstance().getServer().getConsoleSender().sendMessage("Ability: " + abilityName);
                        queryBuilder.append(abilityName).append("_speed INT DEFAULT 1,");
                        queryBuilder.append(abilityName).append("_damage INT DEFAULT 1,");
                        queryBuilder.append(abilityName).append("_range INT DEFAULT 1,");
                        addedAbilities.add(abilityName);
                    }
                }

                queryBuilder.deleteCharAt(queryBuilder.length() - 1);
                queryBuilder.append(")");
                DBConnection.sql.modifyQuery(queryBuilder.toString(), false);
            } else { // SQLite
                StringBuilder queryBuilder = new StringBuilder("CREATE TABLE `pk_rpplayer` (");
                queryBuilder.append("uuid TEXT(36) PRIMARY KEY,");
                queryBuilder.append("xp DOUBLE DEFAULT 0,");
                queryBuilder.append("level INT DEFAULT 1,");

                try {
                    Connection connection = DriverManager.getConnection("jdbc:sqlite:" + DBConnection.sql.getConnection());
                    Statement statement = connection.createStatement();
                    ResultSet resultSet = statement.executeQuery("SELECT DISTINCT name FROM pk_players");

                    while (resultSet.next()) {
                        String abilityName = resultSet.getString("name").toLowerCase();
                        ProjectKorraLevelSystem.getInstance().getServer().getConsoleSender().sendMessage("Ability: " + abilityName);
                        queryBuilder.append(abilityName).append("_speed INT DEFAULT 1,");
                        queryBuilder.append(abilityName).append("_damage INT DEFAULT 1,");
                        queryBuilder.append(abilityName).append("_range INT DEFAULT 1,");
                    }

                    statement.close();
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                queryBuilder.deleteCharAt(queryBuilder.length() - 1);
                queryBuilder.append(")");
                DBConnection.sql.modifyQuery(queryBuilder.toString(), false);
            }
            ProjectKorraLevelSystem.getInstance().getLogger().info("Database created!");
        }
    }


    public static RpPlayer getPlayer(UUID uuid) {
        ResultSet rs2 = DBConnection.sql.readQuery("SELECT * FROM pk_rpplayer WHERE uuid = '" + uuid + "'");
        try {
            if (rs2 != null && rs2.next()) {
                double xp = rs2.getDouble("xp");
                int level = rs2.getInt("level");
                ProjectKorraLevelSystem.getInstance().getServer().getConsoleSender().sendMessage("Player exists. XP: " + xp + ", Level: " + level);
                RpPlayer rpPlayer = new RpPlayer(uuid, xp, level);
                RpPlayer.registerRpPlayer(rpPlayer);
                return rpPlayer;
            } else {
                ProjectKorraLevelSystem.getInstance().getServer().getConsoleSender().sendMessage("Player does not exist. Creating player...");
                return createPlayer(uuid, 0, 1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs2 != null) {
                    rs2.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static RpPlayer createPlayer(UUID uuid, double xp, int level) {
        StringBuilder queryBuilder = new StringBuilder("INSERT INTO pk_rpplayer (uuid, xp, level");

        for (CoreAbility ability : abilities) {
            String abilityName = ability.getName().toLowerCase();
            queryBuilder.append(", ").append(abilityName).append("_speed, ").append(abilityName).append("_damage, ").append(abilityName).append("_range");
        }

        queryBuilder.append(") VALUES ('").append(uuid).append("', ").append(xp).append(", ").append(level);

        for (CoreAbility ability : abilities) {
            queryBuilder.append(", 1, 1, 1");
        }

        queryBuilder.append(")");

        DBConnection.sql.modifyQuery(queryBuilder.toString(), false);

        RpPlayer rpPlayer = new RpPlayer(uuid, xp, level);
        RpPlayer.registerRpPlayer(rpPlayer);

        return rpPlayer;
    }

    public static void savePlayer(RpPlayer rpPlayer) {
        StringBuilder queryBuilder = new StringBuilder("UPDATE pk_rpplayer SET ");
        queryBuilder.append("xp = ").append(rpPlayer.getXp()).append(", ");
        queryBuilder.append("level = ").append(rpPlayer.getLevel()).append(", ");

        for (CoreAbility ability : abilities) {
            String abilityName = ability.getName().toLowerCase();
            queryBuilder.append(abilityName).append("_speed = ").append(rpPlayer.getAbilitySpeed(abilityName)).append(", ");
            queryBuilder.append(abilityName).append("_damage = ").append(rpPlayer.getAbilityDamage(abilityName)).append(", ");
            queryBuilder.append(abilityName).append("_range = ").append(rpPlayer.getAbilityRange(abilityName)).append(", ");
        }

        queryBuilder.delete(queryBuilder.length() - 2, queryBuilder.length());
        queryBuilder.append(" WHERE uuid = '").append(rpPlayer.getUUID()).append("'");

        DBConnection.sql.modifyQuery(queryBuilder.toString(), false);
    }

}
