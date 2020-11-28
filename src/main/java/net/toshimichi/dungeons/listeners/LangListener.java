package net.toshimichi.dungeons.listeners;

import net.toshimichi.dungeons.DungeonsPlugin;
import net.toshimichi.dungeons.events.LocaleLoadEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.PluginDisableEvent;

public class LangListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Bukkit.getScheduler().runTaskAsynchronously(DungeonsPlugin.getPlugin(), () -> {
            DungeonsPlugin.getLocaleManager().getLocale(e.getPlayer());
            DungeonsPlugin.getLocaleManager().getSuggestedLocale(e.getPlayer());
            Bukkit.getScheduler().runTask(DungeonsPlugin.getPlugin(), () -> {
                LocaleLoadEvent event = new LocaleLoadEvent(e.getPlayer(), DungeonsPlugin.getLocaleManager(), DungeonsPlugin.getLocaleManager().getLocale(e.getPlayer()));
                Bukkit.getPluginManager().callEvent(event);
            });
        });
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        DungeonsPlugin.getLocaleManager().save(e.getPlayer());
    }

    @EventHandler
    public void onDisable(PluginDisableEvent e) {
        if (!e.getPlugin().equals(DungeonsPlugin.getPlugin())) return;
        DungeonsPlugin.getLocaleManager().saveAll();
    }
}
