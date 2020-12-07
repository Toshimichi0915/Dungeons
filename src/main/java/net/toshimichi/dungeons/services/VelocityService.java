package net.toshimichi.dungeons.services;

import net.toshimichi.dungeons.utils.VelocityUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.WeakHashMap;

public class VelocityService implements Service {

    private final WeakHashMap<Player, Vector> vectors = new WeakHashMap<>();

    @Override
    public void run() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            Vector old = vectors.getOrDefault(p, new Vector(0, 0, 0));
            Vector now = p.getLocation().toVector();
            VelocityUtils.setVelocity(p, now.clone().subtract(old));
            vectors.put(p, now);
        }
    }
}
