package net.toshimichi.dungeons.world.empty;

import net.toshimichi.dungeons.world.normal.NormalActiveRoom;
import net.toshimichi.dungeons.world.normal.NormalDungeon;
import net.toshimichi.dungeons.world.normal.NormalRoom;
import net.toshimichi.dungeons.world.normal.NormalRoomFactory;

public class EmptyRoom extends NormalRoom {

    public EmptyRoom(NormalDungeon dungeon, NormalRoomFactory roomFactory, String id) {
        super(dungeon, roomFactory, id);
    }

    @Override
    protected NormalActiveRoom newActiveRoom() {
        return new EmptyActiveRoom(this);
    }
}
