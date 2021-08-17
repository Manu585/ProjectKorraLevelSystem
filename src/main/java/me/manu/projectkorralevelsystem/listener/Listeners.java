package me.manu.projectkorralevelsystem.listener;

import com.projectkorra.projectkorra.ability.CoreAbility;
import com.projectkorra.projectkorra.attribute.Attribute;
import com.projectkorra.projectkorra.attribute.AttributeModifier;
import com.projectkorra.projectkorra.event.AbilityStartEvent;
import me.manu.projectkorralevelsystem.events.RpPlayerLevelChangeEvent;
import me.manu.projectkorralevelsystem.events.RpPlayerXPChangeEvent;
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
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.lang.reflect.Field;

public class Listeners implements Listener {

    @EventHandler
    public void onStart(AbilityStartEvent event) {
        CoreAbility ability = (CoreAbility) event.getAbility();
        Player p = ability.getPlayer();
        RpPlayer rpPlayer = RpPlayer.getRpPlayer(p.getUniqueId());

        Methods.sendBar(p.getUniqueId());
        rpPlayer.addXP(1);

        //This part is made by Simplicitee
        try {
            for (Field field : ability.getClass().getDeclaredFields()) {
                if (!field.isAnnotationPresent(Attribute.class)) {
                    continue;
                }

                String attribute = field.getAnnotation(Attribute.class).value();

                    ability.addAttributeModifier(attribute, Methods.getPowerLevel(rpPlayer.getUUID()), AttributeModifier.MULTIPLICATION);
                    }
                } catch (Exception ignored) {
            }
        }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        DatabaseUtil.getPlayer(p.getUniqueId());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        RpPlayer rpPlayer = RpPlayer.getRpPlayer(p.getUniqueId());
        if (rpPlayer == null) return;
        DatabaseUtil.savePlayer(rpPlayer);
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
    }

    @EventHandler
    public void RpPlayerLevelChange(RpPlayerLevelChangeEvent e) {
        RpPlayer rpPlayer = e.getPlayer();
        Player p = Bukkit.getPlayer(rpPlayer.getUUID());
        int level = e.getLevel();
        assert p != null;

        p.sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + "--Level up!--");
        p.sendMessage(ChatColor.YELLOW + "You are now level " + ChatColor.GOLD + level);
        p.getWorld().playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 5, 10);
    }
}
