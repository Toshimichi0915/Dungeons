package net.toshimichi.dungeons.enchants.armor.gottagofast;

import net.toshimichi.dungeons.enchants.Enchant;
import net.toshimichi.dungeons.enchants.Enchanter;
import org.apache.commons.lang.math.RandomUtils;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class GottaGoFastEnchanter extends Enchanter {

    private int count;
    private float changed;

    public GottaGoFastEnchanter(Enchant enchant, Player player, ItemStack itemStack) {
        super(enchant, player, itemStack);
    }

    private float getModifier() {
        if(getEnchant().getLevel() == 1)
            return 1.05F;
        else if(getEnchant().getLevel() == 2)
            return 1.10F;
        else
            return 1.2F;
    }

    @Override
    protected void onEnabled() {
        changed = getPlayer().getWalkSpeed() * (getModifier() - 1);
        getPlayer().setWalkSpeed(getPlayer().getWalkSpeed() + changed);
    }

    @Override
    public void tick() {
        if(count++ % 3 != 0) return;
        Location loc = getPlayer().getLocation();
        loc.add(RandomUtils.nextDouble() % 0.4 - 0.2, RandomUtils.nextDouble() % 0.2, RandomUtils.nextDouble() % 0.4 - 0.2);
        World world = loc.getWorld();
        world.spawnParticle(Particle.CLOUD, loc, 0, 0, 0.6, 0);
    }

    @Override
    protected void onDisabled() {
        getPlayer().setWalkSpeed(getPlayer().getWalkSpeed() - changed);
    }

    @Override
    public boolean isAvailable() {
        return getPlayer().getInventory().getItem(EquipmentSlot.FEET).equals(getItemStack());
    }
}
