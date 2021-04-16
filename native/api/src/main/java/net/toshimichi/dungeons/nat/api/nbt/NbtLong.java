package net.toshimichi.dungeons.nat.api.nbt;

/**
 * {@code long} に変換できる {@link Nbt} を表します.
 */
public interface NbtLong extends Nbt {

    @Override
    default float getAsFloat() {
        return getAsLong();
    }

    @Override
    default double getAsDouble() {
        return getAsLong();
    }
}
