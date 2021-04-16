package net.toshimichi.dungeons.listeners;

import net.toshimichi.dungeons.Dungeons;
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
        Bukkit.getScheduler().runTaskAsynchronously(Dungeons.getInstance().getPlugin(), () -> {
            Dungeons.getInstance().getLocaleManager().getLocale(e.getPlayer());
            Dungeons.getInstance().getLocaleManager().getSuggestedLocale(e.getPlayer());
            Bukkit.getScheduler().runTask(Dungeons.getInstance().getPlugin(), () -> {
                LocaleLoadEvent event = new LocaleLoadEvent(e.getPlayer(), Dungeons.getInstance().getLocaleManager(), Dungeons.getInstance().getLocaleManager().getLocale(e.getPlayer()));
                Bukkit.getPluginManager().callEvent(event);
            });
        });
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Dungeons.getInstance().getLocaleManager().save(e.getPlayer());
    }

    @EventHandler
    public void onDisable(PluginDisableEvent e) {
        if (!e.getPlugin().equals(Dungeons.getInstance().getPlugin())) return;
        Dungeons.getInstance().getLocaleManager().saveAll();
    }
}
