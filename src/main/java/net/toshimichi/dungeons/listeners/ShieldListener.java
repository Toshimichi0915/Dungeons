package net.toshimichi.dungeons.listeners;

import net.toshimichi.dungeons.events.PlayerShieldEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class ShieldListener implements Listener {

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e) {
        if (!(e.getEntity() instanceof HumanEntity)) return;
        if (!((HumanEntity) e.getEntity()).isBlocking()) return;
        if (e.getDamage() == 0 || e.getFinalDamage() != 0) return;
        PlayerShieldEvent fake = new PlayerShieldEvent(e);
        Bukkit.getPluginManager().callEvent(fake);
    }

}
