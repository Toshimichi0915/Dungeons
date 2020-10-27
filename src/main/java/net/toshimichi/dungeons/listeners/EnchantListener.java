package net.toshimichi.dungeons.listeners;

import net.toshimichi.dungeons.DungeonsPlugin;
import net.toshimichi.dungeons.enchants.EnchantManager;
import net.toshimichi.dungeons.utils.InventoryUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.inventory.ItemStack;

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

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        ItemStack[] inv = InventoryUtils.getPrimaryItemStacks(e.getEntity());
        EnchantManager manager = DungeonsPlugin.getEnchantManager();
        for (ItemStack itemStack : inv) {
            int originalLives = manager.getLives(itemStack);
            if (originalLives == -1) {
                continue;
            } else if (originalLives == 1) {
                e.getEntity().getInventory().removeItem(itemStack);
                continue;
            }
            manager.setLives(itemStack, originalLives - 1);
        }
    }

    @EventHandler
    public void onUse(PlayerItemDamageEvent e) {
        if (!Arrays.asList(InventoryUtils.getPrimaryItemStacks(e.getPlayer())).contains(e.getItem())) return;
        if (DungeonsPlugin.getEnchantManager().getEnchants(e.getItem()).size() == 0) return;
        e.setCancelled(true);
    }
}
