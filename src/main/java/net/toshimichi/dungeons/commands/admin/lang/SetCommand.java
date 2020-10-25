package net.toshimichi.dungeons.commands.admin.lang;

import net.toshimichi.dungeons.DungeonsPlugin;
import net.toshimichi.dungeons.commands.Arguments;
import net.toshimichi.dungeons.commands.PlayerCommand;
import net.toshimichi.dungeons.exceptions.IllegalCommandUsageException;
import net.toshimichi.dungeons.lang.Locale;
import net.toshimichi.dungeons.lang.LocaleManager;
import org.bukkit.entity.Player;

public class SetCommand implements PlayerCommand {

    @Override
    public void onCommand(Player player, Arguments arguments, String cmd) {
        LocaleManager manager = DungeonsPlugin.getLocaleManager();
        String lang = arguments.getString(0, "言語名");
        Locale selected = manager.getLocale(lang);
        if (selected == null)
            throw new IllegalCommandUsageException(lang + "という英語名を持つ言語は存在しません");
        manager.setLocale(player, selected);
    }

    @Override
    public String getDescription() {
        return "言語を設定します";
    }

    @Override
    public String getPermission() {
        return "net.toshimichi.dungeons.lang.set";
    }
}
