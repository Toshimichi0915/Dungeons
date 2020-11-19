package net.toshimichi.dungeons.gui;

import org.bukkit.entity.Player;

/**
 * GUIクリック時の処理を表すクラスです.
 */
@FunctionalInterface
public interface GuiItemListener {

    /**
     * GUIクリック時に処理を行います.
     *
     * @param player プレイヤー
     * @param gui    プレイヤーが開いているGUI
     * @param item   クリックされた {@link GuiItem}
     */
    void onClick(Player player, Gui gui, GuiItem item);
}
