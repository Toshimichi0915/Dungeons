package net.toshimichi.dungeons.enchants.sword.lifesteal;

import net.toshimichi.dungeons.enchants.Enchant;
import net.toshimichi.dungeons.enchants.EnchantType;
import net.toshimichi.dungeons.enchants.Enchanter;
import net.toshimichi.dungeons.enchants.Title;
import net.toshimichi.dungeons.lang.Locale;
import net.toshimichi.dungeons.utils.LocaleBuilder;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Lifesteal1 implements Enchant {
    @Override
    public int getId() {
        return 10;
    }

    @Override
    public String getName() {
        return "Lifesteal I";
    }

    @Override
    public int getLevel() {
        return 1;
    }

    @Override
    public int getRarity() {
        return 200;
    }

    @Override
    public String getDescription(Locale locale) {
        return new LocaleBuilder("enchant.lifesteal.1").locale(locale).build();
    }

    @Override
    public EnchantType[] getEnchantType() {
        return new EnchantType[]{EnchantType.SWORD, EnchantType.AXE};
    }

    @Override
    public Title getTitle() {
        return Title.STRONG;
    }

    @Override
    public Enchanter getEnchanter(Player player, ItemStack itemStack) {
        return new LifestealEnchanter(this, player, itemStack);
    }
}
