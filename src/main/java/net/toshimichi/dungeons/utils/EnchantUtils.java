package net.toshimichi.dungeons.utils;

import net.toshimichi.dungeons.Dungeons;
import net.toshimichi.dungeons.enchants.Enchant;
import net.toshimichi.dungeons.enchants.EnchantManager;
import net.toshimichi.dungeons.enchants.EnchantType;
import net.toshimichi.dungeons.enchants.Title;
import org.apache.commons.lang3.RandomUtils;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public class EnchantUtils {

    /**
     * エンチャントにかかる費用を返します.
     *
     * @param itemStack エンチャントするアイテム
     * @return 費用
     */
    public static int getCost(ItemStack itemStack) {
        int tier = Dungeons.getInstance().getEnchantManager().getTier(itemStack);
        if (tier < 0) return -1;
        else if (tier == 0) return 2000;
        else if (tier == 1) return 4000;
        else if (tier == 2) return 8000;
        else return 16000;
    }

    /**
     * 指定されたアイテムをエンチャントします.
     *
     * @param itemStack エンチャントするアイテム
     */
    public static void enchant(ItemStack itemStack) {
        EnchantManager manager = Dungeons.getInstance().getEnchantManager();
        int tier = Dungeons.getInstance().getEnchantManager().getTier(itemStack);
        Lottery<Enchant> lottery = new Lottery<>();
        if (tier == 0) {
            manager.getAllEnchants().stream()
                    .filter(p -> p.getTitle() == Title.COMMON)
                    .filter(p -> EnchantType.matchEnchantType(itemStack, p.getEnchantType()))
                    .filter(p -> p.getLevel() < 3)
                    .forEach(a -> lottery.add(a.getRarity(), a));
            if (RandomUtils.nextInt() % 4 != 0) {
                manager.setEnchants(itemStack, lottery.draw());
            } else {
                Enchant enchant = lottery.draw();
                Enchant secondary;
                do {
                    secondary = lottery.draw();
                } while (!enchant.equals(secondary));
                manager.setEnchants(itemStack, enchant, secondary);
            }
            manager.setTier(itemStack, 1);
            int lives = RandomUtils.nextInt(3, 20);
            manager.setMaxLives(itemStack, lives);
            manager.setLives(itemStack, lives);
        } else {
            List<Enchant> enchants = new ArrayList<>(manager.getEnchants(itemStack));
            enchants.sort(Comparator.comparingInt(Enchant::getId));
            int modifier;
            if (tier >= 2)
                modifier = RandomUtils.nextInt() % 30;
            else
                modifier = RandomUtils.nextInt() % 20;

            if (RandomUtils.nextInt() % 2 == 0) {
                Enchant target;
                Enchant after;
                while (true) {
                    target = enchants.get(RandomUtils.nextInt() % enchants.size());
                    Enchant targetCopy = target;
                    List<Enchant> upgrades = manager.getAllEnchants().stream()
                            .filter(p -> p.getId() == targetCopy.getId())
                            .filter(p -> p.getLevel() > targetCopy.getLevel())
                            .collect(Collectors.toList());
                    if (upgrades.isEmpty())
                        continue;
                    upgrades.forEach(a -> lottery.add(a.getRarity(), a));
                    after = lottery.draw();
                    break;
                }
                enchants.remove(target);
                enchants.add(after);
            } else {
                for (Enchant e : manager.getAllEnchants()) {
                    if (!EnchantType.matchEnchantType(itemStack, e.getEnchantType())) continue;
                    if (tier >= 2) {
                        lottery.add(e.getRarity(), e);
                    } else {
                        if (e.getTitle() == Title.UNIQUE) continue;
                        if (e.getTitle() == Title.RARE)
                            lottery.add(e.getRarity() / 2, e);
                        else
                            lottery.add(e.getRarity(), e);
                    }
                }
                AtomicReference<Enchant> ref = new AtomicReference<>();
                do {
                    ref.set(lottery.draw());
                } while (enchants.stream().anyMatch(p -> ref.get().getId() == p.getId()));
                enchants.add(ref.get());
            }
            manager.setEnchants(itemStack, enchants.toArray(new Enchant[0]));
            manager.setMaxLives(itemStack, manager.getMaxLives(itemStack) + modifier);
            manager.setLives(itemStack, manager.getLives(itemStack) + modifier);
            manager.setTier(itemStack, manager.getTier(itemStack) + 1);
        }
    }

}
