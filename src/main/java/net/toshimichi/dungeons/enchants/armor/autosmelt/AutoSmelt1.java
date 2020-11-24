package net.toshimichi.dungeons.enchants.armor.autosmelt;

import net.toshimichi.dungeons.enchants.Enchant;
import net.toshimichi.dungeons.enchants.EnchantType;
import net.toshimichi.dungeons.enchants.Enchanter;
import net.toshimichi.dungeons.enchants.Title;
import net.toshimichi.dungeons.lang.Locale;
import net.toshimichi.dungeons.utils.LocaleBuilder;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class AutoSmelt1 implements Enchant {
    @Override
    public int getId() {
        return 11;
    }

    @Override
    public String getName() {
        return ChatColor.DARK_RED + "UNIQUE! " + ChatColor.BLUE + "Auto Smelt I";
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
        return new LocaleBuilder("enchant.autosmelt.1").locale(locale).build();
    }

    @Override
    public EnchantType[] getEnchantType() {
        return new EnchantType[]{EnchantType.HELMET};
    }

    @Override
    public Title getTitle() {
        return Title.UNIQUE;
    }

    @Override
    public Enchanter getEnchanter(Player player, ItemStack itemStack) {
        return new AutoSmeltEnchanter(this, player, itemStack);
    }
}
