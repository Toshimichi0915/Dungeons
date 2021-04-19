package net.toshimichi.dungeons.world;

import java.util.List;

public interface DungeonManager {
    List<Dungeon> getDungeons();

    List<DungeonFactory> getDungeonFactories();

    Dungeon newDungeon(String factoryId);

    void load();

    void save();
}
