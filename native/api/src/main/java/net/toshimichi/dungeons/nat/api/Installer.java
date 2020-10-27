package net.toshimichi.dungeons.nat.api;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.ServicesManager;

public interface Installer {

    /**
     * この {@link Installer} が使用可能どうか調べます.
     *
     * @return 適合する場合は {@code true} そうでなければ {@code false}
     */
    boolean isAvailable();

    /**
     * この {@link Installer} が提供するサービスをインストールします.
     *
     * @param manager 使用する {@link ServicesManager}
     * @param plugin プラグイン
     */
    void install(ServicesManager manager, Plugin plugin);
}
