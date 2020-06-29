package net.toshimichi.dungeons.runnable;

import net.toshimichi.dungeons.DungeonsPlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ManaRegenRunnable implements Runnable {

    private int counter;

    @Override
    public void run() {
        if (counter++ % 20 != 0) return;
        for (Player p : Bukkit.getOnlinePlayers()) {
            int mana = DungeonsPlugin.getManaManager().getMana(p);
            int maxMana = DungeonsPlugin.getManaManager().getMaxMana(p);
            if (mana >= maxMana) return;
            int regen = maxMana / 30;
            if (mana + regen >= maxMana) {
                DungeonsPlugin.getManaManager().setMana(p, maxMana);
            } else {
                DungeonsPlugin.getManaManager().setMana(p, mana + regen);
            }
        }
    }
}
