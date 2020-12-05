package net.toshimichi.dungeons.nat.api.nbt;

/**
 * {@code byte[]} に変換できる {@link Nbt} を表します.
 */
public interface NbtByteArray extends Nbt {

    @Override
    default int[] getAsIntArray() {
        byte[] array = getAsByteArray();
        int[] copy = new int[array.length];
        for (int i = 0; i < copy.length; i++)
            copy[i] = array[i];
        return copy;
    }

    void set(int i, byte[] b);
}
