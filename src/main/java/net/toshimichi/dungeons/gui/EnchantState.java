package net.toshimichi.dungeons.gui;


import net.toshimichi.dungeons.DungeonsPlugin;
import net.toshimichi.dungeons.misc.YamlStash;
import net.toshimichi.dungeons.utils.EnchantUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;
import java.util.List;

/**
 * Mystic Wellの状態を表します.
 */
public enum EnchantState {
    /**
     * エンチャント可能であることを表します.
     */
    AVAILABLE(Material.ENCHANTING_TABLE, "mysticwell.button.available"),
    /**
     * エンチャント不可能なアイテムがセットされていることを表します.
     */
    INVALID(Material.RED_CONCRETE, "mysticwell.button.invalid"),

    /**
     * すでに最大までエンチャントされていることを表します.
     */
    MAXED_OUT(Material.RED_CONCRETE, "mysticwell.button.maxed_out"),

    /**
     * エンチャントするのに所持金が足りないことを表します.
     */
    NO_GOLD(Material.RED_CONCRETE, "mysticwell.button.no_gold"),

    /**
     * Mystic Wellにアイテムがセットされていないことを表します.
     */
    NOT_SET(Material.RED_CONCRETE, "mysticwell.button.not_set"),

    /**
     * エラーによりアイテムがエンチャントできないことを表します.
     */
    ERROR(Material.BEDROCK, "mysticwell.button.unknown");

    private final Material material;
    private final String key;

    EnchantState(Material material, String key) {
        this.material = material;
        this.key = key;
    }

    /**
     * エンチャント用のボタンに表示される {@link Material} を返します.
     *
     * @return エンチャント用のボタンに表示される {@link Material}
     */
    public Material getMaterial() {
        return material;
    }

    /**
     * langファイルのキーを返します.
     *
     * @return langファイルのキー
     */
    public String getKey() {
        return key;
    }

    /**
     * 現在のMystic Wellの状態を返します.
     * またMystic Wellのアイテムは {@link YamlStash} の
     * {@code mystic_well} にセットされているアイテムが使用されます.
     *
     * @param player プレイヤー
     * @return 現在のMystic Wellの状態
     */
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
