package net.toshimichi.dungeons.gui;

import org.bukkit.inventory.ItemStack;

/**
 * GUIの1スロットを表します.
 */
public interface GuiItem {

    /**
     * 表示される {@link ItemStack} を返します.
     *
     * @return 表示される {@link ItemStack}
     */
    ItemStack getItemStack();

    /**
     * GUIクリック時の処理を返します.
     *
     * @return GUIクリック時の処理
     */
    GuiItemListener getListener();
}
