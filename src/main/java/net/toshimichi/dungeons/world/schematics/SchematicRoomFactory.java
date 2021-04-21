package net.toshimichi.dungeons.world.schematics;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.WorldEditException;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.math.transform.AffineTransform;
import com.sk89q.worldedit.session.ClipboardHolder;
import net.toshimichi.dungeons.utils.Direction;
import net.toshimichi.dungeons.utils.Pos;
import net.toshimichi.dungeons.utils.Range;
import net.toshimichi.dungeons.world.normal.NormalDungeon;
import net.toshimichi.dungeons.world.normal.NormalRoom;
import net.toshimichi.dungeons.world.normal.NormalRoomFactory;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.List;

abstract public class SchematicRoomFactory extends NormalRoomFactory {

    public static final Direction ORIGIN = Direction.EAST;
    private final Clipboard clipboard;

    public SchematicRoomFactory(NormalDungeon dungeon, Clipboard clipboard) {
        super(dungeon);
        this.clipboard = clipboard;
    }

    abstract public List<? extends Range> getGateways();

    private Range affine(Range range, Pos origin, Direction direction) {
        return range.rotate(0, (int) Math.toDegrees(ORIGIN.rad(direction)) / 90, 0).move(origin);
    }

    @Override
    public List<? extends Range> getGateways(Pos origin, Direction direction) {
        ArrayList<Range> result = new ArrayList<>();
        for (Range range : getGateways()) {
            result.add(affine(range, origin, direction));
        }
        return result;
    }

    @Override
    public Pos getArea(Pos origin, Direction direction) {
        Range range = affine(new Range(clipboard.getRegion()), origin, direction);
        return new Pos(range.getXLength(), range.getYLength(), range.getZLength());
    }

    @Override
    abstract public NormalRoom load(String id, ConfigurationSection section);

    protected void build(Pos origin, Direction direction) {
        try (EditSession editSession = WorldEdit.getInstance().newEditSession(BukkitAdapter.adapt(getDungeon().getWorld()))) {
            ClipboardHolder holder = new ClipboardHolder(clipboard);
            AffineTransform transform = new AffineTransform();
            transform = transform.rotateY(-Math.toDegrees(ORIGIN.rad(direction)));
            holder.setTransform(holder.getTransform().combine(transform));
            Operation operation = holder.createPaste(editSession)
                    .to(origin.toBlockVector3())
                    .build();
            Operations.complete(operation);
        } catch (WorldEditException e) {
            e.printStackTrace();
        }
    }

}
