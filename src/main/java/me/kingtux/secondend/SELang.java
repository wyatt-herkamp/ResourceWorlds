package me.kingtux.secondend;

import me.kingtux.enumconfig.EnumConfig;
import me.kingtux.enumconfig.annotations.ConfigEntry;
import me.kingtux.enumconfig.annotations.ConfigValue;
import org.bukkit.ChatColor;

public enum SELang implements EnumConfig {
    @ConfigEntry
    MISSING_PERMISSION(""),
    @ConfigEntry
    MUST_BE_PLAYER(""),
    @ConfigEntry
    RESETTING_END_ANNOUNCEMENT(""),
    @ConfigEntry
    TELEPORT_TO_END(""),
    @ConfigEntry
    RESETTING_END(""),
    @ConfigEntry
    RELOADING_PLUGIN(""),
    @ConfigEntry
    MUST_OF_ENTERED_PORTAL("");

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