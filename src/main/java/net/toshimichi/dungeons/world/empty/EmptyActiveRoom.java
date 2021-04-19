package net.toshimichi.dungeons.world.empty;

import net.toshimichi.dungeons.world.ActiveRoom;
import net.toshimichi.dungeons.world.normal.NormalActiveRoom;
import net.toshimichi.dungeons.world.normal.NormalRoom;

public class EmptyActiveRoom extends NormalActiveRoom {
    public EmptyActiveRoom(NormalRoom room) {
        super(room);
    }
}
