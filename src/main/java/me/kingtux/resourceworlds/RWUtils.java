package me.kingtux.resourceworlds;

import me.kingtux.resourceworlds.events.ResourceWorldTeleportEvent;
import me.kingtux.resourceworlds.requirements.RWRequirement;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandMap;
import org.bukkit.entity.Player;

import java.io.File;
import java.lang.reflect.Field;
import java.util.Map;

public class RWUtils {
    public static void deleteFolder(File folder) {
        File[] files = folder.listFiles();
        if (files != null) {
            for (File f : files) {
                if (f.isDirectory()) {
                    deleteFolder(f);
                } else {
                    f.delete();
                }
            }
        }
        folder.delete();
    }

    /**
     * Stolen from NitroCommand
     *
     * @return returns the CommandMap
     */
    public static CommandMap getCommandMap() {
        try {
            Field commandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            commandMap.setAccessible(true);
            return (CommandMap) commandMap.get(Bukkit.getServer());
        } catch (Exception e) {
            ResourceWorlds.getInstance().getLogger().severe("Failure to get CommandMap");
            e.printStackTrace();
            return null;
        }
    }

    public static String getWorldPermission(ResourceWorld world) {
        return "resourceworlds.worlds" + world.getPropertiesSection().getName();
    }

    public static void teleportToWorld(Player player, ResourceWorld world) {
        for (RWRequirement rwRequirement : world.getRequirementsList()) {
            if (!rwRequirement.hasPlayerCompletedRequirement(player)) return;
        }
        ResourceWorldTeleportEvent event = new ResourceWorldTeleportEvent(player, world);
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled()) {
            return;
        }
        String permission = getWorldPermission(world);
        if (player.hasPermission(permission)) {
            player.sendMessage(Locale.MUST_BE_PLAYER.colorAndSubstitute(Map.of("permission", permission)));
            return;
        }
        ResourceWorlds resourceWorlds = ResourceWorlds.getInstance();
        World world1 = resourceWorlds.getRwWorldManager().getWorld(world.getName());
        player.teleport(world1.getSpawnLocation());
    }
}
