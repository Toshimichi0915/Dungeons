package net.toshimichi.dungeons.listeners;

import net.toshimichi.dungeons.Dungeons;
import net.toshimichi.dungeons.events.PlayerShieldEvent;
import net.toshimichi.dungeons.nat.api.CooldownUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class ShieldListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onDamage(EntityDamageByEntityEvent e) {
        if (!(e.getEntity() instanceof HumanEntity)) return;
        if (!((HumanEntity) e.getEntity()).isBlocking()) return;
        if (e.getDamage() == 0 || e.getFinalDamage() != 0) return;
        PlayerShieldEvent fake = new PlayerShieldEvent(e);
        Bukkit.getPluginManager().callEvent(fake);
    }

    @EventHandler(ignoreCancelled = true)
    public void onShieldHit(PlayerShieldEvent e) {
        if (!(e.getEntity() instanceof Player)) return;
        Bukkit.getScheduler().runTaskLater(Dungeons.getInstance().getPlugin(), () -> {
            CooldownUtils utils = Bukkit.getServicesManager().load(CooldownUtils.class);
            int cooldown = utils.getCooldown((Player) e.getEntity(), Material.SHIELD);
            if (cooldown > 0) {
                ((Player) e.getDamager()).playSound(e.getEntity().getLocation(), Sound.ITEM_SHIELD_BREAK, 0.5F, 1);
            }
        }, 1);
    }

}
