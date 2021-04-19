package net.toshimichi.dungeons.world;

import org.bukkit.World;

import java.util.List;

public interface DungeonManager {
    List<Dungeon> getDungeons();

    List<DungeonFactory> getDungeonFactories();

    Dungeon newDungeon(String factoryIdd);

    void load();

    void save();
}
