package net.toshimichi.dungeons.enchants.sword.sharpness;

import net.toshimichi.dungeons.enchants.Enchant;
import net.toshimichi.dungeons.enchants.EnchantType;
import net.toshimichi.dungeons.enchants.Enchanter;
import net.toshimichi.dungeons.enchants.Title;
import net.toshimichi.dungeons.lang.Locale;
import net.toshimichi.dungeons.utils.LocaleBuilder;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Sharpness2 implements Enchant {
    @Override
    public int getId() {
        return 1;
    }

    @Override
    public String getName() {
        return "Sharpness II";
    }

    @Override
    public int getLevel() {
        return 2;
    }

    @Override
    public int getRarity() {
        return 500;
    }

    @Override
    public String getDescription(Locale locale) {
//        return "ダメージが" + ChatColor.RED + "12%" + ChatColor.GRAY + "増加";
        return new LocaleBuilder("enchant.sharpness.2").locale(locale).build();
    }

    @Override
    public EnchantType[] getEnchantType() {
        return new EnchantType[]{EnchantType.SWORD};
    }

    @Override
    public Title getTitle() {
        return Title.COMMON;
    }

    @Override
    public Enchanter getEnchanter(Player player, ItemStack itemStack) {
        return new SharpnessEnchanter(this, player, itemStack);
    }
}
