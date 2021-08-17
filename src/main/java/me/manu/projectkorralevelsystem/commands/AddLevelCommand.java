package me.manu.projectkorralevelsystem.commands;

import com.projectkorra.projectkorra.command.PKCommand;
import me.manu.projectkorralevelsystem.methods.Methods;
import me.manu.projectkorralevelsystem.rpplayer.RpPlayer;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class AddLevelCommand extends PKCommand {

    public AddLevelCommand() {
        super("level", "/bending level add <level> <player>", "", new String[] { "level", "lvl" });
    }

    @Override
    public void execute(CommandSender sender, List<String> args) {
        if (!this.hasPermission(sender) || !this.correctLength(sender, args.size(), 3, 3) || !this.isPlayer(sender)) {
            return;
        }
        Player p = (Player) sender;

        //level set <lvl> [player]
        if (args.size() == 3) {
            Player target = Bukkit.getPlayer(args.get(3));
            this.add(sender, Integer.parseInt(args.get(2)), target);
        }
    }

    private void add(CommandSender sender, int lvl, Player target) {
        Player p = (Player) sender;
        target = (Player) sender;
        RpPlayer rpPlayer = RpPlayer.getRpPlayer(target.getUniqueId());

        if (rpPlayer == null) {
            return;
        }

        rpPlayer.addLevel(lvl);
        Methods.sendBar(target.getUniqueId());
        p.sendMessage("You have added " + lvl + " level to " + target.getName());
        p.sendMessage("Not they are level " + rpPlayer.getLevel());
    }
}
