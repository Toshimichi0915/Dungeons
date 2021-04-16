package net.toshimichi.dungeons.nat.v1_16_4.nbt;

import net.minecraft.server.v1_16_R3.NBTBase;
import net.toshimichi.dungeons.nat.api.nbt.Nbt;

public interface NbtConverter {
    NBTBase toNative(Nbt nbt);

    Nbt fromNative(NBTBase base);
}
