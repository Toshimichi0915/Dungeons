package net.toshimichi.dungeons.nat.v1_16_4;

import net.toshimichi.dungeons.nat.api.NbtItemStack;
import net.toshimichi.dungeons.nat.api.nbt.NbtCompound;
import net.toshimichi.dungeons.nat.v1_16_4.nbt.NbtConverter;
import org.bukkit.craftbukkit.v1_16_R3.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

public class NativeNbtItemStack implements NbtItemStack {

    private final NbtConverter converter;
    private final net.minecraft.server.v1_16_R3.ItemStack itemStack;

    public NativeNbtItemStack(NbtConverter converter, net.minecraft.server.v1_16_R3.ItemStack itemStack) {
        this.converter = converter;
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

    @Override
    public boolean hasNbtCompound() {
        return itemStack.hasTag();
    }

    @Override
    public NbtCompound getNbtCompound() {
        return (NbtCompound) converter.fromNative(itemStack.getTag());
    }

    @Override
    public NbtCompound newNbtCompound() {
        return (NbtCompound) converter.fromNative(itemStack.getOrCreateTag());
    }
}
