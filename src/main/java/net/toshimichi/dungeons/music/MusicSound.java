package net.toshimichi.dungeons.music;

import org.bukkit.Sound;

/**
 * 音楽で再生される1つの音を表します.
 */
public interface MusicSound {

    /**
     * 音の種類を返します.
     * @return 音の種類
     */
    Sound getType();

    /**
     * 音のピッチを返します.
     * @return 音のピッチ
     */
    float getPitch();

    /**
     * 音の大きさを返します.
     * @return 音の大きさ
     */
    float getVolume();
}
