package me.manu.projectkorralevelsystem.rpplayer;

import me.manu.projectkorralevelsystem.rpevents.RpPlayerLevelChangeEvent;
import me.manu.projectkorralevelsystem.rpevents.RpPlayerXPChangeEvent;
import me.manu.projectkorralevelsystem.methods.Methods;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class RpPlayer {

    private static final Map<UUID, RpPlayer> PLAYERS = new HashMap<>();
    private final Map<String, AbilityAttributes> abilityAttributesMap = new HashMap<>();

    private final Player player;
    private final UUID uuid;
    private int level;
    private double XP;
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

    public String getGainedXp() {
        double xp = this.getXp();
        double nextLevelXP = this.getNextLevelXP();
        return String.valueOf(nextLevelXP - xp);
    }

    //ATTRIBUTE PART

    /**
     * Set the ability attributes for a specific ability.
     *
     * @param abilityName Name of the ability (case-insensitive).
     * @param speed       Speed attribute value.
     * @param damage      Damage attribute value.
     * @param range       Range attribute value.
     */
    public void setAbilityAttributes(String abilityName, int speed, int damage, int range) {
        abilityAttributesMap.put(abilityName.toLowerCase(), new AbilityAttributes(speed, damage, range));
    }

    /**
     * Get the speed attribute for a specific ability.
     *
     * @param abilityName Name of the ability (case-insensitive).
     * @return Speed attribute value.
     */
    public int getAbilitySpeed(String abilityName) {
        AbilityAttributes attributes = abilityAttributesMap.get(abilityName.toLowerCase());
        return (attributes != null) ? attributes.getSpeed() : 0;
    }

    /**
     * Get the damage attribute for a specific ability.
     *
     * @param abilityName Name of the ability (case-insensitive).
     * @return Damage attribute value.
     */
    public int getAbilityDamage(String abilityName) {
        AbilityAttributes attributes = abilityAttributesMap.get(abilityName.toLowerCase());
        return (attributes != null) ? attributes.getDamage() : 0;
    }

    /**
     * Get the range attribute for a specific ability.
     *
     * @param abilityName Name of the ability (case-insensitive).
     * @return Range attribute value.
     */
    public int getAbilityRange(String abilityName) {
        AbilityAttributes attributes = abilityAttributesMap.get(abilityName.toLowerCase());
        return (attributes != null) ? attributes.getRange() : 0;
    }

    private static class AbilityAttributes {
        private final int speed;
        private final int damage;
        private final int range;

        public AbilityAttributes(int speed, int damage, int range) {
            this.speed = speed;
            this.damage = damage;
            this.range = range;
        }

        public int getSpeed() {
            return speed;
        }

        public int getDamage() {
            return damage;
        }

        public int getRange() {
            return range;
        }
    }
}