package me.manu.projectkorralevelsystem;

import me.manu.projectkorralevelsystem.listener.Listeners;
import me.manu.projectkorralevelsystem.menu.MenuManager;
import me.manu.projectkorralevelsystem.util.JsonDatabase;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public final class ProjectKorraLevelSystem extends JavaPlugin {

    private static ProjectKorraLevelSystem instance;
    public static String prefix = ChatColor.GOLD + "[" + ChatColor.YELLOW + "PK lvl System" + ChatColor.GOLD + "] " + ChatColor.RESET;

    @Override
    public void onEnable() {
        instance = this;
        MenuManager.initMenus();
//      DatabaseUtil.createDB();
        JsonDatabase.createDB();

//      new ConfigManager();
        startMessage();
        registerEvents();
        registerCommands();
    }

    private void registerCommands() {
    }

    @Override
    public void onDisable() {
        stopMessage();
//        for (Player p : Bukkit.getOnlinePlayers()) {
//            RpPlayer rpPlayer = RpPlayer.getRpPlayer(p.getUniqueId());
//            assert rpPlayer != null;
//            DatabaseUtil.savePlayer(rpPlayer);
//        }
    }

    void registerEvents() {
        getServer().getPluginManager().registerEvents(new Listeners(), this);
    }

    void startMessage() {
        getServer().getConsoleSender().sendMessage("+-+----+------+------+----+-+");
        getServer().getConsoleSender().sendMessage("|  PK LEVEL SYSTEM v1.0.1   |");
        getServer().getConsoleSender().sendMessage("|  Made by: Manu and Finn   |");
        getServer().getConsoleSender().sendMessage("|    Powered by RavCraft    |");
        getServer().getConsoleSender().sendMessage("+-+----+------+------+----+-+");
    }

    void stopMessage() {
        getServer().getConsoleSender().sendMessage("+-+----+------+------+----+-+");
        getServer().getConsoleSender().sendMessage("|  PK LEVEL SYSTEM v1.0.1   |");
        getServer().getConsoleSender().sendMessage("|  Made by: Manu and Finn   |");
        getServer().getConsoleSender().sendMessage("|    Powered by RavCraft    |");
        getServer().getConsoleSender().sendMessage("+-+----+------+------+----+-+");
    }

    public static ProjectKorraLevelSystem getInstance() {
        return instance;
    }
}
