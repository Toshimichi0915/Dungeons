package net.toshimichi.dungeons.enchants.sword;

import net.toshimichi.dungeons.DungeonsPlugin;
import net.toshimichi.dungeons.enchants.Enchant;
import net.toshimichi.dungeons.enchants.Enchanter;
import net.toshimichi.dungeons.utils.Nonce;
import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;

public class SharpnessEnchanter extends Enchanter implements Listener {

    public SharpnessEnchanter(Enchant enchant, Player player, ItemStack itemStack) {
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

    @EventHandler(ignoreCancelled = true)
    public void onDamage(EntityDamageByEntityEvent e) {
        if (!e.getDamager().equals(getPlayer())) return;
        if(!(e.getEntity() instanceof LivingEntity)) return;
        if(!((Player)e.getDamager()).getInventory().getItemInMainHand().equals(getItemStack()))
            return;
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
}
