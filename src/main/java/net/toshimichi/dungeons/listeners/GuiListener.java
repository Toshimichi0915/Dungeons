package net.toshimichi.dungeons.listeners;

import net.toshimichi.dungeons.Dungeons;
import net.toshimichi.dungeons.gui.Gui;
import net.toshimichi.dungeons.gui.GuiItem;
import net.toshimichi.dungeons.gui.GuiItemListener;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.inventory.Inventory;

public class GuiListener implements Listener {
    @EventHandler(ignoreCancelled = true)
    public void onClick(InventoryClickEvent e) {
        if (!(e.getWhoClicked() instanceof Player)) return;
        Inventory opened = Dungeons.getInstance().getGuiManager().getInventory((Player) e.getWhoClicked());
        if (opened == null) return;
        if (!opened.equals(e.getClickedInventory())) return;
        int slot = e.getSlot();
        Gui info = Dungeons.getInstance().getGuiManager().getGui((Player) e.getWhoClicked());
        if (slot >= info.getItems().length) return;
        e.setCancelled(true);
        GuiItem item = info.getItems()[slot];
        if (item == null) return;
        GuiItemListener listener = item.getListener();
        if (listener != null)
            listener.onClick((Player) e.getWhoClicked(), info, item);
    }

    @EventHandler(ignoreCancelled = true)
    public void onClose(InventoryCloseEvent e) {
        if (!(e.getPlayer() instanceof Player)) return;
        Dungeons.getInstance().getGuiManager().close((Player) e.getPlayer());
    }

    @EventHandler
    public void onDisable(PluginDisableEvent e) {
        if(!e.getPlugin().equals(Dungeons.getInstance().getPlugin())) return;
        Dungeons.getInstance().getGuiManager().closeAll();
    }
}
