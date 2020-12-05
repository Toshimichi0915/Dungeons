package net.toshimichi.dungeons.nat.api.nbt;

import java.util.Map;

/**
 * {@link NbtCompound} に変換できる {@link Nbt} を表します.
 */
public interface NbtCompound extends Nbt, Map<String, Nbt> {

    @Override
    default NbtCompound getAsCompound() {
        return this;
    }
}
