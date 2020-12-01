package net.toshimichi.dungeons.nat.v1_15_2.nbt;

import net.minecraft.server.v1_15_R1.NBTTagLong;
import net.toshimichi.dungeons.nat.api.nbt.NbtLong;

public class NativeNbtLong implements NbtLong {

    private final NBTTagLong l;

    public NativeNbtLong(NBTTagLong l) {
        this.l = l;
    }

    @Override
    public byte getAsByte() {
        return (byte) getAsLong();
    }

    @Override
    public short getAsShort() {
        return (short) getAsLong();
    }

    @Override
    public int getAsInt() {
        return (int) getAsLong();
    }

    @Override
    public long getAsLong() {
        return l.asLong();
    }
}
