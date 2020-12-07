package net.toshimichi.dungeons.listeners;

import net.toshimichi.dungeons.Dungeons;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.world.WorldSaveEvent;

public class ManaListener implements Listener {
    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Dungeons.getInstance().getManaManager().save(e.getPlayer());
    }

    @EventHandler
    public void onSave(WorldSaveEvent e) {
        for (Player p : e.getWorld().getPlayers()) {
            Dungeons.getInstance().getManaManager().save(p);
        }
    }

    @EventHandler
    public void onDisable(PluginDisableEvent e) {
        if (!e.getPlugin().equals(Dungeons.getInstance().getPlugin())) return;
        Dungeons.getInstance().getManaManager().saveAll();
    }
}
