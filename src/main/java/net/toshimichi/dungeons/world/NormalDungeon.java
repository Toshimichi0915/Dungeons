package net.toshimichi.dungeons.world;

import net.toshimichi.dungeons.utils.Range;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

abstract public class NormalDungeon implements Dungeon {

    private final File activeRoomSaveDir;
    private final String id;
    private ArrayList<NormalRoom> rooms = new ArrayList<>();
    private String name;
    private int creationPoints;

    public NormalDungeon(File activeRoomSaveDir, String id) {
        this.activeRoomSaveDir = activeRoomSaveDir;
        this.id = id;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<? extends Room> getRooms() {
        return new ArrayList<>(rooms);
    }

    @Override
    public int getCreationPoints() {
        return creationPoints;
    }

    @Override
    public void setCreationPoints(int points) {
        this.creationPoints = points;
    }

    @Override
    public void save(ConfigurationSection section) {
        section.set("name", name);
        section.set("creationPoints", creationPoints);
        for (NormalRoom room : rooms) {
            String roomBase = "rooms." + room.getId() + ".";
            section.set(roomBase + "name", room.getName());
            ArrayList<HashMap<String, Object>> passages = new ArrayList<>();
            for (NormalPassage passage : room.getPassages()) {
                HashMap<String, Object> child = new HashMap<>();
                child.put("room1", passage.getRoom1().getId());
                child.put("room2", passage.getRoom2().getId());
                child.put("gateway1", passage.getGateway1());
                child.put("gateway2", passage.getGateway2());
                passages.add(child);
            }
            section.set(roomBase + ".passages", passages);
            section.set(roomBase + ".usableGateways", room.getUsableGateways());
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void load(ConfigurationSection section) {
        name = section.getString("name");
        creationPoints = section.getInt("creationPoints");

        rooms = new ArrayList<>();
        for (String roomId : section.getConfigurationSection("rooms").getKeys(false)) {
            String roomBase = "rooms." + roomId + ".";
            String name = section.getString(roomBase + "name");
            NormalRoom room = new NormalRoom(this, roomId);
            room.setName(name);
            rooms.add(room);
        }

        for (NormalRoom room : rooms) {
            ArrayList<NormalPassage> passages = new ArrayList<>();
            for (Map<?, ?> passage : section.getMapList("rooms." + room.getId() + ".passages")) {
                NormalRoom room1 = (NormalRoom) findRoom((String) passage.get("room1"));
                NormalRoom room2 = (NormalRoom) findRoom((String) passage.get("room2"));
                Range gateway1 = (Range) passage.get("gateway1");
                Range gateway2 = (Range) passage.get("gateway2");
                passages.add(new NormalPassage(room1, room2, gateway1, gateway2));
            }
            room.setPassages(passages);
            room.setUsableGateways((List<Range>) section.getList("rooms." + room.getId() + ".usableGateways"));
        }
    }

    private File getFile(NormalActiveRoom activeRoom) {
        NormalRoom room = activeRoom.getRoom();
        return new File(activeRoomSaveDir, room.getId().substring(0, 2) + "/" + room.getId() + ".yml");
    }

    public NormalActiveRoom loadActiveRoom(NormalRoom room) {
        NormalActiveRoom activeRoom = room.newActiveRoom();
        YamlConfiguration yaml = new YamlConfiguration();
        try {
            yaml.load(getFile(activeRoom));
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
        activeRoom.load(yaml);
        return activeRoom;
    }

    public void saveActiveRoom(NormalActiveRoom activeRoom) {
        YamlConfiguration config = new YamlConfiguration();
        activeRoom.save(config);
        try {
            config.save(getFile(activeRoom));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
