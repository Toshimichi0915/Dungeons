package net.toshimichi.dungeons.enchants.armor.wizard;

import net.toshimichi.dungeons.enchants.Enchant;
import net.toshimichi.dungeons.enchants.EnchantType;
import net.toshimichi.dungeons.enchants.Enchanter;
import net.toshimichi.dungeons.enchants.Title;
import net.toshimichi.dungeons.lang.Locale;
import net.toshimichi.dungeons.utils.LocaleBuilder;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Wizard1 implements Enchant {
    @Override
    public int getId() {
        return 4;
    }

    @Override
    public String getName() {
        return "Wizard I";
    }

    @Override
    public int getLevel() {
        return 1;
    }

    @Override
    public int getRarity() {
        return 300;
    }

    @Override
    public String getDescription(ItemStack itemStack, Locale locale) {
        return new LocaleBuilder("enchant.wizard.1").locale(locale).build();
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
        return new WizardEnchanter(this, player, itemStack);
    }
}
