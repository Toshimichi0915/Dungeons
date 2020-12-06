package net.toshimichi.dungeons.nat.api;

import org.bukkit.Material;

public interface EditSession {

    void setBlock(int x, int y, int z, Material material);

    Material getBlock(int x, int y, int z);

    void update();
}
