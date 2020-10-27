package net.toshimichi.dungeons.nat.v1_15_2;

import net.toshimichi.dungeons.nat.api.NbtItemStack;
import org.bukkit.craftbukkit.v1_15_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

public class NativeNbtItemStack implements NbtItemStack {

    private final net.minecraft.server.v1_15_R1.ItemStack itemStack;

    public NativeNbtItemStack(net.minecraft.server.v1_15_R1.ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    @Override
    public String getName() {
        return itemStack.getName().getText();
    }

    @Override
    public ItemStack toItemStack() {
        return CraftItemStack.asBukkitCopy(itemStack);
    }
}
