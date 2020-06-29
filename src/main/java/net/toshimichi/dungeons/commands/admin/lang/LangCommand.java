package net.toshimichi.dungeons.commands.admin.lang;

import net.toshimichi.dungeons.commands.BranchCommand;
import net.toshimichi.dungeons.commands.SubCommand;

import java.util.HashMap;
import java.util.Map;

public class LangCommand extends BranchCommand {
    @Override
    protected Map<String, SubCommand> getSubCommands() {
        HashMap<String, SubCommand> map = new HashMap<>();
        map.put("ip", new IpCommand());
        map.put("check", new CheckCommand());
        return map;
    }

    @Override
    public String getDescription() {
        return "言語関連のコマンドです";
    }

    @Override
    public String getPermission() {
        return "net.toshimichi.dungeons.lang";
    }
}
