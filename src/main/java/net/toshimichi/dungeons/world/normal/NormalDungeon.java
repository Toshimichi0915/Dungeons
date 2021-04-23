package net.toshimichi.dungeons.world.normal;

import net.toshimichi.dungeons.utils.Range;
import net.toshimichi.dungeons.world.Dungeon;
import net.toshimichi.dungeons.world.Room;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

abstract public class NormalDungeon implements Dungeon {

    private final File activeRoomSaveDir;
    private final String id;
    private final ArrayList<NormalRoomFactory> roomFactories = new ArrayList<>();
    private World world;
    private ArrayList<NormalRoom> rooms = new ArrayList<>();
    private ArrayList<NormalPassage> passages = new ArrayList<>();
    private String name;
    private int creationPoints;
    private int roomIdCounter;
    private int passageIdCounter;

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
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    @Override
    public List<NormalRoom> getRooms() {
        return new ArrayList<>(rooms);
    }

    public void addRoom(NormalRoom room) {
        rooms.add(room);
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
    public ArrayList<NormalRoomFactory> getRoomFactories() {
        return new ArrayList<>(roomFactories);
    }

    protected void addRoomFactory(NormalRoomFactory roomFactory) {
        roomFactories.add(roomFactory);
    }

    @Override
    public void update(int ticks) {
    }

    @Override
    public NormalRoom getRoomById(String id) {
        return (NormalRoom) Dungeon.super.getRoomById(id);
    }

    @Override
    public NormalRoomFactory getRoomFactoryById(String id) {
        return (NormalRoomFactory) Dungeon.super.getRoomFactoryById(id);
    }

    @Override
    public void save(ConfigurationSection section) {
        section.set("name", name);
        section.set("world", world.getUID().toString());
        section.set("creationPoints", creationPoints);
        for (NormalPassage passage : passages) {
            String passageBase = "passages." + passage.getId() + ".";
            section.set(passageBase + "room1", passage.getRoom1().getId());
            section.set(passageBase + "room2", passage.getRoom2().getId());
            section.set(passageBase + "gateway1", passage.getGateway1());
            section.set(passageBase + "gateway2", passage.getGateway2());
        }
        for (NormalRoom room : rooms) {
            ConfigurationSection roomSection = section.createSection("rooms." + room.getId());
            room.save(roomSection);
            roomSection.set("factory", room.getRoomFactory().getId());
        }
    }

    @Override
    public void load(ConfigurationSection section) {
        name = section.getString("name");
        world = Bukkit.getWorld(UUID.fromString(section.getString("world")));
        creationPoints = section.getInt("creationPoints");
        passages = new ArrayList<>();
        passageIdCounter = 0;
        ConfigurationSection passagesSection = section.getConfigurationSection("passages");
        if (passagesSection != null) {
            for (String passageId : passagesSection.getKeys(false)) {
                String room1 = passagesSection.getString(passageId + ".room1");
                String room2 = passagesSection.getString(passageId + ".room2");
                Range gateway1 = (Range) passagesSection.get(passageId + ".gateway1");
                Range gateway2 = (Range) passagesSection.get(passageId + ".gateway2");
                passages.add(new NormalPassage(this, passageId, room1, room2, gateway1, gateway2));
                passageIdCounter++;
            }
        }

        rooms = new ArrayList<>();
        roomIdCounter = 0;
        ConfigurationSection roomsSection = section.getConfigurationSection("rooms");
        if (roomsSection != null) {
            for (String roomId : roomsSection.getKeys(false)) {
                NormalRoomFactory factory = getRoomFactoryById(roomsSection.getString(roomId + ".factory"));
                NormalRoom room = factory.load(roomId, roomsSection.getConfigurationSection(roomId));
                addRoom(room);
                roomIdCounter++;
            }
        }

        for (NormalRoom room : rooms) {
            ConfigurationSection roomSection = section.getConfigurationSection("rooms." + room.getId());
            if (roomSection != null) {
                room.load(roomSection);
            }
        }
    }

    public NormalPassage getPassageById(String id) {
        for (NormalPassage passage : passages) {
            if (passage.getId().equals(id))
                return passage;
        }
        return null;
    }

    public void addPassage(NormalPassage passage) {
        passages.add(passage);
    }

    private File getFile(NormalActiveRoom activeRoom) {
        NormalRoom room = activeRoom.getRoom();
        return new File(activeRoomSaveDir, room.getId().substring(0, 2) + "/" + room.getId() + ".yaml");
    }

    public NormalActiveRoom loadActiveRoom(NormalRoom room) {
        NormalActiveRoom activeRoom = room.newActiveRoom();
        YamlConfiguration yaml = new YamlConfiguration();
        File file = getFile(activeRoom);
        if (file.exists()) {
            try {
                yaml.load(getFile(activeRoom));
            } catch (IOException | InvalidConfigurationException e) {
                e.printStackTrace();
            }
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

    public NormalPassage connect(Room room1, Range gateway1, Room room2, Range gateway2) {
        NormalRoom normalRoom1 = getRoomById(room1.getId());
        NormalRoom normalRoom2 = getRoomById(room2.getId());
        if (normalRoom1 == null || !normalRoom1.equals(room1)) {
            throw new IllegalStateException("Specified room1 is not in the dungeon: " + room1);
        }
        if (normalRoom2 == null || !normalRoom2.equals(room2)) {
            throw new IllegalStateException("Specified room2 is not in the dungeon: " + room2);
        }

        List<Range> gateways1 = normalRoom1.getUsableGateways();
        List<Range> gateways2 = normalRoom2.getUsableGateways();
        gateways1.remove(gateway1);
        gateways2.remove(gateway2);
        normalRoom1.setUsableGateways(gateways1);
        normalRoom2.setUsableGateways(gateways2);

        NormalPassage passage = new NormalPassage(this, newPassageId(),
                normalRoom1.getId(), normalRoom2.getId(), gateway1, gateway2);

        List<NormalPassage> passages1 = normalRoom1.getPassages();
        List<NormalPassage> passages2 = normalRoom2.getPassages();
        passages1.add(passage);
        passages2.add(passage);
        normalRoom1.setPassages(passages1);
        normalRoom1.setPassages(passages2);
        addPassage(passage);

        return passage;
    }

    @Override
    public String newRoomId() {
        return "room_" + roomIdCounter++;
    }

    public String newPassageId() {
        return "passage_" + passageIdCounter++;
    }

}
