package net.toshimichi.dungeons.utils;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.WeakHashMap;

/**
 * クールダウンの管理を行います.
 */
public class SilentCooldown {

    private static final WeakHashMap<ItemStack, HashMap<String, Integer>> cooldownMap = new WeakHashMap<>();

    /**
     * クールダウンを返します.
     *
     * @param itemStack    アイテム
     * @param cooldownType クールダウンの種類
     * @return クールダウン. 存在しない場合は {@code -1}
     */
    public static int getCooldown(ItemStack itemStack, String cooldownType) {
        if (itemStack == null) return -1;
        HashMap<String, Integer> map = cooldownMap.getOrDefault(itemStack, new HashMap<>());
        return map.getOrDefault(cooldownType, -1);
    }

    /**
     * クールダウンを設定します.
     *
     * @param itemStack    アイテム
     * @param cooldownType クールダウンの種類
     * @param cooldown     クールダウン. {@code -1} を指定した場合はクールダウンの削除. そうでない場合は {@code 0} 以上
     * @throws IllegalArgumentException クールダウンの値が {@code -1} 以下の場合
     */
    public static void setCooldown(ItemStack itemStack, String cooldownType, int cooldown) {
        if (cooldown < -1)
            throw new IllegalArgumentException("クールダウンは-1以上を指定してください");
        if (itemStack == null) return;
        HashMap<String, Integer> map = cooldownMap.getOrDefault(itemStack, new HashMap<>());
        if (cooldown == -1) {
            map.remove(cooldownType);
        } else {
            map.put(cooldownType, cooldown);
        }
        cooldownMap.put(itemStack, map);
    }

    /**
     * クールダウンの値を {@code 1} 減らします
     *
     * @param itemStack    アイテム
     * @param cooldownType クールダウンの種類
     */
    public static void reduceCooldown(ItemStack itemStack, String cooldownType) {
        int coolDown = getCooldown(itemStack, cooldownType);
        if (coolDown == -1 || coolDown == 0) return;
        setCooldown(itemStack, cooldownType, coolDown - 1);
    }
}
