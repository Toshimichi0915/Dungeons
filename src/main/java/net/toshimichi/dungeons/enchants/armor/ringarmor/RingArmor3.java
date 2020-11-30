package net.toshimichi.dungeons.enchants.armor.ringarmor;

import net.toshimichi.dungeons.enchants.Enchant;
import net.toshimichi.dungeons.enchants.EnchantType;
import net.toshimichi.dungeons.enchants.Enchanter;
import net.toshimichi.dungeons.enchants.Title;
import net.toshimichi.dungeons.lang.Locale;
import net.toshimichi.dungeons.utils.LocaleBuilder;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class RingArmor3 implements Enchant {
    @Override
    public int getId() {
        return 21;
    }

    @Override
    public String getName() {
        return "Ring Armor III";
    }

    @Override
    public int getLevel() {
        return 3;
    }

    @Override
    public int getRarity() {
        return 900;
    }

    @Override
    public String getDescription(ItemStack itemStack, Locale locale) {
        return new LocaleBuilder("enchant.ringarmor.3").locale(locale).build();
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
        return new RingArmorEnchanter(this, player, itemStack);
    }
}
