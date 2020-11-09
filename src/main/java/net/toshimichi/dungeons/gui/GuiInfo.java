package net.toshimichi.dungeons.gui;

import org.bukkit.inventory.ItemStack;

public class GuiInfo {
    private final String title;
    private final GuiItem[] items;

    public GuiInfo(String title, GuiItem[] items) {
        this.title = title;
        this.items = items;
    }

    public String getTitle() {
        return title;
    }

    public GuiItem[] getItems() {
        return items;
    }
}
