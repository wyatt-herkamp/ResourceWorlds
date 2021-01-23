package me.kingtux.resourceworlds;

import me.kingtux.resourceworlds.requirements.RWRequirement;
import org.bukkit.World;
import org.bukkit.WorldType;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.List;

public class ResourceWorldBuilder {
    private String name;
    private int resetTime;
    private List<RWRequirement> requirementsList = new ArrayList<>();
    private long seed = 0;
    private int cost = 0;
    private boolean regenOnStart = false;
    private boolean generateStructures = true;
    private WorldType worldType;
    private World.Environment environment;
    private String generator = null;
    private ConfigurationSection propertiesSection;

    public ResourceWorldBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public ResourceWorldBuilder setResetTime(int resetTime) {
        this.resetTime = resetTime;
        return this;
    }

    public ResourceWorldBuilder setRequirementsList(List<RWRequirement> requirementsList) {
        this.requirementsList = requirementsList;
        return this;
    }

    public ResourceWorldBuilder addRequirement(RWRequirement requirement) {
        if (requirementsList == null) requirementsList = new ArrayList<>();
        requirementsList.add(requirement);
        return this;
    }

    public ResourceWorldBuilder setSeed(long seed) {
        this.seed = seed;
        return this;
    }

    public ResourceWorldBuilder setCost(int cost) {
        this.cost = cost;
        return this;
    }

    public ResourceWorldBuilder setRegenOnStart(boolean regenOnStart) {
        this.regenOnStart = regenOnStart;
        return this;
    }

    public ResourceWorldBuilder setGenerateStructures(boolean generateStructures) {
        this.generateStructures = generateStructures;
        return this;
    }

    public ResourceWorldBuilder setWorldType(WorldType worldType) {
        this.worldType = worldType;
        return this;
    }

    public ResourceWorldBuilder setEnvironment(World.Environment environment) {
        this.environment = environment;
        return this;
    }

    public ResourceWorldBuilder setProperties(ConfigurationSection configurationSection) {
        this.propertiesSection = configurationSection;
        return this;
    }

    public ResourceWorldBuilder setGenerator(String generator) {
        if (generator.isEmpty() || generator.isBlank()) {
            generator = null;
        } else
            this.generator = generator;
        return this;
    }

    public ResourceWorld createResourceWorld() {
        return new ResourceWorld(name, resetTime, requirementsList, seed, cost, regenOnStart, generateStructures, worldType, environment, generator, propertiesSection);
    }
}