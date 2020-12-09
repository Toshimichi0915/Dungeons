package net.toshimichi.dungeons.enchants.sword.backstab;

import net.toshimichi.dungeons.enchants.Enchant;
import net.toshimichi.dungeons.enchants.sword.SwordEnchanter;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

public class BackStabEnchanter extends SwordEnchanter implements Listener {
    public BackStabEnchanter(Enchant enchant, Player player, ItemStack itemStack) {
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
