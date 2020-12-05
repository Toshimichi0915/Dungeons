package net.toshimichi.dungeons.nat.api.nbt;

/**
 * {@code byte} に変換できる {@link Nbt} を表します.
 */
public interface NbtByte extends Nbt {

    @Override
    default short getAsShort() {
        return getAsByte();
    }

    @Override
    default int getAsInt() {
        return getAsByte();
    }

    @Override
    default long getAsLong() {
        return getAsByte();
    }

    @Override
    default float getAsFloat() {
        return getAsByte();
    }

    @Override
    default double getAsDouble() {
        return getAsByte();
    }
}
