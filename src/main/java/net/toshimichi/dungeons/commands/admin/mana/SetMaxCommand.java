package net.toshimichi.dungeons.commands.admin.mana;

import net.toshimichi.dungeons.Dungeons;
import net.toshimichi.dungeons.commands.Arguments;
import net.toshimichi.dungeons.commands.PlayerCommand;
import org.bukkit.entity.Player;

public class SetMaxCommand implements PlayerCommand {
    @Override
    public void onCommand(Player player, Arguments arguments, String cmd) {
        Dungeons.getInstance().getManaManager().setMaxMana(player, arguments.getInt(0, "最大魔力"));
    }

    @Override
    public String getDescription() {
        return "最大魔力を設定します";
    }

    @Override
    public String getPermission() {
        return "net.toshimichi.dungeons.mana.setmax";
    }
}
