package me.kingtux.resourceworlds.events;

import me.kingtux.resourceworlds.ResourceWorld;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.jetbrains.annotations.NotNull;

public class ResourceWorldTeleportEvent extends PlayerEvent implements Cancellable {
    private boolean cancelled = false;
    private static final HandlerList HANDLERS_LIST = new HandlerList();
    private ResourceWorld resourceWorld;

    public ResourceWorldTeleportEvent(@NotNull Player who, ResourceWorld resourceWorld) {
        super(who);
        this.resourceWorld = resourceWorld;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS_LIST;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        cancelled = cancel;
    }

    public void setResourceWorld(ResourceWorld world) {
        resourceWorld = world;
    }

    public ResourceWorld getResourceWorld() {
        return resourceWorld;
    }
}
