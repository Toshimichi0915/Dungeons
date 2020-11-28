package net.toshimichi.dungeons.enchants.sword.billionaire;

import net.toshimichi.dungeons.DungeonsPlugin;
import net.toshimichi.dungeons.enchants.Enchant;
import net.toshimichi.dungeons.enchants.Enchanter;
import net.toshimichi.dungeons.events.PlayerDamageEvent;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

public class BillionaireEnchanter extends Enchanter implements Listener {

    private int estimatedMoney;
    private boolean updated;
    private int counter;

    public BillionaireEnchanter(Enchant enchant, Player player, ItemStack itemStack) {
        super(enchant, player, itemStack);
    }

    private void updateMoney() {
        Bukkit.getScheduler().runTaskAsynchronously(DungeonsPlugin.getPlugin(), () -> {
            int money = DungeonsPlugin.getEconomy().getBalance(getPlayer().getUniqueId());
            Bukkit.getScheduler().runTask(DungeonsPlugin.getPlugin(), () -> estimatedMoney = money);
        });
    }

    @Override
    protected void onEnabled() {
        Bukkit.getPluginManager().registerEvents(this, DungeonsPlugin.getPlugin());
        updateMoney();
    }

    @Override
    public void tick() {
        if (counter++ % 100 != 0) return;
        updateMoney();
    }

    @Override
    protected void onDisabled() {
        HandlerList.unregisterAll(this);
    }

    @EventHandler(ignoreCancelled = true)
    public void onAttack(EntityDamageByEntityEvent e1) {
        PlayerDamageEvent e = new PlayerDamageEvent(e1);
        if (!getPlayer().equals(e.getTrueDamager())) return;
        int cost;
        double modifier;
        if (getEnchant().getLevel() == 1) {
            cost = 100;
            modifier = 1.4;
        } else if (getEnchant().getLevel() == 2) {
            cost = 150;
            modifier = 1.8;
        } else {
            cost = 200;
            modifier = 2.2;
        }
        if (estimatedMoney < cost)
            return;
        e.setDamage(e.getDamage() * modifier);
        Bukkit.getScheduler().runTaskAsynchronously(DungeonsPlugin.getPlugin(), () ->
                DungeonsPlugin.getEconomy().withdraw(getPlayer().getUniqueId(), cost));
        getPlayer().playSound(getPlayer().getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 1, 1);
    }

    @Override
    public boolean isAvailable() {
        return getItemStack().equals(getPlayer().getInventory().getItemInMainHand());
    }
}
