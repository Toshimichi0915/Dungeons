package net.toshimichi.dungeons.gui;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;

public class GuiManager {

    private final HashMap<Player, GuiInfo> guis = new HashMap<>();
    private final HashMap<Player, Inventory> inventories = new HashMap<>();

    public GuiInfo getGui(Player player) {
        return guis.get(player);
    }

    public Inventory getInventory(Player player) {
        return inventories.get(player);
    }

    public void show(Player player, GuiInfo info) {
        GuiItem[] items = info.getItems();
        guis.put(player, info);
        Inventory known = inventories.get(player);
        if (known == null || items.length != known.getSize()) {
            known = Bukkit.createInventory(null, items.length, info.getTitle());
            inventories.put(player, known);
        }
        for (int i = 0; i < items.length; i++) {
            known.setItem(i, items[i].getItemStack());
        }
    }

    public void close(Player player) {
        player.closeInventory();
        inventories.remove(player);
    }

}
