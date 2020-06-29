package net.toshimichi.dungeons.lang.ipstack;

public class IpStackCurrency {
    private String code;
    private String name;
    private String plural;
    private String symbol;
    private String symbolNative;

    public IpStackCurrency(String code, String name, String plural, String symbol, String symbolNative) {
        this.code = code;
        this.name = name;
        this.plural = plural;
        this.symbol = symbol;
        this.symbolNative = symbolNative;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getPlural() {
        return plural;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getSymbolNative() {
        return symbolNative;
    }
}
