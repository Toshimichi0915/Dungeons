package net.toshimichi.dungeons.misc;

import java.io.IOException;
import java.util.UUID;

/**
 * プレイヤーの所持金を管理します.
 * このインターフェースのメソッドは全て非同期で呼ばれなければなりません.
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
     * @return 設定できた場合は {@code true} そうでなければ {@code false}
     */
    boolean setMoney(UUID uuid, int money);

    /**
     * プレイヤーの所持金から指定された額を引き出します.
     * @param uuid プレイヤーの {@link UUID}
     * @param money 所持金
     * @return 引き出せた場合は {@code true} そうでなけれは {@code false}
     */
    boolean withdraw(UUID uuid, int money);

    /**
     * プレイヤーの所持金を増やします.
     * @param uuid プレイヤーの {@link UUID}
     * @param money 増やすお金
     * @return 増やせた場合は {@code true} そうでなければ {@code false}
     */
    boolean deposit(UUID uuid, int money);

    /**
     * プレイヤーのデータが確実にセーブされていることを確実にします.
     * @param uuid プレイヤーの {@link UUID}
     * @throws IOException セーブに失敗した場合
     */
    void save(UUID uuid) throws IOException;

    /**
     * ロードされている全てのプレイヤーのデータがセーブされていることを確実にします.
     * @throws IOException セーブに失敗した場合
     */
    void saveAll() throws IOException;
}
