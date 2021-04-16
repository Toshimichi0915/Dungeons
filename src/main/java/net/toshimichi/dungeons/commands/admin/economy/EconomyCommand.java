package net.toshimichi.dungeons.commands.admin.economy;

import net.toshimichi.dungeons.commands.BranchCommand;
import net.toshimichi.dungeons.commands.SubCommand;

import java.util.HashMap;
import java.util.Map;

public class EconomyCommand extends BranchCommand {
    @Override
    protected Map<String, SubCommand> getSubCommands() {
        HashMap<String, SubCommand> map = new HashMap<>();
        map.put("balance", new BalanceCommand());
        map.put("set", new SetCommand());
        return map;
    }

    @Override
    public String getDescription() {
        return "Economy関連のコマンドです";
    }

    @Override
    public String getPermission() {
        return "net.toshimichi.dungeons.economy.help";
    }
}
