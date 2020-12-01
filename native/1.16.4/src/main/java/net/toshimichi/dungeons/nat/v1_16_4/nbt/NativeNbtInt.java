package net.toshimichi.dungeons.nat.v1_16_4.nbt;

import net.minecraft.server.v1_16_R3.NBTTagInt;
import net.toshimichi.dungeons.nat.api.nbt.NbtInt;

public class NativeNbtInt implements NbtInt {

    private final NBTTagInt i;

    public NativeNbtInt(NBTTagInt i) {
        this.i = i;
    }

    @Override
    public byte getAsByte() {
        return (byte) getAsInt();
    }

    @Override
    public short getAsShort() {
        return (short) getAsInt();
    }

    @Override
    public int getAsInt() {
        return i.asInt();
    }
}
