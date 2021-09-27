package me.manu.projectkorralevelsystem.rpplayer;

import me.manu.projectkorralevelsystem.events.RpPlayerLevelChangeEvent;
import me.manu.projectkorralevelsystem.events.RpPlayerXPChangeEvent;
import me.manu.projectkorralevelsystem.methods.Methods;
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
    private double nextLevelXP;

    public RpPlayer(final UUID uuid, final double xp, final int level) {
        this.uuid = uuid;
        this.XP = xp;
        this.level = level;
        this.player = Bukkit.getPlayer(uuid);
        this.nextLevelXP = Methods.nextLevel(this.level);
    }

    /**
     *
     * @return the players UUID
     *
     */
    public UUID getUUID() {
        return uuid;
    }

    /**
     *
     * @return the players XP
     *
     */
    public double getXp() {
        return XP;
    }

    /**
     *
     * @return the level of the player
     *
     */
    public int getLevel() {
        return level;
    }

    /**
     *
     * @return the modifier of the playe
     *
     */
    public double getModifier() {
        return modifier;
    }

    public double getNextLevelXP() {
        return this.nextLevelXP;
    }

    /**
     * Add level to a player
     * @param level
     *
     */
    public void addLevel(int level) {
        RpPlayerLevelChangeEvent e = new RpPlayerLevelChangeEvent(this, this.level, level);
        Bukkit.getServer().getPluginManager().callEvent(e);
        if (!e.isCancelled()) {
            this.level += level;
        }
    }

    /**
     * Set the level of a player
     * @param level
     *
     */
    public void setLevel(int level) {
        RpPlayerLevelChangeEvent e = new RpPlayerLevelChangeEvent(this, this.level, level);
        Bukkit.getServer().getPluginManager().callEvent(e);
        if (!e.isCancelled()) {
            this.level = level;
        }
    }

    /**
     * Add XP to the player
     * @param xp
     *
     **/
    public void addXP(double xp) {
        RpPlayerXPChangeEvent e = new RpPlayerXPChangeEvent(this, XP, xp);
        Bukkit.getServer().getPluginManager().callEvent(e);
        if (!e.isCancelled()) {
            this.XP += xp;
        }
    }

    /**
     * Set the XP of the player.
     * @param xp
     *
     **/
    public void setXP(double xp) {
        RpPlayerXPChangeEvent e = new RpPlayerXPChangeEvent(this, XP, xp);
        Bukkit.getServer().getPluginManager().callEvent(e);
        if (!e.isCancelled()) {
            this.XP = xp;
        }
    }

    /**
     * Make the players bending stronger
     * @param mod
     *
     */
    public void addModifier(double mod) {
        this.modifier += mod;
    }

    /**
     * Make the players bending stronger
     * @param mod
     *
     */
    public void setModifier(double mod) {
        this.modifier = mod;
    }

    public void CreateLevelUp() {
        this.XP = - getNextLevelXP();
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