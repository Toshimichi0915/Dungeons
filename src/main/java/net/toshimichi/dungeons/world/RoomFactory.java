package net.toshimichi.dungeons.world;

import net.toshimichi.dungeons.utils.Range;

public interface RoomFactory {

    String getId();

    String getName();

    Passage newRoom(Room room1, Range gate, Range openGate);

    Room newDistinctRoom(Range openGate);
}
