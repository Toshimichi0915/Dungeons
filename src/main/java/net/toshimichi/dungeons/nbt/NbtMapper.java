package net.toshimichi.dungeons.nbt;

import net.toshimichi.dungeons.nat.api.nbt.Nbt;

public interface NbtMapper {

    Nbt serialize(Object o);

    <T> T deserialize(Nbt nbt, Class<T> type);

    void addNbtSerializer(NbtSerializer<?> s);
}
