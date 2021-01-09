package me.kingtux.resourceworlds.commands;

import me.kingtux.resourceworlds.Locale;
import me.kingtux.resourceworlds.ResourceWorlds;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.advancement.AdvancementProgress;
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
            if (!sender.hasPermission("resourceworlds.use")) {
                sender.sendMessage(Locale.MISSING_PERMISSION.color());
                return false;
            }
            if (!(sender instanceof Player)) {
                sender.sendMessage(Locale.MUST_BE_PLAYER.color());
                return false;
            }
            Player player = (Player) sender;
            if (resourceWorlds.getConfig().getBoolean("require-portal-achievement")) {
                AdvancementProgress advancementProgress = player.getAdvancementProgress(Bukkit.getAdvancement(NamespacedKey.minecraft("end/root")));
                if (!advancementProgress.isDone()) {
                    sender.sendMessage(Locale.MUST_OF_ENTERED_PORTAL.color());
                    return false;
                }
            }
            //player.teleport(resourceWorlds.getSecondEndWorld().getSpawnLocation());
            player.sendMessage(Locale.TELEPORT_TO_END.color());
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
        }
        if (args[0].equalsIgnoreCase("reload")) {
            if (!sender.hasPermission("resourceworlds.reload")) {
                sender.sendMessage(Locale.MISSING_PERMISSION.color());
                return false;
            }
            sender.sendMessage(Locale.RELOADING_PLUGIN.color());
            resourceWorlds.reload();
        }
        return true;
    }


    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        List<String> strings = new ArrayList<>();
        if (sender.hasPermission("resourceworlds.reset")) {
            strings.add("reset");
        }
        if (sender.hasPermission("resourceworlds.reload")) {
            strings.add("reload");
        }
        return strings;
    }
}
