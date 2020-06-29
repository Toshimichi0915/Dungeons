package net.toshimichi.dungeons.commands.admin.mana;

import net.toshimichi.dungeons.DungeonsPlugin;
import net.toshimichi.dungeons.commands.Arguments;
import net.toshimichi.dungeons.commands.PlayerCommand;
import org.bukkit.entity.Player;

public class SetManaCommand implements PlayerCommand {
    @Override
    public void onCommand(Player player, Arguments arguments, String cmd) {
        DungeonsPlugin.getManaManager().setMana(player, arguments.getInt(0, "魔力"));
    }

    @Override
    public String getDescription() {
        return "魔力を設定します";
    }

    @Override
    public String getPermission() {
        return "net.toshimichi.dungeons.mana.setmana";
    }
}
