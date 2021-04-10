package net.toshimichi.dungeons.enchants.armor.featherfalling;

import net.toshimichi.dungeons.Dungeons;
import net.toshimichi.dungeons.enchants.Enchant;
import net.toshimichi.dungeons.enchants.armor.ArmorEnchanter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;

public class FeatherFallingEnchanter extends ArmorEnchanter implements Listener {
    public FeatherFallingEnchanter(Enchant enchant, Player player, ItemStack itemStack) {
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

    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        if (!getPlayer().equals(e.getEntity())) return;
        if (e.getCause() != EntityDamageEvent.DamageCause.FALL) return;
        e.setDamage(e.getDamage() * (1 - 0.3 * getEnchant().getLevel()));
    }
}
