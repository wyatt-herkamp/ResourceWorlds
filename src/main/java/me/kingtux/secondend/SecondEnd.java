package me.kingtux.secondend;

import me.kingtux.enumconfig.BukkitYamlHandler;
import me.kingtux.enumconfig.EnumConfigLoader;
import me.kingtux.secondend.worldmanager.BukkitWorldManager;
import me.kingtux.secondend.worldmanager.MultiverseWorldManager;
import me.kingtux.secondend.worldmanager.WorldManager;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldType;
import org.bukkit.entity.Player;
import org.bukkit.plugin.UnknownDependencyException;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Random;

public final class SecondEnd extends JavaPlugin implements Runnable {
    private WorldManager worldManager;
    private final Random random = new Random();
    private int task;

    @Override
    public void onEnable() {
        try {
            Class.forName("com.onarandombox.MultiverseCore.MultiverseCore");
            worldManager = new MultiverseWorldManager(this);
        } catch (ClassNotFoundException e) {
            worldManager = new BukkitWorldManager(this);
        } catch (UnknownDependencyException e) {
            System.out.println("Multiverse not found! Defaulting to Bukkit world manager.");
            worldManager = new BukkitWorldManager(this);
        }

        saveDefaultConfig();
        loadPlugin();
        SecondEndCommand endCommand = new SecondEndCommand(this);

        Bukkit.getPluginCommand("secondend").setExecutor(endCommand);
        Bukkit.getPluginCommand("secondend").setTabCompleter(endCommand);
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
        if (!worldManager.worldExists(getConfig().getString("end-name"))) {
            createWorld();
        }

        task = Bukkit.getScheduler().runTaskTimer(this, this, time, time).getTaskId();
        BukkitYamlHandler yamlHandler = new BukkitYamlHandler(new File(getDataFolder(), "lang.yml"));
        EnumConfigLoader.loadLang(yamlHandler, SELang.class, true);
    }

    public void deleteWorld() {
        String endName = getConfig().getString("end-name");
        if (worldManager.worldExists(endName)) {
            Bukkit.broadcastMessage(SELang.RESETTING_END_ANNOUNCEMENT.color());
            World world = worldManager.getWorld(endName);
            String returnWorldName = getConfig().getString("return-world", "world");
            if (returnWorldName == null) return;
            World returnWorld = Bukkit.getWorld(returnWorldName);
            if (returnWorld == null) return;
            for (Player player : world.getPlayers()) player.teleport(returnWorld.getSpawnLocation());
            worldManager.deleteWorld(endName);
        }
    }

    public World getSecondEndWorld() {
        String endName = getConfig().getString("end-name");
        return worldManager.getWorld(endName);
    }

    public void createWorld() {
        String endName = getConfig().getString("end-name");

        String endSeed = getConfig().getString("end-seed");
        if (endSeed == null || endSeed.isEmpty()) {
            endSeed = String.valueOf(random.nextLong());
        }

        worldManager.createWorld(endName, World.Environment.THE_END, endSeed, WorldType.NORMAL, true, null);
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
        World world = worldManager.getWorld(endName);
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
