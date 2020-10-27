package net.toshimichi.dungeons.nat.api;

import org.bukkit.inventory.ItemStack;

public interface NbtItemStack {

    String getName();

    ItemStack toItemStack();
}
