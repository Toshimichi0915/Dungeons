package net.toshimichi.dungeons.nat.v1_16_4;

import net.toshimichi.dungeons.nat.api.NbtItemStack;
import org.bukkit.craftbukkit.v1_16_R3.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

public class NativeNbtItemStack implements NbtItemStack {

    private final net.minecraft.server.v1_16_R3.ItemStack itemStack;

    public NativeNbtItemStack(net.minecraft.server.v1_16_R3.ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    @Override
    public String getName() {
        return itemStack.getName().getString();
    }

    @Override
    public ItemStack toItemStack() {
        return CraftItemStack.asBukkitCopy(itemStack);
    }
}
