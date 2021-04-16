package net.toshimichi.dungeons.nat.api;

import net.toshimichi.dungeons.nat.api.nbt.LocalNbtCompound;
import net.toshimichi.dungeons.nat.api.nbt.Nbt;
import net.toshimichi.dungeons.nat.api.nbt.NbtCompound;
import net.toshimichi.dungeons.nat.api.nbt.NbtException;
import org.bukkit.inventory.ItemStack;

public interface NbtItemStack {

    String getName();

    ItemStack toItemStack();

    boolean hasNbtCompound();

    NbtCompound getNbtCompound();

    NbtCompound newNbtCompound();

    default Nbt getNbt(String name, String label) {
        try {
            if (!hasNbtCompound()) return null;
            Nbt target = getNbtCompound().get(name);
            if (target == null) return null;
            return target.getAsCompound().get(label);
        } catch (NbtException e) {
            return null;
        }
    }

    default void setNbt(String name, String label, Nbt sub) {
        NbtCompound root = newNbtCompound();
        Nbt d1 = root.getOrDefault(name, new LocalNbtCompound());
        if (!(d1 instanceof NbtCompound))
            d1 = new LocalNbtCompound();
        d1.getAsCompound().put(label, sub);
        root.put(name, d1);
    }
}
