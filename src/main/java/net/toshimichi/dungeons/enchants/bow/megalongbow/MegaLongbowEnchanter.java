package net.toshimichi.dungeons.enchants.bow.megalongbow;

import net.toshimichi.dungeons.Dungeons;
import net.toshimichi.dungeons.enchants.Enchant;
import net.toshimichi.dungeons.enchants.bow.BowEnchanter;
import net.toshimichi.dungeons.utils.VelocityUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.util.Vector;

public class MegaLongbowEnchanter extends BowEnchanter implements Listener {

    public MegaLongbowEnchanter(Enchant enchant, Player player, ItemStack itemStack) {
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
    public void onShot(EntitySpawnEvent e) {
        if (!(e.getEntity() instanceof Arrow)) return;
        Arrow arrow = (Arrow) e.getEntity();
        ProjectileSource source = arrow.getShooter();
        if (source == null) return;
        if (!getPlayer().equals(source)) return;
        Vector vector = getPlayer().getEyeLocation().getDirection().multiply(3);
        vector.add(VelocityUtils.getVelocity(getPlayer()));
        arrow.setVelocity(vector);
        arrow.setCritical(true);

        int level;
        if (getEnchant().getLevel() == 1)
            level = 1;
        else if (getEnchant().getLevel() == 2)
            level = 2;
        else
            level = 3;
        getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 100, level, false, false));
    }
}
