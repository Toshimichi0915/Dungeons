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
    BOW("Bow"),
    WAND("Wand"),
    HELMET("Helmet"),
    CHESTPLATE("Chestplate"),
    LEGGINGS("Leggings"),
    BOOTS("Boots"),
    SHIELD("Shield"),
    PICKAXE("Pickaxe"),
    AXE("Axe"),
    SHOVEL("Shovel"),
    HOE("Hoe"),
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

    public static EnchantType[] getTools() {
        return new EnchantType[]{PICKAXE, AXE, SHOVEL, HOE};
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

        if (material.isAir())
            return null;
        else if (MaterialUtils.isSword(material))
            return SWORD;
        else if (MaterialUtils.isHelmet(material))
            return HELMET;
        else if (MaterialUtils.isChestplate(material))
            return CHESTPLATE;
        else if (MaterialUtils.isLeggings(material))
            return LEGGINGS;
        else if (MaterialUtils.isBoots(material))
            return BOOTS;
        else if (material == Material.SHIELD)
            return SHIELD;
        else if (material == Material.BOW)
            return BOW;
        else if (MaterialUtils.isPickaxe(material))
            return PICKAXE;
        else if (MaterialUtils.isAxe(material))
            return AXE;
        else if (MaterialUtils.isShovel(material))
            return SHOVEL;
        else if (MaterialUtils.isHoe(material))
            return HOE;
        else if (material == Material.STICK && meta != null && meta.hasCustomModelData() &&
                meta.getCustomModelData() > 1000)
            return WAND;
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
