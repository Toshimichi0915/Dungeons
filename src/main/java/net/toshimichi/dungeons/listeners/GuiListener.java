package net.toshimichi.dungeons.listeners;

import net.toshimichi.dungeons.DungeonsPlugin;
import net.toshimichi.dungeons.gui.Gui;
import net.toshimichi.dungeons.gui.GuiItem;
import net.toshimichi.dungeons.gui.GuiItemListener;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public class GuiListener implements Listener {
    @EventHandler(ignoreCancelled = true)
    public void onClick(InventoryClickEvent e) {
        if (!(e.getWhoClicked() instanceof Player)) return;
        Inventory opened = DungeonsPlugin.getGuiHolder().getInventory((Player) e.getWhoClicked());
        if (!opened.equals(e.getClickedInventory())) return;
        int slot = e.getSlot();
        Gui info = DungeonsPlugin.getGuiHolder().getGui((Player) e.getWhoClicked());
        if (slot >= info.getItems().length) return;
        GuiItem item = info.getItems()[slot];
        if (item == null) return;
        e.setCancelled(true);
        GuiItemListener listener = item.getListener();
        if(listener != null)
            listener.onClick((Player) e.getWhoClicked(), info, item);
    }
}
