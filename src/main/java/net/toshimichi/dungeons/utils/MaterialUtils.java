package net.toshimichi.dungeons.utils;

import org.bukkit.Material;

public class MaterialUtils {

    public static boolean isSword(Material material) {
        return material.name().endsWith("SWORD");
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

    public static boolean isPickaxe(Material material) {
        return material.name().endsWith("PICKAXE");
    }

    public static boolean isAxe(Material material) {
        return material.name().endsWith("AXE");
    }

    public static boolean isShovel(Material material) {
        return material.name().endsWith("SHOVEL");
    }

    public static boolean isHoe(Material material) {
        return material.name().endsWith("HOE");
    }

    public static boolean isArmor(Material material) {
        return isHelmet(material) ||
                isChestplate(material) ||
                isLeggings(material) ||
                isBoots(material);
    }
}
