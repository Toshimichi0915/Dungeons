package net.toshimichi.dungeons.nbt;

import net.toshimichi.dungeons.nat.api.nbt.Nbt;

/**
 * NBTとオブジェクトの相互変換を行うクラスです.
 * @param <T> 相互変換の対象
 */
public interface NbtSerializer<T> {

    /**
     * オブジェクトをNBTに変換します.
     * @param obj 変換するオブジェクト
     * @param mapper 使用する {@link NbtMapper} もしくは {@code null}
     * @return 変換されたNBT
     */
    Nbt serialize(T obj, NbtMapper mapper);

    /**
     * NBTをオブジェクトに変換します
     * @param nbt 変換するNBT
     * @param mapper 使用する {@link NbtMapper} もしくは {@code null}
     * @return 変換されたオブジェクト
     */
    T deserialize(Nbt nbt, NbtMapper mapper);

    /**
     * 変換するオブジェクトの {@link Class} を返します.
     * @return 変換するオブジェクトの {@link Class}
     */
    Class<T> getType();
}
