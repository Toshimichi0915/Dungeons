package net.toshimichi.dungeons.enchants.armor.wizard;

import net.toshimichi.dungeons.Dungeons;
import net.toshimichi.dungeons.enchants.Enchant;
import net.toshimichi.dungeons.enchants.armor.ArmorEnchanter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class WizardEnchanter extends ArmorEnchanter {

    private int delta;

    public WizardEnchanter(Enchant enchant, Player player, ItemStack itemStack) {
        super(enchant, player, itemStack);
    }

    @Override
    protected void onEnabled() {
        int maxMana = Dungeons.getInstance().getManaManager().getMaxMana(getPlayer());
        if (getEnchant().getLevel() == 1)
            delta = 10;
        else if (getEnchant().getLevel() == 2)
            delta = 15;
        else if (getEnchant().getLevel() == 3)
            delta = 30;
        Dungeons.getInstance().getManaManager().setMaxMana(getPlayer(), maxMana + delta);
    }

    @Override
    public void tick() {
    }

    @Override
    protected void onDisabled() {
        int maxMana = Dungeons.getInstance().getManaManager().getMaxMana(getPlayer());
        Dungeons.getInstance().getManaManager().setMaxMana(getPlayer(), maxMana - delta);
    }

}
