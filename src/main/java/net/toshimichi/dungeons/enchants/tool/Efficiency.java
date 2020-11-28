package net.toshimichi.dungeons.enchants.tool;

import net.toshimichi.dungeons.enchants.Enchant;
import net.toshimichi.dungeons.lang.Locale;
import net.toshimichi.dungeons.utils.LocaleBuilder;
import org.bukkit.inventory.ItemStack;

abstract public class Efficiency implements Enchant {

    @Override
    public String getDescription(ItemStack itemStack, Locale locale) {
        double modifier;
        int level = getLevel();
        String name = itemStack.getType().name();
        if (level == 1)
            modifier = 100;
        else if (level == 2)
            modifier = 250;
        else if (level == 3)
            modifier = 500;
        else if (level == 4)
            modifier = 850;
        else
            modifier = 1300;

        if (name.startsWith("STONE"))
            modifier /= 2;
        else if (name.startsWith("IRON"))
            modifier /= 3;
        else if (name.startsWith("DIAMOND") || name.startsWith("NETHERITE"))
            modifier /= 4;
        else if (name.startsWith("GOLDEN"))
            modifier /= 6;

        return new LocaleBuilder("enchant.efficiency." + level)
                .locale(locale)
                .replace("{modifier}", Integer.toString((int)modifier))
                .build();
    }
}
