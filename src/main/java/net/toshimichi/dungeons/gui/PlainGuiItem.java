package net.toshimichi.dungeons.gui;

import org.bukkit.inventory.ItemStack;

public class PlainGuiItem implements GuiItem {
    private final ItemStack itemStack;
    private final GuiItemListener listener;

    public PlainGuiItem(ItemStack itemStack, GuiItemListener listener) {
        this.itemStack = itemStack;
        this.listener = listener;
    }

    @Override
    public ItemStack getItemStack() {
        return itemStack;
    }

    @Override
    public GuiItemListener getListener() {
        return listener;
    }
}
