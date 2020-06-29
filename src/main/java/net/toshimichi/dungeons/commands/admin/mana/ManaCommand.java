package net.toshimichi.dungeons.commands.admin.mana;

import net.toshimichi.dungeons.commands.BranchCommand;
import net.toshimichi.dungeons.commands.SubCommand;

import java.util.HashMap;
import java.util.Map;

public class ManaCommand extends BranchCommand {
    @Override
    protected Map<String, SubCommand> getSubCommands() {
        HashMap<String, SubCommand> map = new HashMap<>();
        map.put("setmana", new SetManaCommand());
        map.put("setmaxmana", new SetMaxManaCommand());
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
