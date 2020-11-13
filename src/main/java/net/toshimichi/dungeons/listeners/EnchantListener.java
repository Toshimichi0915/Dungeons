package net.toshimichi.dungeons.listeners;

import net.toshimichi.dungeons.DungeonsPlugin;
import net.toshimichi.dungeons.enchants.EnchantManager;
import net.toshimichi.dungeons.utils.InventoryUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * エンチャント関連のリスナー
 */
public class EnchantListener implements Listener {

    private void refresh(Player p) {
        DungeonsPlugin.getEnchantManager().refresh(p);
    }

    private void refreshLater(Player p) {
        Bukkit.getScheduler().runTaskLater(DungeonsPlugin.getPlugin(), () -> refresh(p), 1);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        refresh(e.getPlayer());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        refresh(e.getPlayer());
        DungeonsPlugin.getEnchantManager().disable(e.getPlayer());
    }

    @EventHandler
    public void onDisable(PluginDisableEvent e) {
        if (!e.getPlugin().equals(DungeonsPlugin.getPlugin())) return;
        for (Player p : Bukkit.getOnlinePlayers())
            DungeonsPlugin.getEnchantManager().disable(p);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onDeath(PlayerDeathEvent e) {
        if (e.getKeepInventory()) return;
        e.setKeepInventory(true);
        e.getDrops().clear();
        ArrayList<ItemStack> keepInv = new ArrayList<>();
        PlayerInventory inventory = e.getEntity().getInventory();
        ItemStack[] contents = InventoryUtils.getPrimaryItemStacks(e.getEntity());
        EnchantManager manager = DungeonsPlugin.getEnchantManager();

        //reduce a life from mystics
        for (ItemStack itemStack : contents) {
            int lives = manager.getLives(itemStack);
            if (lives == -1) {
                continue;
            } else if (lives == 1) {
                InventoryUtils.reduce(inventory, itemStack);
                continue;
            }
            manager.setLives(itemStack, lives - 1);
            keepInv.add(itemStack);
        }

        //drop items
        for (ItemStack itemStack : inventory.getContents()) {
            if (itemStack == null) continue;
            if (keepInv.contains(itemStack)) continue;
            if (!InventoryUtils.reduce(inventory, itemStack)) continue;
            e.getEntity().getWorld().dropItem(e.getEntity().getLocation(), itemStack);
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onUse(PlayerItemDamageEvent e) {
        if (!Arrays.asList(InventoryUtils.getPrimaryItemStacks(e.getPlayer())).contains(e.getItem())) return;
        if (DungeonsPlugin.getEnchantManager().getEnchants(e.getItem()).size() == 0) return;
        e.setCancelled(true);
    }
}
