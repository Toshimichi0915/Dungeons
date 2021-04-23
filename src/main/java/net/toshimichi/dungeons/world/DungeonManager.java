package net.toshimichi.dungeons.world;

import java.util.List;

public interface DungeonManager {
    List<Dungeon> getDungeons();

    List<DungeonFactory> getDungeonFactories();

    Dungeon newDungeon(String factoryId) throws Exception;

    void load() throws Exception;

    void save() throws Exception;
}
