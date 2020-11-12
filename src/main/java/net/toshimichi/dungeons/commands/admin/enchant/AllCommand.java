package net.toshimichi.dungeons.commands.admin.enchant;

import net.toshimichi.dungeons.DungeonsPlugin;
import net.toshimichi.dungeons.commands.Arguments;
import net.toshimichi.dungeons.commands.SubCommand;
import net.toshimichi.dungeons.enchants.Enchant;
import net.toshimichi.dungeons.enchants.EnchantManager;
import net.toshimichi.dungeons.utils.CharLengths;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.function.Function;

public class AllCommand implements SubCommand {

    private void add(ArrayList<Enchant> enchants, ArrayList<StringBuilder> builders, Function<Enchant, String> supplier) {
        int maxLength = 0;
        for (int i = 0; i < enchants.size(); i++) {
            Enchant e = enchants.get(i);
            StringBuilder b = builders.get(i);
            b.append(supplier.apply(e));
            int length = CharLengths.getLength(b.toString());
            if (length > maxLength)
                maxLength = length;
        }

        for (StringBuilder builder : builders) {
            int added = (maxLength - CharLengths.getLength(builder.toString()) + 2) / 4;
            for (int i = 0; i < added; i++)
                builder.append(' ');
        }
    }

    @Override
    public void onCommand(CommandSender sender, Arguments args, String cmd) {
        EnchantManager manager = DungeonsPlugin.getEnchantManager();

        ArrayList<Enchant> enchants = new ArrayList<>(manager.getAllEnchants());

        enchants.sort((o1, o2) -> {
            int id = o1.getId() - o2.getId();
            if (id != 0) return id / Math.abs(id);
            int level = o1.getLevel() - o2.getLevel();
            if (level != 0) return level / Math.abs(level);
            return 0;
        });
        ArrayList<StringBuilder> builders = new ArrayList<>();
        for (int i = 0; i < enchants.size(); i++)
            builders.add(new StringBuilder());
        add(enchants, builders, e -> "(" + e.getId());
        add(enchants, builders, e -> ", " + e.getRarity() + ")");
        add(enchants, builders, e -> " " + ChatColor.BLUE + e.getName() + ChatColor.RESET);

        for (StringBuilder b : builders)
            sender.sendMessage(b.toString());
    }

    @Override
    public String getDescription() {
        return "全てのエンチャントを表示します";
    }

    @Override
    public String getPermission() {
        return "net.toshimichi.dungeons.enchant.all";
    }
}
