package net.toshimichi.dungeons.world;

import net.toshimichi.dungeons.utils.Pos;

import java.util.Collection;

public interface ActiveRoom {

    Room getRoom();

    Collection<ModifiedBlock> getModifiedBlocks();

    void addModifiedBlock(ModifiedBlock block);

    void removeModifiedBlock(Pos pos);
}
