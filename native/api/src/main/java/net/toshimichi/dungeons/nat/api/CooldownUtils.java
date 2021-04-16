package net.toshimichi.dungeons.nat.api;

import org.bukkit.Material;
import org.bukkit.entity.Player;

public interface CooldownUtils {

    /**
     * 指定されたアイテムに関してクールダウンを設定します.
     * クールダウン中は指定されたアイテムが使えなくなります.
     *
     * @param player   クールダウンの影響を受けるプレイヤー
     * @param material クールダウンを設定するアイテム
     * @param tick     クールダウンの期間 (tick)
     */
    void setCooldown(Player player, Material material, int tick);

    /**
     * 指定されたアイテムに関してプレイヤーが持つクールダウンを返します.
     * クールダウン中は指定されたアイテムが使えなくなります.
     *
     * @param player   クールダウンの影響を受けるプレイヤー
     * @param material クールダウンが設定されたアイテム
     * @return クールダウンの期間 ( tick )
     */
    int getCooldown(Player player, Material material);
}
