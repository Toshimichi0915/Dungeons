package net.toshimichi.dungeons.enchants.armor;

import net.toshimichi.dungeons.enchants.Enchant;
import net.toshimichi.dungeons.enchants.EnchantType;
import net.toshimichi.dungeons.enchants.Enchanter;
import net.toshimichi.dungeons.enchants.Title;
import net.toshimichi.dungeons.lang.Locale;
import net.toshimichi.dungeons.utils.LocaleBuilder;

public class Wizard3 implements Enchant {
    @Override
    public int getId() {
        return 4;
    }

    @Override
    public String getName() {
        return "Wizard III";
    }

    @Override
    public int getLevel() {
        return 3;
    }

    @Override
    public int getRarity() {
        return 30;
    }

    @Override
    public String getDescription(Locale locale) {
        return new LocaleBuilder("enchant.wizard.3").locale(locale).build();
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
    public Class<? extends Enchanter> getEnchanter() {
        return WizardEnchanter.class;
    }
}
