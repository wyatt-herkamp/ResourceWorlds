package me.kingtux.resourceworlds;

import me.kingtux.resourceworlds.requirements.RWRequirement;

import java.util.ArrayList;
import java.util.List;

public class ResourceWorldBuilder {
    private String name;
    private int resetTime;
    private List<RWRequirement> requirementsList;
    private long seed;
    private int cost;

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

    public ResourceWorld createResourceWorld() {
        return new ResourceWorld(name, resetTime, requirementsList, seed, cost);
    }
}