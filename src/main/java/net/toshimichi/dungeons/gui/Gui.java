package net.toshimichi.dungeons.gui;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public interface Gui extends GuiAnimation{

    String getTitle();

    GuiItem[] getItems();

    void onOpen(Player player, Inventory inventory);

    void onClose();
}
