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
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.permissions.Permission;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class SecondEndCommand implements CommandExecutor, TabCompleter {
    private SecondEnd secondEnd;

    public SecondEndCommand(SecondEnd secondEnd) {
        this.secondEnd = secondEnd;
        Bukkit.getPluginManager().addPermission(new Permission("secondend.reload"));
        Bukkit.getPluginManager().addPermission(new Permission("secondend.use"));
        Bukkit.getPluginManager().addPermission(new Permission("secondend.reset"));
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 0) {
            if (!sender.hasPermission("secondend.use")) {
                sender.sendMessage(SELang.MISSING_PERMISSION.color());
                return false;
            }
            if (!(sender instanceof Player)) {
                sender.sendMessage(SELang.MUST_BE_PLAYER.color());
                return false;
            }
            Player player = (Player) sender;
            if (secondEnd.getConfig().getBoolean("require-portal-achievement")) {
                AdvancementProgress advancementProgress = player.getAdvancementProgress(Bukkit.getAdvancement(NamespacedKey.minecraft("end/root")));
                if (!advancementProgress.isDone()) {
                    sender.sendMessage(SELang.MUST_OF_ENTERED_PORTAL.color());
                    return false;
                }
            }
            player.teleport(secondEnd.getSecondEndWorld().getSpawnLocation());
            player.sendMessage(SELang.TELEPORT_TO_END.color());
            return true;
        }
        if (args[0].equalsIgnoreCase("reset")) {
            if (!sender.hasPermission("secondend.reset")) {
                sender.sendMessage(SELang.MISSING_PERMISSION.color());

                return false;
            }
            sender.sendMessage(SELang.RESETTING_END.color());
            secondEnd.run();
        }
        if (args[0].equalsIgnoreCase("reload")) {
            if (!sender.hasPermission("secondend.reload")) {
                sender.sendMessage(SELang.MISSING_PERMISSION.color());
                return false;
            }
            sender.sendMessage(SELang.RELOADING_PLUGIN.color());
            secondEnd.reload();
        }
        return true;
    }


    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        List<String> strings = new ArrayList<>();
        if (sender.hasPermission("secondend.reset")) {
            strings.add("reset");
        }
        if (sender.hasPermission("secondend.reload")) {
            strings.add("reload");
        }
        return strings;
    }
}
