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

    public static void sendBar(UUID uuid) {
        RpPlayer rpPlayer = RpPlayer.getRpPlayer(uuid);
        Player p = Bukkit.getPlayer(uuid);
        assert p != null;
        ActionBar.sendActionBar(getChatColor("&2[&a+&2] &e&n2&r &aXP"), p);
    }

    public static String getChatColor(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }
}