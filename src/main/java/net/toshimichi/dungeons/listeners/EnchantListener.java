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
