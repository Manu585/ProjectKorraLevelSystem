package me.manu.projectkorralevelsystem.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.manu.projectkorralevelsystem.ProjectKorraLevelSystem;
import me.manu.projectkorralevelsystem.rpplayer.RpPlayer;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class JsonDatabase {
    private static final String PLUGIN_FOLDER = "plugins";
    private static final String PLUGIN_NAME = "ProjectKorraLevelSystem";
    private static final String DATABASE_FILE_NAME = "player_data.json";
    private final ObjectMapper mapper = new ObjectMapper();
    public static File databaseFile;


    public static void createDB() {
        File pluginFolder = new File(PLUGIN_FOLDER, PLUGIN_NAME);
        if (!pluginFolder.exists()) {
            pluginFolder.mkdirs();
        }

        databaseFile = new File(pluginFolder, DATABASE_FILE_NAME);
        if (!databaseFile.exists()) {
            try {
                databaseFile.createNewFile();
            } catch (IOException e) {
                ProjectKorraLevelSystem.getInstance().getLogger().severe("Could not create player_data.json file!");
                e.printStackTrace();
            }
        }
    }

    public boolean playerExists(RpPlayer rpPlayer) {
        try {
            if (mapper.readValue(databaseFile, RpPlayer.class) != null) {
                return true;
            } else {
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void addPlayer(RpPlayer rpPlayer) {
        if (!playerExists(rpPlayer)) {
        try {
            //rpPlayer.fillMap();
            mapper.writeValue(databaseFile, rpPlayer);
        } catch (IOException e) {
            e.printStackTrace();
            }
        } else {
            ProjectKorraLevelSystem.getInstance().getLogger().severe("Player already exists in database!");
        }
    }

    public void savePlayer(RpPlayer rpPlayer) {
        try {
            mapper.writeValue(databaseFile, rpPlayer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addAttribute(UUID uuid, String abilityName, String attributeName, double modifier) {
        Map<String, Double> attributes = getPlayerAttributes(uuid);
        if (attributes == null) {
            attributes = new HashMap<>();
        }
        String key = abilityName + "_" + attributeName;
        attributes.put(key, modifier);
        savePlayerAttributes(uuid, attributes);
    }

    public void updatePlayer(RpPlayer rpPlayer) {
        try {
            mapper.writeValue(databaseFile, rpPlayer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public File getDatabaseFile() {
        return databaseFile;
    }

    private Map<String, Double> getPlayerAttributes(UUID uuid) {
        try {
            if (databaseFile.exists()) {
                Map<UUID, Map<String, Double>> playerData = mapper.readValue(databaseFile, new TypeReference<Map<UUID, Map<String, Double>>>() {});
                return playerData.get(uuid);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void savePlayerAttributes(UUID uuid, Map<String, Double> attributes) {
        try {
            Map<UUID, Map<String, Double>> playerData = new HashMap<>();
            playerData.put(uuid, attributes);
            mapper.writeValue(databaseFile, playerData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
