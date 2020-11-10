package net.toshimichi.dungeons.gui;

import org.bukkit.inventory.ItemStack;

public interface GuiItem {

    ItemStack getItemStack();

    GuiItemListener getListener();
}
