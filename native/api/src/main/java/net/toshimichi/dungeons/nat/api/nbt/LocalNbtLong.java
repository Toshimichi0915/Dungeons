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
}
