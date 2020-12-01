package net.toshimichi.dungeons.nat.v1_16_4.nbt;

import net.minecraft.server.v1_16_R3.NBTTagShort;
import net.toshimichi.dungeons.nat.api.nbt.NbtShort;

public class NativeNbtShort implements NbtShort {

    private final NBTTagShort s;

    public NativeNbtShort(NBTTagShort s) {
        this.s = s;
    }

    @Override
    public byte getAsByte() {
        return (byte) getAsShort();
    }

    @Override
    public short getAsShort() {
        return s.asShort();
    }
}
