package net.toshimichi.dungeons.enchants.wand;

import net.toshimichi.dungeons.DungeonsPlugin;
import net.toshimichi.dungeons.enchants.Enchant;
import net.toshimichi.dungeons.enchants.Enchanter;
import net.toshimichi.dungeons.utils.SilentCooldown;
import org.apache.commons.lang.math.RandomUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class SanctityEnchanter extends Enchanter implements Listener {

    public SanctityEnchanter(Enchant enchant, Player player, ItemStack itemStack) {
        super(enchant, player, itemStack);
    }

    @Override
    protected void onEnabled() {
        Bukkit.getPluginManager().registerEvents(this, DungeonsPlugin.getPlugin());
    }

    @Override
    public void tick() {
        SilentCooldown.reduceCooldown(getItemStack(), "enchant.sanctity.cooldown");
    }

    @Override
    protected void onDisabled() {
        HandlerList.unregisterAll(this);
    }

    private boolean tryUse() {
        ItemStack item = getPlayer().getInventory().getItemInMainHand();
        if (!item.equals(getItemStack())) return false;
        int level = getEnchant().getLevel();
        if (SilentCooldown.getCooldown(item, "enchant.sanctity.cooldown") > 1) return false;
        if (!DungeonsPlugin.getManaManager().consumeMana(getPlayer(), 20 * level)) return false;
        SilentCooldown.setCooldown(item, "enchant.sanctity.cooldown", 10);
        double healAmount;
        if (level == 1)
            healAmount = 2;
        else if (level == 2)
            healAmount = 3;
        else
            healAmount = 6;
        double maxHp = getPlayer().getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
        double hp = getPlayer().getHealth() + healAmount;
        if (hp > maxHp) hp = maxHp;
        getPlayer().setHealth(hp);

        getPlayer().playSound(getPlayer().getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 0.1F, 0.8F);
        for (int i = 0; i < 2; i++) {
            Location loc = getPlayer().getLocation();
            loc.add(RandomUtils.nextDouble() - 0.5, 0, RandomUtils.nextDouble() - 0.5);
            getPlayer().getWorld().spawnParticle(Particle.HEART, loc, 0, 0, 0.2, 0);
        }
        return true;
    }

    @EventHandler(ignoreCancelled = true)
    public void onInteract(PlayerInteractEvent e) {
        if (!e.getPlayer().equals(getPlayer())) return;
        if (e.getAction() != Action.RIGHT_CLICK_AIR && e.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        if (tryUse())
            e.setCancelled(true);
    }

}
