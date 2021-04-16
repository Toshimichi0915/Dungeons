package net.toshimichi.dungeons.music;

/**
 * 音楽を表します.
 * 音楽は1つ以上の {@link MusicSound} で構成され, 同時もしくは連続的に音を流します.
 */
public interface Music {

    /**
     * ある位置において再生される {@link MusicSound} を返します.
     *
     * @param pos 音楽の再生位置(tick単位)
     * @return 再生する {@link MusicSound} の一覧, もしくは {@code null}
     */
    MusicSound[] getMusicSounds(int pos);

    /**
     * 音楽の長さを返します.
     *
     * @return 音楽の長さ(tick単位)
     */
    int getLength();
}
