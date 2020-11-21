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
}
