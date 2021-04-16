package net.toshimichi.dungeons.nat.api.nbt;

import java.util.ArrayList;
import java.util.Collection;

public class LocalNbtList extends ArrayList<Nbt> implements NbtList {

    public LocalNbtList(int initialCapacity) {
        super(initialCapacity);
    }

    public LocalNbtList() {
    }

    public LocalNbtList(Collection<? extends Nbt> c) {
        super(c);
    }
}
