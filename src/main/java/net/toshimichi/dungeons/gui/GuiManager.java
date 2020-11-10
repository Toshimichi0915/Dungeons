package net.toshimichi.dungeons.gui;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class GuiManager {

    private final HashMap<Player, Gui> guis = new HashMap<>();
    private final HashMap<Player, Inventory> inventories = new HashMap<>();
    private final HashSet<Player> closed = new HashSet<>();

    public Gui getGui(Player player) {
        return guis.get(player);
    }

    public Inventory getInventory(Player player) {
        return inventories.get(player);
    }

    /**
     * GUIを開いているプレイヤーの一覧を返します.
     * @return GUIを開いているプレイヤーの一覧
     */
    public Set<Player> getPlayers() {
        return guis.keySet();
    }

    /**
     * 指定されたGUIを特定のプレイヤーに表示します.
     * @param player プレイヤー
     * @param info GUI
     */
    public void show(Player player, Gui info) {
        GuiItem[] items = info.getItems();
        Inventory inv = Bukkit.createInventory(null, items.length, info.getTitle());
        guis.put(player, info);
        inventories.put(player, inv);
        for (int i = 0; i < items.length; i++) {
            if (items[i] == null) continue;
            inv.setItem(i, items[i].getItemStack());
        }
        player.openInventory(inv);
        info.onOpen(player, inv);
    }

    /**
     * プレイヤーのGUIを安全な方法で閉じます.
     * @param player プレイヤー
     */
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

}
