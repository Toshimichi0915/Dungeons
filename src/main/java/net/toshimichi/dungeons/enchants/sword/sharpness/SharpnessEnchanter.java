package net.toshimichi.dungeons.enchants.sword.sharpness;

import net.toshimichi.dungeons.Dungeons;
import net.toshimichi.dungeons.enchants.Enchant;
import net.toshimichi.dungeons.enchants.sword.SwordEnchanter;
import net.toshimichi.dungeons.events.PlayerDamageEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

public class SharpnessEnchanter extends SwordEnchanter implements Listener {

    public SharpnessEnchanter(Enchant enchant, Player player, ItemStack itemStack) {
        super(enchant, player, itemStack);
    }

    @Override
    protected void onEnabled() {
        Bukkit.getPluginManager().registerEvents(this, Dungeons.getInstance().getPlugin());
    }

    @Override
    public void tick() {
    }

    @Override
    protected void onDisabled() {
        HandlerList.unregisterAll(this);
    }

    @EventHandler(ignoreCancelled = true)
    public void onDamage(EntityDamageByEntityEvent e1) {
        PlayerDamageEvent e = new PlayerDamageEvent(e1);
        if (!getPlayer().equals(e.getTrueDamager())) return;
        double modifier;
        if (getEnchant().getLevel() == 1) {
            modifier = 0.08;
        } else if (getEnchant().getLevel() == 2) {
            modifier = 0.12;
        } else {
            modifier = 0.15;
        }

        e.setDamage(e.getDamage() * (1 + modifier));
    }

    @Override
    public boolean isAvailable() {
        return getPlayer().getInventory().getItemInMainHand().equals(getItemStack());
    }
}
