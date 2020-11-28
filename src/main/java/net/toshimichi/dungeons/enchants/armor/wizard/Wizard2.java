package net.toshimichi.dungeons.enchants.armor.wizard;

import net.toshimichi.dungeons.enchants.Enchant;
import net.toshimichi.dungeons.enchants.EnchantType;
import net.toshimichi.dungeons.enchants.Enchanter;
import net.toshimichi.dungeons.enchants.Title;
import net.toshimichi.dungeons.lang.Locale;
import net.toshimichi.dungeons.utils.LocaleBuilder;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Wizard2 implements Enchant {
    @Override
    public int getId() {
        return 4;
    }

    @Override
    public String getName() {
        return "Wizard II";
    }

    @Override
    public int getLevel() {
        return 2;
    }

    @Override
    public int getRarity() {
        return 30;
    }

    @Override
    public String getDescription(ItemStack itemStack, Locale locale) {
        return new LocaleBuilder("enchant.wizard.2").locale(locale).build();
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
