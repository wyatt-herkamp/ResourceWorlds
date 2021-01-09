package me.kingtux.resourceworlds.listeners;

import me.kingtux.resourceworlds.Locale;
import me.kingtux.resourceworlds.events.ResourceWorldResetEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.Map;

public class SelfListener implements Listener {
    @EventHandler
    public void onWorldReset(ResourceWorldResetEvent e) {
        Bukkit.broadcastMessage(Locale.RESETTING_WORLD.colorAndSubstitute(Map.of("world", e.getResourceWorld().getName())));
    }
}
