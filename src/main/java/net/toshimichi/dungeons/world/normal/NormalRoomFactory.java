package net.toshimichi.dungeons.world.normal;

import net.toshimichi.dungeons.world.RoomFactory;
import org.bukkit.configuration.ConfigurationSection;

abstract public class NormalRoomFactory implements RoomFactory {

    private final NormalDungeon dungeon;

    public NormalRoomFactory(NormalDungeon dungeon) {
        this.dungeon = dungeon;
    }

    @Override
    abstract public NormalRoom load(String id, ConfigurationSection section);

    @Override
    public NormalDungeon getDungeon() {
        return dungeon;
    }
}
