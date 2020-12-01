package net.toshimichi.dungeons.nat.v1_16_4.nbt;

import net.minecraft.server.v1_16_R3.NBTTagFloat;
import net.toshimichi.dungeons.nat.api.nbt.NbtFloat;

public class NativeNbtFloat implements NbtFloat {

    private final NBTTagFloat f;

    public NativeNbtFloat(NBTTagFloat f) {
        this.f = f;
    }

    @Override
    public float getAsFloat() {
        return f.asFloat();
    }
}
