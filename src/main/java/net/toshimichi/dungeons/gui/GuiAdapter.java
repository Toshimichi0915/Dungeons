package net.toshimichi.dungeons.gui;

import org.bukkit.inventory.Inventory;

public class GuiAdapter implements Gui {
    private final String title;
    private final GuiItem[] items;
    private final GuiAnimation animation;

    public GuiAdapter(String title, GuiItem[] items, GuiAnimation animation) {
        this.title = title;
        this.items = items;
        this.animation = animation;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public GuiItem[] getItems() {
        return items;
    }

    @Override
    public void next(Gui gui, Inventory inv) {
        animation.next(gui, inv);
    }
}
