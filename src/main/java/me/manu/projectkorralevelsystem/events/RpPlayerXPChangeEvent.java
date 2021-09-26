package me.manu.projectkorralevelsystem.events;

import me.manu.projectkorralevelsystem.rpplayer.RpPlayer;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class RpPlayerXPChangeEvent extends Event implements Cancellable {

    private static final HandlerList HANDLERS = new HandlerList();

    private boolean cancelled;
    private RpPlayer rpPlayer;
    private double xp;
    private double addedXP;

    public RpPlayerXPChangeEvent(RpPlayer player, double xp, double addedXP) {
        this.rpPlayer = player;
        this.xp = xp;
        this.addedXP = addedXP;
        this.cancelled = false;
    }

    public RpPlayer getPlayer() {
        return rpPlayer;
    }

    public double getXp() {
        return xp;
    }

    public double getGainedXp() {
        return addedXP;
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
