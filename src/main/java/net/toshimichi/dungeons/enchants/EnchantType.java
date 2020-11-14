package net.toshimichi.dungeons.enchants;

import net.toshimichi.dungeons.utils.MaterialUtils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public enum EnchantType {
    SWORD, AXE, BOW, WAND, HELMET, CHESTPLATE, LEGGINGS, BOOTS, SHIELD, ARTIFACT;

    public static EnchantType[] getArmor() {
        return new EnchantType[]{HELMET, CHESTPLATE, LEGGINGS, BOOTS};
    }

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
}
