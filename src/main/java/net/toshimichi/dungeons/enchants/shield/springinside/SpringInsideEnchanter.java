package net.toshimichi.dungeons.enchants.shield.springinside;

import net.toshimichi.dungeons.Dungeons;
import net.toshimichi.dungeons.enchants.Enchant;
import net.toshimichi.dungeons.enchants.Enchanter;
import net.toshimichi.dungeons.events.PlayerShieldEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

public class SpringInsideEnchanter extends Enchanter implements Listener {

    public SpringInsideEnchanter(Enchant enchant, Player player, ItemStack itemStack) {
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
    public void onBlock(PlayerShieldEvent e) {
        if (!getPlayer().equals(e.getEntity())) return;
        if (!(e.getDamager() instanceof LivingEntity)) return;
        double modifier = 0.1 * getEnchant().getLevel();
        ((LivingEntity) e.getDamager()).damage(e.getDamage() * modifier, getPlayer());
    }
}
