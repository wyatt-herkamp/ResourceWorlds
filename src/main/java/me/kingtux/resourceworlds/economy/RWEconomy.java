package me.kingtux.resourceworlds.economy;

import org.bukkit.OfflinePlayer;

public interface RWEconomy {

    void withdrawPlayer(OfflinePlayer offlinePlayer, double amount);
}
