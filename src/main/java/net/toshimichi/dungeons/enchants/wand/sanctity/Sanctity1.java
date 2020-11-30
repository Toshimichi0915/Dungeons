package net.toshimichi.dungeons.enchants.wand.sanctity;

import net.toshimichi.dungeons.enchants.Enchant;
import net.toshimichi.dungeons.enchants.EnchantType;
import net.toshimichi.dungeons.enchants.Enchanter;
import net.toshimichi.dungeons.enchants.Title;
import net.toshimichi.dungeons.lang.Locale;
import net.toshimichi.dungeons.utils.LocaleBuilder;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Sanctity1 implements Enchant {
    @Override
    public int getId() {
        return 3;
    }

    @Override
    public String getName() {
        return ChatColor.LIGHT_PURPLE + "RARE! " + ChatColor.BLUE + "Sanctity I";
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
    public String getDescription(ItemStack itemStack, Locale locale) {
//        return  "体力を" + ChatColor.RED + "1❤" + ChatColor.GRAY + "回復";
        return new LocaleBuilder("enchant.sanctity.1").locale(locale).build();
    }

    @Override
    public EnchantType[] getEnchantType() {
        return new EnchantType[]{EnchantType.WAND};
    }

    @Override
    public Title getTitle() {
        return Title.RARE;
    }

    @Override
    public Enchanter getEnchanter(Player player, ItemStack itemStack) {
        return new SanctityEnchanter(this, player, itemStack);
    }
}
