package net.toshimichi.dungeons.commands.admin.bar;

import net.toshimichi.dungeons.commands.Arguments;
import net.toshimichi.dungeons.commands.SubCommand;
import org.bukkit.Bukkit;
import org.bukkit.boss.KeyedBossBar;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.HumanEntity;

import java.util.Iterator;

public class ListCommand implements SubCommand {
    @Override
    public void onCommand(CommandSender sender, Arguments arguments, String cmd) {
        Iterator<KeyedBossBar> iterator = Bukkit.getBossBars();
        while (iterator.hasNext()) {
            KeyedBossBar bar = iterator.next();
            String names = bar.getPlayers().stream()
                    .map(HumanEntity::getName)
                    .reduce((a, b) -> a + " " + b).orElse("なし");
            sender.sendMessage("タイトル: " + bar.getTitle() + " 表示プレイヤー: " + names);
        }
    }

    @Override
    public String getDescription() {
        return "BossBar一覧を表示します";
    }

    @Override
    public String getPermission() {
        return "net.toshimichi.dungeons.bar.list";
    }
}
