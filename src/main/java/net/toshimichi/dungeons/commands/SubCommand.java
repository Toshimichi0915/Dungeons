package net.toshimichi.dungeons.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public interface SubCommand extends CommandExecutor {
    void onCommand(CommandSender sender, Arguments args, String cmd);

    String getDescription();

    String getPermission();

    @Override
    default boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        String args = String.join(" ", strings);
        if (args.length() > 0)
            args = " " + args;
        try {
            onCommand(commandSender, new Arguments(strings), command.getName() + args);
        } catch (CommandException e) {
            for (String message : e.getMessages()) {
                commandSender.sendMessage(ChatColor.RED + message);
            }
        }
        return true;
    }

    default String red(String str) {
        return ChatColor.RED + str;
    }

    default String green(String str) {
        return ChatColor.GREEN + str;
    }

    default String gold(String str) {
        return ChatColor.GOLD + str;
    }
}
