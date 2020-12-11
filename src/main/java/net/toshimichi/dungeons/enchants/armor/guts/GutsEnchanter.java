package net.toshimichi.dungeons.enchants.armor.guts;

import net.toshimichi.dungeons.enchants.Enchant;
import net.toshimichi.dungeons.enchants.armor.ArmorEnchanter;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class GutsEnchanter extends ArmorEnchanter {

    private AttributeInstance instance;
    private AttributeModifier modifier;

    public GutsEnchanter(Enchant enchant, Player player, ItemStack itemStack) {
        super(enchant, player, itemStack);
    }

    @Override
    protected void onEnabled() {
        instance = getPlayer().getAttribute(Attribute.GENERIC_MAX_HEALTH);
        modifier = new AttributeModifier("guts", getEnchant().getLevel(), AttributeModifier.Operation.ADD_NUMBER);
        instance.addModifier(modifier);
    }

    @Override
    public void tick() {

    }

    @Override
    protected void onDisabled() {
        if (instance != null && modifier != null)
            instance.removeModifier(modifier);
    }

}
