package net.toshimichi.dungeons.enchants.wand;

import net.toshimichi.dungeons.Dungeons;
import net.toshimichi.dungeons.enchants.Enchant;
import net.toshimichi.dungeons.enchants.Enchanter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

abstract public class WandEnchanter extends Enchanter implements Listener {

    private int coolTime;

    public WandEnchanter(Enchant enchant, Player player, ItemStack itemStack) {
        super(enchant, player, itemStack);
    }

    @Override
    protected void onEnabled() {
        Bukkit.getPluginManager().registerEvents(this, Dungeons.getInstance().getPlugin());
    }

    @Override
    public void tick() {
        if (coolTime > 0) {
            coolTime--;
        }
    }

    @Override
    protected void onDisabled() {
        HandlerList.unregisterAll(this);
    }

    protected void activateCoolTime(int coolTime) {
        this.coolTime = coolTime;
    }

    abstract public void onInteract(PlayerInteractEvent e);

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        if (!getPlayer().equals(e.getPlayer())) return;
        if (coolTime > 0) return;
        if (e.getAction() != Action.RIGHT_CLICK_AIR && e.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        onInteract(e);
        e.setCancelled(true);
    }
}
