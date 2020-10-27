package net.toshimichi.dungeons.commands.admin.stash;

import net.toshimichi.dungeons.commands.BranchCommand;
import net.toshimichi.dungeons.commands.SubCommand;

import java.util.HashMap;
import java.util.Map;

public class StashCommand extends BranchCommand {
    @Override
    protected Map<String, SubCommand> getSubCommands() {
        HashMap<String, SubCommand> result = new HashMap<>();
        result.put("check", new CheckCommand());
        result.put("put", new PutCommand());
        result.put("load", new LoadCommand());
        result.put("list", new ListCommand());
        result.put("clear", new ClearCommand());
        return result;
    }

    @Override
    public String getDescription() {
        return "Stash関係のコマンドです";
    }

    @Override
    public String getPermission() {
        return "net.toshimichi.dungeons.stash.help";
    }
}
