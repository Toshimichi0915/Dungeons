package net.toshimichi.dungeons.music;

import net.toshimichi.dungeons.DungeonsPlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

public class PersonalMusicPlayer implements MusicPlayer {

    private BukkitTask task;
    private final Music music;
    private final Player player;
    private int pos;

    public PersonalMusicPlayer(Music music, Player player) {
        this.music = music;
        this.player = player;
    }

    @Override
    public void start() {
        pos = 0;
        resume();
    }

    @Override
    public void pause() {
        if (task != null)
            task.cancel();
        task = null;
    }

    @Override
    public void resume() {
        if (task != null) return;
        task = Bukkit.getScheduler().runTaskTimer(DungeonsPlugin.getPlugin(), () -> {
            if (pos >= music.getLength()) return;
            for (MusicSound sound : music.getMusicSounds(pos)) {
                player.playSound(player.getLocation(), sound.getType(), sound.getPitch(), sound.getVolume());
            }
            pos++;
        }, 1, 1);
    }

    @Override
    public void stop() {
        pause();
        pos = 0;
    }
}
