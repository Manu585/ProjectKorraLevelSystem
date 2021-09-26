package me.manu.projectkorralevelsystem.commands;

import me.manu.projectkorralevelsystem.methods.Methods;
import me.manu.projectkorralevelsystem.rpplayer.RpPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class XpCommand extends RpCommand {

    public XpCommand() {
        super("xp", "/bending rp xp <add/set> <amount> [user]", "Manipulate xp for a specified target.", new String[]{ "xp", "experience" });
    }

    @Override
    public void execute(CommandSender sender, List<String> args) {
        Player target = null;

        if (!correctLength(sender, args.size(), 2, 3)) {
            return;
        }

        String cmd = args.get(0);
        int xp;
        try {
            xp = Integer.parseInt(args.get(1));
        } catch (Exception e) {
            sender.sendMessage(ChatColor.RED + "Unknown xp amount given!");
            return;
        }
        if (args.size() == 2) {
            if (!isPlayer(sender) || !hasPermission(sender)) {
                return;
            }

            target = (Player) sender;
        } else if (args.size() == 3) {
            if (!hasPermission(sender, "others")) {
                return;
            }

            target = Bukkit.getPlayer(args.get(2));
        }

        if (target == null) {
            sender.sendMessage(ChatColor.RED + "Player target not found!");
            return;
        }

        RpPlayer rpPlayer = RpPlayer.getRpPlayer(target.getUniqueId());
        if (rpPlayer == null) {
            sender.sendMessage(ChatColor.RED + "Player target was not found!");
            return;
        }

        if (cmd.equalsIgnoreCase("add")) {
            Methods.addXP(rpPlayer.getUUID(), xp);
        } else if (cmd.equalsIgnoreCase("set")) {
            Methods.setXP(rpPlayer.getUUID(), xp);
        }

        sender.sendMessage(ChatColor.DARK_AQUA + rpPlayer.getPlayer().getName() + ChatColor.GOLD + "'s xp is now " + ChatColor.DARK_AQUA + "" + rpPlayer.getXp());
    }

    @Override
    public List<String> getTabCompletion(CommandSender sender, List<String> args) {
        if (args.size() == 0) {
            return Arrays.asList("set", "add");
        } else if (args.size() == 1) {
            return Arrays.asList("<amount>");
        } else if (args.size() == 2 && hasPermission(sender, "others")) {
            return Arrays.asList(Bukkit.getOnlinePlayers().stream().map(Player::getName).toArray(String[]::new));
        } else {
            return new ArrayList<>();
        }
    }
}
