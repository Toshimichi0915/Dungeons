package net.toshimichi.dungeons.enchants.sword.katana;

import net.toshimichi.dungeons.DungeonsPlugin;
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

public class KatanaEnchanter extends SwordEnchanter implements Listener {
    public KatanaEnchanter(Enchant enchant, Player player, ItemStack itemStack) {
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
    public void onAttack(EntityDamageByEntityEvent e1) {
        PlayerDamageEvent e = new PlayerDamageEvent(e1);
        if (!(e.getTrueDamager() instanceof Player)) return;
        double modifier;
        if (getEnchant().getLevel() == 1)
            modifier = 0.9;
        else if (getEnchant().getLevel() == 2)
            modifier = 0.8;
        else
            modifier = 0.6;
        Bukkit.getScheduler().runTask(DungeonsPlugin.getPlugin(), () ->
                e.getEntity().setVelocity(e.getEntity().getVelocity().multiply(modifier)));
    }
}
