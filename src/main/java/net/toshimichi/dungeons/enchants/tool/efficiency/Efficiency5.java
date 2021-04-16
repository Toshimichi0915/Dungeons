package net.toshimichi.dungeons.enchants.tool.efficiency;

import net.toshimichi.dungeons.enchants.EnchantType;
import net.toshimichi.dungeons.enchants.Enchanter;
import net.toshimichi.dungeons.enchants.NullEnchanter;
import net.toshimichi.dungeons.enchants.Title;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class Efficiency5 extends Efficiency {
    @Override
    public int getId() {
        return 15;
    }

    @Override
    public String getName() {
        return "Efficiency V";
    }

    @Override
    public int getLevel() {
        return 5;
    }

    @Override
    public int getRarity() {
        return 400;
    }

    @Override
    public EnchantType[] getEnchantType() {
        return EnchantType.getTools();
    }

    @Override
    public Title getTitle() {
        return Title.COMMON;
    }

    @Override
    public Enchanter getEnchanter(Player player, ItemStack itemStack) {
        return new NullEnchanter(this, player, itemStack);
    }

    @Override
    public Map<Enchantment, Integer> getEnchantments() {
        HashMap<Enchantment, Integer> map = new HashMap<>();
        map.put(Enchantment.DIG_SPEED, 5);
        return map;
    }
}
