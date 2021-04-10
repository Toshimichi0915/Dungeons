package net.toshimichi.dungeons.gui;

import net.toshimichi.dungeons.Dungeons;
import net.toshimichi.dungeons.enchants.Enchant;
import net.toshimichi.dungeons.enchants.EnchantManager;
import net.toshimichi.dungeons.enchants.Title;
import net.toshimichi.dungeons.utils.AnvilUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public enum AnvilState {
    AVAILABLE(Material.ANVIL, "anvil.button.available"),
    LEFT_NOT_SET(Material.RED_CONCRETE, "anvil.button.left_not_set"),
    RIGHT_NOT_SET(Material.RED_CONCRETE, "anvil.button.right_not_set"),
    INVALID(Material.RED_CONCRETE, "anvil.button.invalid"),
    TOKEN_INVALID(Material.RED_CONCRETE, "anvil.button.token_invalid"),
    RARE_INVALID(Material.RED_CONCRETE, "anvil.button.rare_invalid"),
    ALREADY_SET(Material.RED_CONCRETE, "anvil.button.already_set"),
    NO_XP(Material.RED_CONCRETE, "anvil.button.no_xp"),
    ERROR(Material.BEDROCK, "anvil.button.unknown");

    AnvilState(Material material, String key) {
        this.material = material;
        this.key = key;
    }

    private final Material material;
    private final String key;

    public Material getMaterial() {
        return material;
    }

    public String getKey() {
        return key;
    }

    public static AnvilState getAnvilState(Player player) {
        ItemStack left = null;
        ItemStack right = null;
        ItemStack result = null;
        try {
            List<ItemStack> leftList = Dungeons.getInstance().getStash().getItemStacks(player.getUniqueId(), "anvil_left");
            List<ItemStack> rightList = Dungeons.getInstance().getStash().getItemStacks(player.getUniqueId(), "anvil_right");
            List<ItemStack> resultList = Dungeons.getInstance().getStash().getItemStacks(player.getUniqueId(), "anvil_result");
            if (leftList != null && !leftList.isEmpty())
                left = leftList.get(0);
            if (rightList != null && !rightList.isEmpty())
                right = rightList.get(0);
            if (resultList != null && !resultList.isEmpty())
                result = resultList.get(0);
        } catch (IOException e) {
            e.printStackTrace();
            return ERROR;
        }
        if (left == null)
            return LEFT_NOT_SET;
        if (right == null)
            return RIGHT_NOT_SET;
        if (result != null)
            return ALREADY_SET;
        if (left.getType() != right.getType())
            return INVALID;
        if (player.getLevel() < 15)
            return NO_XP;
        EnchantManager manager = Dungeons.getInstance().getEnchantManager();
        Set<Enchant> leftEnchants = manager.getEnchants(left);
        Set<Enchant> rightEnchants = manager.getEnchants(right);
        if (leftEnchants.isEmpty() || rightEnchants.isEmpty())
            return INVALID;
        Set<Enchant> resultEnchants = manager.getEnchants(AnvilUtils.combine(left, right));
        int tokens = resultEnchants.stream()
                .map(Enchant::getLevel)
                .reduce(Integer::sum)
                .get();
        boolean rare = resultEnchants.stream()
                .anyMatch(p -> p.getTitle() == Title.RARE || p.getTitle() == Title.UNIQUE);
        if (tokens > 10)
            return TOKEN_INVALID;
        if (rare)
            return RARE_INVALID;

        return AnvilState.AVAILABLE;
    }
}
