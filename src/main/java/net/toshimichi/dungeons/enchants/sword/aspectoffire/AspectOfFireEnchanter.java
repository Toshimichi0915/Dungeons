package net.toshimichi.dungeons.enchants.sword.aspectoffire;

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

public class AspectOfFireEnchanter extends SwordEnchanter implements Listener {
    public AspectOfFireEnchanter(Enchant enchant, Player player, ItemStack itemStack) {
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
    public void onEntityDamage(EntityDamageByEntityEvent e1) {
        PlayerDamageEvent e = new PlayerDamageEvent(e1);
        if (!e.getTrueDamager().equals(getPlayer())) return;
        int ticks;
        if (getEnchant().getLevel() == 1)
            ticks = 200;
        else if (getEnchant().getLevel() == 2)
            ticks = 400;
        else
            ticks = 600;
        e.getEntity().setFireTicks(ticks);
    }
}
