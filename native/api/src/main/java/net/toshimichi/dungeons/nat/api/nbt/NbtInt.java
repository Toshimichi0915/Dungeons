package net.toshimichi.dungeons.nat.api.nbt;

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
