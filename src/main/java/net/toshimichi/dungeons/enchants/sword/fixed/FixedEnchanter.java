package net.toshimichi.dungeons.enchants.sword.fixed;

import net.toshimichi.dungeons.Dungeons;
import net.toshimichi.dungeons.enchants.Enchant;
import net.toshimichi.dungeons.enchants.sword.SwordEnchanter;
import net.toshimichi.dungeons.events.PlayerDamageEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

public class FixedEnchanter extends SwordEnchanter implements Listener {
    public FixedEnchanter(Enchant enchant, Player player, ItemStack itemStack) {
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

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void onDamage(EntityDamageByEntityEvent e1) {
        PlayerDamageEvent e = new PlayerDamageEvent(e1);
        if(!getPlayer().equals(e.getTrueDamager())) return;
        if(!(e.getEntity() instanceof LivingEntity)) return;
        e.setCancelled(true);
        ((LivingEntity)e.getEntity()).damage(1, getPlayer());
    }
}
