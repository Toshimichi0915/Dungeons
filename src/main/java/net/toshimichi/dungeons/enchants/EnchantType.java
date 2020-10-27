package net.toshimichi.dungeons.enchants;

public enum EnchantType {
    SWORD, BOW, WAND, HELMET, CHEST_PLATE, LEGGINGS, BOOTS, SHIELD, ARTIFACT;

    public static EnchantType[] getArmor() {
        return new EnchantType[]{HELMET, CHEST_PLATE, LEGGINGS, BOOTS};
    }
}
