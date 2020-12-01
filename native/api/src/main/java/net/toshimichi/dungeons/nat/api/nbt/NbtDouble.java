package net.toshimichi.dungeons.nat.api.nbt;

public interface NbtDouble extends NbtFloat {

    @Override
    default double getAsDouble() {
        return getAsFloat();
    }
}
