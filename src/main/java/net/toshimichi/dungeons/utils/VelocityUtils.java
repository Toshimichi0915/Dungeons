package net.toshimichi.dungeons.utils;

import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.WeakHashMap;

/**
 * プレイヤーの移動速度を管理します.
 */
public class VelocityUtils {

    private static final WeakHashMap<Player, Vector> vectors = new WeakHashMap<>();

    /**
     * プレイヤーの移動速度を返します.
     * @param player プレイヤー
     * @return プレイヤーの移動速度
     */
    public static Vector getVelocity(Player player) {
        return vectors.getOrDefault(player, new Vector(0, 0, 0));
    }

    /**
     * プレイヤーの移動速度を設定します.
     * @param player プレイヤー
     * @param velocity プレイヤーの移動速度
     */
    public static void setVelocity(Player player, Vector velocity) {
        vectors.put(player, velocity);
    }
}
