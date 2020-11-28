package net.toshimichi.dungeons.enchants.sword.sharpness;

import net.toshimichi.dungeons.enchants.Enchant;
import net.toshimichi.dungeons.enchants.EnchantType;
import net.toshimichi.dungeons.enchants.Enchanter;
import net.toshimichi.dungeons.enchants.Title;
import net.toshimichi.dungeons.lang.Locale;
import net.toshimichi.dungeons.utils.LocaleBuilder;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Sharpness1 implements Enchant {
    @Override
    public int getId() {
        return 1;
    }

    @Override
    public String getName() {
        return "Sharpness I";
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
    public String getDescription(Locale locale) {
//        return "ダメージが" + ChatColor.RED + "8%" + ChatColor.GRAY + "上昇";
        return new LocaleBuilder("enchant.sharpness.1").locale(locale).build();
    }

    @Override
    public EnchantType[] getEnchantType() {
        return new EnchantType[]{EnchantType.SWORD, EnchantType.AXE};
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
