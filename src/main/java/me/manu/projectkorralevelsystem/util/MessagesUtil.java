package me.manu.projectkorralevelsystem.util;

import me.manu.projectkorralevelsystem.rpplayer.RpPlayer;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class MessagesUtil {
    private RpPlayer rpPlayer;
    public static final String PREFIX = ChatColor.YELLOW + "[PK lvl System] " + ChatColor.RESET;

    public MessagesUtil(RpPlayer rpPlayer) {
        this.rpPlayer = rpPlayer;
    }

    public String getLevelUpMessage() {
        return PREFIX + "You leveled up to level " + rpPlayer.getLevel() + "!";
    }

    public String getLevelMessage() {
        return PREFIX + "You are level " + rpPlayer.getLevel() + "!";
    }

    public String getLevelMessage(Player p) {
        return PREFIX + p.getName() + " is level " + rpPlayer.getLevel() + "!";
    }

    public String getXPMessage() {
        return PREFIX + "You have " + rpPlayer.getXp() + " XP!";
    }

    public String getXPMessage(Player p) {
        return PREFIX + p.getName() + " has " + rpPlayer.getXp() + " XP!";
    }

}
