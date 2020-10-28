package net.toshimichi.dungeons.utils;

import net.toshimichi.dungeons.enchants.EnchantType;
import org.bukkit.Material;

public class MaterialUtils {

    public static boolean isSword(Material material) {
        return material.name().endsWith("SWORD");
    }

    public static boolean isAxe(Material material) {
        return material.name().endsWith("AXE");
    }

    public static boolean isHelmet(Material material) {
        return material.name().endsWith("HELMET");
    }

    public static boolean isChestplate(Material material) {
        return material.name().endsWith("CHESTPLATE");
    }

    public static boolean isLeggings(Material material) {
        return material.name().endsWith("LEGGINGS");
    }

    public static boolean isBoots(Material material) {
        return material.name().endsWith("BOOTS");
    }

    public static boolean isArmor(Material material) {
        return isHelmet(material) ||
                isChestplate(material) ||
                isLeggings(material) ||
                isBoots(material);
    }
}
