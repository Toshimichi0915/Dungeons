package net.toshimichi.dungeons.nat.v1_16_4.nbt;

import net.minecraft.server.v1_16_R3.NBTTagByte;
import net.toshimichi.dungeons.nat.api.nbt.NbtByte;

public class NativeNbtByte implements NbtByte {

    private final NBTTagByte b;

    public NativeNbtByte(NBTTagByte b) {
        this.b = b;
    }

    @Override
    public byte getAsByte() {
        return b.asByte();
    }
}
