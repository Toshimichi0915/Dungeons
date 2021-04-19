package net.toshimichi.dungeons.world;

import java.io.File;

public interface DungeonFactory {

    String getId();

    Dungeon newDungeon(File activeRoomSaveDir, String id);
}
