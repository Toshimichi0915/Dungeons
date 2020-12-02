package net.toshimichi.dungeons.nat.api.nbt;

import java.util.Arrays;

public class LocalNbtIntArray implements NbtIntArray {
    private byte[] copy;
    private final int[] array;

    public LocalNbtIntArray(int[] array) {
        this.array = array;
    }

    @Override
    public byte[] getAsByteArray() {
        if (copy == null) {
            copy = new byte[array.length];
            for (int i = 0; i < copy.length; i++)
                copy[i] = (byte) array[i];
        }
        return copy;
    }

    @Override
    public int[] getAsIntArray() {
        return array;
    }

    @Override
    public void set(int i, int[] array) {
        System.arraycopy(array, 0, array, i, array.length);
        copy = null;
    }

    @Override
    public String toString() {
        return Arrays.toString(array);
    }
}
