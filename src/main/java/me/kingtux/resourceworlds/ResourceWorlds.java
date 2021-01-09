package me.kingtux.resourceworlds;

import me.kingtux.enumconfig.BukkitYamlHandler;
import me.kingtux.enumconfig.EnumConfigLoader;
import me.kingtux.resourceworlds.commands.ResourceWorldCommand;
import me.kingtux.resourceworlds.economy.RWEconomy;
import me.kingtux.resourceworlds.economy.VaultRWEconomy;
import me.kingtux.resourceworlds.worldmanager.BukkitRWWorldManager;
import me.kingtux.resourceworlds.worldmanager.MultiverseRWWorldManager;
import me.kingtux.resourceworlds.worldmanager.RWWorldManager;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldType;
import org.bukkit.entity.Player;
import org.bukkit.plugin.UnknownDependencyException;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Random;

public final class ResourceWorlds extends JavaPlugin implements Runnable {
    private RWWorldManager RWWorldManager;
    private final Random random = new Random();
    private int task;
    private RWEconomy rwEconomy;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        if (getConfig().getBoolean("force-bukkit-api", false)) {
            RWWorldManager = new BukkitRWWorldManager(this);
        } else {

            try {
                Class.forName("com.onarandombox.MultiverseCore.MultiverseCore");
                RWWorldManager = new MultiverseRWWorldManager(this);
            } catch (ClassNotFoundException e) {
                RWWorldManager = new BukkitRWWorldManager(this);
            } catch (UnknownDependencyException e) {
                System.out.println("Multiverse not found! Defaulting to Bukkit world manager.");
                RWWorldManager = new BukkitRWWorldManager(this);
            }
        }
        if (Bukkit.getPluginManager().isPluginEnabled("Vault")) {
            try {
                rwEconomy = new VaultRWEconomy();
            } catch (Exception e) {
                rwEconomy = null;
                getLogger().warning("Vault was unable to initialize. Disabling Econ feature.");
                if (getConfig().getBoolean("debug-mode", false)) {
                    e.printStackTrace();
                }
            }
        }


        loadPlugin();
        ResourceWorldCommand resourceWorldCommand = new ResourceWorldCommand(this);

        Bukkit.getPluginCommand("resourceworlds").setExecutor(resourceWorldCommand);
        Bukkit.getPluginCommand("resourceworlds").setTabCompleter(resourceWorldCommand);
        new Metrics(this, 9932);
    }

    private void loadPlugin() {
        reloadConfig();
        int time = getConfig().getInt("reset-time") * 20;
        boolean regenOnStart = getConfig().getBoolean("regen-on-start");
        if (regenOnStart) {
            //If it is set to regenOnStart delete it
            deleteWorld();
        }
        if (!RWWorldManager.worldExists(getConfig().getString("end-name"))) {
            createWorld();
        }

        task = Bukkit.getScheduler().runTaskTimer(this, this, time, time).getTaskId();
        BukkitYamlHandler yamlHandler = new BukkitYamlHandler(new File(getDataFolder(), "lang.yml"));
        EnumConfigLoader.loadLang(yamlHandler, Locale.class, true);
    }

    public void deleteWorld() {
        String endName = getConfig().getString("end-name");
        if (RWWorldManager.worldExists(endName)) {
            Bukkit.broadcastMessage(Locale.RESETTING_END_ANNOUNCEMENT.color());
            World world = RWWorldManager.getWorld(endName);
            String returnWorldName = getConfig().getString("return-world", "world");
            if (returnWorldName == null) return;
            World returnWorld = Bukkit.getWorld(returnWorldName);
            if (returnWorld == null) return;
            for (Player player : world.getPlayers()) player.teleport(returnWorld.getSpawnLocation());
            RWWorldManager.deleteWorld(endName);
        }
    }

    public World getSecondEndWorld() {
        String endName = getConfig().getString("end-name");
        return RWWorldManager.getWorld(endName);
    }

    public void createWorld() {
        String endName = getConfig().getString("end-name");

        String endSeed = getConfig().getString("end-seed");
        if (endSeed == null || endSeed.isEmpty()) {
            endSeed = String.valueOf(random.nextLong());
        }

        RWWorldManager.createWorld(endName, World.Environment.THE_END, endSeed, WorldType.NORMAL, true, null);
    }

    private void closePlugin() {
        Bukkit.getScheduler().cancelTask(task);
        String endName = getConfig().getString("end-name");
    }

    @Override
    public void onDisable() {
        closePlugin();
    }

    @Override
    public void run() {
        String endName = getConfig().getString("end-name");
        World world = RWWorldManager.getWorld(endName);
        if (world != null) {
            deleteWorld();
        }
        createWorld();
    }

    public void reload() {
        closePlugin();
        loadPlugin();
    }
}
