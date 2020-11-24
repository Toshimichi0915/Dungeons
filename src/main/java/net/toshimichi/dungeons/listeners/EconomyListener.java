package net.toshimichi.dungeons.listeners;

import net.toshimichi.dungeons.DungeonsPlugin;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.world.WorldSaveEvent;

import java.io.IOException;

public class EconomyListener implements Listener {
    @EventHandler
    public void onQuit(PlayerQuitEvent e) throws IOException {
        DungeonsPlugin.getEconomy().save(e.getPlayer().getUniqueId());
    }

    @EventHandler
    public void onSave(WorldSaveEvent e) throws IOException {
        for (Player p : e.getWorld().getPlayers()) {
            DungeonsPlugin.getEconomy().save(p.getUniqueId());
        }
    }

    @EventHandler
    public void onDisable(PluginDisableEvent e) throws IOException {
        if (!e.getPlugin().equals(DungeonsPlugin.getPlugin())) return;
        DungeonsPlugin.getEconomy().saveAll();
    }
}
