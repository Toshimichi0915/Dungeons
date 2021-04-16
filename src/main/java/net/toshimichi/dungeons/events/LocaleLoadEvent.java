package net.toshimichi.dungeons.events;

import net.toshimichi.dungeons.lang.Locale;
import net.toshimichi.dungeons.lang.LocaleManager;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * プレイヤーの言語がロードされたときに発生するイベントです.
 */
public class LocaleLoadEvent extends Event {

    private static final HandlerList handlerList = new HandlerList();

    private final Player player;
    private final LocaleManager localeManager;
    private final Locale locale;

    public LocaleLoadEvent(Player player, LocaleManager localeManager, Locale locale) {
        this.player = player;
        this.localeManager = localeManager;
        this.locale = locale;
    }

    /**
     * ロードされたプレイヤーを返します.
     *
     * @return ロードされたプレイヤー
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * 使用された {@link LocaleManager} を返します.
     *
     * @return 使用された {@link LocaleManager}
     */
    public LocaleManager getLocaleManager() {
        return localeManager;
    }

    /**
     * プレイヤーが使用する {@link Locale} を返します.
     *
     * @return 使用する {@link Locale}
     */
    public Locale getLocale() {
        return locale;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }
}
