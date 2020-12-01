package net.toshimichi.dungeons.nat.api.nbt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class LocalNbtCompound extends HashMap<String, Nbt> implements NbtCompound {

    public LocalNbtCompound(int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor);
    }

    public LocalNbtCompound(int initialCapacity) {
        super(initialCapacity);
    }

    public LocalNbtCompound() {
    }

    public LocalNbtCompound(Map<? extends String, ? extends Nbt> m) {
        super(m);
    }
}
