package net.toshimichi.dungeons.lang.ipstack;

/**
 * IpStackにより取得された情報を表します.
 */
public class IpStackInfo {
    private String ip;
    private String hostname;
    private String type;
    private String continentCode;
    private String continentName;
    private String countryCode;
    private String countryName;
    private String regionCode;
    private String regionName;
    private String city;
    private String zip;
    private double latitude;
    private double longitude;
    private IpStackLocation location;
    private String countryFlag;
    private String countryFlagEmoji;
    private String countryFlagEmojiUnicode;
    private String callingCode;
    private boolean isEu;
    private IpStackTimeZone timeZone;
    private IpStackCurrency currency;
    private IpStackConnection connection;
    private IpStackSecurity security;

    public String getIp() {
        return ip;
    }

    public String getHostname() {
        return hostname;
    }

    public String getType() {
        return type;
    }

    public String getContinentCode() {
        return continentCode;
    }

    public String getContinentName() {
        return continentName;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public String getCountryName() {
        return countryName;
    }

    public String getRegionCode() {
        return regionCode;
    }

    public String getRegionName() {
        return regionName;
    }

    public String getCity() {
        return city;
    }

    public String getZip() {
        return zip;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public IpStackLocation getLocation() {
        return location;
    }

    public String getCountryFlag() {
        return countryFlag;
    }

    public String getCountryFlagEmoji() {
        return countryFlagEmoji;
    }

    public String getCountryFlagEmojiUnicode() {
        return countryFlagEmojiUnicode;
    }

    public String getCallingCode() {
        return callingCode;
    }

    public boolean isEu() {
        return isEu;
    }

    public IpStackTimeZone getTimeZone() {
        return timeZone;
    }

    public IpStackCurrency getCurrency() {
        return currency;
    }

    public IpStackConnection getConnection() {
        return connection;
    }

    public IpStackSecurity getSecurity() {
        return security;
    }
}
