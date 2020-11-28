package net.toshimichi.dungeons.services;

import net.toshimichi.dungeons.DungeonsPlugin;
import net.toshimichi.dungeons.utils.BossBarChat;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.text.SimpleDateFormat;
import java.util.*;

public class BossBarChatService implements Service {

    private Iterator<? extends Player> iterator;
    private boolean asyncComplete = true;
    private final WeakHashMap<Player, Integer> moneyMap = new WeakHashMap<>();

    @Override
    public void start() {
        Bukkit.getScheduler().runTaskTimer(DungeonsPlugin.getPlugin(), () -> {
            if (!asyncComplete) return;
            asyncComplete = false;
            ArrayList<Player> players = new ArrayList<>(Bukkit.getOnlinePlayers());
            Bukkit.getScheduler().runTaskAsynchronously(DungeonsPlugin.getPlugin(), () -> {
                for (Player p : players) {
                    int money = DungeonsPlugin.getEconomy().getBalance(p.getUniqueId());
                    Bukkit.getScheduler().runTask(DungeonsPlugin.getPlugin(), () ->
                            moneyMap.put(p, money));
                }
                asyncComplete = true;
            });
        }, 1, 1);
    }

    @Override
    public void run() {
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
            String gold = ChatColor.GOLD + Integer.toString(moneyMap.getOrDefault(p, 0)) + "g";
            String mana = "" + ChatColor.AQUA + DungeonsPlugin.getManaManager().getMana(p) + "/" +
                    DungeonsPlugin.getManaManager().getMaxMana(p);
            BossBarChat.setPrimaryMessage(p, gold + " " + mana + " " + time);
        }
    }
}
