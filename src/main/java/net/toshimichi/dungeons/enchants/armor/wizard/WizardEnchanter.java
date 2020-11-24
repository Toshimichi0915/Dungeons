package net.toshimichi.dungeons.enchants.armor.wizard;

import net.toshimichi.dungeons.DungeonsPlugin;
import net.toshimichi.dungeons.enchants.Enchant;
import net.toshimichi.dungeons.enchants.Enchanter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class WizardEnchanter extends Enchanter {

    private int delta;

    public WizardEnchanter(Enchant enchant, Player player, ItemStack itemStack) {
        super(enchant, player, itemStack);
    }

    @Override
    protected void onEnabled() {
        int maxMana = DungeonsPlugin.getManaManager().getMaxMana(getPlayer());
        if (getEnchant().getLevel() == 1)
            delta = 10;
        else if (getEnchant().getLevel() == 2)
            delta = 15;
        else if (getEnchant().getLevel() == 3)
            delta = 30;
        DungeonsPlugin.getManaManager().setMaxMana(getPlayer(), maxMana + delta);
    }

    @Override
    public void tick() {
    }

    @Override
    protected void onDisabled() {
        int maxMana = DungeonsPlugin.getManaManager().getMaxMana(getPlayer());
        DungeonsPlugin.getManaManager().setMaxMana(getPlayer(), maxMana - delta);
    }

    @Override
    public boolean isAvailable() {
        return Arrays.asList(getPlayer().getInventory().getArmorContents()).contains(getItemStack());
    }
}
