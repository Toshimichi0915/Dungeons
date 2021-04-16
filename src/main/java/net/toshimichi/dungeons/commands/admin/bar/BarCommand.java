package net.toshimichi.dungeons.commands.admin.bar;

import net.toshimichi.dungeons.commands.BranchCommand;
import net.toshimichi.dungeons.commands.SubCommand;

import java.util.HashMap;
import java.util.Map;

public class BarCommand extends BranchCommand {
    @Override
    protected Map<String, SubCommand> getSubCommands() {
        HashMap<String, SubCommand> map = new HashMap<>();
        map.put("list", new ListCommand());
        map.put("clear", new ClearCommand());
        map.put("send", new SendCommand());
        return map;
    }

    @Override
    public String getDescription() {
        return "BossBarのデバッグ用コマンドです";
    }

    @Override
    public String getPermission() {
        return "net.toshimichi.dungeons.bar.help";
    }
}
