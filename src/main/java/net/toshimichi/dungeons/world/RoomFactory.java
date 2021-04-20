package net.toshimichi.dungeons.world;

import net.toshimichi.dungeons.utils.Direction;
import net.toshimichi.dungeons.utils.Pos;
import net.toshimichi.dungeons.utils.Range;
import org.bukkit.configuration.ConfigurationSection;

import java.util.List;

public interface RoomFactory {

    String getId();

    Room load(String id, ConfigurationSection section);

    Pos getArea(Pos origin, Direction direction);

    List<? extends Range> getGateways(Pos origin, Direction direction);

    Dungeon getDungeon();
}
