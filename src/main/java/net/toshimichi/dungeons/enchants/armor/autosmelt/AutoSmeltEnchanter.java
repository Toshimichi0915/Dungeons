package net.toshimichi.dungeons.enchants.armor.autosmelt;

import net.toshimichi.dungeons.Dungeons;
import net.toshimichi.dungeons.enchants.Enchant;
import net.toshimichi.dungeons.enchants.armor.ArmorEnchanter;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class AutoSmeltEnchanter extends ArmorEnchanter implements Listener {

    public AutoSmeltEnchanter(Enchant enchant, Player player, ItemStack itemStack) {
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

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
    public void onMine(BlockBreakEvent e) {
        if (!getPlayer().equals(e.getPlayer())) return;
        if (getPlayer().getGameMode() == GameMode.CREATIVE) return;
        e.setCancelled(true);
        e.setDropItems(true);

        for (ItemStack drop : e.getBlock().getDrops()) {
            if (drop.getType() == Material.IRON_ORE)
                drop.setType(Material.IRON_INGOT);
            else if (drop.getType() == Material.GOLD_ORE)
                drop.setType(Material.GOLD_INGOT);
            e.getBlock().getWorld().dropItemNaturally(e.getBlock().getLocation(), drop);
        }
        e.getBlock().setType(Material.AIR);
    }
}
