package net.toshimichi.dungeons.commands.admin.world;

import net.toshimichi.dungeons.commands.Arguments;
import net.toshimichi.dungeons.commands.CommandException;
import net.toshimichi.dungeons.commands.PlayerCommand;
import net.toshimichi.dungeons.nat.api.EditSession;
import net.toshimichi.dungeons.nat.api.EditSessionFactory;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class FillCommand implements PlayerCommand {
    @Override
    public void onCommand(Player player, Arguments args, String cmd) {
        EditSessionFactory factory = Bukkit.getServicesManager().load(EditSessionFactory.class);
        EditSession session = factory.newEditSession(player.getWorld());
        String blockName = args.getString(6, "ブロック");
        Material blockType = Material.getMaterial(blockName);
        if (blockType == null)
            throw new CommandException(blockName + "という名前のブロックは存在しません");
        for (int x = args.getInt(0); x <= args.getInt(3); x++) {
            for (int y = args.getInt(1); y <= args.getInt(4); y++) {
                for (int z = args.getInt(2); z <= args.getInt(5); z++) {
                    session.setBlock(x, y, z, blockType);
                }
            }
        }
        session.update();
    }

    @Override
    public String getDescription() {
        return "指定された範囲をブロックで埋めます";
    }

    @Override
    public String getPermission() {
        return "net.toshimichi.dungeons.world.fill";
    }
}
