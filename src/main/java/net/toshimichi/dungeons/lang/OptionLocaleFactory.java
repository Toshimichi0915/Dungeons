package net.toshimichi.dungeons.lang;

import org.bukkit.entity.Player;

public class OptionLocaleFactory implements LocaleFactory {

    private final String name;
    private final Locale locale;
    private final String code;

    public OptionLocaleFactory(String name, Locale locale, String code) {
        this.name = name;
        this.locale = locale;
        this.code = code;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Locale getLocale() {
        return locale;
    }

    @Override
    public boolean match(Player player) {
        return player.getLocale().equals(code);
    }

    @Override
    public boolean isAvailable() {
        return true;
    }

    @Override
    public int getPriority() {
        return 20;
    }
}
