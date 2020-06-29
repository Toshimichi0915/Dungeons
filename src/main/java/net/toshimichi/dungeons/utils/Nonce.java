package net.toshimichi.dungeons.utils;

import net.toshimichi.dungeons.DungeonsPlugin;
import org.apache.commons.lang.math.RandomUtils;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

/**
 * アイテムの固有番号を管理します.
 */
public class Nonce {

    private static final NamespacedKey key = new NamespacedKey(DungeonsPlugin.getPlugin(), "nonce");

    /**
     * アイテムの固有番号を設定します.
     *
     * @param itemStack アイテム
     * @param nonce     固有番号
     */
    public static void setNonce(ItemStack itemStack, long nonce) {
        ItemMeta meta = itemStack.getItemMeta();
        if(meta == null) return;
        PersistentDataContainer container = meta.getPersistentDataContainer();
        container.set(key, PersistentDataType.LONG, nonce);
        itemStack.setItemMeta(meta);
    }

    /**
     * アイテムの固有番号を生成して設定します.
     * 既にアイテムが固有番号を持っている場合は新しく固有番号は設定されません.
     * 生成された固有番号で {@code 0} は存在しません.
     *
     * @param itemStack アイテム
     */
    public static void newNonce(ItemStack itemStack) {
        if(hasNonce(itemStack)) return;
        while(true) {
            long nonce = RandomUtils.nextLong();
            if(nonce == 0) continue;
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
        ItemMeta meta = itemStack.getItemMeta();
        if(meta == null) throw new IllegalArgumentException("Item cannot have nonce");
        PersistentDataContainer container = meta.getPersistentDataContainer();
        return container.get(key, PersistentDataType.LONG);
    }

    public static boolean hasNonce(ItemStack itemStack) {
        ItemMeta meta = itemStack.getItemMeta();
        if(meta == null)return false;
        PersistentDataContainer container = meta.getPersistentDataContainer();
        return container.has(key, PersistentDataType.LONG);
    }
}
