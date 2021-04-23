package net.toshimichi.dungeons.enchants.wand.world;

import net.toshimichi.dungeons.Dungeons;
import net.toshimichi.dungeons.enchants.Enchant;
import net.toshimichi.dungeons.enchants.wand.WandEnchanter;
import net.toshimichi.dungeons.utils.Direction;
import net.toshimichi.dungeons.utils.LocaleBuilder;
import net.toshimichi.dungeons.utils.Pos;
import net.toshimichi.dungeons.utils.Range;
import net.toshimichi.dungeons.world.Dungeon;
import net.toshimichi.dungeons.world.DungeonManager;
import net.toshimichi.dungeons.world.Room;
import net.toshimichi.dungeons.world.RoomFactory;
import net.toshimichi.dungeons.world.normal.NormalDungeon;
import net.toshimichi.dungeons.world.normal.NormalRoom;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.BlockIterator;

abstract public class RoomEnchanter extends WandEnchanter {

    public RoomEnchanter(Enchant enchant, Player player, ItemStack itemStack) {
        super(enchant, player, itemStack);
    }

    abstract protected boolean canCreate(Dungeon dungeon, Pos origin, Direction direction);

    abstract protected String getRoomFactoryId();

    abstract protected NormalRoom newRoom(NormalDungeon dungeon, RoomFactory factory, Pos origin, Direction direction);

    private boolean contains(Range range1, Range range2) {
        Pos[] vertex = new Pos[8];
        Pos minPoint = range1.getMinPoint();
        vertex[0] = minPoint;
        vertex[1] = minPoint.add(new Pos(range1.getXLength(), 0, 0));
        vertex[2] = minPoint.add(new Pos(0, range1.getYLength(), 0));
        vertex[3] = minPoint.add(new Pos(0, 0, range1.getZLength()));
        vertex[4] = minPoint.add(new Pos(range1.getXLength(), range1.getYLength(), 0));
        vertex[5] = minPoint.add(new Pos(0, range1.getYLength(), range1.getZLength()));
        vertex[6] = minPoint.add(new Pos(range1.getXLength(), 0, range1.getZLength()));
        vertex[7] = range1.getMaxPoint();

        for (Pos pos : vertex) {
            if (range2.contains(pos))
                return true;
        }
        return false;
    }

    private boolean checkCollision(Range range1, Range range2) {
        return contains(range1, range2) || contains(range2, range1);
    }

    private NormalDungeon findDungeon() {
        Pos playerPos = new Pos(getPlayer().getLocation().toVector());
        Pos delta = new Pos(5, 5, 5);
        Range range = new Range(playerPos.subtract(delta), playerPos.add(delta));
        DungeonManager dungeonManager = Dungeons.getInstance().getDungeonManager();
        for (Dungeon dungeon : dungeonManager.getDungeons()) {
            if (!(dungeon instanceof NormalDungeon))
                continue;
            if (!dungeon.getWorld().equals(getPlayer().getWorld()))
                continue;

            for (Room room : dungeon.getRooms()) {
                if (checkCollision(range, room.getArea()))
                    return (NormalDungeon) dungeon;
            }
        }
        return null;
    }

    private Pair<Pos, Range> findGateway(RoomFactory factory, Range targetGateway, Direction direction) {
        find:
        for (Range gateway : factory.getGateways(Pos.ZERO, direction)) {
            Pos gatewayOrigin = targetGateway.lerp(0.5, 0, 0.5)
                    .subtract(gateway.lerp(0.5, 0, 0.5))
                    .add(new Pos(direction.getX(), 0, direction.getZ()));
            Range area = factory.getArea(gatewayOrigin, direction);
            if (checkCollision(gateway, area))
                continue;
            for (Dungeon dungeon : Dungeons.getInstance().getDungeonManager().getDungeons()) {
                for (Room room : dungeon.getRooms()) {
                    if (checkCollision(room.getArea(), area))
                        continue find;
                }
            }
            return Pair.of(gatewayOrigin, gateway);
        }
        return null;
    }

    private Pair<Room, Range> getLookingGateway(Location location) {
        for (Dungeon dungeon : Dungeons.getInstance().getDungeonManager().getDungeons()) {
            if (!dungeon.getWorld().equals(location.getWorld())) {
                continue;
            }
            for (Room room : dungeon.getRooms()) {
                if (room.getArea().lerp(0.5, 0, 0.5).distanceSq(new Pos(location.toVector())) > 100) {
                    continue;
                }
                // maybe rewrite this code with ray cast?
                for (Range gateway : room.getUsableGateways()) {
                    BlockIterator iterator = new BlockIterator(location);
                    for (int i = 0; i < 10; i++) {
                        Block block = iterator.next();
                        if (gateway.lerp(0.5, 0, 0.5).distanceSq(new Pos(block.getLocation().toVector())) < 5) {
                            return Pair.of(room, gateway);
                        }
                    }
                }
            }
        }
        return null;
    }

    @Override
    public void onInteract(PlayerInteractEvent e) {
        activateCoolTime(20);
        Direction direction = Direction.getDirection(getPlayer().getEyeLocation().getDirection());
        NormalDungeon dungeon = findDungeon();
        if (dungeon == null) {
            new LocaleBuilder("world.create.no_dungeon")
                    .player(getPlayer())
                    .sendMessage();
            return;
        }
        RoomFactory factory = dungeon.getRoomFactoryById(getRoomFactoryId());
        Pair<Room, Range> pair = getLookingGateway(getPlayer().getLocation());
        if (pair == null) {
            new LocaleBuilder("world.create.invalid_gateway")
                    .player(getPlayer())
                    .sendMessage();
            return;
        }
        Pair<Pos, Range> gateway = findGateway(factory, pair.getRight(), direction);
        if (gateway == null) {
            new LocaleBuilder("world.create.invalid_gateway")
                    .player(getPlayer())
                    .sendMessage();
            return;
        }
        if (!canCreate(dungeon, gateway.getLeft(), direction)) return;
        NormalRoom newRoom = newRoom(dungeon, factory, gateway.getLeft(), direction);
        dungeon.addRoom(newRoom);
        dungeon.connect(pair.getLeft(), pair.getRight(), newRoom, gateway.getRight());
    }
}
