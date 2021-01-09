package me.kingtux.resourceworlds.requirements;

import org.bukkit.advancement.Advancement;
import org.bukkit.entity.Player;

public class AdvancementRequirement implements RWRequirement {
    private Advancement advancement;

    public AdvancementRequirement(Advancement advancement) {
        this.advancement = advancement;
    }

    @Override
    public boolean hasPlayerCompletedRequirement(Player player) {
        return player.getAdvancementProgress(advancement).isDone();
    }
}
