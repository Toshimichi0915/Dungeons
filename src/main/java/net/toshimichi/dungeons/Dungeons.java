package net.toshimichi.dungeons;

import net.toshimichi.dungeons.enchants.EnchantManager;
import net.toshimichi.dungeons.gui.GuiManager;
import net.toshimichi.dungeons.lang.Locale;
import net.toshimichi.dungeons.lang.LocaleManager;
import net.toshimichi.dungeons.lang.ipstack.IpStackApi;
import net.toshimichi.dungeons.misc.Economy;
import net.toshimichi.dungeons.misc.ManaManager;
import net.toshimichi.dungeons.misc.Stash;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.util.List;

public interface Dungeons {

    EnchantManager getEnchantManager();

    ManaManager getManaManager();

    Economy getEconomy();

    Stash getStash();

    IpStackApi getIpStackApi();

    LocaleManager getLocaleManager();

    GuiManager getGuiManager();

    List<Locale> getLocales();

    Locale getDefaultLocale();

    Plugin getPlugin();

    static Dungeons getInstance() {
        return Bukkit.getServicesManager().load(Dungeons.class);
    }
}
