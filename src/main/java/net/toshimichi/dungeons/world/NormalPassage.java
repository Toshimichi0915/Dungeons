package net.toshimichi.dungeons.world;

import net.toshimichi.dungeons.utils.Range;

final public class NormalPassage implements Passage {

    private final NormalRoom room1;
    private final NormalRoom room2;
    private final Range gateway1;
    private final Range gateway2;

    public NormalPassage(NormalRoom room1, NormalRoom room2, Range gateway1, Range gateway2) {
        this.room1 = room1;
        this.room2 = room2;
        this.gateway1 = gateway1;
        this.gateway2 = gateway2;
    }

    @Override
    public NormalRoom getRoom1() {
        return room1;
    }

    @Override
    public Range getGateway1() {
        return gateway1;
    }

    @Override
    public NormalRoom getRoom2() {
        return room2;
    }

    @Override
    public Range getGateway2() {
        return gateway2;
    }
}
