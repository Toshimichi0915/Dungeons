package net.toshimichi.dungeons.listeners;

import net.toshimichi.dungeons.Dungeons;
import net.toshimichi.dungeons.enchants.EnchantManager;
import net.toshimichi.dungeons.gui.EnchantGui;
import net.toshimichi.dungeons.utils.InventoryUtils;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * エンチャント関連のリスナー
 */
public class EnchantListener implements Listener {

    private void refresh(Player p) {
        Dungeons.getInstance().getEnchantManager().refresh(p);
    }

    private void refreshLater(Player p) {
        Bukkit.getScheduler().runTaskLater(Dungeons.getInstance().getPlugin(), () -> refresh(p), 1);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        refresh(e.getPlayer());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        refresh(e.getPlayer());
        Dungeons.getInstance().getEnchantManager().disable(e.getPlayer());
    }

    @EventHandler
    public void onDisable(PluginDisableEvent e) {
        if (!e.getPlugin().equals(Dungeons.getInstance().getPlugin())) return;
        for (Player p : Bukkit.getOnlinePlayers())
            Dungeons.getInstance().getEnchantManager().disable(p);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onDeath(PlayerDeathEvent e) {
        if (e.getKeepInventory()) return;
        e.setKeepInventory(true);
        e.getDrops().clear();
        ArrayList<ItemStack> keepInv = new ArrayList<>();
        PlayerInventory inventory = e.getEntity().getInventory();
        ItemStack[] contents = e.getEntity().getInventory().getContents();
        EnchantManager manager = Dungeons.getInstance().getEnchantManager();

        //reduce a life from mystics
        for (ItemStack itemStack : contents) {
            int lives = manager.getLives(itemStack);
            if (lives == -1) {
                continue;
            } else if (lives == 1) {
                InventoryUtils.reduce(inventory, itemStack);
                continue;
            }
            manager.setLives(itemStack, lives - 1);
            keepInv.add(itemStack);
        }

        //drop items
        for (ItemStack itemStack : inventory.getContents()) {
            if (itemStack == null) continue;
            if (keepInv.contains(itemStack)) continue;
            if (!InventoryUtils.reduce(inventory, itemStack)) continue;
            e.getEntity().getWorld().dropItem(e.getEntity().getLocation(), itemStack);
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onUse(PlayerItemDamageEvent e) {
        if (!Arrays.asList(InventoryUtils.getPrimaryItemStacks(e.getPlayer())).contains(e.getItem())) return;
        if (Dungeons.getInstance().getEnchantManager().getTier(e.getItem()) < 0) return;
        e.setCancelled(true);
    }

    @EventHandler(ignoreCancelled = true)
    public void onInventoryOpen(InventoryOpenEvent e) {
        if (e.getInventory().getType() != InventoryType.ENCHANTING) return;
        if (!(e.getPlayer() instanceof Player)) return;
        Player p = (Player) e.getPlayer();
        e.setCancelled(true);
        Dungeons.getInstance().getGuiManager().show(p, new EnchantGui());
        p.playSound(p.getLocation(), Sound.ENTITY_ITEM_PICKUP, 1, 1);
    }
}
