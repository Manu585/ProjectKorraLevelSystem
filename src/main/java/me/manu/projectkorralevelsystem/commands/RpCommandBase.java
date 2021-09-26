package me.manu.projectkorralevelsystem.commands;

import com.projectkorra.projectkorra.command.PKCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class RpCommandBase extends PKCommand {

    public RpCommandBase() {
        super("rp", "/bending rp", "Base command for the RP side plugin", new String[] { "rp" });
    }

    @Override
    public void execute(CommandSender sender, List<String> args) {
        if (args.size() == 0) {
            sender.sendMessage(ChatColor.RED + "/bending rp level set <player> " + ChatColor.YELLOW + "Set the level of a player.");
            sender.sendMessage(ChatColor.RED + "/bending rp help " + ChatColor.YELLOW + "Display help.");
            sender.sendMessage(ChatColor.RED + "/bending rpgworldevent <argument> [worldevent] " + ChatColor.YELLOW + "Manipulate events.");
            return;
        }
        for (RpCommand command : RpCommand.instances.values()) {
            if (Arrays.asList(command.getAliases()).contains(args.get(0).toLowerCase())) {
                command.execute(sender, args.subList(1, args.size()));
            }
        }
    }

    @Override
    protected List<String> getTabCompletion(CommandSender sender, List<String> args) {
        if (args.size() == 0) {
            List<String> l = new ArrayList<String>();
            for (RpCommand cmd : RpCommand.instances.values()) {
                l.add(cmd.getName());
            }
            Collections.sort(l);
            return l;
        } else
            for (RpCommand cmd : RpCommand.instances.values()) {
                if (Arrays.asList(cmd.getAliases()).contains(args.get(0).toLowerCase()) && sender.hasPermission("bending.command.rp." + cmd.getName())) {
                    List<String> newargs = new ArrayList<String>();
                    for (int i = 1; i < args.size(); i++) {
                        if (!(args.get(i).equals("") || args.get(i).equals(" ")) && args.size() >= 1)
                            newargs.add(args.get(i));
                    }
                    return cmd.getTabCompletion(sender, newargs);
                }
            }
        return new ArrayList<String>();
    }
}
