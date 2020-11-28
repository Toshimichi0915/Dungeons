package net.toshimichi.dungeons.lang;

/**
 * 単一の言語を用いて文字列の集合をマップします.
 * {@link Locale} を継承するクラスはマルチスレッド対応でなくてはなりません.
 */
public interface Locale {

    /**
     * このクラスが表す言語で表記された文字列を返します.
     *
     * @param key キー
     * @return 特定の言語で表された文字列 存在しない場合は {@code null}
     */
    String get(String key);
}
