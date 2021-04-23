package net.toshimichi.dungeons.enchants.wand.sanctity;

import net.toshimichi.dungeons.Dungeons;
import net.toshimichi.dungeons.enchants.Enchant;
import net.toshimichi.dungeons.enchants.wand.WandEnchanter;
import net.toshimichi.dungeons.utils.SilentCooldown;
import org.apache.commons.lang.math.RandomUtils;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class SanctityEnchanter extends WandEnchanter {

    public SanctityEnchanter(Enchant enchant, Player player, ItemStack itemStack) {
        super(enchant, player, itemStack);
    }

    @Override
    public void tick() {
        SilentCooldown.reduceCooldown(getItemStack(), "enchant.sanctity.cooldown");
    }

    @Override
    public void onInteract(PlayerInteractEvent e) {
        int level = getEnchant().getLevel();
        if (SilentCooldown.getCooldown(getItemStack(), "enchant.sanctity.cooldown") > 1) return;
        if (!Dungeons.getInstance().getManaManager().consumeMana(getPlayer(), 20 * level)) return;
        SilentCooldown.setCooldown(getItemStack(), "enchant.sanctity.cooldown", 10);
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
    }

    @Override
    public boolean isAvailable() {
        ItemStack mainHand = getPlayer().getInventory().getItemInMainHand();
        ItemStack offHand = getPlayer().getInventory().getItemInOffHand();
        return mainHand.equals(getItemStack()) || offHand.equals(getItemStack());
    }
}
