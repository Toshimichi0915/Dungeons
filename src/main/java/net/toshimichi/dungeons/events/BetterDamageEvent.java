package net.toshimichi.dungeons.events;

import org.bukkit.entity.*;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;


/**
 * {@link EntityDamageByEntityEvent} の拡張クラスです.
 */
public class BetterDamageEvent implements Cancellable {
    private final EntityDamageByEntityEvent parent;

    public BetterDamageEvent(EntityDamageByEntityEvent e) {
        this.parent = e;
    }

    private LivingEntity castEntity(Object o) {
        if (o instanceof LivingEntity) return (LivingEntity) o;
        else return null;
    }

    /**
     * ダメージを与えた張本人を返します.
     * 矢がダメージを与えた場合は矢を打った {@link Entity} を返し、TNTがダメージを与えた場合はTNTを設置した {@link Entity} を返します.
     *
     * @return ダメージを与えた張本人
     */
    public LivingEntity getTrueDamager() {
        Entity damager = getDamager();
        if (damager instanceof LivingEntity) {
            return (LivingEntity) damager;
        } else if (damager instanceof Arrow) {
            return castEntity(((Arrow) damager).getShooter());
        } else if (damager instanceof TNTPrimed) {
            return castEntity(((TNTPrimed) damager).getSource());
        } else {
            return null;
        }
    }

    public Entity getDamager() {
        return parent.getDamager();
    }

    @Override
    public boolean isCancelled() {
        return parent.isCancelled();
    }

    @Override
    public void setCancelled(boolean cancel) {
        parent.setCancelled(cancel);
    }

    public double getOriginalDamage(EntityDamageEvent.DamageModifier type) throws IllegalArgumentException {
        return parent.getOriginalDamage(type);
    }

    public void setDamage(EntityDamageEvent.DamageModifier type, double damage) throws IllegalArgumentException, UnsupportedOperationException {
        parent.setDamage(type, damage);
    }

    public double getDamage(EntityDamageEvent.DamageModifier type) throws IllegalArgumentException {
        return parent.getDamage(type);
    }

    public boolean isApplicable(EntityDamageEvent.DamageModifier type) throws IllegalArgumentException {
        return parent.isApplicable(type);
    }

    public double getDamage() {
        return parent.getDamage();
    }

    public double getFinalDamage() {
        return parent.getFinalDamage();
    }

    public void setDamage(double damage) {
        parent.setDamage(damage);
    }

    public EntityDamageEvent.DamageCause getCause() {
        return parent.getCause();
    }

    public HandlerList getHandlers() {
        return parent.getHandlers();
    }

    public static HandlerList getHandlerList() {
        return EntityDamageEvent.getHandlerList();
    }

    public Entity getEntity() {
        return parent.getEntity();
    }

    public EntityType getEntityType() {
        return parent.getEntityType();
    }

    public String getEventName() {
        return parent.getEventName();
    }

    public boolean isAsynchronous() {
        return parent.isAsynchronous();
    }
}
