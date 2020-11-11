package net.toshimichi.dungeons.enchants.bow.fletching;

import net.toshimichi.dungeons.DungeonsPlugin;
import net.toshimichi.dungeons.enchants.Enchant;
import net.toshimichi.dungeons.enchants.Enchanter;
import net.toshimichi.dungeons.events.BetterDamageEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

public class FletchingEnchanter extends Enchanter implements Listener {

    public FletchingEnchanter(Enchant enchant, Player player, ItemStack itemStack) {
        super(enchant, player, itemStack);
    }

    @Override
    protected void onEnabled() {
        Bukkit.getPluginManager().registerEvents(this, DungeonsPlugin.getPlugin());
    }

    @Override
    public void tick() {
    }

    @Override
    protected void onDisabled() {
        HandlerList.unregisterAll(this);
    }


    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e1) {
        BetterDamageEvent e = new BetterDamageEvent(e1);
        if (!getPlayer().equals(e.getTrueDamager())) return;
        if (!(e.getDamager() instanceof Arrow)) return;
        double modifier;
        if (getEnchant().getLevel() == 1) {
            modifier = 0.10;
        } else if (getEnchant().getLevel() == 2) {
            modifier = 0.15;
        } else {
            modifier = 0.20;
        }

        e.setDamage(e.getDamage() * (1 + modifier));
    }
}
