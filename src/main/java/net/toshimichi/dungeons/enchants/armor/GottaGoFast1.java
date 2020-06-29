package net.toshimichi.dungeons.enchants.armor;

import net.toshimichi.dungeons.enchants.Enchant;
import net.toshimichi.dungeons.enchants.EnchantType;
import net.toshimichi.dungeons.enchants.Enchanter;
import net.toshimichi.dungeons.enchants.Title;
import net.toshimichi.dungeons.enchants.sword.SharpnessEnchanter;
import net.toshimichi.dungeons.lang.Locale;
import net.toshimichi.dungeons.utils.LocaleBuilder;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class GottaGoFast1 implements Enchant {
    @Override
    public int getId() {
        return 2;
    }

    @Override
    public String getName() {
        return "Gotta Go Fast I";
    }

    @Override
    public int getLevel() {
        return 1;
    }

    @Override
    public int getRarity() {
        return 50;
    }

    @Override
    public String getDescription(Locale locale) {
//        return "移動速度が" + ChatColor.YELLOW + "5%" + ChatColor.GRAY + "増加";
        return new LocaleBuilder("enchant.gottagofast.1").locale(locale).build();
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
        return new GottaGoFastEnchanter(this, player, itemStack);
    }
}
