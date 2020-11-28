package net.toshimichi.dungeons.misc;

import org.bukkit.entity.Player;

/**
 * プレイヤーの魔力を管理します.
 */
public interface ManaManager {

    /**
     * プレイヤーの魔力を返します.
     *
     * @param player プレイヤー
     * @return 魔力
     */
    int getMana(Player player);

    /**
     * プレイヤーの最大魔力を返します.
     *
     * @param player プレイヤー
     * @return 最大魔力
     */
    int getMaxMana(Player player);

    /**
     * プレイヤーの魔力を設定します.
     *
     * @param player プレイヤー
     * @param mana   魔力
     */
    void setMana(Player player, int mana);

    /**
     * プレイヤーの最大魔力を設定します.
     *
     * @param player プレイヤー
     * @param mana   最大魔力
     */
    void setMaxMana(Player player, int mana);

    /**
     * プレイヤーの魔力を消費します.
     *
     * @param player プレイヤー
     * @param mana   消費する魔力
     * @return 消費できた場合は {@code true} そうでない場合は
     */
    boolean consumeMana(Player player, int mana);

    /**
     * プレイヤーのデータを保存します.
     *
     * @param player プレイヤー
     */
    void save(Player player);

    /**
     * 全てのデータを保存します.
     */
    void saveAll();
}
