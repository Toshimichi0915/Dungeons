package net.toshimichi.dungeons.lang.ipstack;

import java.util.Set;

public class IpStackLocation {
    private String geonameId;
    private String capital;
    private Set<IpStackLanguage> languages;

    public String getGeonameId() {
        return geonameId;
    }

    public String getCapital() {
        return capital;
    }

    public Set<IpStackLanguage> getLanguages() {
        return languages;
    }
}
