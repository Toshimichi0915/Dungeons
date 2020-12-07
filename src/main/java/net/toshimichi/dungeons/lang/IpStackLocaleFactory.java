package net.toshimichi.dungeons.lang;

import net.toshimichi.dungeons.Dungeons;
import net.toshimichi.dungeons.lang.ipstack.IpStackInfo;
import net.toshimichi.dungeons.lang.ipstack.IpStackLanguage;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.util.Set;

public class IpStackLocaleFactory implements LocaleFactory {

    private final String name;
    private final Locale locale;
    private final String code;

    public IpStackLocaleFactory(String name, Locale locale, String code) {
        this.name = name;
        this.locale = locale;
        this.code = code;
    }

    @Override
    public String getName() {
        return code;
    }

    @Override
    public Locale getLocale() {
        return locale;
    }

    @Override
    public boolean match(Player player) {
        try {
            IpStackInfo info = Dungeons.getInstance().getIpStackApi().getInfo(player.getAddress().getHostName());
            Set<IpStackLanguage> languages = info.getLocation().getLanguages();
            for (IpStackLanguage lang : languages) {
                if (lang.getCode().equals(code))
                    return true;
            }
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean isAvailable() {
        return Dungeons.getInstance().getIpStackApi().isAvailable();
    }

    @Override
    public int getPriority() {
        return 10;
    }
}
