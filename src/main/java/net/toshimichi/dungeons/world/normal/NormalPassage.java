package net.toshimichi.dungeons.world.normal;

import net.toshimichi.dungeons.utils.Range;
import net.toshimichi.dungeons.world.Passage;

final public class NormalPassage implements Passage {

    private final NormalDungeon dungeon;
    private final String id;
    private final String room1;
    private final String room2;
    private final Range gateway1;
    private final Range gateway2;

    public NormalPassage(NormalDungeon dungeon, String id, String room1, String room2, Range gateway1, Range gateway2) {
        this.dungeon = dungeon;
        this.id = id;
        this.room1 = room1;
        this.room2 = room2;
        this.gateway1 = gateway1;
        this.gateway2 = gateway2;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public NormalRoom getRoom1() {
        return dungeon.getRoomById(room1);
    }

    @Override
    public Range getGateway1() {
        return gateway1;
    }

    @Override
    public NormalRoom getRoom2() {
        return dungeon.getRoomById(room2);
    }

    @Override
    public Range getGateway2() {
        return gateway2;
    }
}
