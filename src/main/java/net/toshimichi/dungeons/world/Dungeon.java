package net.toshimichi.dungeons.world;

import org.bukkit.configuration.ConfigurationSection;

import java.util.List;

public interface Dungeon {

    String getId();

    String getName();

    void setName(String name);

    List<? extends Room> getRooms();

    default Room getRoomById(String id) {
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

    default RoomFactory getRoomFactoryById(String id) {
        for(RoomFactory factory : getRoomFactories()) {
            if(factory.getId().equals(id)) {
                return factory;
            }
        }
        return null;
    }

    void save(ConfigurationSection section);

    void load(ConfigurationSection section);

    void update(int ticks);

    String newRoomId();
}
