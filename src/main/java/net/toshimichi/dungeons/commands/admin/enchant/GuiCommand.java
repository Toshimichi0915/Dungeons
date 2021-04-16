package net.toshimichi.dungeons.commands.admin.enchant;

import net.toshimichi.dungeons.Dungeons;
import net.toshimichi.dungeons.commands.Arguments;
import net.toshimichi.dungeons.commands.PlayerCommand;
import net.toshimichi.dungeons.gui.AdminEnchantGui;
import org.bukkit.entity.Player;

public class GuiCommand implements PlayerCommand {
    @Override
    public void onCommand(Player player, Arguments arguments, String cmd) {
        Dungeons.getInstance().getGuiManager().show(player, new AdminEnchantGui());
    }

    @Override
    public String getDescription() {
        return "エンチャント管理用GUIを開きます";
    }

    @Override
    public String getPermission() {
        return "net.toshimichi.dungeons.enchant.gui";
    }
}
