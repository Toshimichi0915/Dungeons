package net.toshimichi.dungeons.commands.admin.world;

import net.toshimichi.dungeons.commands.BranchCommand;
import net.toshimichi.dungeons.commands.SubCommand;

import java.util.HashMap;
import java.util.Map;

public class WorldCommand extends BranchCommand {
    @Override
    protected Map<String, SubCommand> getSubCommands() {
        HashMap<String, SubCommand> result = new HashMap<>();
        result.put("fill", new FillCommand());
        return result;
    }

    @Override
    public String getDescription() {
        return "世界を編集するためのコマンドです";
    }

    @Override
    public String getPermission() {
        return "net.toshimichi.dungeons.world";
    }
}
