package net.toshimichi.dungeons.enchants.wand.world;

import net.toshimichi.dungeons.Dungeons;
import net.toshimichi.dungeons.enchants.Enchant;
import net.toshimichi.dungeons.utils.Direction;
import net.toshimichi.dungeons.utils.Pos;
import net.toshimichi.dungeons.world.DungeonManager;
import net.toshimichi.dungeons.world.normal.NormalRoom;
import net.toshimichi.dungeons.world.tower.Passage0Factory;
import net.toshimichi.dungeons.world.tower.TowerDungeon;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class TowerEnchanter extends DungeonEnchanter {

    public TowerEnchanter(Enchant enchant, Player player, ItemStack itemStack) {
        super(enchant, player, itemStack);
    }

    @Override
    protected boolean canCreate(Pos origin, Direction direction) {
        return true;
    }

    @Override
    protected void newDungeon(Pos origin, Direction direction) {
        try {
            DungeonManager dungeonManager = Dungeons.getInstance().getDungeonManager();
            TowerDungeon dungeon = (TowerDungeon) dungeonManager.newDungeon("tower");
            String roomId = dungeon.newRoomId();
            dungeon.setName(roomId);
            dungeon.setWorld(getPlayer().getWorld());
            Passage0Factory roomFactory = (Passage0Factory) dungeon.getRoomFactoryById("passage_0");
            NormalRoom room = roomFactory.build(roomId, origin, direction);
            dungeon.addRoom(room);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
