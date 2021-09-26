package me.manu.projectkorralevelsystem.commands;

import com.projectkorra.projectkorra.command.SubCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class RpCommand implements SubCommand {

    public static HashMap<String, RpCommand> instances = new HashMap<>();

    private String name;
    private String properUse;
    private String description;
    private String[] aliases;

    public RpCommand(String name, String properUse, String description, String[] aliases) {
        this.name = name;
        this.properUse = properUse;
        this.description = description;
        this.aliases = aliases;
        instances.put(name, this);
    }

    public String getName() {
        return name;
    }

    public String getProperUse() {
        return properUse;
    }

    public String getDescription() {
        return description;
    }

    public String[] getAliases() {
        return aliases;
    }

    public void help(CommandSender sender, boolean description) {
        sender.sendMessage(ChatColor.GOLD + "Proper Usage: " + ChatColor.DARK_AQUA + properUse);
        if (description) {
            sender.sendMessage(ChatColor.YELLOW + this.description);
        }
    }

    protected boolean hasPermission(CommandSender sender) {
        if (sender.hasPermission("bending.command.rp." + name)) {
            return true;
        } else {
            sender.sendMessage(ChatColor.RED + "You don't have permission to do that.");
            return false;
        }
    }

    protected boolean hasPermission(CommandSender sender, String extra) {
        if (sender.hasPermission("bending.command.rp." + name + "." + extra)) {
            return true;
        } else {
            sender.sendMessage(ChatColor.RED + "You don't have permission to do that.");
            return false;
        }
    }

    protected boolean correctLength(CommandSender sender, int size, int min, int max) {
        if (size < min || size > max) {
            help(sender, false);
            return false;
        } else {
            return true;
        }
    }

    protected boolean isPlayer(CommandSender sender) {
        if (sender instanceof Player) {
            return true;
        } else {
            sender.sendMessage(ChatColor.RED + "You must be a player to use that command.");
            return false;
        }
    }

    protected List<String> getTabCompletion(CommandSender sender, List<String> args) {
        return new ArrayList<String>();
    }
}
