package net.toshimichi.dungeons.world.normal;

import net.toshimichi.dungeons.utils.Direction;
import net.toshimichi.dungeons.utils.Range;
import net.toshimichi.dungeons.world.Room;
import net.toshimichi.dungeons.world.RoomFactory;

abstract public class NormalRoomFactory implements RoomFactory {

    private final NormalDungeon dungeon;

    public NormalRoomFactory(NormalDungeon dungeon) {
        this.dungeon = dungeon;
    }

    @Override
    abstract public NormalRoom newConnectedRoom(String id, Room room1, Range gate1, Range openGate, Direction direction);

    @Override
    abstract public NormalRoom newDistinctRoom(String id, Range openGate, Direction direction);

    @Override
    abstract public NormalRoom newEmptyRoom(String id);

    @Override
    public NormalDungeon getDungeon() {
        return dungeon;
    }
}
