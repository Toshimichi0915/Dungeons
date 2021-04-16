package net.toshimichi.dungeons.commands.admin.enchant;

import net.toshimichi.dungeons.commands.BranchCommand;
import net.toshimichi.dungeons.commands.SubCommand;

import java.util.HashMap;
import java.util.Map;

public class EnchantCommand extends BranchCommand {
    @Override
    protected Map<String, SubCommand> getSubCommands() {
        HashMap<String, SubCommand> result = new HashMap<>();
        result.put("add", new EnchantAddCommand());
        result.put("list", new EnchantListCommand());
        result.put("lives", new LivesCommand());
        result.put("tier", new TierCommand());
        result.put("maxlives", new MaxLivesCommand());
        result.put("nonce", new NonceCommand());
        result.put("applied", new AppliedCommand());
        result.put("refresh", new RefreshCommand());
        result.put("lang", new LangCommand());
        result.put("all", new AllCommand());
        result.put("well", new WellCommand());
        result.put("gui", new GuiCommand());
        return result;
    }

    @Override
    public String getDescription() {
        return "エンチャント管理用コマンドです";
    }

    @Override
    public String getPermission() {
        return "net.toshimichi.dungeons.enchant.help";
    }
}
