package net.toshimichi.dungeons.utils;

import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * {@link BossBar} を利用して擬似的なメッセージバーを作り出します.
 */
public class BossBarChat {
    private static final int maxLine = 5;
    private static final WeakHashMap<Player, HashMap<Integer, BossBar>> map = new WeakHashMap<>();

    /**
     * プレイヤーにメッセージを送ります.
     * {@code message} に {@code null} が指定された場合は一番上の表示を消します.
     *
     * @param player  プレイヤー
     * @param message メッセージ
     */
    public static void sendMessage(Player player, String message) {
        if (message == null) {
            HashMap<Integer, BossBar> bars = map.get(player);
            if (bars == null) return;
            for (Map.Entry<Integer, BossBar> entry : new HashMap<>(bars).entrySet()) {
                if (entry.getKey() == 0) continue;
                bars.put(entry.getKey() - 1, entry.getValue());
            }
            bars.get(bars.size() - 1).removeAll();
            bars.remove(bars.size() - 1);
            map.put(player, bars);
            return;
        }
        //空白の大きさを計算する
        StringBuilder builder = new StringBuilder();
        String space;
        int length = (int) Math.round(((double) CharLengths.getLength(message) + 150) / 4);
        for (int i = 0; i < length; i++) builder.append(' ');
        space = builder.toString();

        HashMap<Integer, BossBar> bars = map.get(player);
        if (bars == null) {
            bars = new HashMap<>();
            BossBar primary = Bukkit.createBossBar("", BarColor.WHITE, BarStyle.SOLID);
            primary.addPlayer(player);
            bars.put(0, primary);
        }
        int lines = bars.size();
        BossBar bar = bars.get(lines);
        if (bar == null) {
            bar = Bukkit.createBossBar(space + message, BarColor.WHITE, BarStyle.SOLID);
            bar.addPlayer(player);
        } else {
            bar.setTitle(space + message);
        }
        bars.put(lines, bar);

        if (lines >= maxLine) {
            bars.get(1).removeAll();
            bars.remove(1);
            for (Map.Entry<Integer, BossBar> entry : new HashMap<>(bars).entrySet()) {
                if (entry.getKey() == 0) continue;
                bars.put(entry.getKey() - 1, entry.getValue());
            }
            bars.remove(lines);
        }
        map.put(player, bars);
    }

    /**
     * プレイヤーのメッセージバーを消去します.
     *
     * @param player プレイヤー
     */
    public static void clear(Player player) {
        HashMap<Integer, BossBar> bars = map.get(player);
        if (bars == null) return;
        bars.values().forEach(BossBar::removeAll);
        map.remove(player);
    }

    private static BossBar getPrimaryBossBar(Player player) {
        HashMap<Integer, BossBar> bars = map.getOrDefault(player, new HashMap<>());
        BossBar result = bars.get(0);
        if(result == null) {
            result = Bukkit.createBossBar("", BarColor.WHITE, BarStyle.SOLID);
            result.addPlayer(player);
            bars.put(0, result);
            map.put(player, bars);
        }
        return result;
    }

    /**
     * 常に表示されるメッセージを設定します.
     * @param player プレイヤー
     * @param message メッセージ
     */
    public static void setPrimaryMessage(Player player, String message) {
        getPrimaryBossBar(player).setTitle(message);
    }

    /**
     * 常に表示されるメッセージを返します.
     * @param player プレイヤー
     * @return メッセージ
     */
    public static String getPrimaryMessage(Player player) {
        return getPrimaryBossBar(player).getTitle();
    }
}
