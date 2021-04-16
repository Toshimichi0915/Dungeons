package net.toshimichi.dungeons.commands.admin.nbt;

import net.toshimichi.dungeons.commands.BranchCommand;
import net.toshimichi.dungeons.commands.SubCommand;

import java.util.HashMap;
import java.util.Map;

public class NbtCommand extends BranchCommand {
    @Override
    protected Map<String, SubCommand> getSubCommands() {
        HashMap<String, SubCommand> result = new HashMap<>();
        result.put("list", new ListCommand());
        return result;
    }

    @Override
    public String getDescription() {
        return "NBTを操作します";
    }

    @Override
    public String getPermission() {
        return "net.toshimichi.dungeons.nbt";
    }
}
