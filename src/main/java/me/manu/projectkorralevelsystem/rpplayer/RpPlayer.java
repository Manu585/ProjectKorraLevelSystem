package me.manu.projectkorralevelsystem.rpplayer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import me.manu.projectkorralevelsystem.rpevents.RpPlayerLevelChangeEvent;
import me.manu.projectkorralevelsystem.rpevents.RpPlayerXPChangeEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class RpPlayer {

    @JsonIgnore
    private static Map<UUID, RpPlayer> PLAYERS = new HashMap<>();
    @JsonIgnore
    private final Player player;
    private final UUID uuid;
    private int level;
    private double XP;

    private Map<String, Double> abilityAttributesMap = new HashMap<>();

    public RpPlayer(final UUID uuid, double xp, int level) {
        this.uuid = uuid;
        this.XP = xp;
        this.level = level;
        this.player = Bukkit.getPlayer(uuid);
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

//    public void fillMap() {
//        for (CoreAbility ability : CoreAbility.getAbilities()) {
//                Field[] fields = ability.getClass().getDeclaredFields();
//                for (Field field : fields) {
//                    if (field.isAnnotationPresent(Attribute.class)) {
//                        Attribute attribute = field.getAnnotation(Attribute.class);
//                        String attributeName = attribute.value();
//                        field.setAccessible(true);
//                        abilityAttributesMap.put(attributeName, 1.0);
//                    }
//                }
//            }
//        }

    public static RpPlayer getRpPlayer(UUID uuid) {
        return PLAYERS.get(uuid);
    }

    public Player getPlayer() {
        return this.player;
    }

    public static Map<UUID, RpPlayer> getPlayers() {
        return PLAYERS;
    }

    public Map<String, Double> getAbilityAttributesMap() {
        return abilityAttributesMap;
    }

    public void setAbilityAttributesMap(Map<String, Double> abilityAttributesMap) {
        this.abilityAttributesMap = abilityAttributesMap;
    }
}