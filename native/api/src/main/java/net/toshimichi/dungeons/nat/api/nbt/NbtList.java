package net.toshimichi.dungeons.nat.api.nbt;

import java.util.List;

public interface NbtList extends Nbt, List<Nbt> {

    @Override
    default NbtList getAsList() {
        return this;
    }
}
