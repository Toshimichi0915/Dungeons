package net.toshimichi.dungeons.commands.admin.mana;

import net.toshimichi.dungeons.commands.BranchCommand;
import net.toshimichi.dungeons.commands.SubCommand;

import java.util.HashMap;
import java.util.Map;

public class ManaCommand extends BranchCommand {
    @Override
    protected Map<String, SubCommand> getSubCommands() {
        HashMap<String, SubCommand> map = new HashMap<>();
        map.put("set", new SetCommand());
        map.put("setmax", new SetMaxCommand());
        return map;
    }

    @Override
    public String getDescription() {
        return "マナ関連のコマンドです";
    }

    @Override
    public String getPermission() {
        return "net.toshimichi.dungeons.mana.help";
    }
}
