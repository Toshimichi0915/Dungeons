package net.toshimichi.dungeons.runnable;

import net.toshimichi.dungeons.DungeonsPlugin;
import net.toshimichi.dungeons.utils.BossBarChat;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.TimeZone;

public class BossBarChatRunnable implements Runnable {

    private Iterator<? extends Player> iterator;

    @Override
    public void run() {
        if (!Bukkit.getPluginManager().isPluginEnabled(DungeonsPlugin.getPlugin())) return;
        if (Bukkit.getOnlinePlayers().size() == 0) return;
        int size = Bukkit.getOnlinePlayers().size() / 20 + 1;
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        formatter.setTimeZone(TimeZone.getTimeZone("JST"));
        String time = ChatColor.GREEN + formatter.format(new Date());
        for (int i = 0; i < size; i++) {
            if (iterator == null || !iterator.hasNext()) {
                iterator = Bukkit.getOnlinePlayers().iterator();
            }
            Player p = iterator.next();
            String gold = ChatColor.GOLD + Integer.toString(DungeonsPlugin.getEconomy().getMoney(p.getUniqueId())) + "g";
            String mana = "" + ChatColor.AQUA + DungeonsPlugin.getManaManager().getMana(p) + "/" + DungeonsPlugin.getManaManager().getMaxMana(p);
            BossBarChat.setPrimaryMessage(p, gold + " " + mana + " " + time);
        }
    }
}
