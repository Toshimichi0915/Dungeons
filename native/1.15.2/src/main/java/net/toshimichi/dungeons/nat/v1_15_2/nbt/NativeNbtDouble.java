package net.toshimichi.dungeons.nat.v1_15_2.nbt;

import net.minecraft.server.v1_15_R1.NBTTagDouble;
import net.toshimichi.dungeons.nat.api.nbt.NbtDouble;

public class NativeNbtDouble implements NbtDouble {

    private final NBTTagDouble d;

    public NativeNbtDouble(NBTTagDouble d) {
        this.d = d;
    }

    @Override
    public float getAsFloat() {
        return d.asFloat();
    }

    @Override
    public double getAsDouble() {
        return d.asDouble();
    }
}
