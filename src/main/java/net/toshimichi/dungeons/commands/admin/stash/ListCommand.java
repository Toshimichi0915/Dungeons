package net.toshimichi.dungeons.commands.admin.stash;

import net.toshimichi.dungeons.DungeonsPlugin;
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
        Bukkit.getScheduler().runTaskAsynchronously(DungeonsPlugin.getPlugin(), () -> {
            Set<String> set;
            try {
                set = DungeonsPlugin.getStash().getStashes(player.getUniqueId());
            } catch (IOException e) {
                e.printStackTrace();
                Bukkit.getScheduler().runTask(DungeonsPlugin.getPlugin(), () ->
                        player.sendMessage(ChatColor.RED + "Stashをロードできませんでした"));
                return;
            }
            Bukkit.getScheduler().runTask(DungeonsPlugin.getPlugin(), () ->
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
