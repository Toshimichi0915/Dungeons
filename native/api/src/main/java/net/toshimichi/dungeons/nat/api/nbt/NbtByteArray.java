package net.toshimichi.dungeons.nat.api.nbt;

public interface NbtByteArray extends Nbt {

    @Override
    default int[] getAsIntArray() {
        byte[] array = getAsByteArray();
        int[] copy = new int[array.length];
        for (int i = 0; i < copy.length; i++)
            copy[i] = array[i];
        return copy;
    }
}