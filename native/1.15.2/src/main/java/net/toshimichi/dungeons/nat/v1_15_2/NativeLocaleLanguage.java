package net.toshimichi.dungeons.nat.v1_15_2;

import net.toshimichi.dungeons.nat.api.LocaleLanguage;

public class NativeLocaleLanguage implements LocaleLanguage {

    private final net.minecraft.server.v1_15_R1.LocaleLanguage localeLanguage;

    public NativeLocaleLanguage() {
        this.localeLanguage = net.minecraft.server.v1_15_R1.LocaleLanguage.a();
    }

    @Override
    public String getMessage(String key) {
        return localeLanguage.a(key);
    }

    @Override
    public boolean contains(String key) {
        return localeLanguage.b(key);
    }
}
