package net.toshimichi.dungeons.nat.api.nbt;

/**
 * {@code int[]} に変換できる {@link Nbt} を表します.
 */
public interface NbtIntArray extends Nbt {

    void set(int i, int[] array);
}
