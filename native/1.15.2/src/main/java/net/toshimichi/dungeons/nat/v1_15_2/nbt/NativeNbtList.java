package net.toshimichi.dungeons.nat.v1_15_2.nbt;

import net.minecraft.server.v1_15_R1.NBTTagList;
import net.toshimichi.dungeons.nat.api.nbt.Nbt;
import net.toshimichi.dungeons.nat.api.nbt.NbtList;

import java.util.AbstractList;

public class NativeNbtList extends AbstractList<Nbt> implements NbtList {
    private final NbtConverter converter;
    private final NBTTagList l;

    public NativeNbtList(NbtConverter converter, NBTTagList l) {
        this.converter = converter;
        this.l = l;
    }

    @Override
    public Nbt get(int index) {
        return converter.fromNative(l.b(index));
    }

    @Override
    public Nbt set(int index, Nbt element) {
        l.a(index, converter.toNative(element));
        return element;
    }

    @Override
    public void add(int index, Nbt element) {
        l.add(index, converter.toNative(element));
    }

    @Override
    public int size() {
        return l.size();
    }

    @Override
    public boolean add(Nbt nbt) {
        return l.add(converter.toNative(nbt));
    }
}
