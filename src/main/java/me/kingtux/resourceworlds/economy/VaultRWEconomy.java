package me.kingtux.resourceworlds.economy;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.UnknownDependencyException;

public class VaultRWEconomy implements RWEconomy {
    private final Economy economy;

    public VaultRWEconomy(Economy economy) {
        this.economy = economy;
    }

    public VaultRWEconomy() {
        RegisteredServiceProvider<Economy> rsp = Bukkit.getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            throw new UnknownDependencyException("No Vault Econ implementation found.");
        }
        economy = rsp.getProvider();

    }

    @Override
    public void withdrawPlayer(OfflinePlayer offlinePlayer, double amount) {
        economy.withdrawPlayer(offlinePlayer, amount);
    }

    @Override
    public double getBalance(OfflinePlayer player) {
        return economy.getBalance(player);
    }
}
