package me.kingtux.secondend.worldmanager;

import com.onarandombox.MultiverseCore.utils.FileUtils;
import me.kingtux.secondend.SecondEnd;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;

import java.io.File;

public class BukkitWorldManager implements WorldManager {
    private final SecondEnd secondEnd;

    public BukkitWorldManager(SecondEnd secondEnd) {
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
        secondEnd.getServer().createWorld(creator);
    }

    @Override
    public void deleteWorld(String worldName) {
        World world = getWorld(worldName);
        if (world == null) return;
        File worldFolder = world.getWorldFolder();
        secondEnd.getServer().unloadWorld(world, false);
        FileUtils.deleteFolder(worldFolder);
    }
}
