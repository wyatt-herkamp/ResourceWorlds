package me.kingtux.resourceworlds.worldmanager;

import com.onarandombox.MultiverseCore.MultiverseCore;
import com.onarandombox.MultiverseCore.api.MVWorldManager;
import com.onarandombox.MultiverseCore.api.MultiverseWorld;
import me.kingtux.resourceworlds.ResourceWorlds;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.UnknownDependencyException;

public class MultiverseRWWorldManager implements RWWorldManager {
    private final MVWorldManager mvWorldManager;
    private final ResourceWorlds resourceWorlds;

    public MultiverseRWWorldManager(ResourceWorlds resourceWorlds) {
        this.resourceWorlds = resourceWorlds;
        this.mvWorldManager = getMVCoreInstance().getMVWorldManager();
    }

    public MultiverseCore getMVCoreInstance() {
        Plugin plugin = resourceWorlds.getServer().getPluginManager().getPlugin("Multiverse-Core");
        if (plugin instanceof MultiverseCore) return (MultiverseCore) plugin;

        throw new UnknownDependencyException("Multiverse-Core");
    }

    @Override
    public boolean worldExists(String worldName) {
        return mvWorldManager.getMVWorlds().stream().anyMatch(world -> world.getName().equals(worldName));
    }

    @Override
    public World getWorld(String worldName) {
        MultiverseWorld mv = mvWorldManager.getMVWorlds().stream().filter(world -> world.getName().equals(worldName))
                .findFirst()
                .orElse(null);
        if (mv == null) return null;
        return mv.getCBWorld();
    }

    @Override
    public void createWorld(String worldName, World.Environment environment,
                            String seed, WorldType worldType, boolean structures, String generator) {
        mvWorldManager.addWorld(worldName, environment, seed, worldType, structures, generator);
    }

    @Override
    public void createWorld(WorldCreator creator) {
        mvWorldManager.addWorld(creator.name(), creator.environment(), String.valueOf(creator.seed()), creator.type(), creator.generateStructures(), creator.generatorSettings());

    }

    @Override
    public void deleteWorld(String worldName) {
        if (worldExists(worldName)) mvWorldManager.deleteWorld(worldName);
    }
}
