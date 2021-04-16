package net.toshimichi.dungeons.enchants.sword;

import net.toshimichi.dungeons.enchants.Enchant;
import net.toshimichi.dungeons.enchants.Enchanter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

abstract public class SwordEnchanter extends Enchanter {
    public SwordEnchanter(Enchant enchant, Player player, ItemStack itemStack) {
        super(enchant, player, itemStack);
    }

    @Override
    public boolean isAvailable() {
        return getItemStack().equals(getPlayer().getInventory().getItemInMainHand());
    }
}
