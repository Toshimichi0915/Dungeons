package net.toshimichi.dungeons.enchants.bow.bottomlessquiver;

import net.toshimichi.dungeons.DungeonsPlugin;
import net.toshimichi.dungeons.enchants.Enchant;
import net.toshimichi.dungeons.enchants.bow.BowEnchanter;
import org.apache.commons.lang.math.RandomUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.projectiles.ProjectileSource;

import java.util.Arrays;

public class BottomlessQuiverEnchanter extends BowEnchanter implements Listener {

    private static final Material[] arrows = new Material[]{Material.ARROW, Material.SPECTRAL_ARROW, Material.TIPPED_ARROW};
    private ItemStack[] copy;

    public BottomlessQuiverEnchanter(Enchant enchant, Player player, ItemStack itemStack) {
        super(enchant, player, itemStack);
    }

    @Override
    protected void onEnabled() {
        copy = new ItemStack[getPlayer().getInventory().getContents().length];
        Bukkit.getPluginManager().registerEvents(this, DungeonsPlugin.getPlugin());
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
        ProjectileSource source = ((Arrow) e.getEntity()).getShooter();
        if (!getPlayer().equals(source)) return;

        double clearance;
        if (getEnchant().getLevel() == 1)
            clearance = 10;
        else if (getEnchant().getLevel() == 2)
            clearance = 20;
        else
            clearance = 30;

        if (clearance < RandomUtils.nextInt(100)) return;

        ItemStack[] contents = getPlayer().getInventory().getContents();
        for (int i = 0; i < copy.length; i++) {
            copy[i] = null;
            if (contents[i] == null) continue;
            if (!Arrays.asList(arrows).contains(contents[i].getType())) continue;
            copy[i] = contents[i].clone();
        }

        Bukkit.getScheduler().runTaskLater(DungeonsPlugin.getPlugin(), () -> {
            ItemStack[] current = getPlayer().getInventory().getContents();
            for (int i = 0; i < current.length; i++) {
                if (copy[i] == null) continue;
                if (current[i] == null) {
                    current[i] = copy[i];
                } else if (current[i].getAmount() < copy[i].getAmount()) {
                    current[i].setAmount(copy[i].getAmount());
                }
            }
        }, 1);
    }
}
