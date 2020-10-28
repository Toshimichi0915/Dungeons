package net.toshimichi.dungeons.enchants.shield;

import net.toshimichi.dungeons.DungeonsPlugin;
import net.toshimichi.dungeons.enchants.Enchant;
import net.toshimichi.dungeons.enchants.Enchanter;
import net.toshimichi.dungeons.events.BetterDamageEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

public class SpringInsideEnchanter extends Enchanter implements Listener {

    public SpringInsideEnchanter(Enchant enchant, Player player, ItemStack itemStack) {
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
    public void onBlock(EntityDamageByEntityEvent e) {
        if (!getPlayer().equals(e.getEntity())) return;
        if (!getPlayer().isBlocking()) return;
        if (e.getDamage() == 0 || e.getFinalDamage() != 0) return;

        LivingEntity target = new BetterDamageEvent(e).getTrueDamager();
        if (target == null) return;
        double modifier = 0.1 * getEnchant().getLevel();
        target.damage(e.getDamage() * modifier, getPlayer());
    }
}
