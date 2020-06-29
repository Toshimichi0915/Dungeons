package net.toshimichi.dungeons.listeners;

import net.toshimichi.dungeons.DungeonsPlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryPickupItemEvent;
import org.bukkit.event.player.*;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.inventory.InventoryHolder;

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
    public void onPickUp(InventoryPickupItemEvent e) {
        InventoryHolder holder = e.getInventory().getHolder();
        if (!(holder instanceof Player)) return;
        refreshLater((Player) holder);
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent e) {
        refreshLater(e.getPlayer());
    }

    @EventHandler
    public void onSwapping(PlayerSwapHandItemsEvent e) {
        refreshLater(e.getPlayer());
    }

    @EventHandler
    public void onConsuming(PlayerItemConsumeEvent e) {
        refreshLater(e.getPlayer());
    }

    @EventHandler
    public void onHeld(PlayerItemHeldEvent e) {
        refreshLater(e.getPlayer());
    }

    @EventHandler
    public void onBroken(PlayerItemBreakEvent e) {
        refreshLater(e.getPlayer());
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if (!(e.getWhoClicked() instanceof Player)) return;
        refreshLater((Player) e.getWhoClicked());
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
}
