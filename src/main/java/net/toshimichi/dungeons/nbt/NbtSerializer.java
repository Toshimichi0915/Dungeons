package net.toshimichi.dungeons.nbt;

import net.toshimichi.dungeons.nat.api.nbt.Nbt;

public interface NbtSerializer<T> {

    Nbt serialize(T obj, NbtMapper mapper);

    T deserialize(Nbt nbt, NbtMapper mapper);

    Class<T> getType();
}
