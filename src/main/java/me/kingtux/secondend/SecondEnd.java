package me.kingtux.secondend;

import com.onarandombox.MultiverseCore.MVWorld;
import com.onarandombox.MultiverseCore.MultiverseCore;
import com.onarandombox.MultiverseCore.api.MultiverseWorld;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldType;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.UnknownDependencyException;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

public final class SecondEnd extends JavaPlugin implements Runnable {
    private MultiverseCore multiverseCore;
    private Random random = new Random();
    private int task;

    @Override
    public void onEnable() {
        try {
            multiverseCore = getMVCoreInstance();
        } catch (UnknownDependencyException e) {
            e.printStackTrace();
            Bukkit.getPluginManager().disablePlugin(this);
        }
        saveDefaultConfig();
        loadPlugin();
        // Plugin startup logic
        SecondEndCommand endCommand = new SecondEndCommand(this);

        Bukkit.getPluginCommand("secondend").setExecutor(endCommand);
        Bukkit.getPluginCommand("secondend").setTabCompleter(endCommand);
    }

    private void loadPlugin() {
        task = Bukkit.getScheduler().runTaskTimer(this, this, 0, getConfig().getInt("reset-time") * 20).getTaskId();
    }

    private void closePlugin() {
        Bukkit.getScheduler().cancelTask(task);
    }

    @Override
    public void onDisable() {
        closePlugin();
        String endName = getConfig().getString("end-name");

        multiverseCore.getMVWorldManager().deleteWorld(endName);

        // Plugin shutdown logic
    }

    public MultiverseCore getMVCoreInstance() {
        Plugin plugin = getServer().getPluginManager().getPlugin("Multiverse-Core");

        if (plugin instanceof MultiverseCore) {
            return (MultiverseCore) plugin;
        }
        throw new UnknownDependencyException("Multiverse-Core");
    }


    @Override
    public void run() {
        String endName = getConfig().getString("end-name");
        Optional<MultiverseWorld> first = multiverseCore.getMVWorldManager().getMVWorlds().stream().filter(multiverseWorld -> multiverseWorld.getName().equals(endName)).findFirst();
        if (first.isPresent()) {
            MultiverseWorld multiverseWorld = first.get();
            Bukkit.broadcastMessage("Resetting End!");
            List<Player> playerList = Bukkit.getOnlinePlayers().stream().filter(onlinePlayer -> onlinePlayer.getWorld() == multiverseWorld.getCBWorld()).collect(Collectors.toList());
            for (Player player : playerList) {
                Location returnWorld = Bukkit.getWorld(getConfig().getString("return-world", "world")).getSpawnLocation();
                player.teleport(returnWorld);

            }
            multiverseCore.getMVWorldManager().deleteWorld(endName);
        }
        String endSeed = getConfig().getString("end-seed");
        if (endSeed.isEmpty()) {
            endSeed = String.valueOf(random.nextLong());
        }
        multiverseCore.getMVWorldManager().addWorld(endName, World.Environment.THE_END, endSeed, WorldType.NORMAL, true, null);
    }

    public MultiverseCore getMultiverseCore() {
        return multiverseCore;
    }

    public MultiverseWorld getSecondEndWorld() {
        String endName = getConfig().getString("end-name");
        Optional<MultiverseWorld> first = multiverseCore.getMVWorldManager().getMVWorlds().stream().filter(multiverseWorld -> multiverseWorld.getName().equals(endName)).findFirst();
        return first.get();
    }
}
