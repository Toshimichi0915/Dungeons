package net.toshimichi.dungeons.misc;

import java.util.UUID;

/**
 * プレイヤーの所持金を管理します.
 */
public interface Economy {

    /**
     * プレイヤーの所持金を返します.
     * @param uuid プレイヤーの {@link UUID}
     * @return 所持金
     */
    int getMoney(UUID uuid);

    /**
     * プレイヤーの所持金を設定します.
     * @param uuid プレイヤーの {@link UUID}
     * @param money 新しい所持金
     */
    void setMoney(UUID uuid, int money);

    /**
     * プレイヤーの所持金から指定された額を引き出します.
     * @param uuid プレイヤーの {@link UUID}
     * @param money 所持金
     * @return 引き出せた場合は {@code true} そうでない場合は {@code false}
     */
    boolean withdraw(UUID uuid, int money);

    /**
     * プレイヤーの所持金を増やします.
     * @param uuid プレイヤーの {@link UUID}
     * @param money 増やすお金
     */
    void deposit(UUID uuid, int money);

    /**
     * プレイヤーのデータをセーブします.
     * @param uuid プレイヤーの {@link UUID}
     */
    void save(UUID uuid);

    /**
     * ロードされている全てのプレイヤーのデータをセーブします.
     */
    void saveAll();
}
