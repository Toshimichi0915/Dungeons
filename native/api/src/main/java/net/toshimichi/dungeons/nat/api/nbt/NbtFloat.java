package net.toshimichi.dungeons.nat.api.nbt;

/**
 * {@code float} に変換できる {@link Nbt} を表します.
 */
public interface NbtFloat extends Nbt {

    @Override
    default double getAsDouble() {
        return getAsFloat();
    }
}
