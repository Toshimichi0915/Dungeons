package net.toshimichi.dungeons.lang;

import org.bukkit.entity.Player;

/**
 * {@link Locale} を生成します.
 * {@link LocaleFactory} を継承するクラスはマルチスレッド対応でなくてはなりません.
 */
public interface LocaleFactory {

    /**
     * 言語名を返します.
     *
     * @return 言語名
     */
    String getName();

    /**
     * {@link Locale} を返します.
     *
     * @return 対応する {@link Locale}
     */
    Locale getLocale();

    /**
     * 指定されたプレイヤーがこの言語にマッチするか調べます.
     *
     * @param player プレイヤー
     * @return マッチする場合は {@code true} そうでない場合は {@code false}
     */
    boolean match(Player player);

    /**
     * この {@link LocaleFactory} が現在利用可能かどうかを返します.
     *
     * @return 利用できる場合は {@code true} そうでない場合は {@code false}
     */
    boolean isAvailable();

    /**
     * 優先度を返します.
     * この値が低いほど優先されます.
     *
     * @return 優先度
     */
    int getPriority();
}
