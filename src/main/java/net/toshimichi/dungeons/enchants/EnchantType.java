package net.toshimichi.dungeons.enchants;

public enum EnchantType {
    SWORD, BOW, WAND, HELMET, CHESTPLATE, LEGGINGS, BOOTS, ARTIFACT;

    public static EnchantType[] getArmor() {
        return new EnchantType[]{HELMET, CHESTPLATE, LEGGINGS, BOOTS};
    }
}
