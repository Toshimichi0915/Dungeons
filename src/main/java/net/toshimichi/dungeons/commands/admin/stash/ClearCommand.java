package net.toshimichi.dungeons.commands.admin.stash;

import net.toshimichi.dungeons.Dungeons;
import net.toshimichi.dungeons.commands.Arguments;
import net.toshimichi.dungeons.commands.PlayerCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.io.IOException;

public class ClearCommand implements PlayerCommand {
    @Override
    public void onCommand(Player player, Arguments arguments, String cmd) {
        String space = arguments.getString(0, "Stashの名前");
        Bukkit.getScheduler().runTaskAsynchronously(Dungeons.getInstance().getPlugin(), () -> {
            try {
                Dungeons.getInstance().getStash().clearStash(player.getUniqueId(), space);
            } catch (IOException e) {
                e.printStackTrace();
                Bukkit.getScheduler().runTask(Dungeons.getInstance().getPlugin(), () ->
                        player.sendMessage(ChatColor.RED + "Stashをロードできませんでした"));
            }
        });
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
