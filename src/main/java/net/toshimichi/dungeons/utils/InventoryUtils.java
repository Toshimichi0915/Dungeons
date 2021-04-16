package net.toshimichi.dungeons.utils;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class InventoryUtils {

    public static ItemStack[] getPrimaryItemStacks(Player player) {
        PlayerInventory inv = player.getInventory();
        return new ItemStack[]{inv.getItemInMainHand(),
                inv.getItemInOffHand(),
                inv.getHelmet(),
                inv.getChestplate(),
                inv.getLeggings(),
                inv.getBoots()};
    }

    /**
     * インベントリから指定されたアイテムを指定された個数個だけ取り除きます.
     *
     * @param inventory インベントリ
     * @param itemStack 削除するアイテム. 個数も指定されていなければならない
     * @return 削除できた場合は {@code true} そうでなければ {@code false}
     */
    public static boolean reduce(Inventory inventory, ItemStack itemStack) {
        int reduced = 0;
        ItemStack[] contents = inventory.getContents();
        for (int i = 0; i < contents.length; i++) {
            ItemStack invItem = contents[i];
            if (invItem == null) continue;
            if (!invItem.isSimilar(itemStack)) continue;
            int remaining = itemStack.getAmount() - reduced;
            int amount = invItem.getAmount() - remaining;
            if (amount > 0) {
                reduced += remaining;
                invItem.setAmount(amount);
                break;
            } else {
                contents[i] = null;
                reduced += invItem.getAmount();
                if (amount == 0)
                    break;
            }
        }
        if (itemStack.getAmount() > reduced)
            return false;
        inventory.setContents(contents);
        return true;
    }
}
