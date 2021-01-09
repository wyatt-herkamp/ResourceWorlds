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
    /**
     * Valid Params: permission
     */
    @ConfigEntry
    MISSING_PERMISSION("You lack the permission to do that"),
    @ConfigEntry
    MUST_BE_PLAYER("You must be a player"),
    @ConfigEntry
    RELOADING_PLUGIN("Reloading the plugin"),
    /**
     * Valid Params: advancement
     */
    @ConfigEntry
    MUST_OF_ALREADY_COMPLETED_ADVANCEMENT("You must of already completed achievement ${advancement}"),
    /**
     * Valid params: world
     */
    @ConfigEntry
    RESETTING_WORLD("Resetting world: ${world}"),
    @ConfigEntry
    INVALID_WORLD("Invalid World"),
    /**
     * Valid Params: cost
     */
    @ConfigEntry
    LACK_FUNDS("You lack funds to do that"),
    @ConfigEntry
    INVALID_COMMAND("Invalid Command"),
    /**
     * Valid Params: hint
     */
    @ConfigEntry
    INVALID_COMMAND_WITH_HINT("Invalid Command Try: ${hint}");

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