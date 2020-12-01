package net.toshimichi.dungeons.nat.api;

import net.toshimichi.dungeons.nat.api.nbt.NbtCompound;
import org.bukkit.inventory.ItemStack;

public interface NbtItemStack {

    String getName();

    ItemStack toItemStack();

    NbtCompound getNbtCompound();

    void updateNbtCompound();
}
