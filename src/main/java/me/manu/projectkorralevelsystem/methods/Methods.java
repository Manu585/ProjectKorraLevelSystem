package me.manu.projectkorralevelsystem.methods;

import com.projectkorra.projectkorra.util.ActionBar;
import me.manu.projectkorralevelsystem.rpplayer.RpPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.UUID;

public class Methods {

    public static double getPowerLevel(UUID uuid) {
        RpPlayer rpPlayer = RpPlayer.getRpPlayer(uuid);
        int level = rpPlayer.getLevel();
        double modifier = rpPlayer.getModifier();

        modifier = level / (2.3 * Math.PI);

        return modifier;
    }

    public static void sendBar(UUID uuid, double added) {
        RpPlayer rpPlayer = RpPlayer.getRpPlayer(uuid);
        Player p = Bukkit.getPlayer(uuid);
        assert p != null;
        ActionBar.sendActionBar(getChatColor("&2[&a+&2] &e&n" + added + "&r &aXP"), p);
    }

    public static void addXP(UUID uuid, double xp) {
        RpPlayer rpPlayer = RpPlayer.getRpPlayer(uuid);
        rpPlayer.addXP(xp);
        ReadyToLevelUp(uuid);
    }

    public static void setXP(UUID uuid, double xp) {
        RpPlayer rpPlayer = RpPlayer.getRpPlayer(uuid);
        rpPlayer.setXP(xp);
        ReadyToLevelUp(uuid);
    }

    public static void addLevel(UUID uuid, int lvl) {
        RpPlayer rpPlayer = RpPlayer.getRpPlayer(uuid);
        rpPlayer.addLevel(lvl);
    }

    public static void setLevel(UUID uuid, int lvl) {
        RpPlayer rpPlayer = RpPlayer.getRpPlayer(uuid);
        rpPlayer.setLevel(lvl);
    }

    public static double nextLevel(int level) {
        double exponent = 1.5;
        double baseXP = 1000;
        return Math.floor(baseXP * (Math.pow(level, exponent)));
    }

    public static boolean ReadyToLevelUp(UUID uuid) {
        RpPlayer rpPlayer = RpPlayer.getRpPlayer(uuid);
        return rpPlayer.getXp() >= rpPlayer.getNextLevelXP();
    }

    public static String getChatColor(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }
}