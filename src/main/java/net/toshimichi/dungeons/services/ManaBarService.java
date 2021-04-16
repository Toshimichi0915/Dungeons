package net.toshimichi.dungeons.services;

import net.toshimichi.dungeons.Dungeons;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ManaBarService implements Service {

    public void run() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            int maxMana = Dungeons.getInstance().getManaManager().getMaxMana(p);
            int mana = Dungeons.getInstance().getManaManager().getMana(p);
            float progress = (float) mana / maxMana;
            if (progress > 1) progress = 1;
            p.setExp(progress);
            p.setLevel(mana);
        }
    }

}
