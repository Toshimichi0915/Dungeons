package net.toshimichi.dungeons.misc;

import java.util.UUID;

import static org.bukkit.Bukkit.getOfflinePlayer;

public class VaultEconomy implements Economy {

    private final net.milkbowl.vault.economy.Economy vault;

    public VaultEconomy(net.milkbowl.vault.economy.Economy vault) {
        this.vault = vault;
    }

    @Override
    public int getMoney(UUID uuid) {
        return (int) vault.getBalance(getOfflinePlayer(uuid));
    }

    @Override
    public void setMoney(UUID uuid, int money) {
        int current = getMoney(uuid);
        int delta = money - current;
        if (delta == 0) return;
        if (delta > 0)
            deposit(uuid, delta);
        else
            withdraw(uuid, -delta);
    }

    @Override
    public boolean withdraw(UUID uuid, int money) {
        return vault.withdrawPlayer(getOfflinePlayer(uuid), money).transactionSuccess();
    }

    @Override
    public void deposit(UUID uuid, int money) {
        vault.depositPlayer(getOfflinePlayer(uuid), money);
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
