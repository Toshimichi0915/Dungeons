package net.toshimichi.dungeons.nat.api.nbt;

/**
 * {@code int} に変換できる {@link Nbt} を表します.
 */
public interface NbtInt extends Nbt {

    @Override
    default long getAsLong() {
        return getAsInt();
    }

    @Override
    default float getAsFloat() {
        return getAsInt();
    }

    @Override
    default double getAsDouble() {
        return getAsInt();
    }
}
