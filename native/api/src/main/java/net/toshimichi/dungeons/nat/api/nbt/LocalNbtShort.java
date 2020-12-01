package net.toshimichi.dungeons.nat.api.nbt;

public class LocalNbtShort implements NbtShort {

    private final short s;

    public LocalNbtShort(short s) {
        this.s = s;
    }

    @Override
    public byte getAsByte() {
        return (byte)s;
    }

    @Override
    public short getAsShort() {
        return s;
    }
}
