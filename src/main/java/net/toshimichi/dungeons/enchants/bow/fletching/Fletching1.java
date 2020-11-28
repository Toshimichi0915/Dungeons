package net.toshimichi.dungeons.enchants.bow.fletching;

import net.toshimichi.dungeons.enchants.Enchant;
import net.toshimichi.dungeons.enchants.EnchantType;
import net.toshimichi.dungeons.enchants.Enchanter;
import net.toshimichi.dungeons.enchants.Title;
import net.toshimichi.dungeons.lang.Locale;
import net.toshimichi.dungeons.utils.LocaleBuilder;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Fletching1 implements Enchant {
    @Override
    public int getId() {
        return 8;
    }

    @Override
    public String getName() {
        return "Fletching I";
    }

    @Override
    public int getLevel() {
        return 1;
    }

    @Override
    public int getRarity() {
        return 1000;
    }

    @Override
    public String getDescription(ItemStack itemStack, Locale locale) {
        return new LocaleBuilder("enchant.fletching.1").locale(locale).build();
    }

    @Override
    public EnchantType[] getEnchantType() {
        return new EnchantType[]{EnchantType.BOW};
    }

    @Override
    public Title getTitle() {
        return Title.COMMON;
    }

    @Override
    public Enchanter getEnchanter(Player player, ItemStack itemStack) {
        return new FletchingEnchanter(this, player, itemStack);
    }
}
