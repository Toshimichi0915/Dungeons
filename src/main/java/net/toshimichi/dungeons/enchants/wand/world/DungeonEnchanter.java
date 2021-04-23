package net.toshimichi.dungeons.enchants.wand.world;

import net.toshimichi.dungeons.enchants.Enchant;
import net.toshimichi.dungeons.enchants.wand.WandEnchanter;
import net.toshimichi.dungeons.utils.Direction;
import net.toshimichi.dungeons.utils.Pos;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

abstract public class DungeonEnchanter extends WandEnchanter {

    public DungeonEnchanter(Enchant enchant, Player player, ItemStack itemStack) {
        super(enchant, player, itemStack);
    }

    abstract protected boolean canCreate(Pos origin, Direction direction);

    abstract protected void newDungeon(Pos origin, Direction direction);

    @Override
    public void onInteract(PlayerInteractEvent e) {
        Pos origin = new Pos(getPlayer().getLocation().toVector());
        Direction direction = Direction.getDirection(getPlayer().getEyeLocation().getDirection());
        if (!canCreate(origin, direction)) return;
        activateCoolTime(20);
        newDungeon(origin, direction);
    }
}
