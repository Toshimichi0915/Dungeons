package net.toshimichi.dungeons.nat.v1_16_4.nbt;

import net.minecraft.server.v1_16_R3.NBTTagByte;
import net.minecraft.server.v1_16_R3.NBTTagByteArray;
import net.toshimichi.dungeons.nat.api.nbt.NbtByteArray;

public class NativeNbtByteArray implements NbtByteArray {

    private final NBTTagByteArray array;

    public NativeNbtByteArray(NBTTagByteArray array) {
        this.array = array;
    }

    @Override
    public byte[] getAsByteArray() {
        return array.getBytes();
    }

    @Override
    public void set(int i, byte[] b) {
        for (int j = 0; j < b.length; j++) {
            array.set(j + i, NBTTagByte.a(b[j]));
        }
    }

    @Override
    public String toString() {
        return array.toString();
    }
}
