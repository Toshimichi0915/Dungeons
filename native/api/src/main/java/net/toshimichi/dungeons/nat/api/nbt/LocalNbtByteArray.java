package net.toshimichi.dungeons.nat.api.nbt;

import java.util.Arrays;

public class LocalNbtByteArray implements NbtByteArray {

    private final byte[] array;

    public LocalNbtByteArray(byte[] array) {
        this.array = array;
    }

    @Override
    public byte[] getAsByteArray() {
        return array;
    }

    @Override
    public void set(int i, byte[] b) {
        System.arraycopy(b, 0, array, i, array.length);
    }
}
