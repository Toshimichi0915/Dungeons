package net.toshimichi.dungeons.listeners;

import net.toshimichi.dungeons.DungeonsPlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.world.WorldSaveEvent;

import java.io.IOException;

public class StashListener implements Listener {
    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Bukkit.getScheduler().runTaskAsynchronously(DungeonsPlugin.getPlugin(), () -> {
            try {
                DungeonsPlugin.getStash().save(e.getPlayer().getUniqueId());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }

    @EventHandler
    public void onSave(WorldSaveEvent e) {
        for (Player p : e.getWorld().getPlayers()) {
            Bukkit.getScheduler().runTaskAsynchronously(DungeonsPlugin.getPlugin(), () -> {
                try {
                    DungeonsPlugin.getStash().save(p.getUniqueId());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });
        }
    }

    @EventHandler
    public void onDisable(PluginDisableEvent e) {
        if (!e.getPlugin().equals(DungeonsPlugin.getPlugin())) return;
        try {
            DungeonsPlugin.getStash().saveAll();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
