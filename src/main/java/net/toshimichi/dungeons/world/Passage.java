package net.toshimichi.dungeons.world;

import net.toshimichi.dungeons.utils.Range;

public interface Passage {

    String getId();

    Room getRoom1();

    Range getGateway1();

    Room getRoom2();

    Range getGateway2();
}
