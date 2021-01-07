package me.kingtux.secondend;

import me.kingtux.enumconfig.EnumConfig;
import me.kingtux.enumconfig.annotations.ConfigEntry;
import me.kingtux.enumconfig.annotations.ConfigValue;
import org.bukkit.ChatColor;

public enum SELang implements EnumConfig {
    @ConfigEntry
    MISSING_PERMISSION("You lack the permission to do that"),
    @ConfigEntry
    MUST_BE_PLAYER("You must be a player"),
    @ConfigEntry
    RESETTING_END_ANNOUNCEMENT("Resetting the end"),
    @ConfigEntry
    TELEPORT_TO_END("Teleported you to the end"),
    @ConfigEntry
    RESETTING_END("Forcing a end reset"),
    @ConfigEntry
    RELOADING_PLUGIN("Reloading the plugin"),
    @ConfigEntry
    MUST_OF_ENTERED_PORTAL("you must of already found and entered the end portal");

    @ConfigValue
    private String value;

    SELang(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public String color() {
        return ChatColor.translateAlternateColorCodes('&', getValue());
    }

    public void setValue(String value) {
        this.value = value;
    }
}