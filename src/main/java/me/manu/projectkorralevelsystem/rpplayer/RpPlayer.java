package me.manu.projectkorralevelsystem.rpplayer;

import me.manu.projectkorralevelsystem.events.RpPlayerLevelChangeEvent;
import me.manu.projectkorralevelsystem.events.RpPlayerXPChangeEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class RpPlayer {

    private static final Map<UUID, RpPlayer> PLAYERS = new HashMap<>();

    private final Player player;
    private final UUID uuid;
    private int level;
    private double XP;
    private double modifier;

    public RpPlayer(final UUID uuid, final double xp, final int level) {
        this.uuid = uuid;
        this.XP = xp;
        this.level = level;
        this.player = Bukkit.getPlayer(uuid);
    }

    public UUID getUUID() {
        return uuid;
    }

    public double getXp() {
        return XP;
    }

    public int getLevel() {
        return level;
    }

    public double getModifier() {
        return modifier;
    }

    public void addLevel(int level) {
        RpPlayerLevelChangeEvent e = new RpPlayerLevelChangeEvent(this, this.level);
        Bukkit.getServer().getPluginManager().callEvent(e);
        if (!e.isCancelled()) {
            this.level += level;
        }
    }

    public void setLevel(int level) {
        RpPlayerLevelChangeEvent e = new RpPlayerLevelChangeEvent(this, this.level);
        Bukkit.getServer().getPluginManager().callEvent(e);
        if (!e.isCancelled()) {
            this.level += level;
        }
    }

    public void addXP(double xp) {
        RpPlayerXPChangeEvent e = new RpPlayerXPChangeEvent(this, XP);
        Bukkit.getServer().getPluginManager().callEvent(e);
        if (!e.isCancelled()) {
            this.XP += xp;
        }
    }

    /**
     * To set the xp of a player.
     * @param xp
     *
     **/
    public void setXP(double xp) {
        RpPlayerXPChangeEvent e = new RpPlayerXPChangeEvent(this, XP);
        Bukkit.getServer().getPluginManager().callEvent(e);
        if (!e.isCancelled()) {
            this.XP += xp;
        }
    }

    public void addModifier(double mod) {
        this.modifier += mod;
    }

    public void setModifier(double mod) {
        this.modifier = mod;
    }

    public static void registerRpPlayer(RpPlayer player) {
        PLAYERS.put(player.getUUID(), player);
    }

    public static RpPlayer getRpPlayer(UUID uuid) {
        return PLAYERS.get(uuid);
    }

    public Player getPlayer() {
        return this.player;
    }

    public static Map<UUID, RpPlayer> getPlayers() {
        return PLAYERS;
    }
}