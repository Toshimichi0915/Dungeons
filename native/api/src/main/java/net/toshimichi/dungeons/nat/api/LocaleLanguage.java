package net.toshimichi.dungeons.nat.api;

public interface LocaleLanguage {

    /**
     * 特定の文字列に関連付けられたメッセージを返します.
     *
     * @param key キー
     * @return メッセージ
     */
    String getMessage(String key);

    /**
     * 指定されたキーに関連付けられたメッセージがあるか調べます.
     *
     * @param key キー
     * @return ある場合は {@code true} そうでなければ {@code false}
     */
    boolean contains(String key);
}
