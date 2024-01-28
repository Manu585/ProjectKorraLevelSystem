package me.manu.projectkorralevelsystem.listener;

import com.projectkorra.projectkorra.ability.CoreAbility;
import com.projectkorra.projectkorra.event.AbilityStartEvent;
import me.manu.projectkorralevelsystem.levelmanager.LevelManager;
import me.manu.projectkorralevelsystem.methods.Methods;
import me.manu.projectkorralevelsystem.rpevents.RpPlayerLevelChangeEvent;
import me.manu.projectkorralevelsystem.rpevents.RpPlayerXPChangeEvent;
import me.manu.projectkorralevelsystem.rpplayer.RpPlayer;
import me.manu.projectkorralevelsystem.util.JsonDatabase;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Listeners implements Listener {
    private final JsonDatabase jsonDatabase;

    public Listeners() {
        this.jsonDatabase = new JsonDatabase(JsonDatabase.databaseFile);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        loadAndSavePlayerAttributes(p.getUniqueId());
    }

    private void loadAndSavePlayerAttributes(UUID playerUUID) {
        Map<String, Double> playerAttributes = jsonDatabase.getPlayerAttributes(playerUUID);

        if (playerAttributes == null) {
            playerAttributes = new HashMap<>();
            // Add default attributes or perform any necessary initialization
        }

        RpPlayer rpPlayer = RpPlayer.getRpPlayer(playerUUID);
        if (rpPlayer != null) {
            rpPlayer.setAbilityAttributesMap(playerAttributes);
        } else {
            rpPlayer = new RpPlayer(playerUUID, 0.0, 1); // Example initialization, adjust as needed
            rpPlayer.setAbilityAttributesMap(playerAttributes);
            RpPlayer.registerRpPlayer(rpPlayer);
            jsonDatabase.addPlayer(rpPlayer);
        }

        jsonDatabase.savePlayerAttributes(playerUUID, playerAttributes);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        RpPlayer rpPlayer = RpPlayer.getRpPlayer(player.getUniqueId());
        jsonDatabase.savePlayerAttributes(player.getUniqueId(), rpPlayer.getAbilityAttributesMap());
        jsonDatabase.savePlayer(rpPlayer);
    }

    @EventHandler
    public void onBend(AbilityStartEvent e) {
        CoreAbility ability = (CoreAbility) e.getAbility();
        Player p = ability.getPlayer();
        RpPlayer rpPlayer = RpPlayer.getRpPlayer(p.getUniqueId());
        p.sendMessage("YeahYeah");
        jsonDatabase.addAttribute(rpPlayer, "FireBlast", "speed", 1.5);
    }

    @EventHandler
    public void RpPlayerXpChange(RpPlayerXPChangeEvent e) {
        RpPlayer rp = e.getPlayer();
        Player p = Bukkit.getPlayer(rp.getUUID());
        assert p != null;
        Methods.sendBar(p.getUniqueId(), e.getGainedXp());
        LevelManager.calculateLevel(p.getUniqueId());
    }

    @EventHandler
    public void RpPlayerLevelChange(RpPlayerLevelChangeEvent e) {
        RpPlayer rpPlayer = e.getPlayer();
        Player p = Bukkit.getPlayer(rpPlayer.getUUID());
        assert p != null;
    }

    @EventHandler
    public void RpPlayerLevelUp(RpPlayerLevelChangeEvent e) {
        RpPlayer rpPlayer = e.getPlayer();
        Player p = Bukkit.getPlayer(rpPlayer.getUUID());
        int level = e.getLevel();
        int addedlvl = e.getAddedLevel();
        
        assert p != null;
        if (level < addedlvl) {
            p.sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + "--Level up!--");
            p.sendMessage(ChatColor.YELLOW + "You are now level " + ChatColor.GOLD + addedlvl);
            p.getWorld().playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 5, 10);
        } else {
            p.sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + "--Level down!--");
            p.sendMessage(ChatColor.YELLOW + "You are now level " + ChatColor.GOLD + level);
        }
    }
}
