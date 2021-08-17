package me.manu.projectkorralevelsystem.commands;

import com.projectkorra.projectkorra.command.PKCommand;
import me.manu.projectkorralevelsystem.ProjectKorraLevelSystem;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Commands {
    private final ProjectKorraLevelSystem plugin;


    public Commands(final ProjectKorraLevelSystem plugin) {
        this.plugin = plugin;
        this.init();
    }

    private void init() {
        final PluginCommand projectkorra = this.plugin.getCommand("projectkorra");

        new AddLevelCommand();

        final CommandExecutor exe = (s, c, label, args) -> {
            if (args.length > 0) {
                final List<String> sendingArgs = Arrays.asList(args).subList(1, args.length);
                for (final PKCommand command : PKCommand.instances.values()) {
                    if (Arrays.asList(command.getAliases()).contains(args[0].toLowerCase())) {
                        command.execute(s, sendingArgs);
                        return true;
                    }
                }

                PKCommand.instances.get("help").execute(s, new ArrayList<String>());
                return true;
            }
            return false;
        };
        assert projectkorra != null;
        projectkorra.setExecutor(exe);
    }
}
