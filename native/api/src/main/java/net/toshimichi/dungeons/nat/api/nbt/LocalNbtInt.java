package net.toshimichi.dungeons.nat.api.nbt;

public class LocalNbtInt implements NbtInt {

    private final int i;

    public LocalNbtInt(int i) {
        this.i = i;
    }

    @Override
    public byte getAsByte() {
        return (byte) i;
    }

    @Override
    public short getAsShort() {
        return (short) i;
    }

    @Override
    public int getAsInt() {
        return i;
    }
}
