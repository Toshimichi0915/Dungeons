package net.toshimichi.dungeons.lang;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.WeakHashMap;

public class YamlLocaleManager implements LocaleManager {

    private LocaleFactory def;
    private File baseDir;
    private LocaleFactory[] factories;
    private WeakHashMap<Player, Locale> localeMap = new WeakHashMap<>();
    private WeakHashMap<Player, Locale> suggestedLocaleMap = new WeakHashMap<>();

    private File getFile(Player player) {
        return new File(baseDir, player.getUniqueId() + ".yaml");
    }

    private YamlConfiguration getYaml(Player player) {
        YamlConfiguration conf = new YamlConfiguration();
        File f = getFile(player);
        try {
            if (!f.exists()) {
                f.getParentFile().mkdirs();
                f.createNewFile();
            }
            conf.load(f);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
            return null;
        }
        return conf;
    }

    public YamlLocaleManager(LocaleFactory def, File baseDir, LocaleFactory... factories) {
        this.def = def;
        this.baseDir = baseDir;
        this.factories = factories;
    }

    @Override
    public synchronized Locale getLocale(String locale) {
        for (LocaleFactory factory : factories) {
            if (factory.getName().equals(locale))
                return factory.getLocale();
            if (locale.equals(factory.getLocale().get("general.lang.english")))
                return factory.getLocale();
        }
        return null;
    }

    @Override
    public synchronized Locale getLocale(Player player) {
        Locale locale = localeMap.get(player);
        if (locale == null) {
            YamlConfiguration conf = getYaml(player);
            if (conf != null && conf.contains("lang"))
                locale = getLocale(conf.getString("lang"));
        }
        if (locale == null)
            locale = getSuggestedLocale(player);
        localeMap.put(player, locale);
        return locale;
    }

    @Override
    public synchronized Locale getSuggestedLocale(Player player) {
        Locale suggested = suggestedLocaleMap.get(player);
        if (suggested == null) {
            YamlConfiguration conf = getYaml(player);
            if (conf != null && conf.contains("suggested"))
                suggested = getLocale(conf.getString("suggested"));
        }
        if (suggested == null) {
            suggested = Arrays.stream(factories)
                    .filter(p -> p.match(player))
                    .reduce((a, b) -> {
                        if (a.getPriority() < b.getPriority()) return a;
                        else return b;
                    }).orElse(def).getLocale();
        }
        suggestedLocaleMap.put(player, suggested);
        return suggested;
    }

    @Override
    public synchronized void setLocale(Player player, Locale locale) {
        localeMap.put(player, locale);
    }

    private String getName(Locale locale) {
        for (LocaleFactory factory : factories) {
            if (factory.getLocale().equals(locale))
                return factory.getName();
        }
        return null;
    }

    @Override
    public synchronized void save(Player player) {
        Locale locale = localeMap.get(player);
        if (locale == null) return;
        String name = getName(locale);
        if (name == null) return;
        YamlConfiguration conf = new YamlConfiguration();
        conf.set("lang", name);
        conf.set("suggested", getName(getSuggestedLocale(player)));
        try {
            conf.save(getFile(player));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public synchronized void saveAll() {
        localeMap.keySet().forEach(this::save);
    }
}
