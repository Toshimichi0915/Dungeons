package net.toshimichi.dungeons.commands.admin.stash;

import net.toshimichi.dungeons.DungeonsPlugin;
import net.toshimichi.dungeons.commands.Arguments;
import net.toshimichi.dungeons.exceptions.IllegalCommandUsageException;
import net.toshimichi.dungeons.commands.PlayerCommand;
import org.bukkit.entity.Player;

import java.io.IOException;

public class ClearCommand implements PlayerCommand {
    @Override
    public void onCommand(Player player, Arguments arguments, String cmd) {
        String space = arguments.getString(0, "Stashの名前");
        try {
            DungeonsPlugin.getStash().clearStash(player.getUniqueId(), space);
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalCommandUsageException("Stashをロードできませんでした");
        }
    }

    @Override
    public String getDescription() {
        return "Stashの中身を空にします";
    }

    @Override
    public String getPermission() {
        return "net.toshimichi.dungeons.stash.clear";
    }
}
