package net.toshimichi.dungeons.nat.v1_15_2;

import net.toshimichi.dungeons.nat.api.CooldownUtils;
import net.toshimichi.dungeons.nat.api.Installer;
import net.toshimichi.dungeons.nat.api.LocaleLanguage;
import net.toshimichi.dungeons.nat.api.NbtItemStackFactory;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.ServicesManager;

public class NativeInstaller implements Installer {
    @Override
    public boolean isAvailable() {
        return Bukkit.getVersion().equals("1.15.2");
    }

    @Override
    public void install(ServicesManager manager, Plugin plugin) {
        manager.register(LocaleLanguage.class, new NativeLocaleLanguage(), plugin, ServicePriority.High);
        manager.register(NbtItemStackFactory.class, new NativeNbtItemStackFactory(), plugin, ServicePriority.High);
        manager.register(CooldownUtils.class, new NativeCooldownUtils(), plugin, ServicePriority.High);
    }
}
