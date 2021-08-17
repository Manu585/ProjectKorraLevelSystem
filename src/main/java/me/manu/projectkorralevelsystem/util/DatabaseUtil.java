package me.manu.projectkorralevelsystem.util;

import com.projectkorra.projectkorra.ProjectKorra;
import com.projectkorra.projectkorra.storage.DBConnection;
import com.projectkorra.projectkorra.storage.MySQL;
import me.manu.projectkorralevelsystem.ProjectKorraLevelSystem;
import me.manu.projectkorralevelsystem.rpplayer.RpPlayer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class DatabaseUtil {

    public static String databaseType = ProjectKorra.plugin.getConfig().getString("Storage.engine");

    public static void createDB() {
        if (!DBConnection.sql.tableExists("pk_rpplayer")) {
            ProjectKorraLevelSystem.getInstance().getLogger().info("Creating database to store RpPlayer");

            if (DBConnection.sql instanceof MySQL) {
                String query = "CREATE TABLE `pk_rpplayer` (" + "uuid varchar(36) NOT NULL," + "xp DOUBLE DEFAULT 0, level INT DEFAULT 1, PRIMARY KEY (uuid));";
                DBConnection.sql.modifyQuery(query, false);
            } else { //SQLite
                String query = "CREATE TABLE `pk_rpplayer` (" + "uuid TEXT(36) PRIMARY KEY," + "xp DOUBLE DEFAULT 0, level INT DEFAULT 1);";
                DBConnection.sql.modifyQuery(query, false);
            }
            ProjectKorraLevelSystem.getInstance().getLogger().info("Database created!");
        }
    }

    public static RpPlayer getPlayer(UUID uuid) {
        ResultSet rs2 = DBConnection.sql.readQuery("SELECT * FROM pk_rpplayer WHERE uuid = '" + uuid + "'");
        try {
            if (!rs2.next()) {
                return createPlayer(uuid, 0, 1);
            } else {
                double xp = rs2.getInt("xp");
                int level = rs2.getInt("level");
                RpPlayer rpPlayer = new RpPlayer(uuid, xp, level);
                RpPlayer.registerRpPlayer(rpPlayer);
                return rpPlayer;
                }
            } catch(SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static RpPlayer createPlayer(UUID uuid, double xp, int level) {
        DBConnection.sql.modifyQuery("INSERT INTO pk_rpplayer (uuid, xp, level) VALUES (" + "'" + uuid + "'" + "," + xp + "," + level + ")");
        RpPlayer rpPlayer = new RpPlayer(uuid, xp, level);
        RpPlayer.registerRpPlayer(rpPlayer);
        return rpPlayer;
    }

    public static void savePlayer(RpPlayer rpPlayer) {
        DBConnection.sql.modifyQuery("UPDATE pk_rpplayer SET xp = " + rpPlayer.getXp() + ", level = " + rpPlayer.getLevel() + " WHERE uuid= " + "'" + rpPlayer.getUUID() + "'", false);
    }
}
