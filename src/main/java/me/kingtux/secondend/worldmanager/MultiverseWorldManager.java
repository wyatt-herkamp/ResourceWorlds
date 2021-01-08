package me.kingtux.secondend.worldmanager;

import com.onarandombox.MultiverseCore.MultiverseCore;
import com.onarandombox.MultiverseCore.api.MVWorldManager;
import com.onarandombox.MultiverseCore.api.MultiverseWorld;
import me.kingtux.secondend.SecondEnd;
import org.bukkit.World;
import org.bukkit.WorldType;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.UnknownDependencyException;

public class MultiverseWorldManager implements WorldManager {
    private final MVWorldManager mvWorldManager;
    private final SecondEnd secondEnd;

    public MultiverseWorldManager(SecondEnd secondEnd) {
        this.secondEnd = secondEnd;
        this.mvWorldManager = getMVCoreInstance().getMVWorldManager();
    }

    public MultiverseCore getMVCoreInstance() {
        Plugin plugin = secondEnd.getServer().getPluginManager().getPlugin("Multiverse-Core");
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
    public void deleteWorld(String worldName) {
        if (worldExists(worldName)) mvWorldManager.deleteWorld(worldName);
    }
}
