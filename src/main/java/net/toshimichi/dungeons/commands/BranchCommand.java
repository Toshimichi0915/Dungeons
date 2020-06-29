package net.toshimichi.dungeons.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.Map;

abstract public class BranchCommand implements SubCommand {

    private void showHelp(CommandSender sender, String cmd) {
        if(!sender.hasPermission(getPermission())) {
            sender.sendMessage(ChatColor.RED + "ヘルプを表示する権限がありません");
            return;
        }
        //ヘルプを表示
//        sender.sendMessage("=============================");
        for (Map.Entry<String, SubCommand> entry : getSubCommands().entrySet()) {
            sender.sendMessage(ChatColor.YELLOW + "/" + cmd + " " + entry.getKey() + " - " + entry.getValue().getDescription());
        }
//        sender.sendMessage("=============================");
    }

    @Override
    public void onCommand(CommandSender sender, Arguments args, String cmd) {
        if (args.length() < 1) {
            showHelp(sender, cmd);
            return;
        }

        SubCommand subCommand = getSubCommands().get(args.getString(0, "サブコマンド"));
        if (subCommand == null) {
            showHelp(sender, cmd.substring(0, cmd.length() - String.join(" ", args.getRaw()).length() - 1));
        } else {
            if(!sender.hasPermission(subCommand.getPermission())) {
                sender.sendMessage(ChatColor.RED + "権限がありません");
                return;
            }
            subCommand.onCommand(sender, new Arguments(Arrays.copyOfRange(args.getRaw(), 1, args.length())), cmd);
        }
    }

    abstract protected Map<String, SubCommand> getSubCommands();
}
