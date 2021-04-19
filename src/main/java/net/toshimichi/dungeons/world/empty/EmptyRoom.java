package net.toshimichi.dungeons.world.empty;

import net.toshimichi.dungeons.utils.Direction;
import net.toshimichi.dungeons.utils.Pos;
import net.toshimichi.dungeons.world.normal.NormalActiveRoom;
import net.toshimichi.dungeons.world.normal.NormalDungeon;
import net.toshimichi.dungeons.world.normal.NormalRoom;
import net.toshimichi.dungeons.world.normal.NormalRoomFactory;

public class EmptyRoom extends NormalRoom {

    public EmptyRoom(NormalDungeon dungeon, NormalRoomFactory roomFactory, String id) {
        super(dungeon, roomFactory, id);
    }

    public EmptyRoom(NormalDungeon dungeon, NormalRoomFactory roomFactory, String id, Pos origin, Direction direction) {
        super(dungeon, roomFactory, id, origin, direction);
    }

    @Override
    protected NormalActiveRoom newActiveRoom() {
        return new EmptyActiveRoom(this);
    }
}
