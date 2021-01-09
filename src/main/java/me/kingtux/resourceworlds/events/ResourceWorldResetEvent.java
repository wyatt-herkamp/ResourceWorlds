package me.kingtux.resourceworlds.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class ResourceWorldResetEvent extends Event {
    private static final HandlerList HANDLERS_LIST = new HandlerList();

    @Override
    public HandlerList getHandlers() {
        return HANDLERS_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS_LIST;
    }

}
