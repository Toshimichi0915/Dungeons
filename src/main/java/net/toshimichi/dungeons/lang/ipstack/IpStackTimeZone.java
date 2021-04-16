package net.toshimichi.dungeons.lang.ipstack;

public class IpStackTimeZone {
    private String id;
    private int currentTime;
    private int gmtOffset;
    private String code;
    private boolean isDaylightSaving;

    public String getId() {
        return id;
    }

    public int getCurrentTime() {
        return currentTime;
    }

    public int getGmtOffset() {
        return gmtOffset;
    }

    public String getCode() {
        return code;
    }

    public boolean isDaylightSaving() {
        return isDaylightSaving;
    }
}
