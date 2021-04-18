package net.toshimichi.dungeons.world;

import net.toshimichi.dungeons.utils.Direction;
import net.toshimichi.dungeons.utils.Pos;
import net.toshimichi.dungeons.utils.Range;

public interface RoomFactory {

    String getId();

    Room newConnectedRoom(String id, Room room1, Range gate1, Range openGate, Direction direction);

    Room newDistinctRoom(String id, Range openGate, Direction direction);

    Room newEmptyRoom(String id);

    Pos getSize();

    Dungeon getDungeon();
}
