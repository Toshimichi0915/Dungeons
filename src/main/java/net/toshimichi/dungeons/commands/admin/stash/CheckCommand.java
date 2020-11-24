package net.toshimichi.dungeons.commands.admin.stash;

import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.toshimichi.dungeons.DungeonsPlugin;
import net.toshimichi.dungeons.commands.Arguments;
import net.toshimichi.dungeons.commands.PlayerCommand;
import net.toshimichi.dungeons.misc.Stash;
import net.toshimichi.dungeons.nat.api.LocaleLanguage;
import net.toshimichi.dungeons.nat.api.NbtItemStack;
import net.toshimichi.dungeons.nat.api.NbtItemStackFactory;
import net.toshimichi.dungeons.utils.RomanNumber;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.ServicesManager;

import java.util.List;
import java.util.Map;

public class CheckCommand implements PlayerCommand {

    private TextComponent getTextComponent(ItemStack itemStack) {
        ServicesManager services = Bukkit.getServicesManager();
        NbtItemStackFactory factory = services.load(net.toshimichi.dungeons.nat.api.NbtItemStackFactory.class);
        NbtItemStack nbtItemStack = factory.newNbtItemStack(itemStack);
        LocaleLanguage lang = services.load(net.toshimichi.dungeons.nat.api.LocaleLanguage.class);
        String name = nbtItemStack.getName();
        TextComponent result = new TextComponent(name + ChatColor.GRAY + " x" + itemStack.getAmount());
        StringBuilder builder = new StringBuilder(name);
        builder.append('\n');
        ItemMeta meta = itemStack.getItemMeta();
        if (meta != null) {
            List<String> lore = meta.getLore();
            if (lore != null) {
                for (String s : lore) {
                    builder.append(s);
                    builder.append('\n');
                }
            }
            for (Map.Entry<Enchantment, Integer> entry : meta.getEnchants().entrySet()) {
                builder.append(ChatColor.GRAY);
                builder.append(lang.getMessage("enchantment.minecraft." + entry.getKey().getKey().getKey().toLowerCase()));
                builder.append(' ');
                builder.append(RomanNumber.convert(entry.getValue()));
            }
        }
        result.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponent[]{new TextComponent(builder.toString())}));
        return result;
    }

    @Override
    public void onCommand(Player player, Arguments arguments, String cmd) {
        Stash stash = DungeonsPlugin.getStash();
        Bukkit.getScheduler().runTaskAsynchronously(DungeonsPlugin.getPlugin(), () -> {
            List<ItemStack> list = stash.getItemStacksSilently(player.getUniqueId(), arguments.getString(0, "Stashの名前"));
            Bukkit.getScheduler().runTask(DungeonsPlugin.getPlugin(), () -> {
                if (list.isEmpty()) {
                    player.sendMessage("Stashには何も入っていません");
                    return;
                }
                for (ItemStack itemStack : list) {
                    player.spigot().sendMessage(getTextComponent(itemStack));
                }
            });
        });
    }

    @Override
    public String getDescription() {
        return "Stashの中身を確認します";
    }

    @Override
    public String getPermission() {
        return "net.toshimichi.dungeons.stash.check";
    }
}
