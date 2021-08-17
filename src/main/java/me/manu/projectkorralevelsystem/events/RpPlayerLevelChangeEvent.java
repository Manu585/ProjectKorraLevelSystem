package me.manu.projectkorralevelsystem.events;

import me.manu.projectkorralevelsystem.rpplayer.RpPlayer;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class RpPlayerLevelChangeEvent extends Event implements Cancellable {

    private static final HandlerList HANDLERS = new HandlerList();

    private boolean cancelled;
    private RpPlayer rpPlayer;
    private int level;

    public RpPlayerLevelChangeEvent(RpPlayer player, int level) {
        this.rpPlayer = player;
        this.level = level;
        this.cancelled = false;
        cancelled = false;
    }

    public RpPlayer getPlayer() {
        return rpPlayer;
    }

    public int getLevel() {
        return level;
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
