package net.toshimichi.dungeons.commands;

import net.toshimichi.dungeons.commands.admin.CustomModelDataCommand;
import net.toshimichi.dungeons.commands.admin.WalkSpeedCommand;
import net.toshimichi.dungeons.commands.admin.bar.BarCommand;
import net.toshimichi.dungeons.commands.admin.economy.EconomyCommand;
import net.toshimichi.dungeons.commands.admin.enchant.EnchantCommand;
import net.toshimichi.dungeons.commands.admin.lang.LangCommand;
import net.toshimichi.dungeons.commands.admin.mana.ManaCommand;
import net.toshimichi.dungeons.commands.admin.nbt.NbtCommand;
import net.toshimichi.dungeons.commands.admin.stash.StashCommand;

import java.util.HashMap;
import java.util.Map;

public class DungeonsCommand extends BranchCommand {
    @Override
    protected Map<String, SubCommand> getSubCommands() {
        HashMap<String, SubCommand> result = new HashMap<>();
        result.put("enchant", new EnchantCommand());
        result.put("stash", new StashCommand());
        result.put("economy", new EconomyCommand());
        result.put("mana", new ManaCommand());
        result.put("bar", new BarCommand());
        result.put("lang", new LangCommand());
        result.put("walkspeed", new WalkSpeedCommand());
        result.put("dummy", new DummyCommand());
        result.put("model", new CustomModelDataCommand());
        result.put("nbt", new NbtCommand());
        return result;
    }

    @Override
    public String getDescription() {
        return "Dungeonsの管理コマンド";
    }

    @Override
    public String getPermission() {
        return "net.toshimichi.dungeons";
    }
}
