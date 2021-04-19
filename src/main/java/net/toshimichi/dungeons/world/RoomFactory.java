package net.toshimichi.dungeons.world;

import net.toshimichi.dungeons.utils.Pos;
import net.toshimichi.dungeons.utils.Range;
import org.bukkit.configuration.ConfigurationSection;

import java.util.List;

public interface RoomFactory {

    String getId();

    Room load(String id, ConfigurationSection section);

    Pos getSize();

    List<? extends Range> getGateways();

    Dungeon getDungeon();
}
