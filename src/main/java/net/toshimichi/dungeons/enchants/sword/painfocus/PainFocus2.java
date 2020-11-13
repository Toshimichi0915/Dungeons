package net.toshimichi.dungeons.enchants.sword.painfocus;

import net.toshimichi.dungeons.enchants.Enchant;
import net.toshimichi.dungeons.enchants.EnchantType;
import net.toshimichi.dungeons.enchants.Enchanter;
import net.toshimichi.dungeons.enchants.Title;
import net.toshimichi.dungeons.lang.Locale;
import net.toshimichi.dungeons.utils.LocaleBuilder;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PainFocus2 implements Enchant {
    @Override
    public int getId() {
        return 9;
    }

    @Override
    public String getName() {
        return "Pain Focus I";
    }

    @Override
    public int getLevel() {
        return 2;
    }

    @Override
    public int getRarity() {
        return 150;
    }

    @Override
    public String getDescription(Locale locale) {
        return new LocaleBuilder("enchant.painfocus.1").locale(locale).build();
    }

    @Override
    public EnchantType[] getEnchantType() {
        return new EnchantType[]{EnchantType.SWORD};
    }

    @Override
    public Title getTitle() {
        return Title.STRONG;
    }

    @Override
    public Enchanter getEnchanter(Player player, ItemStack itemStack) {
        return new PainFocusEnchanter(this, player, itemStack);
    }
}
