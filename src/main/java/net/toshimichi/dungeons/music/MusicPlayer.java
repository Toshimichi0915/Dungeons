package net.toshimichi.dungeons.music;

/**
 * 音楽を再生, 停止等を管理します.
 */
public interface MusicPlayer {

    /**
     * 音楽の再生を最初から開始します.
     */
    void start();

    /**
     * 音楽の再生を中断します.
     */
    void pause();

    /**
     * 音楽の再生を中断したところから, もしくは最初から開始します.
     */
    void resume();

    /**
     * 音楽の再生を停止し, 音楽の再生位置を最初に戻します.
     */
    void stop();

    /**
     * 音楽が再生中かそうでないかを調べます.
     *
     * @return 再生中の場合は {@code true} そうでなければ {@code false}
     */
    boolean isPlaying();

    /**
     * 現在の音楽の再生位置を返します.
     *
     * @return 現在の音楽の再生位置
     */
    int getPos();

    /**
     * 再生されている音楽を返します.
     *
     * @return 再生されている音楽
     */
    Music getMusic();
}
