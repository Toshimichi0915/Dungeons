package net.toshimichi.dungeons.services;

import net.toshimichi.dungeons.Dungeons;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ManaRegenService implements Service {

    private int counter;

    @Override
    public void run() {
        if (counter++ % 20 != 0) return;
        for (Player p : Bukkit.getOnlinePlayers()) {
            int mana = Dungeons.getInstance().getManaManager().getMana(p);
            int maxMana = Dungeons.getInstance().getManaManager().getMaxMana(p);
            if (mana >= maxMana) return;
            int regen = maxMana / 30;
            if (mana + regen >= maxMana) {
                Dungeons.getInstance().getManaManager().setMana(p, maxMana);
            } else {
                Dungeons.getInstance().getManaManager().setMana(p, mana + regen);
            }
        }
    }
}
