package net.toshimichi.dungeons.nat.v1_15_2.nbt;

import net.minecraft.server.v1_15_R1.NBTTagByte;
import net.minecraft.server.v1_15_R1.NBTTagByteArray;
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
}
