package me.kingtux.secondend.worldmanager;

import org.bukkit.World;
import org.bukkit.WorldType;

public interface WorldManager {
    boolean worldExists(String worldName);
    World getWorld(String worldName);
    void createWorld(String worldName, World.Environment environment, String seed, WorldType worldType, boolean structures, String generator);
    void deleteWorld(String worldName);
}
