package net.toshimichi.dungeons.enchants;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class NullEnchanter extends Enchanter {
    public NullEnchanter(Enchant enchant, Player player, ItemStack itemStack) {
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
