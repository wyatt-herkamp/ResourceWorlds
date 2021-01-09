package me.kingtux.resourceworlds.commands;

import me.kingtux.resourceworlds.Locale;
import me.kingtux.resourceworlds.RWUtils;
import me.kingtux.resourceworlds.ResourceWorld;
import me.kingtux.resourceworlds.ResourceWorlds;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ResourceWorldCommand implements CommandExecutor, TabCompleter {
    private ResourceWorlds resourceWorlds;

    public ResourceWorldCommand(ResourceWorlds resourceWorlds) {
        this.resourceWorlds = resourceWorlds;
        Bukkit.getPluginManager().addPermission(new Permission("resourceworlds.reload"));
        Bukkit.getPluginManager().addPermission(new Permission("resourceworlds.use"));
        Bukkit.getPluginManager().addPermission(new Permission("resourceworlds.reset"));
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 0) {
            sender.sendMessage(Locale.INVALID_COMMAND.color());
            return true;
        }
        if (args[0].equalsIgnoreCase("reset")) {
            if (!sender.hasPermission("resourceworlds.reset")) {
                sender.sendMessage(Locale.MISSING_PERMISSION.color());

                return false;
            }

            if (args.length == 2) {
                if (args[1].equalsIgnoreCase("all")) {
                    resourceWorlds.getRunnable().run();
                } else {
                    //TODO
                }
            } else {
                sender.sendMessage("Invalid Command /rw reset {all,{WORLD_NAME}}");
            }
            sender.sendMessage(Locale.RESETTING_END.color());
        } else if (args[0].equalsIgnoreCase("reload")) {
            if (!sender.hasPermission("resourceworlds.reload")) {
                sender.sendMessage(Locale.MISSING_PERMISSION.color());
                return false;
            }
            sender.sendMessage(Locale.RELOADING_PLUGIN.color());
            resourceWorlds.reload();
        } else {
            Optional<ResourceWorld> worldOptional = resourceWorlds.getWorld(args[0]);
            if (worldOptional.isEmpty()) {
                sender.sendMessage(Locale.INVALID_WORLD.color());
            }
            ResourceWorld resourceWorld = worldOptional.get();
            RWUtils.teleportToWorld(((Player) sender), resourceWorld);
            return true;
        }
        return true;
    }


    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String
            alias, @NotNull String[] args) {
        List<String> strings = new ArrayList<>();
        if (sender.hasPermission("resourceworlds.reset")) {
            strings.add("reset");
        }
        if (sender.hasPermission("resourceworlds.reload")) {
            strings.add("reload");
        }
        for (ResourceWorld resourceWorld : resourceWorlds.getResourceWorlds()) {
            if (sender.hasPermission(RWUtils.getWorldPermission(resourceWorld))) {
                strings.add(resourceWorld.getName());
            }
        }
        return strings;
    }
}
