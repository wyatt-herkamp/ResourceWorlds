package me.kingtux.resourceworlds;

import me.kingtux.enumconfig.EnumConfig;
import me.kingtux.enumconfig.annotations.ConfigEntry;
import me.kingtux.enumconfig.annotations.ConfigValue;
import org.apache.commons.text.StringSubstitutor;
import org.bukkit.ChatColor;

import java.util.Map;

//https://commons.apache.org/proper/commons-text/javadocs/api-release/org/apache/commons/text/StringSubstitutor.html
//Variables ${var}
public enum Locale implements EnumConfig {
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
    MUST_OF_ENTERED_PORTAL("you must of already found and entered the end portal"),
    @ConfigEntry
    INVALID_WORLD("Invalid World"),
    @ConfigEntry
    LACK_FUNDS("You lack funds to do that"),
    @ConfigEntry
    INVALID_COMMAND("Invalid Command");

    @ConfigValue
    private String value;

    Locale(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public String color() {
        return ChatColor.translateAlternateColorCodes('&', getValue());
    }

    public String colorAndSubstitute(Map<String, String> values) {
        return ChatColor.translateAlternateColorCodes('&', getValueAndSubstitute(values));
    }

    private String getValueAndSubstitute(Map<String, String> values) {
        return StringSubstitutor.replace(getValue(), values);
    }

    public void setValue(String value) {
        this.value = value;
    }
}