package net.toshimichi.dungeons.world;

import net.toshimichi.dungeons.utils.Direction;
import net.toshimichi.dungeons.utils.Pos;
import net.toshimichi.dungeons.world.normal.NormalRoom;

public interface Buildable {
    NormalRoom build(String id, Pos origin, Direction direction);
}
