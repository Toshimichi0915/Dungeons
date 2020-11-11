package net.toshimichi.dungeons.gui;


import net.toshimichi.dungeons.DungeonsPlugin;
import net.toshimichi.dungeons.utils.EnchantUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;
import java.util.List;

public enum EnchantState {
    AVAILABLE(Material.ENCHANTING_TABLE, "mysticwell.button.available"),
    INVALID(Material.RED_CONCRETE, "mysticwell.button.invalid"),
    MAXED_OUT(Material.RED_CONCRETE, "mysticwell.button.maxed_out"),
    NO_GOLD(Material.RED_CONCRETE, "mysticwell.button.no_gold"),
    NOT_SET(Material.RED_CONCRETE, "mysticwell.button.not_set"),
    ERROR(Material.BEDROCK, "mysticwell.button.unknown");

    private final Material material;
    private final String key;

    EnchantState(Material material, String key) {
        this.material = material;
        this.key = key;
    }

    public Material getMaterial() {
        return material;
    }

    public String getKey() {
        return key;
    }

    public static EnchantState getEnchantState(Player player) {
        List<ItemStack> mysticWell;
        try {
            mysticWell = DungeonsPlugin.getStash().getItemStacks(player.getUniqueId(), "mystic_well");
        } catch (IOException e) {
            e.printStackTrace();
            return EnchantState.ERROR;
        }
        if (mysticWell.isEmpty())
            return EnchantState.NOT_SET;
        int tier = DungeonsPlugin.getEnchantManager().getTier(mysticWell.get(0));
        if (tier < 0)
            return EnchantState.INVALID;
        else if (tier == 3)
            return EnchantState.MAXED_OUT;

        if (EnchantUtils.getCost(mysticWell.get(0)) > DungeonsPlugin.getEconomy().getMoney(player.getUniqueId()))
            return EnchantState.NO_GOLD;

        return EnchantState.AVAILABLE;
    }
}
