package net.toshimichi.dungeons.commands.admin.lang;

import net.toshimichi.dungeons.DungeonsPlugin;
import net.toshimichi.dungeons.commands.Arguments;
import net.toshimichi.dungeons.commands.PlayerCommand;
import net.toshimichi.dungeons.exceptions.IllegalCommandUsageException;
import net.toshimichi.dungeons.lang.Locale;
import net.toshimichi.dungeons.lang.LocaleManager;
import net.toshimichi.dungeons.utils.LocaleBuilder;
import org.bukkit.entity.Player;

public class SetCommand implements PlayerCommand {

    @Override
    public void onCommand(Player player, Arguments arguments, String cmd) {
        LocaleManager manager = DungeonsPlugin.getLocaleManager();
        String lang = arguments.getString(0, "言語名");
        Locale selected = null;
        for (Locale locale : DungeonsPlugin.getLocales()) {
            if (new LocaleBuilder("general.lang.english").locale(locale).build().equals(lang)) {
                selected = locale;
                break;
            }
        }
        if (selected == null)
            throw new IllegalCommandUsageException(lang + "という英語名を持つ言語は存在しません");
        manager.setLocale(player, selected);
        String nativeName = new LocaleBuilder("general.lang.native").player(player).build();
        String message = new LocaleBuilder("general.lang.change").player(player).replace("{lang}", nativeName).build();
        player.sendMessage(message);
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
