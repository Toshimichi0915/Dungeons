package net.toshimichi.dungeons.commands.admin;

import net.toshimichi.dungeons.commands.Arguments;
import net.toshimichi.dungeons.commands.PlayerCommand;
import org.bukkit.entity.Player;

public class WalkSpeedCommand implements PlayerCommand {
    @Override
    public void onCommand(Player player, Arguments arguments, String cmd) {
        if(arguments.length() == 0) {
            player.sendMessage("歩く速度: " + player.getWalkSpeed());
            return;
        }
        player.setWalkSpeed(arguments.getFloat(0));
    }

    @Override
    public String getDescription() {
        return "歩く速度を調整します";
    }

    @Override
    public String getPermission() {
        return "net.toshimichi.dungeons.walkspeed";
    }
}
