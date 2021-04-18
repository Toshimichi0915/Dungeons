package net.toshimichi.dungeons.world.normal;

import net.toshimichi.dungeons.utils.Direction;
import net.toshimichi.dungeons.utils.Range;
import net.toshimichi.dungeons.world.Room;
import net.toshimichi.dungeons.world.RoomFactory;

import java.util.List;

abstract public class NormalRoomFactory implements RoomFactory {

    private final NormalDungeon dungeon;

    public NormalRoomFactory(NormalDungeon dungeon) {
        this.dungeon = dungeon;
    }

    protected NormalRoom useGateway(Room room, Range gateway) {
        NormalRoom normalRoom = (NormalRoom) dungeon.getRoomById(room.getId());
        if (normalRoom == null || !normalRoom.equals(room)) {
            throw new IllegalStateException("Specified room is not in the dungeon");
        }
        List<Range> gateways = normalRoom.getUsableGateways();
        gateways.remove(gateway);
        normalRoom.setUsableGateways(gateways);
        return normalRoom;
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
