package net.toshimichi.dungeons.music;

import net.toshimichi.dungeons.Dungeons;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
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
        task = new BukkitRunnable() {
            @Override
            public void run() {
                if (pos >= music.getLength()) {
                    cancel();
                    return;
                }
                for (MusicSound sound : music.getMusicSounds(pos)) {
                    player.playSound(player.getLocation(), sound.getType(), sound.getPitch(), sound.getVolume());
                }
                pos++;
            }
        }.runTaskTimer(Dungeons.getInstance().getPlugin(), 1, 1);
    }

    @Override
    public void stop() {
        pause();
        pos = 0;
    }

    @Override
    public boolean isPlaying() {
        return task != null && !task.isCancelled();
    }

    @Override
    public int getPos() {
        return pos;
    }

    @Override
    public Music getMusic() {
        return music;
    }
}
