package net.toshimichi.dungeons.commands.admin.bar;

import net.toshimichi.dungeons.commands.Arguments;
import net.toshimichi.dungeons.commands.SubCommand;
import org.bukkit.Bukkit;
import org.bukkit.boss.KeyedBossBar;
import org.bukkit.command.CommandSender;

import java.util.Iterator;

public class ClearCommand implements SubCommand {

    @Override
    public void onCommand(CommandSender sender, Arguments args, String cmd) {
        Iterator<KeyedBossBar> iterator = Bukkit.getBossBars();
        while (iterator.hasNext()) {
            iterator.next().removeAll();
        }
    }

    @Override
    public String getDescription() {
        return "全てのBossBarを削除します";
    }

    @Override
    public String getPermission() {
        return "net.toshimichi.dungeons.bar.clear";
    }
}
