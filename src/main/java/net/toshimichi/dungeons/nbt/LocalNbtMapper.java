package net.toshimichi.dungeons.nbt;

import net.toshimichi.dungeons.nat.api.nbt.LocalNbtEnd;
import net.toshimichi.dungeons.nat.api.nbt.Nbt;
import net.toshimichi.dungeons.nat.api.nbt.NbtEnd;

import java.util.HashSet;

public class LocalNbtMapper implements NbtMapper {

    @SuppressWarnings("rawtypes")
    private final HashSet<NbtSerializer> serializers = new HashSet<>();

    @SuppressWarnings("rawtypes unchecked")
    @Override
    public Nbt serialize(Object o) {
        if (o == null)
            return new LocalNbtEnd();
        for (NbtSerializer serializer : serializers) {
            if (serializer.getType().equals(o.getClass()))
                return serializer.serialize(o, this);
        }
        return null;
    }

    @SuppressWarnings("rawtypes unchecked")
    @Override
    public <T> T deserialize(Nbt nbt, Class<T> type) {
        if (nbt == null || nbt instanceof NbtEnd)
            return null;
        for (NbtSerializer serializer : serializers) {
            if (serializer.getType().equals(type))
                return (T) serializer.deserialize(nbt, this);
        }
        return null;
    }

    @Override
    public void addNbtSerializer(NbtSerializer<?> s) {
        serializers.add(s);
    }
}
