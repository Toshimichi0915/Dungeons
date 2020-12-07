package net.toshimichi.dungeons.enchants.sword.painfocus;

import net.toshimichi.dungeons.Dungeons;
import net.toshimichi.dungeons.enchants.Enchant;
import net.toshimichi.dungeons.enchants.sword.SwordEnchanter;
import net.toshimichi.dungeons.events.PlayerDamageEvent;
import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

public class PainFocusEnchanter extends SwordEnchanter implements Listener {
    public PainFocusEnchanter(Enchant enchant, Player player, ItemStack itemStack) {
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
    public void onDamage(EntityDamageByEntityEvent e1) {
        PlayerDamageEvent e = new PlayerDamageEvent(e1);
        if (!e.getTrueDamager().equals(getPlayer())) return;
        double coefficient;
        double pain = getPlayer().getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue() - getPlayer().getHealth();
        if (getEnchant().getLevel() == 1)
            coefficient = 0.02;
        else if (getEnchant().getLevel() == 2)
            coefficient = 0.04;
        else
            coefficient = 0.07;

        double modifier = 1 + pain * coefficient;
        e.setDamage(e.getDamage() * modifier);
    }

    @Override
    public boolean isAvailable() {
        return getPlayer().getInventory().getItemInMainHand().equals(getItemStack());
    }
}
