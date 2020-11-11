package net.toshimichi.dungeons.utils;

import net.toshimichi.dungeons.DungeonsPlugin;
import net.toshimichi.dungeons.enchants.Enchant;
import net.toshimichi.dungeons.enchants.EnchantManager;
import net.toshimichi.dungeons.enchants.EnchantType;
import net.toshimichi.dungeons.enchants.Title;
import org.apache.commons.lang3.RandomUtils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
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
        int tier = DungeonsPlugin.getEnchantManager().getTier(itemStack);
        if (tier < 0) return -1;
        else if (tier == 0) return 2000;
        else if (tier == 1) return 4000;
        else if (tier == 2) return 8000;
        else return 16000;
    }

    /**
     * アイテムに付与できるエンチャントの種類を返します.
     *
     * @param itemStack アイテム
     * @return 付与できるエンチャントの種類
     */
    public static EnchantType getEnchantType(ItemStack itemStack) {
        if (itemStack == null) return null;
        else if (MaterialUtils.isSword(itemStack.getType()))
            return EnchantType.SWORD;
        else if (MaterialUtils.isAxe(itemStack.getType()))
            return EnchantType.AXE;
        else if (MaterialUtils.isHelmet(itemStack.getType()))
            return EnchantType.HELMET;
        else if (MaterialUtils.isChestplate(itemStack.getType()))
            return EnchantType.CHESTPLATE;
        else if (MaterialUtils.isLeggings(itemStack.getType()))
            return EnchantType.LEGGINGS;
        else if (MaterialUtils.isBoots(itemStack.getType()))
            return EnchantType.BOOTS;
        else if (itemStack.getType() == Material.SHIELD)
            return EnchantType.SHIELD;

        if (!itemStack.hasItemMeta()) return null;
        ItemMeta meta = itemStack.getItemMeta();
        if (!meta.hasCustomModelData()) return null;
        int data = meta.getCustomModelData();
        if (itemStack.getType() == Material.STICK && data >= 1000 && data <= 2000)
            return EnchantType.WAND;
        return null;
    }

    /**
     * アイテムが指定された {@link EnchantType} に含まれるか調べます.
     *
     * @param itemStack 調べるアイテム
     * @param type      許容される {@link EnchantType} の一覧
     * @return 含まれる場合は {@code true} そうでなければ {@code false}
     */
    public static boolean matchEnchantType(ItemStack itemStack, EnchantType[] type) {
        List<EnchantType> list = Arrays.asList(type);
        return list.contains(getEnchantType(itemStack));
    }

    /**
     * 指定されたアイテムをエンチャントします.
     *
     * @param itemStack エンチャントするアイテム
     */
    public static void enchant(ItemStack itemStack) {
        EnchantManager manager = DungeonsPlugin.getEnchantManager();
        int tier = DungeonsPlugin.getEnchantManager().getTier(itemStack);
        Lottery<Enchant> lottery = new Lottery<>();
        if (tier == 0) {
            manager.getAllEnchants().stream()
                    .filter(p -> p.getTitle() == Title.COMMON)
                    .filter(p -> matchEnchantType(itemStack, p.getEnchantType()))
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
            for (Enchant e : manager.getAllEnchants()) {
                if (!matchEnchantType(itemStack, e.getEnchantType())) continue;
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
            Set<Enchant> enchants = manager.getEnchants(itemStack);
            List<Enchant> upgrade = enchants.stream()
                    .filter(p -> p.getLevel() < 3).collect(Collectors.toList());
            int modifier;
            if (tier >= 2)
                modifier = RandomUtils.nextInt() % 30;
            else
                modifier = RandomUtils.nextInt() % 20;

            boolean upgraded = false;
            if (upgrade.size() > 0 && RandomUtils.nextInt() % 2 == 0) {
                Enchant target = upgrade.get(RandomUtils.nextInt() % upgrade.size());
                Enchant after = manager.getEnchant(target.getId(), target.getLevel() + 1);
                if (after != null) {
                    enchants.remove(target);
                    enchants.add(after);
                    upgraded = true;
                }
            }
            if (!upgraded) {
                AtomicReference<Enchant> ref = new AtomicReference<>();
                do {
                    ref.set(lottery.draw());
                } while (upgrade.stream().anyMatch(p -> ref.get().getId() == p.getId()));
                enchants.add(ref.get());
            }
            manager.setEnchants(itemStack, enchants.toArray(new Enchant[0]));
            manager.setMaxLives(itemStack, manager.getMaxLives(itemStack) + modifier);
            manager.setLives(itemStack, manager.getLives(itemStack) + modifier);
            manager.setTier(itemStack, manager.getTier(itemStack) + 1);
        }
    }

}
