package me.kingtux.resourceworlds.worldmanager;

import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;

/**
 * The World Manager for SecondEnd
 */
public interface RWWorldManager {
    /**
     * Does world exist.
     *
     * @param worldName the WorldName
     * @return the status of the world
     */
    boolean worldExists(String worldName);

    /**
     * Get the World
     *
     * @param worldName the world name
     * @return the world.
     */
    World getWorld(String worldName);

    void createWorld(String worldName, World.Environment environment, String seed, WorldType worldType, boolean structures, String generator);

    /**
     * Creates a World using the WorldCreator
     *
     * @param creator creator
     */
    void createWorld(WorldCreator creator);

    /**
     * Delete the world
     *
     * @param worldName the world name
     */
    void deleteWorld(String worldName);

    String getName();
}
