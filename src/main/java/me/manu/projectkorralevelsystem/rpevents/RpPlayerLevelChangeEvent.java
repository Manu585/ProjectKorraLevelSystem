package me.manu.projectkorralevelsystem.rpevents;

import me.manu.projectkorralevelsystem.rpplayer.RpPlayer;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class RpPlayerLevelChangeEvent extends Event implements Cancellable {

    private static final HandlerList HANDLERS = new HandlerList();

    private boolean cancelled;
    private RpPlayer rpPlayer;
    private int level;
    private int addedLevel;

    public RpPlayerLevelChangeEvent(RpPlayer player, int level, int addedLevel) {
        this.rpPlayer = player;
        this.level = level;
        this.addedLevel = addedLevel;
        this.cancelled = false;
    }

    public RpPlayer getPlayer() {
        return rpPlayer;
    }

    public int getLevel() {
        return level;
    }

    public int getAddedLevel() {
        return addedLevel;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    @Override
    public boolean isCancelled() {
        return false;
    }

    @Override
    public void setCancelled(boolean cancel) {

    }
}
