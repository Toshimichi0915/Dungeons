package net.toshimichi.dungeons.lang;

import org.bukkit.entity.Player;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * {@link Locale} を管理します.
 * {@link LocaleManager} を継承するクラスはマルチスレッド対応でなければなりません.
 */
public interface LocaleManager {

    /**
     * プレイヤーの {@link Locale} を返します.
     * @param player プレイヤー
     * @return プレイヤーの言語に対応した {@link Locale}
     */
    Locale getLocale(Player player);

    /**
     * プレイヤーの使用に最も適しているであろう {@link Locale} を返します.
     * @param player プレイヤー
     * @return プレイヤーに最も適した {@link Locale}
     */
    Locale getSuggestedLocale(Player player);

    /**
     * プレイヤーの {@link Locale} を設定します.
     * @param player プレイヤー
     * @param locale 適応される新しい {@link Locale}
     */
    void setLocale(Player player, Locale locale);

    /**
     * プレイヤーのデータをセーブします.
     * @param player セーブするプレイヤー
     */
    void save(Player player);

    /**
     * 全てのデータをセーブします.
     */
    void saveAll();
}
