package me.kingtux.resourceworlds.requirements;

import me.kingtux.resourceworlds.Locale;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.advancement.Advancement;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.Map;

public class AdvancementRequirement implements RWRequirement {
    private Advancement advancement;

    public AdvancementRequirement(Advancement advancement) {
        this.advancement = advancement;
    }

    public AdvancementRequirement(ConfigurationSection section) {
        advancement = Bukkit.getAdvancement(NamespacedKey.minecraft(section.getString("advancement")));
    }

    @Override
    public boolean hasPlayerCompletedRequirement(Player player) {

        boolean done = player.getAdvancementProgress(advancement).isDone();
        if (!done) {
            player.sendMessage(Locale.MUST_OF_ALREADY_COMPLETED_ADVANCEMENT.colorAndSubstitute(Map.of("advancement", advancement.getKey().getKey())));
        }
        return done;
    }
}
