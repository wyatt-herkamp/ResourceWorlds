package me.kingtux.resourceworlds;

import me.kingtux.enumconfig.BukkitYamlHandler;
import me.kingtux.enumconfig.EnumConfigLoader;
import me.kingtux.resourceworlds.commands.ResourceWorldCommand;
import me.kingtux.resourceworlds.economy.RWEconomy;
import me.kingtux.resourceworlds.economy.VaultRWEconomy;
import me.kingtux.resourceworlds.requirements.RWRequirement;
import me.kingtux.resourceworlds.requirements.RequirementUtils;
import me.kingtux.resourceworlds.worldmanager.BukkitRWWorldManager;
import me.kingtux.resourceworlds.worldmanager.MultiverseRWWorldManager;
import me.kingtux.resourceworlds.worldmanager.RWWorldManager;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldType;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.UnknownDependencyException;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public final class ResourceWorlds extends JavaPlugin implements Runnable {
    private RWWorldManager rwWorldManager;
    private final Random random = new Random();
    private int task;
    private RWEconomy rwEconomy;
    private List<ResourceWorld> resourceWorlds;
    private static ResourceWorlds instance;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        if (getConfig().getBoolean("force-bukkit-api", false)) {
            rwWorldManager = new BukkitRWWorldManager(this);
        } else {

            try {
                Class.forName("com.onarandombox.MultiverseCore.MultiverseCore");
                rwWorldManager = new MultiverseRWWorldManager(this);
            } catch (ClassNotFoundException e) {
                rwWorldManager = new BukkitRWWorldManager(this);
            } catch (UnknownDependencyException e) {
                System.out.println("Multiverse not found! Defaulting to Bukkit world manager.");
                rwWorldManager = new BukkitRWWorldManager(this);
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
        resourceWorlds = new ArrayList<>();

        ConfigurationSection worlds = getConfig().getConfigurationSection("worlds");
        if (worlds == null) {
            getLogger().warning("No Worlds Found");
            Bukkit.getPluginManager().disablePlugin(this);
        }
        for (String key : worlds.getKeys(false)) {
            ConfigurationSection configurationSection = worlds.getConfigurationSection(key);
            if (configurationSection == null) {
                //WATT
                continue;
            }
            resourceWorlds.add(loadWorld(configurationSection));
        }
        task = 0;// = Bukkit.getScheduler().runTaskTimer(this, this, time, time).getTaskId();
        BukkitYamlHandler yamlHandler = new BukkitYamlHandler(new File(getDataFolder(), "lang.yml"));
        EnumConfigLoader.loadLang(yamlHandler, Locale.class, true);
    }

    private ResourceWorld loadWorld(ConfigurationSection world) {
        ResourceWorldBuilder builder = new ResourceWorldBuilder();
        //Strings
        builder.setName(world.getString("name"));
        builder.setGenerator(world.getString("generator", ""));
        //Bukkit Properties
        builder.setWorldType(WorldType.valueOf(world.getString("worldType")));
        builder.setEnvironment(World.Environment.valueOf(world.getString("environment")));
        //Booleans
        builder.setRegenOnStart(world.getBoolean("regenOnStart", false));
        builder.setGenerateStructures(world.getBoolean("generateStructures", true));
        //Ints
        builder.setSeed(world.getLong("seed", 0));
        builder.setResetTime(world.getInt("reset-time", 86400));
        builder.setCost(world.getInt("cost", 0));
        //Requirements
        ConfigurationSection requirements = world.getConfigurationSection("requirements");
        for (String key : requirements.getKeys(false)) {
            ConfigurationSection requirement = requirements.getConfigurationSection(key);
            RWRequirement requirement1 = RequirementUtils.buildRequirement(requirement);
            if (requirement1 == null) {
                //TODO do something better.
                continue;
            }
            builder.addRequirement(requirement1);
        }
        return builder.createResourceWorld();
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
//TODO implement runMethod
    }

    public void reload() {
        closePlugin();
        loadPlugin();
    }

    public static ResourceWorlds getInstance() {
        return instance;
    }
}
