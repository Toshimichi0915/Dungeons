package net.toshimichi.dungeons.world.tower;

import com.sk89q.worldedit.extent.clipboard.Clipboard;
import net.toshimichi.dungeons.utils.Direction;
import net.toshimichi.dungeons.utils.Pos;
import net.toshimichi.dungeons.utils.Range;
import net.toshimichi.dungeons.world.Buildable;
import net.toshimichi.dungeons.world.empty.EmptyRoom;
import net.toshimichi.dungeons.world.normal.NormalDungeon;
import net.toshimichi.dungeons.world.normal.NormalRoom;
import net.toshimichi.dungeons.world.schematics.SchematicRoomFactory;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Passage0Factory extends SchematicRoomFactory implements Buildable {

    private static final List<Range> gateways;

    static {
        ArrayList<Range> ranges = new ArrayList<>();
        ranges.add(new Range(new Pos(1, 0, -1), new Pos(1, 2, 1)));
        ranges.add(new Range(new Pos(6, 0, -1), new Pos(6, 2, 1)));
        gateways = Collections.unmodifiableList(ranges);
    }

    public Passage0Factory(NormalDungeon dungeon, Clipboard clipboard) {
        super(dungeon, clipboard);
    }

    @Override
    public String getId() {
        return "passage_0";
    }

    @Override
    public List<? extends Range> getGateways() {
        return gateways;
    }

    @Override
    public NormalRoom load(String id, ConfigurationSection section) {
        EmptyRoom room = new EmptyRoom(getDungeon(), this, id);
        room.load(section);
        return room;
    }


    public NormalRoom build(String id, Pos origin, Direction direction) {
        build(origin, direction);
        return new EmptyRoom(getDungeon(), this, id, origin, direction);
    }
}
