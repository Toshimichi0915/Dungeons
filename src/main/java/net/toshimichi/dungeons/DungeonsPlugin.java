package net.toshimichi.dungeons;

import net.toshimichi.dungeons.commands.DungeonsCommand;
import net.toshimichi.dungeons.enchants.EnchantManager;
import net.toshimichi.dungeons.enchants.SimpleEnchantManager;
import net.toshimichi.dungeons.enchants.armor.*;
import net.toshimichi.dungeons.enchants.sword.Sharpness1;
import net.toshimichi.dungeons.enchants.sword.Sharpness2;
import net.toshimichi.dungeons.enchants.sword.Sharpness3;
import net.toshimichi.dungeons.enchants.wand.Sanctity1;
import net.toshimichi.dungeons.enchants.wand.Sanctity2;
import net.toshimichi.dungeons.enchants.wand.Sanctity3;
import net.toshimichi.dungeons.lang.*;
import net.toshimichi.dungeons.lang.ipstack.IpStackApi;
import net.toshimichi.dungeons.listeners.*;
import net.toshimichi.dungeons.misc.*;
import net.toshimichi.dungeons.runnable.BossBarChatRunnable;
import net.toshimichi.dungeons.runnable.EnchantRunnable;
import net.toshimichi.dungeons.runnable.ManaBarRunnable;
import net.toshimichi.dungeons.runnable.ManaRegenRunnable;
import net.toshimichi.dungeons.utils.Lottery;
import org.bukkit.Bukkit;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.craftbukkit.libs.org.apache.commons.io.IOUtils;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

public class DungeonsPlugin extends JavaPlugin {

    private static DungeonsPlugin plugin;
    private static SimpleEnchantManager enchantManager;
    private static SimpleManaManager manaManager;
    private static SimpleEconomy economy;
    private static Stash stash;
    private static IpStackApi ipStackApi;
    private static LocaleManager localeManager;
    private static Set<Locale> locales;
    private static Locale defaultLocale;

    public static JavaPlugin getPlugin() {
        return plugin;
    }

    public static EnchantManager getEnchantManager() {
        return enchantManager;
    }

    public static ManaManager getManaManager() {
        return manaManager;
    }

    public static Economy getEconomy() {
        return economy;
    }

    public static Stash getStash() {
        return stash;
    }

    public static IpStackApi getIpStackApi() {
        return ipStackApi;
    }

    public static LocaleManager getLocaleManager() {
        return localeManager;
    }

    public static Set<Locale> getLocales() {
        return new HashSet<>(locales);
    }

    public static Locale getDefaultLocale() {
        return defaultLocale;
    }


    private void copyLang(String locale) throws IOException {
        File f = new File(getDataFolder(), "lang/" + locale + ".lang");
        if (f.exists()) return;
        f.getParentFile().mkdirs();
        f.createNewFile();
        try (InputStream in = getResource("lang/" + locale + ".lang");
             FileOutputStream out = new FileOutputStream(f)) {
            IOUtils.copy(in, out);
        }
    }

    private Locale getLocale(String name) throws IOException {
        File f = new File(getDataFolder(), "lang/" + name + ".lang");
        return new PropertiesLocale(f);
    }

    @Override
    public void onEnable() {
        saveDefaultConfig();
        plugin = this;
        ConfigurationSerialization.registerClass(Lottery.class);

        enchantManager = new SimpleEnchantManager(
                new Sharpness1(), new Sharpness2(), new Sharpness3(),
                new GottaGoFast1(), new GottaGoFast2(), new GottaGoFast3(),
                new Sanctity1(), new Sanctity2(), new Sanctity3(),
                new Wizard1(), new Wizard2(), new Wizard3());
        manaManager = new SimpleManaManager(new File(getDataFolder(), "mana"));
        economy = new SimpleEconomy(new File(getDataFolder(), "economy"));
        stash = new Stash(new File(getDataFolder(), "stash"));
        ipStackApi = new IpStackApi(getConfig().getString("ipstack.api-key"));
        locales = new HashSet<>();

        Set<LocaleFactory> factories = new HashSet<>();
        LocaleFactory defaultFactory = null;
        try {
            copyLang("ja_jp");
            copyLang("en_us");
            Locale jaLocale = getLocale("ja_jp");
            Locale enLoclae = getLocale("en_us");
            locales.add(jaLocale);
            locales.add(enLoclae);
            defaultLocale = jaLocale;
            if (ipStackApi.isAvailable()) {
                defaultFactory = new IpStackLocaleFactory("ja_jp", jaLocale, "ja");
                factories.add(defaultFactory);
                factories.add(new IpStackLocaleFactory("en_us", enLoclae, "en"));
            }
            if (defaultFactory == null)
                defaultFactory = new OptionLocaleFactory("ja_jp", jaLocale, "ja_jp");
            factories.add(new OptionLocaleFactory("ja_jp", jaLocale, "ja_jp"));
            factories.add(new OptionLocaleFactory("en_us", enLoclae, "en_us"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        localeManager = new SimpleLocaleManager(defaultFactory, new File(getDataFolder(), "locale"), factories.toArray(new LocaleFactory[0]));

        getCommand("dungeons").setExecutor(new DungeonsCommand());

        Bukkit.getPluginManager().registerEvents(new EnchantListener(), this);
        Bukkit.getPluginManager().registerEvents(new HpBarListener(), this);
        Bukkit.getPluginManager().registerEvents(new StashListener(), this);
        Bukkit.getPluginManager().registerEvents(new EconomyListener(), this);
        Bukkit.getPluginManager().registerEvents(new ManaListener(), this);
        Bukkit.getPluginManager().registerEvents(new LangListener(), this);
        Bukkit.getPluginManager().registerEvents(new BossBarChatListener(), this);

        Bukkit.getOnlinePlayers().forEach(enchantManager::refresh);

        Bukkit.getScheduler().runTaskTimer(this, new ManaBarRunnable(), 1, 1);
        Bukkit.getScheduler().runTaskTimer(this, new EnchantRunnable(), 1, 1);
        Bukkit.getScheduler().runTaskTimer(this, new ManaRegenRunnable(), 1, 1);
        Bukkit.getScheduler().runTaskTimer(this, enchantManager::tick, 1, 1);
        Bukkit.getScheduler().runTaskTimer(this, new BossBarChatRunnable(), 1, 1);
    }

    @Override
    public void onDisable() {
        Bukkit.getOnlinePlayers().forEach(enchantManager::disable);
    }
}
