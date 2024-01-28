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
    private final ObjectMapper mapper;
    public static File databaseFile;

    public JsonDatabase(File databaseFile) {
        this.mapper = new ObjectMapper();
        this.databaseFile = databaseFile;
    }


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

    public void addAttribute(RpPlayer rpPlayer, String abilityName, String attributeName, double modifier) {
        Map<String, Double> attributes = rpPlayer.getAbilityAttributesMap();
        if (attributes == null) {
            attributes = new HashMap<>();
        }
        String key = abilityName + "_" + attributeName;
        attributes.put(key, modifier);
        savePlayerAttributes(rpPlayer.getUUID(), attributes);
    }

    public Map<UUID, Map<String, Double>> loadPlayerAttributes() {
        try {
            if (databaseFile.exists() && databaseFile.length() > 0) {
                return mapper.readValue(databaseFile, new TypeReference<Map<UUID, Map<String, Double>>>() {});
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new HashMap<>();
    }

    public Map<String, Double> getPlayerAttributes(UUID uuid) {
        Map<UUID, Map<String, Double>> playerData = loadPlayerAttributes();
        return playerData.getOrDefault(uuid, new HashMap<>());
    }

    public void savePlayerAttributes(UUID uuid, Map<String, Double> attributes) {
        Map<UUID, Map<String, Double>> playerData = loadPlayerAttributes();
        playerData.put(uuid, attributes);
        try {
            mapper.writeValue(databaseFile, playerData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
