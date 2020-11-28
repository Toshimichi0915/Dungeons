package net.toshimichi.dungeons.enchants.armor.protection;

import net.toshimichi.dungeons.enchants.Enchant;
import net.toshimichi.dungeons.enchants.EnchantType;
import net.toshimichi.dungeons.enchants.Enchanter;
import net.toshimichi.dungeons.enchants.Title;
import net.toshimichi.dungeons.lang.Locale;
import net.toshimichi.dungeons.utils.LocaleBuilder;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Protection1 implements Enchant {
    @Override
    public int getId() {
        return 12;
    }

    @Override
    public String getName() {
        return "Protection I";
    }

    @Override
    public int getLevel() {
        return 1;
    }

    @Override
    public int getRarity() {
        return 600;
    }

    @Override
    public String getDescription(ItemStack itemStack, Locale locale) {
        return new LocaleBuilder("enchant.protection.1").locale(locale).build();
    }

    @Override
    public EnchantType[] getEnchantType() {
        return EnchantType.getArmor();
    }

    @Override
    public Title getTitle() {
        return Title.COMMON;
    }

    @Override
    public Enchanter getEnchanter(Player player, ItemStack itemStack) {
        return new ProtectionEnchanter(this, player, itemStack);
    }
}
