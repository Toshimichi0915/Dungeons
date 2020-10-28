package net.toshimichi.dungeons.enchants.bow.megalongbow;

import net.toshimichi.dungeons.enchants.Enchant;
import net.toshimichi.dungeons.enchants.EnchantType;
import net.toshimichi.dungeons.enchants.Enchanter;
import net.toshimichi.dungeons.enchants.Title;
import net.toshimichi.dungeons.lang.Locale;
import net.toshimichi.dungeons.utils.LocaleBuilder;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class MegaLongbow2 implements Enchant {
    @Override
    public int getId() {
        return 5;
    }

    @Override
    public String getName() {
        return ChatColor.LIGHT_PURPLE + "RARE! " + ChatColor.BLUE + "Mega Longbow II";
    }

    @Override
    public int getLevel() {
        return 2;
    }

    @Override
    public int getRarity() {
        return 3;
    }

    @Override
    public String getDescription(Locale locale) {
        return new LocaleBuilder("enchant.megalongbow.2").locale(locale).build();
    }

    @Override
    public EnchantType[] getEnchantType() {
        return new EnchantType[]{EnchantType.BOW};
    }

    @Override
    public Title getTitle() {
        return Title.RARE;
    }

    @Override
    public Enchanter getEnchanter(Player player, ItemStack itemStack) {
        return new MegaLongbowEnchanter(this, player, itemStack);
    }
}
