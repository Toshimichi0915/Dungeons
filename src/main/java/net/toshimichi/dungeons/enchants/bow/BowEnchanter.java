package net.toshimichi.dungeons.enchants.bow;

import net.toshimichi.dungeons.enchants.Enchant;
import net.toshimichi.dungeons.enchants.Enchanter;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

abstract public class BowEnchanter extends Enchanter {
    public BowEnchanter(Enchant enchant, Player player, ItemStack itemStack) {
        super(enchant, player, itemStack);
    }

    @Override
    public boolean isAvailable() {
        ItemStack main = getPlayer().getInventory().getItemInMainHand();
        ItemStack off = getPlayer().getInventory().getItemInOffHand();
        if (main.equals(getItemStack())) return true;
        if (off.equals(getItemStack()) && main.getType() != Material.BOW) return true;
        return false;
    }
}
