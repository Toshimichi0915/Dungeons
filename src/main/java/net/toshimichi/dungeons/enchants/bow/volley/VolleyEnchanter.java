package net.toshimichi.dungeons.enchants.bow.volley;

import net.toshimichi.dungeons.DungeonsPlugin;
import net.toshimichi.dungeons.enchants.Enchant;
import net.toshimichi.dungeons.enchants.Enchanter;
import org.apache.commons.lang3.RandomUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class VolleyEnchanter extends Enchanter implements Listener {

    public VolleyEnchanter(Enchant enchant, Player player, ItemStack itemStack) {
        super(enchant, player, itemStack);
    }

    @Override
    protected void onEnabled() {
        Bukkit.getPluginManager().registerEvents(this, DungeonsPlugin.getPlugin());
    }

    @Override
    public void tick() {
    }

    @Override
    protected void onDisabled() {
        HandlerList.unregisterAll(this);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOW)
    public void onShot(EntityShootBowEvent e) {
        if (!getPlayer().equals(e.getEntity())) return;
        if (!(e.getProjectile() instanceof Arrow)) return;
        Arrow projectile = (Arrow) e.getProjectile();
        projectile.remove();

        double velocity = e.getProjectile().getVelocity().subtract(getPlayer().getVelocity()).length();

        getPlayer().playSound(getPlayer().getLocation(), Sound.ENTITY_ARROW_SHOOT, 1F, 0.7F + (float) velocity / 6);

        int arrows = getEnchant().getLevel() + 1;
        for (int i = 0; i < arrows; i++) {
            Location loc = getPlayer().getEyeLocation();
            Arrow arrow = getPlayer().getWorld().spawnArrow(loc, loc.getDirection(), (float) velocity, 0);
            arrow.setCritical(projectile.isCritical());
            arrow.setShooter(getPlayer());

            EntitySpawnEvent event = new EntitySpawnEvent(arrow);
            Bukkit.getPluginManager().callEvent(event);
            if (event.isCancelled())
                arrow.remove();

            Vector v = arrow.getVelocity();
            v.setX(v.getX() + RandomUtils.nextFloat(0, 0.2F) - 0.1F);
            v.setY(v.getY() + RandomUtils.nextFloat(0, 0.2F) - 0.1F);
            v.setZ(v.getZ() + RandomUtils.nextFloat(0, 0.2F) - 0.1F);
            arrow.setVelocity(v);
        }
    }
}
