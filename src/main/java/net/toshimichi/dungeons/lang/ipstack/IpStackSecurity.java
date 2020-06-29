package net.toshimichi.dungeons.lang.ipstack;

public class IpStackSecurity {
    private boolean isProxy;
    private String proxyType;
    private boolean isCrawler;
    private String crawlerName;
    private boolean isTor;
    private String threatLevel;
    private String threatTypes;

    public boolean isProxy() {
        return isProxy;
    }

    public String getProxyType() {
        return proxyType;
    }

    public boolean isCrawler() {
        return isCrawler;
    }

    public String getCrawlerName() {
        return crawlerName;
    }

    public boolean isTor() {
        return isTor;
    }

    public String getThreatLevel() {
        return threatLevel;
    }

    public String getThreatTypes() {
        return threatTypes;
    }
}
