package net.toshimichi.dungeons.misc;

import java.util.UUID;

import static org.bukkit.Bukkit.getOfflinePlayer;

public class VaultEconomy implements Economy {

    private final net.milkbowl.vault.economy.Economy vault;

    public VaultEconomy(net.milkbowl.vault.economy.Economy vault) {
        this.vault = vault;
    }

    @Override
    public int getBalance(UUID uuid) {
        return (int) vault.getBalance(getOfflinePlayer(uuid));
    }

    @Override
    public boolean setBalance(UUID uuid, int balance) {
        int current = getBalance(uuid);
        int delta = balance - current;
        if (delta == 0) return true;
        if (delta > 0)
            return deposit(uuid, delta);
        else
            return withdraw(uuid, -delta);
    }

    @Override
    public boolean withdraw(UUID uuid, int balance) {
        return vault.withdrawPlayer(getOfflinePlayer(uuid), balance).transactionSuccess();
    }

    @Override
    public boolean deposit(UUID uuid, int money) {
        return vault.depositPlayer(getOfflinePlayer(uuid), money).transactionSuccess();
    }

    @Override
    public void save(UUID uuid) {
        //do nothing
    }

    @Override
    public void saveAll() {
        //do nothing
    }
}
