package net.toshimichi.dungeons.world.normal;

import net.toshimichi.dungeons.utils.Direction;
import net.toshimichi.dungeons.utils.Pos;
import net.toshimichi.dungeons.utils.Range;
import net.toshimichi.dungeons.world.ActiveRoom;
import net.toshimichi.dungeons.world.Room;
import net.toshimichi.dungeons.world.RoomFactory;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.List;

abstract public class NormalRoom implements Room {

    private final NormalDungeon dungeon;
    private final RoomFactory roomFactory;
    private final String id;
    private String name;
    private ArrayList<NormalPassage> passages = new ArrayList<>();
    private ArrayList<Range> usableGateways = new ArrayList<>();
    private NormalActiveRoom activeRoom;
    private Range area;
    private Direction direction;

    public NormalRoom(NormalDungeon dungeon, NormalRoomFactory roomFactory, String id) {
        this.dungeon = dungeon;
        this.roomFactory = roomFactory;
        this.id = id;
    }

    public NormalRoom(NormalDungeon dungeon, NormalRoomFactory roomFactory, String id, Room room1, Range gate1, Range openGate, Direction direction) {
        this.dungeon = dungeon;
        this.roomFactory = roomFactory;
        this.id = id;
        this.direction = direction;
        dungeon.addPassage(dungeon.connect(room1, gate1, this, openGate));
        initArea(openGate, direction);
    }

    public NormalRoom(NormalDungeon dungeon, NormalRoomFactory roomFactory, String id, Range openGate, Direction direction) {
        this.dungeon = dungeon;
        this.roomFactory = roomFactory;
        this.id = id;
        this.direction = direction;
        usableGateways.add(openGate);
        initArea(openGate, direction);
    }

    private void initArea(Range openGate, Direction direction) {
        int gateX = (int) (openGate.getMinPoint().getX() * openGate.getXLength() * 0.5);
        int gateY = openGate.getMinPoint().getY();
        int gateZ = (int) (openGate.getMinPoint().getZ() * openGate.getXLength() * 0.5);
        if (direction.getX() != 0) {
            gateX += (int) (openGate.getXLength() * direction.getX() * 0.5);
        }
        if (direction.getZ() != 0) {
            gateZ += (int) (openGate.getZLength() * direction.getZ() * 0.5);
        }

        int lengthX = roomFactory.getSize().getX();
        int lengthY = roomFactory.getSize().getY();
        int lengthZ = roomFactory.getSize().getZ();
        int centerX = (int) (lengthX * direction.getX() * 0.5 + direction.getX() + gateX);
        int centerY = (int) (lengthY * 0.5 + gateY);
        int centerZ = (int) (lengthZ * direction.getZ() * 0.5 + direction.getZ() + gateZ);

        Pos minPos = new Pos(centerX - lengthX, centerY - lengthY, centerZ - lengthZ);
        Pos maxPos = new Pos(centerX + lengthX, centerY + lengthY, centerZ + lengthZ);
        area = new Range(minPos, maxPos);
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
    public NormalDungeon getDungeon() {
        return dungeon;
    }

    @Override
    public List<NormalPassage> getPassages() {
        return new ArrayList<>(passages);
    }

    public void setPassages(List<NormalPassage> passages) {
        this.passages = new ArrayList<>(passages);
    }

    @Override
    public List<Range> getUsableGateways() {
        return new ArrayList<>(usableGateways);
    }

    @Override
    public RoomFactory getRoomFactory() {
        return roomFactory;
    }

    @Override
    public void build() {
    }

    @Override
    public Range getArea() {
        return area;
    }

    public void setArea(Range area) {
        this.area = area;
    }

    @Override
    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    @Override
    public ActiveRoom load() {
        if (activeRoom != null) {
            activeRoom = dungeon.loadActiveRoom(this);
        }
        return activeRoom;
    }

    @Override
    public boolean isLoaded() {
        return activeRoom != null;
    }

    @Override
    public void unload() {
        dungeon.saveActiveRoom(activeRoom);
        activeRoom = null;
    }

    public void setUsableGateways(List<Range> usableGateways) {
        this.usableGateways = new ArrayList<>(usableGateways);
    }

    abstract protected NormalActiveRoom newActiveRoom();

    @SuppressWarnings("unchecked")
    protected void load(ConfigurationSection section) {
        setName(section.getString("name"));
        setArea((Range) section.get("area"));
        setDirection(Direction.valueOf(section.getString("direction")));
        setUsableGateways((List<Range>) section.getList("usableGateways"));
        passages = new ArrayList<>();
        for (String passageId : section.getStringList("passages")) {
            passages.add(dungeon.getPassageById(passageId));
        }
        setPassages(passages);
    }

    protected void save(ConfigurationSection section) {
        section.set("name", getName());
        section.set("area", getArea());
        section.set("direction", direction.name());
        section.set("usableGateways", getUsableGateways());
        ArrayList<String> passageIds = new ArrayList<>();
        for (NormalPassage passage : getPassages()) {
            passageIds.add(passage.getId());
        }
        section.set("passages", passageIds);
    }
}
