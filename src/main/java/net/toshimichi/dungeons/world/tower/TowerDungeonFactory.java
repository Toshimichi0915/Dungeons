package net.toshimichi.dungeons.world.tower;

import com.sk89q.worldedit.extent.clipboard.Clipboard;
import net.toshimichi.dungeons.world.Dungeon;
import net.toshimichi.dungeons.world.DungeonFactory;

import java.io.File;
import java.util.Map;

public class TowerDungeonFactory implements DungeonFactory {

    private final Map<String, Clipboard> schematics;

    public TowerDungeonFactory(Map<String, Clipboard> schematics) {
        this.schematics = schematics;
    }

    @Override
    public String getId() {
        return "tower";
    }

    @Override
    public Dungeon newDungeon(File activeRoomSaveDir, String id) {
        return new TowerDungeon(activeRoomSaveDir, id, schematics);
    }
}
