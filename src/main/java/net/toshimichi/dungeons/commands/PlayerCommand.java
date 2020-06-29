package net.toshimichi.dungeons.commands;

import net.toshimichi.dungeons.exceptions.IllegalCommandUsageException;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public interface PlayerCommand extends SubCommand {

    void onCommand(Player player, Arguments arguments, String cmd);

    @Override
    default void onCommand(CommandSender sender, Arguments args, String cmd) {
        if (!(sender instanceof Player))
            throw new IllegalCommandUsageException("このコマンドはプレイヤーのみ使用できます");
        onCommand((Player) sender, args, cmd);
    }
}
