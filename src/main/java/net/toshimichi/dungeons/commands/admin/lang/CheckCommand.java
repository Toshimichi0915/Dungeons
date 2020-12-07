package net.toshimichi.dungeons.commands.admin.lang;

import net.toshimichi.dungeons.Dungeons;
import net.toshimichi.dungeons.Dungeons;
import net.toshimichi.dungeons.commands.Arguments;
import net.toshimichi.dungeons.commands.PlayerCommand;
import net.toshimichi.dungeons.lang.ipstack.IpStackInfo;
import net.toshimichi.dungeons.lang.ipstack.IpStackLanguage;
import net.toshimichi.dungeons.lang.ipstack.IpStackLocation;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.io.IOException;

public class CheckCommand implements PlayerCommand {
    @Override
    public void onCommand(Player player, Arguments arguments, String cmd) {
        player.sendMessage("選択中の言語: " + Dungeons.getInstance().getLocaleManager().getLocale(player).get("general.lang.name"));
        player.sendMessage("推奨された言語: " + Dungeons.getInstance().getLocaleManager().getSuggestedLocale(player).get("general.lang.name"));
        player.sendMessage("ゲーム内言語: " + player.getLocale());
        Bukkit.getScheduler().runTaskAsynchronously(Dungeons.getInstance().getPlugin(), () -> {
            String ip = player.getAddress().getHostName();
            boolean available = Dungeons.getInstance().getIpStackApi().isAvailable();
            if (!available) return;
            IpStackInfo info;
            try {
                info = Dungeons.getInstance().getIpStackApi().getInfo(ip);
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }

            if (info == null) return;
            IpStackLocation loc = info.getLocation();
            if (loc == null || loc.getLanguages() == null) return;
            int count = 0;
            for (IpStackLanguage lang : loc.getLanguages()) {
                if (lang == null) continue;
                int finalCount = count;
                Bukkit.getScheduler().runTask(Dungeons.getInstance().getPlugin(), () -> {
                    player.sendMessage("プレイヤーの国の言語(" + finalCount + "): " + lang.getName());
                });
                count++;
            }
        });
    }

    @Override
    public String getDescription() {
        return "言語情報を確認します";
    }

    @Override
    public String getPermission() {
        return "net.toshimichi.dungeons.lang.check";
    }
}
