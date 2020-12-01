package net.toshimichi.dungeons.nat.api.nbt;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface NbtCompound extends Nbt, Map<String, Nbt> {

    @Override
    default NbtCompound getAsCompound() {
        return this;
    }
}
