package me.kingtux.resourceworlds.events;

import me.kingtux.resourceworlds.ResourceWorld;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class ResourceWorldResetEvent extends Event {
    private static final HandlerList HANDLERS_LIST = new HandlerList();
    private final ResourceWorld resourceWorld;

    public ResourceWorldResetEvent(ResourceWorld resourceWorld) {
        this.resourceWorld = resourceWorld;
    }

    public ResourceWorld getResourceWorld() {
        return resourceWorld;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS_LIST;
    }

}
