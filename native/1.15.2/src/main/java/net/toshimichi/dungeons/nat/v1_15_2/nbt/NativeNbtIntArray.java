package net.toshimichi.dungeons.nat.v1_15_2.nbt;

import net.minecraft.server.v1_15_R1.NBTTagInt;
import net.minecraft.server.v1_15_R1.NBTTagIntArray;
import net.toshimichi.dungeons.nat.api.nbt.NbtIntArray;

public class NativeNbtIntArray implements NbtIntArray {

    private final NBTTagIntArray array;

    public NativeNbtIntArray(NBTTagIntArray array) {
        this.array = array;
    }

    @Override
    public int[] getAsIntArray() {
        return array.getInts();
    }

    @Override
    public void set(int i, int[] b) {
        for (int j = 0; j < b.length; j++) {
            array.set(j + i, NBTTagInt.a(b[j]));
        }
    }
}
