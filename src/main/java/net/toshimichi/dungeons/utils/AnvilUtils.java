package net.toshimichi.dungeons.utils;

import net.toshimichi.dungeons.Dungeons;
import net.toshimichi.dungeons.enchants.Enchant;
import net.toshimichi.dungeons.enchants.EnchantManager;
import org.bukkit.inventory.ItemStack;

import java.util.HashSet;

public class AnvilUtils {
    public static ItemStack combine(ItemStack a, ItemStack b) {
        EnchantManager manager = Dungeons.getInstance().getEnchantManager();
        HashSet<Enchant> newEnchants = new HashSet<>(manager.getEnchants(a));
        for (Enchant e : manager.getEnchants(b)) {
            Enchant match = newEnchants.stream().filter(p -> p.getId() == e.getId()).findAny().orElse(null);
            if (match == null) {
                newEnchants.add(e);
                continue;
            }
            if (e.getLevel() > match.getLevel()) {
                newEnchants.remove(match);
                newEnchants.add(e);
            }
        }

        ItemStack result = new ItemStack(a.getType());
        manager.setEnchants(result, newEnchants.toArray(new Enchant[0]));
        manager.setLives(result, Math.min(manager.getLives(a), manager.getLives(b)));
        manager.setMaxLives(result, Math.min(manager.getLives(a), manager.getLives(b)));
        manager.setTier(result, Math.max(manager.getTier(a), manager.getTier(b)));
        return result;
    }
}
