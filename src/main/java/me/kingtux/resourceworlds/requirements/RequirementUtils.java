package me.kingtux.resourceworlds.requirements;

import me.kingtux.resourceworlds.ResourceWorlds;
import org.bukkit.configuration.ConfigurationSection;

import java.lang.reflect.InvocationTargetException;

public class RequirementUtils {
    /**
     * Just some ugly ass code. Please Ignore
     *
     * @param requirement the requirement
     * @return the built Requirement;
     */
    public static RWRequirement buildRequirement(ConfigurationSection requirement) {
        ResourceWorlds instance = ResourceWorlds.getInstance();
        ClassLoader classLoader = instance.getClass().getClassLoader();
        String aClass = requirement.getString("class");
        try {
            Class<?> clazz = Class.forName(aClass, false, classLoader);
            return (RWRequirement) clazz.getConstructor(ConfigurationSection.class).newInstance(requirement);
        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }
}
