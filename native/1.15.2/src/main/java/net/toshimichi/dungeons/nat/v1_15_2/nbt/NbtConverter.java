package net.toshimichi.dungeons.nat.v1_15_2.nbt;

import net.minecraft.server.v1_15_R1.NBTBase;
import net.toshimichi.dungeons.nat.api.nbt.Nbt;

public interface NbtConverter {
    NBTBase toNative(Nbt nbt);

    Nbt fromNative(NBTBase base);
}
