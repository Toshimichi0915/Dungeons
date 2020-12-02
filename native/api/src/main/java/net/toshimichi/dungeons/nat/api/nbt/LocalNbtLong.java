package net.toshimichi.dungeons.nat.api.nbt;

public class LocalNbtLong implements NbtLong {

    private final long l;

    public LocalNbtLong(long l) {
        this.l = l;
    }

    @Override
    public byte getAsByte() {
        return (byte)l;
    }

    @Override
    public short getAsShort() {
        return (short)l;
    }

    @Override
    public int getAsInt() {
        return (int)l;
    }

    @Override
    public long getAsLong() {
        return l;
    }
}
