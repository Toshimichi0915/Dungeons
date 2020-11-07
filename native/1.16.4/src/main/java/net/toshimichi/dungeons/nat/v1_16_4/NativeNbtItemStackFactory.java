package net.toshimichi.dungeons.nat.v1_16_4;

import net.toshimichi.dungeons.nat.api.NbtItemStack;
import net.toshimichi.dungeons.nat.api.NbtItemStackFactory;
import org.bukkit.craftbukkit.v1_16_R3.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

public class NativeNbtItemStackFactory implements NbtItemStackFactory {
    @Override
    public NbtItemStack newNbtItemStack(ItemStack itemStack) {
        return new NativeNbtItemStack(CraftItemStack.asNMSCopy(itemStack));
    }
}
