package net.toshimichi.dungeons.nat.v1_16_4.nbt;

import net.minecraft.server.v1_16_R3.NBTTagCompound;
import net.toshimichi.dungeons.nat.api.nbt.Nbt;
import net.toshimichi.dungeons.nat.api.nbt.NbtCompound;

import java.util.*;

public class NativeNbtCompound implements NbtCompound {

    private final NbtConverter converter;
    private final NBTTagCompound c;

    public NativeNbtCompound(NbtConverter converter, NBTTagCompound c) {
        this.converter = converter;
        this.c = c;
    }

    @Override
    public int size() {
        return c.e();
    }

    @Override
    public boolean isEmpty() {
        return c.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        if (!(key instanceof String)) return false;
        return c.hasKey((String) key);
    }

    @Override
    public boolean containsValue(Object value) {
        return values().contains(value);
    }

    @Override
    public Nbt get(Object key) {
        if (!(key instanceof String)) return null;
        return converter.fromNative(c.get((String) key));
    }

    @Override
    public Nbt put(String key, Nbt value) {
        c.set(key, converter.toNative(value));
        return value;
    }

    @Override
    public Nbt remove(Object key) {
        if (!(key instanceof String)) return null;
        Nbt removed = converter.fromNative(c.get((String) key));
        c.remove((String) key);
        return removed;
    }

    @Override
    public void putAll(Map<? extends String, ? extends Nbt> m) {
        m.forEach(this::put);
    }

    @Override
    public void clear() {
        c.getKeys().forEach(this::remove);
    }

    @Override
    public Set<String> keySet() {
        return c.getKeys();
    }

    @Override
    public ArrayList<Nbt> values() {
        ArrayList<Nbt> result = new ArrayList<>();
        for (String key : c.getKeys()) {
            result.add(converter.fromNative(c.get(key)));
        }
        return result;
    }

    @Override
    public HashSet<Entry<String, Nbt>> entrySet() {
        HashSet<Entry<String, Nbt>> result = new HashSet<>();
        for (String key : c.getKeys()) {
            result.add(new AbstractMap.SimpleEntry<>(key, converter.fromNative(c.get(key))));
        }
        return result;
    }
}
