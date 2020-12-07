package net.toshimichi.dungeons.nat.v1_16_4;

import net.toshimichi.dungeons.nat.api.NbtItemStack;
import net.toshimichi.dungeons.nat.api.NbtItemStackFactory;
import net.toshimichi.dungeons.nat.v1_16_4.nbt.NativeNbtConverter;
import org.bukkit.craftbukkit.v1_16_R3.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

public class NativeNbtItemStackFactory implements NbtItemStackFactory {

    private static final NativeNbtConverter converter = new NativeNbtConverter();

    @Override
    public NbtItemStack newNbtItemStack(ItemStack itemStack) {
        return new NativeNbtItemStack(converter, CraftItemStack.asNMSCopy(itemStack));
    }
}
