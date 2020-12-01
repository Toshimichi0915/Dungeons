package net.toshimichi.dungeons.nat.api.nbt;

public class LocalNbtByteArray implements NbtByteArray {

    private final byte[] array;

    public LocalNbtByteArray(byte[] array) {
        this.array = array;
    }

    @Override
    public byte[] getAsByteArray() {
        return array;
    }
}
