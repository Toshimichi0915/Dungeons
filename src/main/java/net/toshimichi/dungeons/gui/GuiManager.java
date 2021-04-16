package net.toshimichi.dungeons.gui;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.Set;

/**
 * {@link Gui} を管理します.
 */
public interface GuiManager {
    /**
     * プレイヤーが開いている {@link Gui} を返します.
     *
     * @param player プレイヤー
     * @return 開かれた {@link Gui} 存在しない場合は {@code null}
     */
    Gui getGui(Player player);

    /**
     * プレイヤーが開いているGUIの {@link Inventory} を返します.
     *
     * @param player プレイヤー
     * @return 開いているGUIの {@link Inventory} 存在しない場合は {@code null}
     */
    Inventory getInventory(Player player);

    /**
     * GUIを開いているプレイヤーの一覧を返します.
     *
     * @return GUIを開いているプレイヤーの一覧
     */
    Set<Player> getPlayers();

    /**
     * 指定されたGUIを特定のプレイヤーに表示します.
     *
     * @param player プレイヤー
     * @param info   GUI
     */
    void show(Player player, Gui info);

    /**
     * プレイヤーのGUIを安全な方法で閉じます.
     *
     * @param player プレイヤー
     */
    void close(Player player);

    /**
     * 現在開かれている全てのGUIを安全な方法で閉じます.
     */
    void closeAll();
}
