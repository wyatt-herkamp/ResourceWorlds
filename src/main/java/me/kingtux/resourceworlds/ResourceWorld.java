package me.kingtux.resourceworlds;

import me.kingtux.resourceworlds.requirements.RWRequirement;

import java.util.List;

public class ResourceWorld {
    private final String name;
    private final int resetTime;
    private final List<RWRequirement> requirementsList;
    private final long seed;
    private final int cost;

    public ResourceWorld(String name, int resetTime, List<RWRequirement> requirementsList, long seed, int cost) {
        this.name = name;
        this.resetTime = resetTime;
        this.requirementsList = requirementsList;
        this.seed = seed;
        this.cost = cost;
    }

    public String getName() {
        return name;
    }

    public int getResetTime() {
        return resetTime;
    }

    public List<RWRequirement> getRequirementsList() {
        return requirementsList;
    }

    public long getSeed() {
        return seed;
    }

    public int getCost() {
        return cost;
    }
}
