package net.toshimichi.dungeons.nat.api.nbt;

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
