package net.toshimichi.dungeons.nat.api.nbt;

/**
 * 単一のNBTを表します.
 * <p>
 * このクラスを直接継承することができるクラスは {@link NbtByte}, {@link NbtShort}, {@link NbtInt}, {@link NbtLong},
 * {@link NbtFloat}, {@link NbtDouble}, {@link NbtByteArray}, {@link NbtString}, {@link NbtList}, {@link NbtCompound}
 * {@link NbtIntArray}, {@link NbtEnd} に限られます.
 * </p>
 * <p>
 * このクラスにはNBTを値に直接変換することができますが, 情報の損失が起こらない場合はアッパーキャストをサポートしなければなりません.
 * 例えば {@link #getAsByte()} が使用可能な場合は {@link #getAsShort()}, {@link #getAsInt()}, {@link #getAsLong()}
 * もサポートされなければならないということです. ダウンキャストについては保証されません.
 * </p>
 */
public interface Nbt {

    /**
     * NBTを {@code byte} に変換して返します.
     *
     * @return 変換された値
     * @throws NbtException 変換できなかった場合
     */
    default byte getAsByte() {
        throw new NbtException(getClass().getName() + " cannot be cast to byte");
    }

    /**
     * NBTを {@code short} に変換して返します.
     *
     * @return 変換された値
     * @throws NbtException 変換できなかった場合
     */
    default short getAsShort() {
        throw new NbtException(getClass().getName() + " cannot be cast to short");
    }

    /**
     * NBTを {@code int} に変換して返します.
     *
     * @return 変換された値
     * @throws NbtException 変換できなかった場合
     */
    default int getAsInt() {
        throw new NbtException(getClass().getName() + " cannot be cast to int");
    }

    /**
     * NBTを {@code long} に変換して返します.
     *
     * @return 変換された値
     * @throws NbtException 変換できなかった場合
     */
    default long getAsLong() {
        throw new NbtException(getClass().getName() + " cannot be cast to long");
    }

    /**
     * NBTを {@code float} に変換して返します.
     *
     * @return 変換された値
     * @throws NbtException 変換できなかった場合
     */
    default float getAsFloat() {
        throw new NbtException(getClass().getName() + " cannot be cast to float");
    }

    /**
     * NBTを {@code double} に変換して返します.
     *
     * @return 変換された値
     * @throws NbtException 変換できなかった場合
     */
    default double getAsDouble() {
        throw new NbtException(getClass().getName() + " cannot be cast to double");
    }

    /**
     * NBTを {@code byte[]} に変換して返します.
     *
     * @return 変換された値
     * @throws NbtException 変換できなかった場合
     */
    default byte[] getAsByteArray() {
        throw new NbtException(getClass().getName() + " cannot be cast to byte array");
    }

    /**
     * NBTを {@link String} に変換して返します.
     *
     * @return 変換された値
     * @throws NbtException 変換できなかった場合
     */
    default String getAsString() {
        throw new NbtException(getClass().getName() + " cannot be cast to byte String");
    }

    /**
     * NBTを {@link NbtList} に変換して返します.
     *
     * @return 変換された {@link NbtList}
     * @throws NbtException 変換できなかった場合
     */
    default NbtList getAsList() {
        throw new NbtException(getClass().getName() + " cannot be cast to byte List");
    }

    /**
     * NBTを {@link NbtCompound} に変換して返します.
     *
     * @return 変換された {@link NbtCompound}
     * @throws NbtException 変換できなかった場合
     */
    default NbtCompound getAsCompound() {
        throw new NbtException(getClass().getName() + " cannot be cast to Nbt Compound");
    }

    /**
     * NBTを {@code int[]} に変換して返します.
     *
     * @return 変換された値
     * @throws NbtException 変換できなかった場合
     */
    default int[] getAsIntArray() {
        throw new NbtException(getClass().getName() + " cannot be cast to int array");
    }

}
