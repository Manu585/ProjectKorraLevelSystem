package me.manu.projectkorralevelsystem.levelmanager;

import me.manu.projectkorralevelsystem.rpplayer.RpPlayer;

import java.util.UUID;

public class LevelManager {
    public static void calculateLevel(UUID uuid) {
        RpPlayer rpPlayer = RpPlayer.getRpPlayer(uuid);
        int level = rpPlayer.getLevel();
        double xp = rpPlayer.getXp();

    }
}
