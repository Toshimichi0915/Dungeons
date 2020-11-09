package net.toshimichi.dungeons.gui;

import org.bukkit.inventory.ItemStack;

public class GuiItem {
    private final ItemStack itemStack;
    private final GuiItemListener listener;

    public GuiItem(ItemStack itemStack, GuiItemListener listener) {
        this.itemStack = itemStack;
        this.listener = listener;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public GuiItemListener getListener() {
        return listener;
    }
}
