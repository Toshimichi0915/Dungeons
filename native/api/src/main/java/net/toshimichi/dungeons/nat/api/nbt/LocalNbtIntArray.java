package net.toshimichi.dungeons.nat.api.nbt;

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
}
