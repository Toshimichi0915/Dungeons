package net.toshimichi.dungeons.world;

import org.bukkit.configuration.ConfigurationSection;

import java.util.List;

public interface Dungeon {

    String getId();

    String getName();

    void setName(String name);

    List<? extends Room> getRooms();

    default Room findRoom(String id) {
        for (Room room : getRooms()) {
            if (room.getId().equals(id)) {
                return room;
            }
        }
        return null;
    }

    int getCreationPoints();

    void setCreationPoints(int points);

    List<? extends RoomFactory> getRoomFactories();

    void save(ConfigurationSection section);

    void load(ConfigurationSection section);
}
