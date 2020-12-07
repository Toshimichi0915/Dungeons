package net.toshimichi.dungeons.listeners;

import net.toshimichi.dungeons.Dungeons;
import net.toshimichi.dungeons.utils.BossBarChat;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginDisableEvent;

public class BossBarChatListener implements Listener {

    @EventHandler
    public void onDisable(PluginDisableEvent e) {
        if (!e.getPlugin().equals(Dungeons.getInstance().getPlugin())) return;
        Bukkit.getOnlinePlayers().forEach(BossBarChat::clear);
    }
}
