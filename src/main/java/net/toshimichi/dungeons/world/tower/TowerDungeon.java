package net.toshimichi.dungeons.world.tower;

import com.sk89q.worldedit.extent.clipboard.Clipboard;
import net.toshimichi.dungeons.world.normal.NormalDungeon;

import java.io.File;
import java.util.Map;

public class TowerDungeon extends NormalDungeon {

    public TowerDungeon(File activeRoomSaveDir, String id, Map<String, Clipboard> schematics) {
        super(activeRoomSaveDir, id);
        addRoomFactory(new TowerHub0Factory(this, schematics.get("tower/hub_0")));
        addRoomFactory(new Passage0Factory(this, schematics.get("tower/passage_0")));
        addRoomFactory(new Upstairs0Factory(this, schematics.get("tower/upstairs_0")));
    }
}
