package net.toshimichi.dungeons.nat.v1_16_4.nbt;

import net.minecraft.server.v1_16_R3.NBTTagString;
import net.toshimichi.dungeons.nat.api.nbt.NbtString;

public class NativeNbtString implements NbtString {

    private final NBTTagString s;

    public NativeNbtString(NBTTagString s) {
        this.s = s;
    }

    @Override
    public String getAsString() {
        return s.asString();
    }

    @Override
    public String toString() {
        return s.toString();
    }
}
