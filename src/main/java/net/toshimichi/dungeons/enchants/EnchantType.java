package net.toshimichi.dungeons.enchants;

import net.toshimichi.dungeons.utils.MaterialUtils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;

/**
 * エンチャントを付与できるアイテムの種類を表します.
 */
public enum EnchantType {
    SWORD("Sword"),
    AXE("Axe"),
    BOW("Bow"),
    WAND("Wand"),
    HELMET("Helmet"),
    CHESTPLATE("Chestplate"),
    LEGGINGS("Leggings"),
    BOOTS("Boots"),
    SHIELD("Shield"),
    ARTIFACT("Artifact");

    private final String prefix;

    EnchantType(String prefix) {
        this.prefix = prefix;
    }

    /**
     * 表示名を返します.
     *
     * @return 表示名
     */
    public String getPrefix() {
        return prefix;
    }

    /**
     * 防具の一覧を返します.
     *
     * @return 防具の一覧
     */
    public static EnchantType[] getArmor() {
        return new EnchantType[]{HELMET, CHESTPLATE, LEGGINGS, BOOTS};
    }

    /**
     * アイテムに付与できるエンチャントの種類を返します.
     *
     * @param itemStack アイテム
     * @return 付与できるエンチャントの種類
     */
    public static EnchantType getEnchantType(ItemStack itemStack) {
        if (itemStack == null)
            return null;
        Material material = itemStack.getType();

        ItemMeta meta = null;
        if (itemStack.hasItemMeta())
            meta = itemStack.getItemMeta();

        if (material.isAir() || material.isBlock())
            return null;
        else if (MaterialUtils.isSword(material))
            return EnchantType.SWORD;
        else if (MaterialUtils.isAxe(material))
            return EnchantType.AXE;
        else if (MaterialUtils.isHelmet(material))
            return EnchantType.HELMET;
        else if (MaterialUtils.isChestplate(material))
            return EnchantType.CHESTPLATE;
        else if (MaterialUtils.isLeggings(material))
            return EnchantType.LEGGINGS;
        else if (MaterialUtils.isBoots(material))
            return EnchantType.BOOTS;
        else if (material == Material.SHIELD)
            return EnchantType.SHIELD;
        else if (material == Material.BOW)
            return EnchantType.BOW;
        else if (material == Material.STICK && meta != null && meta.hasCustomModelData() &&
                meta.getCustomModelData() > 1000)
            return EnchantType.WAND;
        else
            return EnchantType.ARTIFACT;
    }

    /**
     * アイテムが指定された {@link EnchantType} に含まれるか調べます.
     *
     * @param itemStack 調べるアイテム
     * @param type      許容される {@link EnchantType} の一覧
     * @return 含まれる場合は {@code true} そうでなければ {@code false}
     */
    public static boolean matchEnchantType(ItemStack itemStack, EnchantType[] type) {
        List<EnchantType> list = Arrays.asList(type);
        return list.contains(getEnchantType(itemStack));
    }
}
