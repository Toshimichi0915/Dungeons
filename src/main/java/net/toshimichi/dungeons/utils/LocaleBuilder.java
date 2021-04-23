package net.toshimichi.dungeons.utils;

import net.toshimichi.dungeons.Dungeons;
import net.toshimichi.dungeons.lang.Locale;
import net.toshimichi.dungeons.lang.LocaleManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * {@link net.toshimichi.dungeons.lang.Locale} からを {@link LocaleManager} を経由して行います.
 */
public class LocaleBuilder {
    private static boolean chatColorEnabledByDefault = true;

    private final String key;
    private final HashMap<String, Supplier<String>> replaceMap = new HashMap<>();
    private Player player;
    private Locale locale;
    private boolean chatColor;

    /**
     * キーを指定してインスタンスを作ります.
     *
     * @param key 言語ファイルで用いられるキー
     */
    public LocaleBuilder(String key) {
        this.key = key;
        chatColor = chatColorEnabledByDefault;
    }

    /**
     * チャットカラーの有無を変更します.
     *
     * @param enabled 有効の場合は {@code true} そうでない場合は {@code false}
     * @return このインスタンス
     */
    public LocaleBuilder chatColor(boolean enabled) {
        chatColor = enabled;
        return this;
    }

    /**
     * 文字列を置き換えます.
     * 置き換えは {@link #build()} 時に行われます.
     *
     * @param from 置き換え元
     * @param to   置き換え後
     * @return このインスタンス
     */
    public LocaleBuilder replace(String from, Supplier<String> to) {
        replaceMap.put(from, to);
        return this;
    }

    /**
     * 文字列を置き換えます.
     * 置き換えは {@link #build()} 時に行われます.
     *
     * @param from 置き換え元
     * @param to   置き換え後
     * @return このインスタンス
     */
    public LocaleBuilder replace(String from, String to) {
        replaceMap.put(from, () -> to);
        return this;
    }

    /**
     * メッセージを送るプレイヤーを設定します.
     *
     * @param player プレイヤー
     * @return このインスタンス
     */
    public LocaleBuilder player(Player player) {
        this.player = player;
        return this;
    }

    /**
     * 使用される {@link Locale} を設定します.
     * このメソッドは {@link #player(Player)} よりも優先されます
     *
     * @param locale 使用される {@link Locale}
     * @return このインスタンス
     */
    public LocaleBuilder locale(Locale locale) {
        this.locale = locale;
        return this;
    }

    /**
     * 与えられた情報を用いて文字列を取得します.
     *
     * @return 文字列
     */
    public String build() {
        if (key == null) return "";
        String result = null;
        LocaleManager mng = Dungeons.getInstance().getLocaleManager();

        if (locale != null)
            result = locale.get(key);
        else if (player != null)
            result = mng.getLocale(player).get(key);

        if (result == null)
            result = Dungeons.getInstance().getDefaultLocale().get(key);
        if (result == null && player != null)
            result = mng.getSuggestedLocale(player).get(key);
        if (result == null)
            result = key;
        for (Map.Entry<String, Supplier<String>> entry : replaceMap.entrySet())
            result = result.replace(entry.getKey(), entry.getValue().get());
        if (chatColor)
            result = ChatColor.translateAlternateColorCodes('&', result);
        return result;
    }

    /**
     * {@link LocaleBuilder#player(Player)} で設定されたプレイヤーに文字列を送信します.
     */
    public void sendMessage() {
        if (player != null) {
            player.sendMessage(build());
        }
    }

    /**
     * チャットカラーのデフォルトの有無を変更します.
     *
     * @param enabled 有効の場合は {@code true} そうでない場合は {@code false}
     */
    public static void setChatColorEnabled(boolean enabled) {
        chatColorEnabledByDefault = enabled;
    }
}
