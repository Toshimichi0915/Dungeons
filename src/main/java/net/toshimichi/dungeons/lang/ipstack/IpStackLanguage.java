package net.toshimichi.dungeons.lang.ipstack;

import com.google.gson.annotations.SerializedName;

public class IpStackLanguage {
    private String code;
    private String name;
    @SerializedName("native")
    private String nativeLang;

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getNative() {
        return nativeLang;
    }
}
