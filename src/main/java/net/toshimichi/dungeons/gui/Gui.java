package net.toshimichi.dungeons.gui;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

/**
 * {@link Inventory} を利用したGUIを表します.
 */
public interface Gui extends GuiAnimation {

    /**
     * GUIのタイトルを返します.
     *
     * @param player プレイヤー
     * @return GUIのタイトル
     */
    String getTitle(Player player);

    /**
     * GUIに表示させる {@link GuiItem} の一覧を返します.
     * 配列の順序は, {@link Inventory#getContents()} の順序と同等です.
     *
     * @return GUIに表示させる {@link GuiItem} の一覧
     */
    GuiItem[] getItems();

    /**
     * GUI表示時に処理を実行します.
     *
     * @param player    プレイヤー
     * @param inventory 開かれたGUIに関連する {@link Inventory}
     */
    void onOpen(Player player, Inventory inventory);

    /**
     * GUI非表示時に処理を実行します.
     */
    void onClose();
}
