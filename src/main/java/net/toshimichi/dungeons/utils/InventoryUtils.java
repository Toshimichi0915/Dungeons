package net.toshimichi.dungeons.utils;

import org.bukkit.entity.Player;
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
}
