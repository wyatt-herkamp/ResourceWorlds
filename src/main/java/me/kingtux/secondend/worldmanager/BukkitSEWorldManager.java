package me.kingtux.secondend.worldmanager;

import me.kingtux.secondend.SecondEnd;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.util.FileUtil;

import java.io.File;

public class BukkitSEWorldManager implements SEWorldManager {
    private final SecondEnd secondEnd;

    public BukkitSEWorldManager(SecondEnd secondEnd) {
        this.secondEnd = secondEnd;
    }

    @Override
    public boolean worldExists(String worldName) {
        return secondEnd.getServer().getWorld(worldName) != null;
    }

    @Override
    public World getWorld(String worldName) {
        return secondEnd.getServer().getWorld(worldName);
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
        creator.generator(generator);
        createWorld(creator);
    }

    @Override
    public void createWorld(WorldCreator creator) {
        secondEnd.getServer().createWorld(creator);
    }

    @Override
    public void deleteWorld(String worldName) {
        World world = getWorld(worldName);
        if (world == null) return;
        File worldFolder = world.getWorldFolder();
        secondEnd.getServer().unloadWorld(world, false);
        SEUtils.deleteFolder(worldFolder);
    }
}
