package net.toshimichi.dungeons.events;

import org.bukkit.entity.Entity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityEvent;

public class PlayerShieldEvent extends EntityEvent implements Cancellable {

    private static final HandlerList handlerList = new HandlerList();

    private final EntityDamageByEntityEvent origin;

    public PlayerShieldEvent(EntityDamageByEntityEvent origin) {
        super(origin.getEntity());
        this.origin = origin;
    }

    public boolean isCancelled() {
        return origin.isCancelled();
    }

    public void setCancelled(boolean cancel) {
        origin.setCancelled(cancel);
    }

    public double getOriginalDamage(EntityDamageEvent.DamageModifier type) throws IllegalArgumentException {
        return origin.getOriginalDamage(type);
    }

    public void setDamage(EntityDamageEvent.DamageModifier type, double damage) throws IllegalArgumentException, UnsupportedOperationException {
        origin.setDamage(type, damage);
    }

    public double getDamage(EntityDamageEvent.DamageModifier type) throws IllegalArgumentException {
        return origin.getDamage(type);
    }

    public boolean isApplicable(EntityDamageEvent.DamageModifier type) throws IllegalArgumentException {
        return origin.isApplicable(type);
    }

    public double getDamage() {
        return origin.getDamage();
    }

    public final double getFinalDamage() {
        return origin.getFinalDamage();
    }

    public void setDamage(double damage) {
        origin.setDamage(damage);
    }

    public EntityDamageEvent.DamageCause getCause() {
        return origin.getCause();
    }

    @Override
    public HumanEntity getEntity() {
        return (HumanEntity) origin.getEntity();
    }

    public Entity getDamager() {
        return origin.getDamager();
    }

    public EntityDamageByEntityEvent getOrigin() {
        return origin;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }
}
