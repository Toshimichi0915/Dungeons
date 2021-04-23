package net.toshimichi.dungeons.enchants.wand.world;

import net.toshimichi.dungeons.enchants.Enchant;
import net.toshimichi.dungeons.enchants.EnchantType;
import net.toshimichi.dungeons.enchants.Enchanter;
import net.toshimichi.dungeons.utils.Direction;
import net.toshimichi.dungeons.utils.Pos;
import net.toshimichi.dungeons.world.Buildable;
import net.toshimichi.dungeons.world.Dungeon;
import net.toshimichi.dungeons.world.RoomFactory;
import net.toshimichi.dungeons.world.normal.NormalDungeon;
import net.toshimichi.dungeons.world.normal.NormalRoom;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

abstract public class RoomEnchant implements Enchant {

    @Override
    public EnchantType[] getEnchantType() {
        return new EnchantType[]{EnchantType.WAND};
    }

    abstract public String getRoomFactoryId();

    @Override
    public Enchanter getEnchanter(Player player, ItemStack itemStack) {
        return new RoomEnchanter(this, player, itemStack) {
            @Override
            protected boolean canCreate(Dungeon dungeon, Pos origin, Direction direction) {
                return true;
            }

            @Override
            protected String getRoomFactoryId() {
                return RoomEnchant.this.getRoomFactoryId();
            }

            @Override
            protected NormalRoom newRoom(NormalDungeon dungeon, RoomFactory factory, Pos origin, Direction direction) {
                return ((Buildable) factory).build(dungeon.newRoomId(), origin, direction);
            }
        };
    }
}
