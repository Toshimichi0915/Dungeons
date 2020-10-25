package net.toshimichi.dungeons.runnable;

import net.toshimichi.dungeons.DungeonsPlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.Arrays;
import java.util.WeakHashMap;

public class EnchantRunnable implements Runnable {

    private final WeakHashMap<Player, ItemStack[]> itemMap = new WeakHashMap<>();

    private ItemStack[] newInventory(Player p) {
        ItemStack[] result = new ItemStack[6];
        PlayerInventory inv = p.getInventory();
        result[0] = inv.getItemInMainHand();
        result[1] = inv.getItemInOffHand();
        result[2] = inv.getHelmet();
        result[3] = inv.getChestplate();
        result[4] = inv.getLeggings();
        result[5] = inv.getBoots();
        return result;
    }

    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            ItemStack[] inv = newInventory(player);
            ItemStack[] old = itemMap.get(player);
            if (Arrays.equals(inv, old)) return;
            itemMap.put(player, old);
            DungeonsPlugin.getEnchantManager().refresh(player);
        }
    }
}
