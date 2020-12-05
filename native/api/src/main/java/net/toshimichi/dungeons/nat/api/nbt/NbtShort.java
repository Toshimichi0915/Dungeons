package net.toshimichi.dungeons.nat.api.nbt;

/**
 * {@code short} に変換できる {@link Nbt} を表します.
 */
public interface NbtShort extends Nbt {

    @Override
    default int getAsInt() {
        return getAsShort();
    }

    @Override
    default long getAsLong() {
        return getAsShort();
    }

    @Override
    default float getAsFloat() {
        return getAsShort();
    }

    @Override
    default double getAsDouble() {
        return getAsShort();
    }
}
