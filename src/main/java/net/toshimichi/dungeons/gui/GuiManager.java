package net.toshimichi.dungeons.gui;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;

public class GuiManager {

    private final HashMap<Player, Gui> guis = new HashMap<>();
    private final HashMap<Player, Inventory> inventories = new HashMap<>();

    public Gui getGui(Player player) {
        return guis.get(player);
    }

    public Inventory getInventory(Player player) {
        return inventories.get(player);
    }

    public void show(Player player, Gui info) {
        GuiItem[] items = info.getItems();
        Inventory inv = Bukkit.createInventory(null, items.length, info.getTitle());
        guis.put(player, info);
        inventories.put(player, inv);
        for (int i = 0; i < items.length; i++) {
            inv.setItem(i, items[i].getItemStack());
        }
    }

    public void close(Player player) {
        player.closeInventory();
        inventories.remove(player);
    }

}
