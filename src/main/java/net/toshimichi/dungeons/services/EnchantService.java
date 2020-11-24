package net.toshimichi.dungeons.services;

import net.toshimichi.dungeons.DungeonsPlugin;
import net.toshimichi.dungeons.utils.InventoryUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.WeakHashMap;

public class EnchantService implements Service{

    private final WeakHashMap<Player, ItemStack[]> itemMap = new WeakHashMap<>();

    @Override
    public void run() {
        DungeonsPlugin.getEnchantManager().tick();
        for (Player player : Bukkit.getOnlinePlayers()) {
            ItemStack[] inv = InventoryUtils.getPrimaryItemStacks(player);
            ItemStack[] old = itemMap.get(player);
            if (Arrays.equals(inv, old)) return;
            itemMap.put(player, old);
            DungeonsPlugin.getEnchantManager().refresh(player);
        }
    }
}
