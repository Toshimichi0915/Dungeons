package net.toshimichi.dungeons.enchants.shield;

import net.toshimichi.dungeons.enchants.Enchant;
import net.toshimichi.dungeons.enchants.EnchantType;
import net.toshimichi.dungeons.enchants.Enchanter;
import net.toshimichi.dungeons.enchants.Title;
import net.toshimichi.dungeons.lang.Locale;
import net.toshimichi.dungeons.utils.LocaleBuilder;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class SpringInside2 implements Enchant {
    @Override
    public int getId() {
        return 6;
    }

    @Override
    public String getName() {
        return "Spring Inside II";
    }

    @Override
    public int getLevel() {
        return 2;
    }

    @Override
    public int getRarity() {
        return 10;
    }

    @Override
    public String getDescription(Locale locale) {
        return new LocaleBuilder("enchant.springinside.2").locale(locale).build();
    }

    @Override
    public EnchantType[] getEnchantType() {
        return new EnchantType[]{EnchantType.SHIELD};
    }

    @Override
    public Title getTitle() {
        return Title.STRONG;
    }

    @Override
    public Enchanter getEnchanter(Player player, ItemStack itemStack) {
        return new SpringInsideEnchanter(this, player, itemStack);
    }
}