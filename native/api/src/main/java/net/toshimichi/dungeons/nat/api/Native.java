package net.toshimichi.dungeons.nat.api;

public interface Native {

    /**
     * サーバーのバージョンが適合するかを返します.
     * @return 適合する場合は {@code true} そうでなければ {@code false}
     */
    boolean matchVersion();
}
