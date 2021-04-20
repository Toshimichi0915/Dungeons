package net.toshimichi.dungeons.world.empty;

import com.sk89q.worldedit.extent.clipboard.Clipboard;
import net.toshimichi.dungeons.utils.Direction;
import net.toshimichi.dungeons.utils.Pos;
import net.toshimichi.dungeons.utils.Range;
import net.toshimichi.dungeons.world.normal.NormalDungeon;
import net.toshimichi.dungeons.world.normal.NormalRoom;
import net.toshimichi.dungeons.world.schematics.SchematicRoomFactory;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.List;

public class EmptySchematicRoomFactory extends SchematicRoomFactory {

    public EmptySchematicRoomFactory(NormalDungeon dungeon, Clipboard clipboard) {
        super(dungeon, clipboard);
    }

    @Override
    public List<? extends Range> getGateways() {
        return new ArrayList<>();
    }

    @Override
    public String getId() {
        return "empty-schematic";
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
