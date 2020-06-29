package net.toshimichi.dungeons.lang.ipstack;

/**
 * IpStackで利用できるオプションを表します.
 */
public class IpStackOption {
    private String key;
    private String value;

    /**
     * キーと値を指定して初期化します.
     * @param key キー
     * @param value 値
     */
    public IpStackOption(String key, String value) {
        this.key = key;
        this.value = value;
    }

    /**
     * キーを返します.
     * @return オプションのキー
     */
    public String getKey() {
        return key;
    }

    /**
     * 値を返します.
     * @return オプションの値
     */
    public String getValue() {
        return value;
    }
}
