package me.manu.projectkorralevelsystem.listener;

import com.projectkorra.projectkorra.ability.CoreAbility;
import com.projectkorra.projectkorra.event.AbilityStartEvent;
import me.manu.projectkorralevelsystem.rpevents.RpPlayerLevelChangeEvent;
import me.manu.projectkorralevelsystem.rpevents.RpPlayerXPChangeEvent;
import me.manu.projectkorralevelsystem.levelmanager.LevelManager;
import me.manu.projectkorralevelsystem.methods.Methods;
import me.manu.projectkorralevelsystem.rpplayer.RpPlayer;
import me.manu.projectkorralevelsystem.util.DatabaseUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class Listeners implements Listener {

    @EventHandler
    public void onStart(AbilityStartEvent event) {
        CoreAbility ability = (CoreAbility) event.getAbility();
        Player p = ability.getPlayer();
        RpPlayer rpPlayer = RpPlayer.getRpPlayer(p.getUniqueId());

        p.sendMessage("Xp you need to level up: " + rpPlayer.getNextLevelXP());
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        DatabaseUtil.getPlayer(p.getUniqueId());
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        RpPlayer rpPlayer = RpPlayer.getRpPlayer(player.getUniqueId());
        if (rpPlayer != null) {
            DatabaseUtil.savePlayer(rpPlayer);
            RpPlayer.getPlayers().remove(player.getUniqueId());
        }
    }

    @EventHandler
    public void playerKill(PlayerDeathEvent e) {
        Player p = e.getEntity();
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
