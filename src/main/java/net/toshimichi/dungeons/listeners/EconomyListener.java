package net.toshimichi.dungeons.listeners;

import net.toshimichi.dungeons.DungeonsPlugin;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.world.WorldSaveEvent;

public class EconomyListener implements Listener {
    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        DungeonsPlugin.getEconomy().save(e.getPlayer().getUniqueId());
    }

    @EventHandler
    public void onSave(WorldSaveEvent e) {
        for (Player p : e.getWorld().getPlayers()) {
            DungeonsPlugin.getEconomy().save(p.getUniqueId());
        }
    }

    @EventHandler
    public void onDisable(PluginDisableEvent e) {
        if (!e.getPlugin().equals(DungeonsPlugin.getPlugin())) return;
        DungeonsPlugin.getEconomy().saveAll();
    }
}
