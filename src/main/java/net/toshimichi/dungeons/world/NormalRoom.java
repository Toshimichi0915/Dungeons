package net.toshimichi.dungeons.world;

import net.toshimichi.dungeons.utils.Range;

import java.util.ArrayList;
import java.util.List;

final public class NormalRoom implements Room {

    private final NormalDungeon dungeon;
    private final String id;
    private String name;
    private ArrayList<NormalPassage> passages = new ArrayList<>();
    private ArrayList<Range> usableGateways = new ArrayList<>();
    private NormalActiveRoom activeRoom;

    public NormalRoom(NormalDungeon dungeon, String id) {
        this.dungeon = dungeon;
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
    public NormalDungeon getDungeon() {
        return dungeon;
    }

    @Override
    public List<NormalPassage> getPassages() {
        return new ArrayList<>(passages);
    }

    public void setPassages(ArrayList<NormalPassage> passages) {
        this.passages = passages;
    }

    @Override
    public List<Range> getUsableGateways() {
        return new ArrayList<>(usableGateways);
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

    protected NormalActiveRoom newActiveRoom() {
        return new NormalActiveRoom(this);
    }
}
