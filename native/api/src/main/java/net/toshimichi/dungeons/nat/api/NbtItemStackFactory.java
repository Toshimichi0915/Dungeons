package net.toshimichi.dungeons.nat.api;

import org.bukkit.inventory.ItemStack;

public interface NbtItemStackFactory {
    NbtItemStack newNbtItemStack(ItemStack itemStack);
}
