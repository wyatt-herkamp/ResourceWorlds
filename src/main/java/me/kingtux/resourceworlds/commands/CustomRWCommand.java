package me.kingtux.resourceworlds.commands;

import me.kingtux.resourceworlds.Locale;
import me.kingtux.resourceworlds.RWUtils;
import me.kingtux.resourceworlds.ResourceWorld;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class CustomRWCommand extends BukkitCommand {
    private final ResourceWorld resourceWorld;

    public CustomRWCommand(@NotNull ConfigurationSection command, ResourceWorld resourceWorld) {
        super(Objects.requireNonNull(command.getString("command")), Objects.requireNonNull(command.getString("description", "")), "/" + command.getString("command"), command.getStringList("aliases"));
        this.resourceWorld = resourceWorld;
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Locale.MUST_BE_PLAYER.color());
            return false;
        }
        RWUtils.teleportToWorld(((Player) sender), resourceWorld);
        return true;
    }

    @NotNull
    @Override
    public List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args) throws IllegalArgumentException {
        return Collections.emptyList();
    }
}
