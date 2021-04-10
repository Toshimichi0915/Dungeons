package net.toshimichi.dungeons.listeners;

import net.toshimichi.dungeons.Dungeons;
import net.toshimichi.dungeons.gui.AnvilGui;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;

public class AnvilListener implements Listener {

    @EventHandler(ignoreCancelled = true)
    public void onInventoryOpen(InventoryOpenEvent e) {
        if (e.getInventory().getType() != InventoryType.ANVIL) return;
        if (!(e.getPlayer() instanceof Player)) return;
        Player p = (Player) e.getPlayer();
        e.setCancelled(true);
        Dungeons.getInstance().getGuiManager().show(p, new AnvilGui());
        p.playSound(p.getLocation(), Sound.ENTITY_ITEM_PICKUP, 1, 1);
    }
}
