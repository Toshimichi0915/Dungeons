package net.toshimichi.dungeons;

import net.toshimichi.dungeons.commands.DungeonsCommand;
import net.toshimichi.dungeons.enchants.EnchantManager;
import net.toshimichi.dungeons.enchants.NbtEnchantManager;
import net.toshimichi.dungeons.enchants.armor.autosmelt.AutoSmelt1;
import net.toshimichi.dungeons.enchants.armor.gottagofast.GottaGoFast1;
import net.toshimichi.dungeons.enchants.armor.gottagofast.GottaGoFast2;
import net.toshimichi.dungeons.enchants.armor.gottagofast.GottaGoFast3;
import net.toshimichi.dungeons.enchants.armor.protection.Protection1;
import net.toshimichi.dungeons.enchants.armor.protection.Protection2;
import net.toshimichi.dungeons.enchants.armor.protection.Protection3;
import net.toshimichi.dungeons.enchants.armor.ringarmor.RingArmor1;
import net.toshimichi.dungeons.enchants.armor.ringarmor.RingArmor2;
import net.toshimichi.dungeons.enchants.armor.ringarmor.RingArmor3;
import net.toshimichi.dungeons.enchants.armor.wizard.Wizard1;
import net.toshimichi.dungeons.enchants.armor.wizard.Wizard2;
import net.toshimichi.dungeons.enchants.armor.wizard.Wizard3;
import net.toshimichi.dungeons.enchants.bow.fletching.Fletching1;
import net.toshimichi.dungeons.enchants.bow.fletching.Fletching2;
import net.toshimichi.dungeons.enchants.bow.fletching.Fletching3;
import net.toshimichi.dungeons.enchants.bow.megalongbow.MegaLongbow1;
import net.toshimichi.dungeons.enchants.bow.megalongbow.MegaLongbow2;
import net.toshimichi.dungeons.enchants.bow.megalongbow.MegaLongbow3;
import net.toshimichi.dungeons.enchants.bow.telebow.Bugbow;
import net.toshimichi.dungeons.enchants.bow.telebow.Telebow1;
import net.toshimichi.dungeons.enchants.bow.telebow.Telebow2;
import net.toshimichi.dungeons.enchants.bow.telebow.Telebow3;
import net.toshimichi.dungeons.enchants.bow.volley.Volley1;
import net.toshimichi.dungeons.enchants.bow.volley.Volley2;
import net.toshimichi.dungeons.enchants.bow.volley.Volley3;
import net.toshimichi.dungeons.enchants.shield.parry.Parry1;
import net.toshimichi.dungeons.enchants.shield.parry.Parry2;
import net.toshimichi.dungeons.enchants.shield.parry.Parry3;
import net.toshimichi.dungeons.enchants.shield.springinside.SpringInside1;
import net.toshimichi.dungeons.enchants.shield.springinside.SpringInside2;
import net.toshimichi.dungeons.enchants.shield.springinside.SpringInside3;
import net.toshimichi.dungeons.enchants.sword.aspectoffire.AspectOfFire1;
import net.toshimichi.dungeons.enchants.sword.aspectoffire.AspectOfFire2;
import net.toshimichi.dungeons.enchants.sword.aspectoffire.AspectOfFire3;
import net.toshimichi.dungeons.enchants.sword.billionaire.Billionaire1;
import net.toshimichi.dungeons.enchants.sword.billionaire.Billionaire2;
import net.toshimichi.dungeons.enchants.sword.billionaire.Billionaire3;
import net.toshimichi.dungeons.enchants.sword.flame.Flame1;
import net.toshimichi.dungeons.enchants.sword.flame.Flame2;
import net.toshimichi.dungeons.enchants.sword.flame.Flame3;
import net.toshimichi.dungeons.enchants.sword.katana.Katana1;
import net.toshimichi.dungeons.enchants.sword.katana.Katana2;
import net.toshimichi.dungeons.enchants.sword.katana.Katana3;
import net.toshimichi.dungeons.enchants.sword.lifesteal.Lifesteal1;
import net.toshimichi.dungeons.enchants.sword.lifesteal.Lifesteal2;
import net.toshimichi.dungeons.enchants.sword.lifesteal.Lifesteal3;
import net.toshimichi.dungeons.enchants.sword.painfocus.PainFocus1;
import net.toshimichi.dungeons.enchants.sword.painfocus.PainFocus2;
import net.toshimichi.dungeons.enchants.sword.painfocus.PainFocus3;
import net.toshimichi.dungeons.enchants.sword.sharpness.Sharpness1;
import net.toshimichi.dungeons.enchants.sword.sharpness.Sharpness2;
import net.toshimichi.dungeons.enchants.sword.sharpness.Sharpness3;
import net.toshimichi.dungeons.enchants.tool.efficiency.*;
import net.toshimichi.dungeons.enchants.tool.silktouch.SilkTouch1;
import net.toshimichi.dungeons.enchants.wand.Sanctity1;
import net.toshimichi.dungeons.enchants.wand.Sanctity2;
import net.toshimichi.dungeons.enchants.wand.Sanctity3;
import net.toshimichi.dungeons.gui.GuiManager;
import net.toshimichi.dungeons.gui.SimpleGuiManager;
import net.toshimichi.dungeons.lang.*;
import net.toshimichi.dungeons.lang.ipstack.IpStackApi;
import net.toshimichi.dungeons.listeners.*;
import net.toshimichi.dungeons.misc.*;
import net.toshimichi.dungeons.nat.api.Installer;
import net.toshimichi.dungeons.services.*;
import net.toshimichi.dungeons.utils.Lottery;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DungeonsPlugin extends JavaPlugin {

    private static final Charset charset = StandardCharsets.UTF_8;
    private static DungeonsPlugin plugin;
    private static EnchantManager enchantManager;
    private static ManaManager manaManager;
    private static Economy economy;
    private static Stash stash;
    private static IpStackApi ipStackApi;
    private static LocaleManager localeManager;
    private static GuiManager guiManager;
    private static ArrayList<Locale> locales;
    private static Locale defaultLocale;

    private final ArrayList<Service> services = new ArrayList<>();
    private File confFile;
    private YamlConfiguration conf;

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

    public static GuiManager getGuiManager() {
        return guiManager;
    }

    public static LocaleManager getLocaleManager() {
        return localeManager;
    }

    public static List<Locale> getLocales() {
        return new ArrayList<>(locales);
    }

    public static Locale getDefaultLocale() {
        return defaultLocale;
    }


    private void copyLang(String locale) throws IOException {
        File f = new File(getDataFolder(), "lang/" + locale + ".lang");
        String contents = IOUtils.toString(getResource("lang/" + locale + ".lang"), StandardCharsets.UTF_8);
        PropertiesLocale newLocale = new PropertiesLocale(contents);
        if (f.exists()) {
            String version = new PropertiesLocale(f).get("version");
            if ("-1".equals(version) || NumberUtils.toInt(version) >= NumberUtils.toInt(newLocale.get("version")))
                return;
            FileUtils.writeStringToFile(f, contents, StandardCharsets.UTF_8);
        }
        f.getParentFile().mkdirs();
        f.createNewFile();
        FileUtils.writeStringToFile(f, contents, StandardCharsets.UTF_8);
    }

    private Locale getLocale(String name) throws IOException {
        File f = new File(getDataFolder(), "lang/" + name + ".lang");
        return new PropertiesLocale(f);
    }

    private void registerService(Service service) {
        services.add(service);
        service.start();
        Bukkit.getScheduler().runTaskTimer(DungeonsPlugin.getPlugin(), service, 1, 1);
    }

    @Override
    public void saveDefaultConfig() {
        try {
            FileUtils.copyInputStreamToFile(getResource("config.yaml"), confFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public FileConfiguration getConfig() {
        if (conf == null)
            reloadConfig();
        return conf;
    }

    @Override
    public void reloadConfig() {
        try (InputStreamReader reader = new InputStreamReader(getResource("config.yaml"), charset)) {
            conf = new YamlConfiguration();
            conf.load(reader);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onEnable() {
        confFile = new File(getDataFolder(), "config.yaml");
        saveDefaultConfig();
        plugin = this;
        ConfigurationSerialization.registerClass(Lottery.class);

        //Inject natives
        YamlConfiguration nativeConf = new YamlConfiguration();
        try (InputStreamReader reader = new InputStreamReader(getResource("injection.yaml"), charset)) {
            nativeConf.load(reader);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
        for (String className : nativeConf.getStringList("native")) {
            Object o;
            try {
                o = Class.forName(className).newInstance();
                if (!(o instanceof Installer)) continue;

                Installer installer = (Installer) o;
                if (!installer.isAvailable()) continue;
                installer.install(Bukkit.getServicesManager(), this);
            } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        //Start manager classes
        enchantManager = new NbtEnchantManager(
                new Sharpness1(), new Sharpness2(), new Sharpness3(),
                new GottaGoFast1(), new GottaGoFast2(), new GottaGoFast3(),
                new Sanctity1(), new Sanctity2(), new Sanctity3(),
                new Wizard1(), new Wizard2(), new Wizard3(),
                new MegaLongbow1(), new MegaLongbow2(), new MegaLongbow3(),
                new SpringInside1(), new SpringInside2(), new SpringInside3(),
                new Volley1(), new Volley2(), new Volley3(),
                new Fletching1(), new Fletching2(), new Fletching3(),
                new PainFocus1(), new PainFocus2(), new PainFocus3(),
                new Lifesteal1(), new Lifesteal2(), new Lifesteal3(),
                new AutoSmelt1(),
                new Protection1(), new Protection2(), new Protection3(),
                new Billionaire1(), new Billionaire2(), new Billionaire3(),
                new Telebow1(), new Telebow2(), new Telebow3(), new Bugbow(),
                new Efficiency1(), new Efficiency2(), new Efficiency3(), new Efficiency4(), new Efficiency5(),
                new SilkTouch1(),
                new Katana1(), new Katana2(), new Katana3(),
                new AspectOfFire1(), new AspectOfFire2(), new AspectOfFire3(),
                new Flame1(), new Flame2(), new Flame3(),
                new Parry1(), new Parry2(), new Parry3(),
                new RingArmor1(), new RingArmor2(), new RingArmor3());

        manaManager = new LocalManaManager(new File(getDataFolder(), "mana"));
        stash = new YamlStash(new File(getDataFolder(), "stash"));
        ipStackApi = new IpStackApi(getConfig().getString("ipstack.api-key"));
        guiManager = new SimpleGuiManager();

        if (Bukkit.getPluginManager().getPlugin("Vault") != null &&
                Bukkit.getServicesManager().load(net.milkbowl.vault.economy.Economy.class) != null) {
            economy = new VaultEconomy(Bukkit.getServicesManager().load(net.milkbowl.vault.economy.Economy.class));
        }
        economy = new YamlEconomy(new File(getDataFolder(), "economy"));

        locales = new ArrayList<>();

        //Install lang files
        Set<LocaleFactory> factories = new HashSet<>();
        LocaleFactory defaultFactory = null;
        try {
            copyLang("ja_jp");
            copyLang("en_us");
            Locale jaLocale = getLocale("ja_jp");
            Locale enLocale = getLocale("en_us");
            locales.add(jaLocale);
            locales.add(enLocale);
            defaultLocale = jaLocale;
            if (ipStackApi.isAvailable()) {
                defaultFactory = new IpStackLocaleFactory("ja_jp", jaLocale, "ja");
                factories.add(defaultFactory);
                factories.add(new IpStackLocaleFactory("en_us", enLocale, "en"));
            }
            if (defaultFactory == null)
                defaultFactory = new OptionLocaleFactory("ja_jp", jaLocale, "ja_jp");
            factories.add(new OptionLocaleFactory("ja_jp", jaLocale, "ja_jp"));
            factories.add(new OptionLocaleFactory("en_us", enLocale, "en_us"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        localeManager = new YamlLocaleManager(defaultFactory, new File(getDataFolder(), "locale"), factories.toArray(new LocaleFactory[0]));

        //Install commands
        getCommand("dungeons").setExecutor(new DungeonsCommand());

        //Register event listeners and runners
        Bukkit.getPluginManager().registerEvents(new EnchantListener(), this);
        Bukkit.getPluginManager().registerEvents(new HpBarListener(), this);
        Bukkit.getPluginManager().registerEvents(new StashListener(), this);
        Bukkit.getPluginManager().registerEvents(new EconomyListener(), this);
        Bukkit.getPluginManager().registerEvents(new ManaListener(), this);
        Bukkit.getPluginManager().registerEvents(new LangListener(), this);
        Bukkit.getPluginManager().registerEvents(new BossBarChatListener(), this);
        Bukkit.getPluginManager().registerEvents(new GuiListener(), this);
        Bukkit.getPluginManager().registerEvents(new ShieldListener(), this);

        Bukkit.getOnlinePlayers().forEach(enchantManager::refresh);

        registerService(new ManaBarService());
        registerService(new EnchantService());
        registerService(new ManaRegenService());
        registerService(new BossBarChatService());
        registerService(new GuiService());
    }

    @Override
    public void onDisable() {
        Bukkit.getOnlinePlayers().forEach(enchantManager::disable);
        services.forEach(Service::stop);
    }
}
