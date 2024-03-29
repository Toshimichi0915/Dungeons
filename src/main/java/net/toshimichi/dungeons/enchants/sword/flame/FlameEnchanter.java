package net.toshimichi.dungeons.enchants.sword.flame;

import net.toshimichi.dungeons.Dungeons;
import net.toshimichi.dungeons.enchants.Enchant;
import net.toshimichi.dungeons.enchants.sword.SwordEnchanter;
import net.toshimichi.dungeons.events.PlayerDamageEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Map;
import java.util.WeakHashMap;

public class FlameEnchanter extends SwordEnchanter implements Listener {

    private static final WeakHashMap<LivingEntity, Integer> burnt = new WeakHashMap<>();

    static {
        Bukkit.getScheduler().runTaskTimer(Dungeons.getInstance().getPlugin(), () -> {
            for (Map.Entry<LivingEntity, Integer> entry : burnt.entrySet()) {
                int next = entry.getValue() - 1;
                if (next <= 0 || entry.getKey().getFireTicks() <= 0)
                    burnt.remove(entry.getKey());
                else
                    burnt.put(entry.getKey(), next);
            }
        }, 1, 1);
    }

    public FlameEnchanter(Enchant enchant, Player player, ItemStack itemStack) {
        super(enchant, player, itemStack);
    }

    @Override
    protected void onEnabled() {
        Bukkit.getPluginManager().registerEvents(this, Dungeons.getInstance().getPlugin());
    }

    @Override
    public void tick() {
    }

    @Override
    protected void onDisabled() {
        HandlerList.unregisterAll(this);
    }

    @EventHandler(ignoreCancelled = true)
    public void onDamage(EntityDamageByEntityEvent e1) {
        PlayerDamageEvent e = new PlayerDamageEvent(e1);
        if (!(e.getEntity() instanceof LivingEntity)) return;
        if (!getPlayer().equals(e.getTrueDamager())) return;
        int ticks;
        if (getEnchant().getLevel() == 1)
            ticks = 60;
        else if (getEnchant().getLevel() == 2)
            ticks = 80;
        else
            ticks = 100;
        burnt.put((LivingEntity) e.getEntity(), ticks);
        e.getEntity().setFireTicks(ticks);
    }

    @EventHandler
    public void onDamage1(EntityDamageEvent e) {
        if (!burnt.containsKey(e.getEntity())) return;
        if (e.getCause() != EntityDamageEvent.DamageCause.FIRE_TICK) return;
        e.setCancelled(true);
        LivingEntity entity = (LivingEntity) e.getEntity();
        int noDamageTicks = entity.getNoDamageTicks();
        entity.damage(1);
        entity.setNoDamageTicks(noDamageTicks);
    }
}
