package net.toshimichi.dungeons.enchants.armor;

import net.toshimichi.dungeons.enchants.Enchant;
import net.toshimichi.dungeons.enchants.Enchanter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

abstract public class ArmorEnchanter extends Enchanter {
    public ArmorEnchanter(Enchant enchant, Player player, ItemStack itemStack) {
        super(enchant, player, itemStack);
    }

    @Override
    public boolean isAvailable() {
        return Arrays.asList(getPlayer().getInventory().getArmorContents()).contains(getItemStack());
    }
}
