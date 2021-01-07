package me.kingtux.secondend;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.advancement.AdvancementProgress;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SecondEndCommand implements CommandExecutor, TabCompleter {
    private SecondEnd secondEnd;

    public SecondEndCommand(SecondEnd secondEnd) {
        this.secondEnd = secondEnd;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 0) {
            if (!sender.hasPermission("secondend.use")) {
                sender.sendMessage("");
                return false;
            }
            if (!(sender instanceof Player)) {
                sender.sendMessage("");
                return false;
            }
            Player player = (Player) sender;
            if (secondEnd.getConfig().getBoolean("require-portal-achievement")) {
                AdvancementProgress advancementProgress = player.getAdvancementProgress(Bukkit.getAdvancement(NamespacedKey.minecraft("end/root")));
                if (!advancementProgress.isDone()) {
                    player.sendMessage("Must of entered the end portal once.");
                    return false;
                }
            }
            player.teleport(secondEnd.getSecondEndWorld().getSpawnLocation());
            return true;
        }
        if (args[0].equalsIgnoreCase("reset")) {
            if (!sender.hasPermission("secondend.reset")) {
                sender.sendMessage("You lack the permission to do that.");
                return false;
            }
            secondEnd.run();
        }
        return true;
    }


    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        return null;
    }
}
