package net.toshimichi.dungeons.commands.admin.lang;

import net.toshimichi.dungeons.DungeonsPlugin;
import net.toshimichi.dungeons.commands.Arguments;
import net.toshimichi.dungeons.commands.SubCommand;
import net.toshimichi.dungeons.exceptions.IllegalCommandUsageException;
import net.toshimichi.dungeons.lang.ipstack.IpStackApi;
import net.toshimichi.dungeons.lang.ipstack.IpStackInfo;
import net.toshimichi.dungeons.lang.ipstack.IpStackLanguage;
import net.toshimichi.dungeons.lang.ipstack.IpStackOption;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.IOException;

public class IpCommand implements SubCommand {

    private void async(Runnable r) {
        Bukkit.getScheduler().runTaskAsynchronously(DungeonsPlugin.getPlugin(), r);
    }

    private void sync(Runnable r) {
        Bukkit.getScheduler().runTask(DungeonsPlugin.getPlugin(), r);
    }

    @Override
    public void onCommand(CommandSender sender, Arguments arguments, String cmd) {
        IpStackApi api = DungeonsPlugin.getIpStackApi();
        Player target = null;
        if (sender instanceof Player)
            target = (Player) sender;
        if (arguments.length() > 0)
            target = arguments.getPlayer(0);
        if (target == null) {
            throw new IllegalCommandUsageException(ChatColor.RED + "このコマンドはプレイヤーのみ使用できます");
        }
        Player finalTarget = target;
        async(() -> {
            String ip = finalTarget.getAddress().getHostName();
            boolean available = api.isAvailable();
            IpStackInfo info = null;
            if (available) {
                try {
                    info = api.getInfo(ip, new IpStackOption("security", "1"));
                    available = true;
                } catch (IOException e) {
                    available = false;
                }
            }
            if (!available) {
                sync(() -> sender.sendMessage("Ipに関する情報は現在使用不可能です"));
                return;
            }
            if (info.getIp() == null)
                sender.sendMessage("IP: 情報なし");
            else
                sender.sendMessage("IP: " + info.getIp());
            if (info.getCountryName() == null)
                sender.sendMessage("国籍: 情報なし");
            else
                sender.sendMessage("国籍: " + info.getCountryName());
            if (info.getLocation() == null || info.getLocation().getLanguages() == null)
                sender.sendMessage("言語名(英語): 情報なし 言語名(ネイティブ): 情報なし 言語コード: 情報なし");
            else
                for (IpStackLanguage language : info.getLocation().getLanguages()) {
                    sender.sendMessage("言語名(英語): " + language.getName() + " 言語名(ネイティブ): " + language.getNative() + " 言語コード: " + language.getCode());
                }
            if (info.getTimeZone() == null)
                sender.sendMessage("タイムゾーン: 情報なし");
            else
                sender.sendMessage("タイムゾーン: " + info.getTimeZone().getCurrentTime());

            if (info.getCurrency() == null || info.getCurrency().getName() == null)
                sender.sendMessage("通貨: 情報なし");
            else
                sender.sendMessage("通貨: " + info.getCurrency().getName());

            if (info.getSecurity() == null || info.getSecurity().getProxyType() == null)
                sender.sendMessage("プロキシ: 情報なし");
            else
                sender.sendMessage("プロキシ: " + info.getSecurity().getProxyType());

            if (info.getSecurity() == null || info.getSecurity().getThreatLevel() == null)
                sender.sendMessage("脅威レベル: 情報なし");
            else
                sender.sendMessage("脅威レベル: " + info.getSecurity().getThreatLevel());

            if (info.getSecurity() == null || info.getSecurity().getThreatTypes() == null)
                sender.sendMessage("脅威の種類: 情報なし");
            else
                sender.sendMessage("脅威の種類: " + info.getSecurity().getThreatTypes());
        });
    }

    @Override
    public String getDescription() {
        return "IPに関連づけられた情報を返します";
    }

    @Override
    public String getPermission() {
        return "net.toshimichi.dungeons.lang.ip";
    }
}
