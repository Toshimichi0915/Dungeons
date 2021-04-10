package net.toshimichi.dungeons.enchants.sword.lifesteal;

import net.toshimichi.dungeons.Dungeons;
import net.toshimichi.dungeons.enchants.Enchant;
import net.toshimichi.dungeons.enchants.sword.SwordEnchanter;
import net.toshimichi.dungeons.events.PlayerDamageEvent;
import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

public class LifestealEnchanter extends SwordEnchanter implements Listener {

    public LifestealEnchanter(Enchant enchant, Player player, ItemStack itemStack) {
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
    public void onAttack(EntityDamageByEntityEvent e1) {
        PlayerDamageEvent e = new PlayerDamageEvent(e1);
        if (!getPlayer().equals(e.getTrueDamager())) return;
        double after = getPlayer().getHealth();
        if (getEnchant().getLevel() == 1)
            after += e.getFinalDamage() * 0.05;
        else if (getEnchant().getLevel() == 2)
            after += e.getFinalDamage() * 0.1;
        else if (getEnchant().getLevel() >= 3)
            after += e.getFinalDamage() * 0.17;
        after = Math.min(after, getPlayer().getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
        getPlayer().setHealth(after);
    }

    @Override
    public boolean isAvailable() {
        return getPlayer().getInventory().getItemInMainHand().equals(getItemStack());
    }
}
