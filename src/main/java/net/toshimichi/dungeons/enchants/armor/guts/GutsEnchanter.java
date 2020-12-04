package net.toshimichi.dungeons.enchants.armor.guts;

import net.toshimichi.dungeons.enchants.Enchant;
import net.toshimichi.dungeons.enchants.armor.ArmorEnchanter;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class GutsEnchanter extends ArmorEnchanter {

    private AttributeModifier modifier;

    public GutsEnchanter(Enchant enchant, Player player, ItemStack itemStack) {
        super(enchant, player, itemStack);
    }

    @Override
    protected void onEnabled() {
        modifier = new AttributeModifier(getPlayer().getUniqueId(), "guts", getEnchant().getLevel(), AttributeModifier.Operation.ADD_NUMBER);
        AttributeInstance attr = getPlayer().getAttribute(Attribute.GENERIC_MAX_HEALTH);
        attr.addModifier(modifier);
    }

    @Override
    public void tick() {

    }

    @Override
    protected void onDisabled() {
        if (modifier != null)
            getPlayer().getAttribute(Attribute.GENERIC_MAX_HEALTH).removeModifier(modifier);
    }

}
