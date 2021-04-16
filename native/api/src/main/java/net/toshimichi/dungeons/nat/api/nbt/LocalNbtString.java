package net.toshimichi.dungeons.nat.api.nbt;

public class LocalNbtString implements NbtString {

    private final String s;

    public LocalNbtString(String s) {
        this.s = s;
    }

    @Override
    public String getAsString() {
        return s;
    }

    @Override
    public String toString() {
        return "\"" + s + "\"";
    }
}
