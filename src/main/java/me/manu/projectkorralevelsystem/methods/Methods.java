package me.manu.projectkorralevelsystem.methods;

import com.projectkorra.projectkorra.util.ActionBar;
import me.manu.projectkorralevelsystem.rpplayer.RpPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.UUID;

public class Methods {
    public static void sendBar(UUID uuid, double added) {
        RpPlayer rpPlayer = RpPlayer.getRpPlayer(uuid);
        Player p = Bukkit.getPlayer(uuid);
        assert p != null;
        ActionBar.sendActionBar(getChatColor("&2[&a+&2] &e&n" + added + "&r &aXP"), p);
    }

    public static double nextLevel(int level) {
        return level * level * 100;
    }

    public static String getChatColor(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }
}