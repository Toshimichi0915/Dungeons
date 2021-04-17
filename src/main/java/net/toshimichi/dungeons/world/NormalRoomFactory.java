package net.toshimichi.dungeons.world;

import net.toshimichi.dungeons.utils.Range;

abstract public class NormalRoomFactory implements RoomFactory {

    private final String id;
    private final String name;

    public NormalRoomFactory(String id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    abstract public NormalPassage newRoom(Room room1, Range gate, Range openGate);
}
