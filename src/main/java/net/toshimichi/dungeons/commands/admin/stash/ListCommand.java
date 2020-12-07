package net.toshimichi.dungeons.commands.admin.stash;

import net.toshimichi.dungeons.Dungeons;
import net.toshimichi.dungeons.commands.Arguments;
import net.toshimichi.dungeons.commands.PlayerCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.util.Set;

public class ListCommand implements PlayerCommand {
    @Override
    public void onCommand(Player player, Arguments arguments, String cmd) {
        Bukkit.getScheduler().runTaskAsynchronously(Dungeons.getInstance().getPlugin(), () -> {
            Set<String> set;
            try {
                set = Dungeons.getInstance().getStash().getStashes(player.getUniqueId());
            } catch (IOException e) {
                e.printStackTrace();
                Bukkit.getScheduler().runTask(Dungeons.getInstance().getPlugin(), () ->
                        player.sendMessage(ChatColor.RED + "Stashをロードできませんでした"));
                return;
            }
            Bukkit.getScheduler().runTask(Dungeons.getInstance().getPlugin(), () ->
                    set.forEach(player::sendMessage));
        });
    }

    @Override
    public String getDescription() {
        return "利用可能なStashの一覧を表示します";
    }

    @Override
    public String getPermission() {
        return "net.toshimichi.dungeons.stash.list";
    }
}
