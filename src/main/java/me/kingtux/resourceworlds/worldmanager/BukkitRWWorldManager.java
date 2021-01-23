package me.kingtux.resourceworlds.worldmanager;

import me.kingtux.resourceworlds.RWUtils;
import me.kingtux.resourceworlds.ResourceWorlds;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;

import java.io.File;

public class BukkitRWWorldManager implements RWWorldManager {
    private final ResourceWorlds resourceWorlds;

    public BukkitRWWorldManager(ResourceWorlds resourceWorlds) {
        this.resourceWorlds = resourceWorlds;
    }

    @Override
    public boolean worldExists(String worldName) {
        return resourceWorlds.getServer().getWorld(worldName) != null;
    }

    @Override
    public World getWorld(String worldName) {
        return resourceWorlds.getServer().getWorld(worldName);
    }

    @Override
    public void createWorld(String worldName, World.Environment environment,
                            String seed, WorldType worldType, boolean structures, String generator) {
        WorldCreator creator = new WorldCreator(worldName);
        creator.environment(environment);
        long longSeed;
        try {
            longSeed = Long.parseLong(seed);
        } catch (NumberFormatException numberformatexception) {
            longSeed = seed.hashCode();
        }
        creator.seed(longSeed);
        creator.type(worldType);
        creator.generateStructures(structures);
        if(!generator.isEmpty() || !generator.isBlank()){
            creator.generator(generator);
        }
        createWorld(creator);
    }

    @Override
    public void createWorld(WorldCreator creator) {
        resourceWorlds.getServer().createWorld(creator);
    }

    @Override
    public void deleteWorld(String worldName) {
        World world = getWorld(worldName);
        if (world == null) return;
        File worldFolder = world.getWorldFolder();
        resourceWorlds.getServer().unloadWorld(world, false);
        RWUtils.deleteFolder(worldFolder);
    }

    @Override
    public String getName() {
        return "Bukkit";
    }
}
