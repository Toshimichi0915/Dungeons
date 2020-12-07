package net.toshimichi.dungeons.nbt;

import net.toshimichi.dungeons.nat.api.nbt.Nbt;

/**
 * 様々なオブジェクトをNBTに相互変換するクラスです.
 */
public interface NbtMapper {

    /**
     * オブジェクトをNBTに変換します.
     *
     * @param o 変換するオブジェクト
     * @return 変換されたNBT. 変換できなかった場合は {@code null}
     */
    Nbt serialize(Object o);

    /**
     * NBTをオブジェクトに変換します.
     *
     * @param nbt  変換するNBT
     * @param type 変換後のオブジェクトの {@link Class}
     * @param <T>  変換するオブジェクトの型
     * @return 変換されたオブジェクト. 変換できなかった場合は {@code null}
     */
    <T> T deserialize(Nbt nbt, Class<T> type);

    /**
     * {@link NbtSerializer} を追加します.
     *
     * @param s 追加する {@link NbtSerializer}
     */
    void addNbtSerializer(NbtSerializer<?> s);
}
