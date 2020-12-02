package net.toshimichi.dungeons.nat.api.nbt;

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
