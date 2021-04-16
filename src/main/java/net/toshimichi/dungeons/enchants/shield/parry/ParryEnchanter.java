package net.toshimichi.dungeons.enchants.shield.parry;

import net.toshimichi.dungeons.Dungeons;
import net.toshimichi.dungeons.enchants.Enchant;
import net.toshimichi.dungeons.enchants.Enchanter;
import net.toshimichi.dungeons.events.PlayerShieldEvent;
import net.toshimichi.dungeons.nat.api.CooldownUtils;
import org.apache.commons.lang3.RandomUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class ParryEnchanter extends Enchanter implements Listener {
    public ParryEnchanter(Enchant enchant, Player player, ItemStack itemStack) {
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

    @EventHandler
    public void onBlock(PlayerShieldEvent e) {
        if (!getPlayer().equals(e.getEntity())) return;
        if (!(e.getDamager() instanceof Player)) return;
        int clearance;
        if (getEnchant().getLevel() == 1)
            clearance = 2;
        else if (getEnchant().getLevel() == 2)
            clearance = 4;
        else
            clearance = 6;
        if (clearance < RandomUtils.nextInt() % 100) return;
        Player p = (Player) e.getDamager();
        CooldownUtils utils = Bukkit.getServicesManager().load(CooldownUtils.class);
        utils.setCooldown(p, p.getInventory().getItemInMainHand().getType(), 40);
        p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 40, 99, false, false));
    }
}
