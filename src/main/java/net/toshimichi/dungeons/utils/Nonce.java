package net.toshimichi.dungeons.utils;

import net.toshimichi.dungeons.nat.api.NbtItemStack;
import net.toshimichi.dungeons.nat.api.NbtItemStackFactory;
import net.toshimichi.dungeons.nat.api.nbt.LocalNbtLong;
import net.toshimichi.dungeons.nat.api.nbt.Nbt;
import net.toshimichi.dungeons.nat.api.nbt.NbtLong;
import org.apache.commons.lang.math.RandomUtils;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;

/**
 * アイテムの固有番号を管理します.
 */
public class Nonce {

    private static final NbtItemStackFactory factory = Bukkit.getServicesManager().load(NbtItemStackFactory.class);

    /**
     * アイテムの固有番号を設定します.
     *
     * @param itemStack アイテム
     * @param nonce     固有番号
     */
    public static void setNonce(ItemStack itemStack, long nonce) {
        NbtItemStack nbtItem = factory.newNbtItemStack(itemStack);
        nbtItem.setNbt("dungeons", "nonce", new LocalNbtLong(nonce));
        itemStack.setItemMeta(nbtItem.toItemStack().getItemMeta());
    }

    /**
     * アイテムの固有番号を生成して設定します.
     * 既にアイテムが固有番号を持っている場合は新しく固有番号は設定されません.
     * 生成された固有番号で {@code 0} は存在しません.
     *
     * @param itemStack アイテム
     */
    public static void newNonce(ItemStack itemStack) {
        if (hasNonce(itemStack)) return;
        while (true) {
            long nonce = RandomUtils.nextLong();
            if (nonce == 0) continue;
            setNonce(itemStack, nonce);
            break;
        }
    }

    /**
     * アイテムの固有番号を返します.
     * 固有番号が存在しない場合には {@code 0} が返されます.
     *
     * @param itemStack アイテム
     * @return 固有番号
     */
    public static long getNonce(ItemStack itemStack) {
        if (!hasNonce(itemStack)) return 0;
        Nbt nbt = factory.newNbtItemStack(itemStack).getNbt("dungeons", "nonce");
        if (!(nbt instanceof NbtLong)) return 0;
        return nbt.getAsLong();
    }

    public static boolean hasNonce(ItemStack itemStack) {
        return factory.newNbtItemStack(itemStack).getNbt("dungeons", "nonce") != null;
    }
}
