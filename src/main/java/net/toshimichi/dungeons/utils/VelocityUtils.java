package net.toshimichi.dungeons.utils;

import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.WeakHashMap;

public class VelocityUtils {

    private static final WeakHashMap<Player, Vector> vectors = new WeakHashMap<>();

    public static Vector getVelocity(Player player) {
        return vectors.getOrDefault(player, new Vector(0, 0, 0));
    }

    public static void setVelocity(Player player, Vector velocity) {
        vectors.put(player, velocity);
    }
}
