package net.toshimichi.dungeons.enchants.wand.portal;

import net.toshimichi.dungeons.Dungeons;
import net.toshimichi.dungeons.enchants.Enchant;
import net.toshimichi.dungeons.enchants.wand.WandEnchanter;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class PortalEnchanter extends WandEnchanter {
    public PortalEnchanter(Enchant enchant, Player player, ItemStack itemStack) {
        super(enchant, player, itemStack);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if (!getPlayer().equals(e.getPlayer())) return;
        if (e.getAction() != Action.RIGHT_CLICK_AIR && e.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        if (!Dungeons.getInstance().getManaManager().consumeMana(getPlayer(), 40)) return;

        int radius;
        if (getEnchant().getLevel() == 1)
            radius = 2;
        else if (getEnchant().getLevel() == 2)
            radius = 4;
        else
            radius = 6;

        for (Entity entity : getPlayer().getWorld().getEntities()) {
            if (!(entity instanceof Item)) return;
            if (getPlayer().getLocation().distanceSquared(entity.getLocation()) > radius * radius) return;
            entity.remove();
        }
        e.setCancelled(true);
    }
}
