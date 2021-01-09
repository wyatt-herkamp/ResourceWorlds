package me.kingtux.resourceworlds.requirements;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.advancement.Advancement;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

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
        return player.getAdvancementProgress(advancement).isDone();
    }
}
