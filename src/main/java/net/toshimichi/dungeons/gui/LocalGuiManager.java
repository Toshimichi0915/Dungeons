package net.toshimichi.dungeons.gui;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class LocalGuiManager implements GuiManager {

    private final HashMap<Player, Gui> guis = new HashMap<>();
    private final HashMap<Player, Inventory> inventories = new HashMap<>();
    private final HashSet<Player> closed = new HashSet<>();

    @Override
    public Gui getGui(Player player) {
        return guis.get(player);
    }

    @Override
    public Inventory getInventory(Player player) {
        return inventories.get(player);
    }

    @Override
    public Set<Player> getPlayers() {
        return guis.keySet();
    }

    @Override
    public void show(Player player, Gui info) {
        GuiItem[] items = info.getItems();
        Inventory inv = Bukkit.createInventory(null, items.length, info.getTitle(player));
        guis.put(player, info);
        inventories.put(player, inv);
        for (int i = 0; i < items.length; i++) {
            if (items[i] == null) continue;
            inv.setItem(i, items[i].getItemStack());
        }
        player.openInventory(inv);
        info.onOpen(player, inv);
    }

    @Override
    public void close(Player player) {
        if (!closed.contains(player)) {
            closed.add(player);
            player.closeInventory();
        }
        closed.remove(player);
        Gui gui = guis.get(player);
        if (gui != null)
            gui.onClose();
        inventories.remove(player);
        guis.remove(player);
    }

    @Override
    public void closeAll() {
        guis.keySet().forEach(this::close);
    }

}
