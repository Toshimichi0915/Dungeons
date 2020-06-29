package net.toshimichi.dungeons.events;

import net.toshimichi.dungeons.lang.Locale;
import net.toshimichi.dungeons.lang.LocaleManager;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class LocaleChangeEvent extends Event implements Cancellable {
    private static final HandlerList handlerList = new HandlerList();

    private final Player player;
    private final LocaleManager localeManager;
    private final Locale locale;
    private boolean cancelled;

    public LocaleChangeEvent(Player player, LocaleManager localeManager, Locale locale) {
        this.player = player;
        this.localeManager = localeManager;
        this.locale = locale;
    }

    /**
     * 言語を変更したプレイヤーを返します.
     *
     * @return 言語を変更したプレイヤー
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
     * プレイヤーが新しく使用する {@link Locale} を返します.
     *
     * @return 新しく使用する {@link Locale}
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

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean b) {
        cancelled = b;
    }
}
