package net.toshimichi.dungeons.nat.api;

import org.bukkit.World;

public interface EditSessionFactory {
     EditSession newEditSession(World world);
}
