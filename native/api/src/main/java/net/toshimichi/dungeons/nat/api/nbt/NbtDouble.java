package net.toshimichi.dungeons.nat.api.nbt;

public interface NbtDouble extends Nbt {

    @Override
    default double getAsDouble() {
        return getAsFloat();
    }
}
