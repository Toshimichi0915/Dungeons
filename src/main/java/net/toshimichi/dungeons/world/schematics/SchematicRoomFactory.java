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

abstract public class SchematicRoomFactory extends NormalRoomFactory {

    private final Clipboard clipboard;

    public SchematicRoomFactory(NormalDungeon dungeon, Clipboard clipboard) {
        super(dungeon);
        this.clipboard = clipboard;
    }

    @Override
    public Pos getSize() {
        Range range = new Range(clipboard.getRegion());
        return new Pos(range.getXLength(), range.getYLength(), range.getZLength());
    }

    @Override
    abstract public NormalRoom load(String id, ConfigurationSection section);

    protected void build(Pos origin, Direction direction) {
        try (EditSession editSession = WorldEdit.getInstance().newEditSession(BukkitAdapter.adapt(getDungeon().getWorld()))) {
            ClipboardHolder holder = new ClipboardHolder(clipboard);
            AffineTransform transform = new AffineTransform();
            transform.rotateY(Direction.EAST.getAngle(direction));
            holder.setTransform(transform);
            Operation operation = holder.createPaste(editSession)
                    .to(origin.toBlockVector3())
                    .ignoreAirBlocks(false)
                    .build();
            Operations.complete(operation);
        } catch (WorldEditException e) {
            e.printStackTrace();
        }
    }

}
