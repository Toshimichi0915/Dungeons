package net.toshimichi.dungeons.enchants.sword.fixed;

import net.toshimichi.dungeons.enchants.Enchant;
import net.toshimichi.dungeons.enchants.EnchantType;
import net.toshimichi.dungeons.enchants.Enchanter;
import net.toshimichi.dungeons.enchants.Title;
import net.toshimichi.dungeons.lang.Locale;
import net.toshimichi.dungeons.utils.LocaleBuilder;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Fixed1 implements Enchant {
    @Override
    public int getId() {
        return 26;
    }

    @Override
    public String getName() {
        return ChatColor.DARK_RED + "UNIQUE! " + ChatColor.BLUE + "Fixed I";
    }

    @Override
    public int getLevel() {
        return 1;
    }

    @Override
    public int getRarity() {
        return 3;
    }

    @Override
    public String getDescription(ItemStack itemStack, Locale locale) {
        return new LocaleBuilder("enchant.fixed.1").locale(locale).build();
    }

    @Override
    public EnchantType[] getEnchantType() {
        return new EnchantType[]{EnchantType.AXE};
    }

    @Override
    public Title getTitle() {
        return Title.UNIQUE;
    }

    @Override
    public Enchanter getEnchanter(Player player, ItemStack itemStack) {
        return new FixedEnchanter(this, player, itemStack);
    }
}
