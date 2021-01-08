package me.kingtux.secondend;

import com.onarandombox.MultiverseCore.MultiverseCore;
import com.onarandombox.MultiverseCore.api.MultiverseWorld;
import me.kingtux.enumconfig.BukkitYamlHandler;
import me.kingtux.enumconfig.EnumConfigLoader;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldType;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.UnknownDependencyException;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

public final class SecondEnd extends JavaPlugin implements Runnable {
    private MultiverseCore multiverseCore;
    private Random random = new Random();
    private int task;
    private BukkitYamlHandler yamlHandler;

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
        SecondEndCommand endCommand = new SecondEndCommand(this);

        Bukkit.getPluginCommand("secondend").setExecutor(endCommand);
        Bukkit.getPluginCommand("secondend").setTabCompleter(endCommand);
        Metrics metrics = new Metrics(this, 9932);
    }

    private void loadPlugin() {
        reloadConfig();
        int time = getConfig().getInt("reset-time") * 20;
        boolean regenOnStart = getConfig().getBoolean("regen-on-start");
        if (regenOnStart) {
            //If it is set to regenOnStart delete it
            deleteWorld();
        }
        if (!doesWorldExist()) {
            createWorld();
        }

        task = Bukkit.getScheduler().runTaskTimer(this, this, time, time).getTaskId();
        yamlHandler = new BukkitYamlHandler(new File(getDataFolder(), "lang.yml"));
        EnumConfigLoader.loadLang(yamlHandler, SELang.class, true);
    }

    public boolean doesWorldExist() {
        String endName = getConfig().getString("end-name");
        Optional<MultiverseWorld> first = multiverseCore.getMVWorldManager().getMVWorlds().stream().filter(multiverseWorld -> multiverseWorld.getName().equals(endName)).findFirst();
        return first.isPresent();
    }

    public void deleteWorld() {
        String endName = getConfig().getString("end-name");
        Optional<MultiverseWorld> first = multiverseCore.getMVWorldManager().getMVWorlds().stream().filter(multiverseWorld -> multiverseWorld.getName().equals(endName)).findFirst();
        if (first.isPresent()) {
            MultiverseWorld multiverseWorld = first.get();
            Bukkit.broadcastMessage(SELang.RESETTING_END_ANNOUNCEMENT.color());
            List<Player> playerList = Bukkit.getOnlinePlayers().stream().filter(onlinePlayer -> onlinePlayer.getWorld() == multiverseWorld.getCBWorld()).collect(Collectors.toList());
            for (Player player : playerList) {
                Location returnWorld = Bukkit.getWorld(getConfig().getString("return-world", "world")).getSpawnLocation();
                player.teleport(returnWorld);

            }
            multiverseCore.getMVWorldManager().deleteWorld(endName);
        }
    }

    public void createWorld() {
        String endName = getConfig().getString("end-name");

        String endSeed = getConfig().getString("end-seed");
        if (endSeed.isEmpty()) {
            endSeed = String.valueOf(random.nextLong());
        }
        multiverseCore.getMVWorldManager().addWorld(endName, World.Environment.THE_END, endSeed, WorldType.NORMAL, true, null);

    }

    private void closePlugin() {
        Bukkit.getScheduler().cancelTask(task);
        String endName = getConfig().getString("end-name");


    }

    @Override
    public void onDisable() {
        closePlugin();
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
            deleteWorld();
        }
        createWorld();
    }

    public MultiverseCore getMultiverseCore() {
        return multiverseCore;
    }

    public MultiverseWorld getSecondEndWorld() {
        String endName = getConfig().getString("end-name");
        Optional<MultiverseWorld> first = multiverseCore.getMVWorldManager().getMVWorlds().stream().filter(multiverseWorld -> multiverseWorld.getName().equals(endName)).findFirst();
        return first.get();
    }

    public void reload() {
        closePlugin();
        loadPlugin();
    }
}
