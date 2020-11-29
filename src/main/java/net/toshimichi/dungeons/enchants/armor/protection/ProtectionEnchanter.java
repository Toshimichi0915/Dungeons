package net.toshimichi.dungeons.enchants.armor.protection;

import net.toshimichi.dungeons.DungeonsPlugin;
import net.toshimichi.dungeons.enchants.Enchant;
import net.toshimichi.dungeons.enchants.Enchanter;
import net.toshimichi.dungeons.enchants.armor.ArmorEnchanter;
import net.toshimichi.dungeons.utils.InventoryUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class ProtectionEnchanter extends ArmorEnchanter implements Listener {
    public ProtectionEnchanter(Enchant enchant, Player player, ItemStack itemStack) {
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
    public void onAttack(EntityDamageByEntityEvent e) {
        if (!getPlayer().equals(e.getEntity())) return;
        double modifier;
        if (getEnchant().getLevel() == 1)
            modifier = -0.04;
        else if (getEnchant().getLevel() == 2)
            modifier = -0.08;
        else
            modifier = -0.13;

        e.setDamage(e.getDamage() * (1 + modifier));
    }
}
