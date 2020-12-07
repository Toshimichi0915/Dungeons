package net.toshimichi.dungeons.commands.admin.enchant;

import net.toshimichi.dungeons.Dungeons;
import net.toshimichi.dungeons.Dungeons;
import net.toshimichi.dungeons.commands.Arguments;
import net.toshimichi.dungeons.commands.CommandException;
import net.toshimichi.dungeons.commands.PlayerCommand;
import net.toshimichi.dungeons.enchants.EnchantManager;
import net.toshimichi.dungeons.lang.Locale;
import net.toshimichi.dungeons.lang.LocaleManager;
import net.toshimichi.dungeons.utils.LocaleBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class LangCommand implements PlayerCommand {
    @Override
    public void onCommand(Player player, Arguments arguments, String cmd) {
        EnchantManager enchantManager = Dungeons.getInstance().getEnchantManager();
        LocaleManager localeManager = Dungeons.getInstance().getLocaleManager();

        ItemStack itemStack = player.getInventory().getItemInMainHand();
        if (itemStack.getType() == Material.AIR)
            throw new CommandException("言語を編集するアイテムを手に持ってください");

        if (arguments.length() > 0) {
            String name = arguments.getString(0, "言語名");
            Locale locale = localeManager.getLocale(name);
            if (locale == null)
                throw new CommandException(name + "という英語名をもつ言語は存在しません");
            enchantManager.setLocale(itemStack, locale);
            return;
        }

        Locale locale = enchantManager.getLocale(player.getInventory().getItemInMainHand());
        if (locale == null)
            player.sendMessage("言語: 未設定");
        else
            player.sendMessage("言語: " + new LocaleBuilder("general.lang.name").locale(locale).build());

    }

    @Override
    public String getDescription() {
        return "アイテムの言語情報を編集します";
    }

    @Override
    public String getPermission() {
        return "net.toshimichi.dungeons.lang";
    }
}
