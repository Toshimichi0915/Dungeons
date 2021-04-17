package net.toshimichi.dungeons.utils;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

final public class CompleteBlock implements ConfigurationSerializable {
    private final Pos pos;
    private final World world;
    private final Material type;
    private final BlockData blockData;

    public CompleteBlock(Pos pos, World world, Material type, BlockData blockData) {
        this.pos = pos;
        this.world = world;
        this.type = type;
        this.blockData = blockData;
    }

    public Pos getPos() {
        return pos;
    }

    public World getWorld() {
        return world;
    }

    public Material getType() {
        return type;
    }

    public void revive() {
        Block block = world.getBlockAt(pos.getX(), pos.getY(), pos.getZ());
        block.setType(type);
        block.setBlockData(blockData);
    }

    @Override
    public Map<String, Object> serialize() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("pos", pos);
        result.put("world", world.getUID().toString());
        result.put("type", type);
        result.put("blockData", blockData.getAsString());
        return result;
    }

    public static CompleteBlock deserialize(Map<String, Object> map) {
        Pos pos = (Pos) map.get("pos");
        World world = Bukkit.getWorld(UUID.fromString((String) map.get("world")));
        Material type = (Material) map.get("type");
        BlockData blockData = Bukkit.createBlockData((String) map.get("blockData"));
        return new CompleteBlock(pos, world, type, blockData);
    }
}
