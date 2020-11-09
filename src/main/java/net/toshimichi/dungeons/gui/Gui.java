package net.toshimichi.dungeons.gui;

import org.bukkit.inventory.ItemStack;

public interface Gui extends GuiAnimation{

    String getTitle();

    GuiItem[] getItems();
}
