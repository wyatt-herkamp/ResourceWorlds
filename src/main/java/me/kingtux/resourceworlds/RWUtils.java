package me.kingtux.resourceworlds;

import me.kingtux.resourceworlds.economy.RWEconomy;
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
        return "resourceworlds.worlds." + world.getPropertiesSection().getName();
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
        if (!player.hasPermission(permission)) {
            player.sendMessage(Locale.MISSING_PERMISSION.colorAndSubstitute(Map.of("permission", permission)));
            return;
        }

        ResourceWorlds resourceWorlds = ResourceWorlds.getInstance();
        if (resourceWorlds.getRwEconomy() != null && world.getCost() > 0) {
            RWEconomy rwEconomy = resourceWorlds.getRwEconomy();
            if (rwEconomy.getBalance(player) >= world.getCost()) {
                player.sendMessage(Locale.LACK_FUNDS.colorAndSubstitute(Map.of("cost", String.valueOf(world.getCost()))));
                return;
            }
            rwEconomy.withdrawPlayer(player, world.getCost());
        }
        World world1 = resourceWorlds.getRwWorldManager().getWorld(world.getName());
        player.teleport(world1.getSpawnLocation());
    }
}
