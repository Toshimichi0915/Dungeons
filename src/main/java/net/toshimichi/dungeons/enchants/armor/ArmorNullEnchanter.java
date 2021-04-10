package net.toshimichi.dungeons.enchants.armor;

import net.toshimichi.dungeons.enchants.Enchant;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ArmorNullEnchanter extends ArmorEnchanter {
    public ArmorNullEnchanter(Enchant enchant, Player player, ItemStack itemStack) {
        super(enchant, player, itemStack);
    }

    @Override
    protected void onEnabled() {
    }

    @Override
    public void tick() {
    }

    @Override
    protected void onDisabled() {
    }
}
