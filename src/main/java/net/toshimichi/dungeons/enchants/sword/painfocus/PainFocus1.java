package net.toshimichi.dungeons.enchants.sword.painfocus;

import net.toshimichi.dungeons.enchants.Enchant;
import net.toshimichi.dungeons.enchants.EnchantType;
import net.toshimichi.dungeons.enchants.Enchanter;
import net.toshimichi.dungeons.enchants.Title;
import net.toshimichi.dungeons.lang.Locale;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PainFocus1 implements Enchant {
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
        return 1;
    }

    @Override
    public int getRarity() {
        return 300;
    }

    @Override
    public String getDescription(Locale locale) {
        return null;
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
        return new PainFocusEnchanter(this, player, itemStack);
    }
}
