package net.toshimichi.dungeons.nat.v1_16_4;

import net.toshimichi.dungeons.nat.api.EditSession;
import net.toshimichi.dungeons.nat.api.EditSessionFactory;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;

public class FastEditSessionFactory implements EditSessionFactory {

    @Override
    public EditSession newEditSession(World world) {
        return new FastEditSession(world, ((CraftWorld) world).getHandle().getMinecraftWorld());
    }
}
