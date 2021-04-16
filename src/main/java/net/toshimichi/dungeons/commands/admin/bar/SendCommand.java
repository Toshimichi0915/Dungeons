package net.toshimichi.dungeons.commands.admin.bar;

import net.toshimichi.dungeons.commands.Arguments;
import net.toshimichi.dungeons.commands.SubCommand;
import net.toshimichi.dungeons.utils.BossBarChat;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class SendCommand implements SubCommand {
    @Override
    public void onCommand(CommandSender sender, Arguments arguments, String cmd) {
        Player target = arguments.getPlayer(0);
        String[] array = Arrays.copyOfRange(arguments.getRaw(), 1, arguments.length());
        BossBarChat.sendMessage(target, String.join(" ", array));
    }

    @Override
    public String getDescription() {
        return "BossBarChatを利用してメッセージを送信します";
    }

    @Override
    public String getPermission() {
        return "net.toshimichi.dungeons.bar.send";
    }
}
