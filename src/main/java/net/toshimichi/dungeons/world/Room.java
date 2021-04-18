package net.toshimichi.dungeons.world;

import net.toshimichi.dungeons.utils.Direction;
import net.toshimichi.dungeons.utils.Range;

import java.util.List;

public interface Room {

    String getId();

    String getName();

    void setName(String name);

    Dungeon getDungeon();

    List<? extends Passage> getPassages();

    List<? extends Range> getUsableGateways();

    Range getArea();

    Direction getDirection();

    RoomFactory getRoomFactory();

    void build();

    ActiveRoom load();

    boolean isLoaded();

    void unload();
}
