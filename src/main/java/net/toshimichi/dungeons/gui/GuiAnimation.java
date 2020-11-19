package net.toshimichi.dungeons.gui;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

/**
 * GUIのアニメーションを管理するクラスです.
 */
public interface GuiAnimation {
    /**
     * 1tick経過時にGUIに対して処理を実行します.
     *
     * @param player GUIを開いているプレイヤー
     * @param gui    GUI
     * @param inv    GUIに関連するインベントリ
     */
    void next(Player player, Gui gui, Inventory inv);
}
