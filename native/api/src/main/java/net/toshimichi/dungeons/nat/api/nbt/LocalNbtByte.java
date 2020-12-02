package net.toshimichi.dungeons.nat.api.nbt;

public class LocalNbtByte implements NbtLong {

    private final byte b;

    public LocalNbtByte(byte b) {
        this.b = b;
    }

    @Override
    public byte getAsByte() {
        return b;
    }

    @Override
    public String toString() {
        return Byte.toString(b);
    }
}
