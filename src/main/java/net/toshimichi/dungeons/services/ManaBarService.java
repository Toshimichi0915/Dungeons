package net.toshimichi.dungeons.services;

import net.toshimichi.dungeons.DungeonsPlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ManaBarService implements Service{

    public void run() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            int maxMana = DungeonsPlugin.getManaManager().getMaxMana(p);
            int mana = DungeonsPlugin.getManaManager().getMana(p);
            float progress = (float) mana / maxMana;
            if (progress > 1) progress = 1;
            p.setExp(progress);
            p.setLevel(mana);
        }
    }

}
