package net.toshimichi.dungeons.nat.api.nbt;

import java.util.List;

/**
 * {@link NbtList} に変換できる {@link Nbt} を表します.
 */
public interface NbtList extends Nbt, List<Nbt> {

    @Override
    default NbtList getAsList() {
        return this;
    }
}
