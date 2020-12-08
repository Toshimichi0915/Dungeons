package net.toshimichi.dungeons.enchants.tool.fortune;

import net.toshimichi.dungeons.enchants.*;
import net.toshimichi.dungeons.lang.Locale;
import net.toshimichi.dungeons.utils.LocaleBuilder;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class Fortune3 implements Enchant {
    @Override
    public int getId() {
        return 25;
    }

    @Override
    public String getName() {
        return "Fortune III";
    }

    @Override
    public int getLevel() {
        return 3;
    }

    @Override
    public int getRarity() {
        return 200;
    }

    @Override
    public String getDescription(ItemStack itemStack, Locale locale) {
        return new LocaleBuilder("enchant.fortune.3").locale(locale).build();
    }

    @Override
    public EnchantType[] getEnchantType() {
        return EnchantType.getTools();
    }

    @Override
    public Title getTitle() {
        return Title.STRONG;
    }

    @Override
    public Enchanter getEnchanter(Player player, ItemStack itemStack) {
        return new NullEnchanter(this, player, itemStack);
    }

    @Override
    public Map<Enchantment, Integer> getEnchantments() {
        HashMap<Enchantment, Integer> result = new HashMap<>();
        result.put(Enchantment.LOOT_BONUS_BLOCKS, 3);
        return result;
    }
}
