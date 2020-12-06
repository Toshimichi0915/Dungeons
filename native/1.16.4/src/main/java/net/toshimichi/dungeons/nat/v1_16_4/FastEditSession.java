package net.toshimichi.dungeons.nat.v1_16_4;

import net.minecraft.server.v1_16_R3.*;
import net.toshimichi.dungeons.nat.api.EditSession;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_16_R3.util.CraftMagicNumbers;

import java.util.HashMap;
import java.util.Map;

public class FastEditSession implements EditSession {

    private final org.bukkit.World bukkitWorld;
    private final World world;
    private final HashMap<BlockPosition, IBlockData> modified = new HashMap<>();

    public FastEditSession(org.bukkit.World bukkitWorld, World world) {
        this.bukkitWorld = bukkitWorld;
        this.world = world;
    }

    @Override
    public void setBlock(int x, int y, int z, Material material) {
        modified.put(new BlockPosition(x, y, z), CraftMagicNumbers.getBlock(material).getBlockData());
    }

    @Override
    public Material getBlock(int x, int y, int z) {
        IBlockData data = modified.get(new BlockPosition(x, y, z));
        if (data != null)
            return CraftMagicNumbers.getMaterial(data).getItemType();
        return new Location(bukkitWorld, x, y, z).getBlock().getType();
    }

    @Override
    public void update() {

        //modify blocks
        for (Map.Entry<BlockPosition, IBlockData> entry : modified.entrySet()) {
            int chunkX = entry.getKey().getX() >> 4;
            int chunkZ = entry.getKey().getZ() >> 4;
            Chunk chunk = world.getChunkProvider().getChunkAt(chunkX, chunkZ, true);
            if (chunk == null) continue;
            chunk.setType(entry.getKey(), entry.getValue(), false);
        }

        LightEngine engine = world.getChunkProvider().getLightEngine();
        //update lights
        for (BlockPosition pos : modified.keySet()) {
            world.getMinecraftWorld().getChunkProvider().flagDirty(pos);
            engine.a(pos);
        }

        //clear modified blocks
        modified.clear();
    }
}
