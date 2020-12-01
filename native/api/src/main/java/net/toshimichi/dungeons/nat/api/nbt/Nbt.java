package net.toshimichi.dungeons.nat.api.nbt;

import java.util.List;

public interface Nbt {

    default byte getAsByte() {
        throw new NbtException(getClass().getName() + " cannot be cast to byte");
    }

    default short getAsShort() {
        throw new NbtException(getClass().getName() + " cannot be cast to short");
    }

    default int getAsInt() {
        throw new NbtException(getClass().getName() + " cannot be cast to int");
    }

    default long getAsLong() {
        throw new NbtException(getClass().getName() + " cannot be cast to long");
    }

    default float getAsFloat() {
        throw new NbtException(getClass().getName() + " cannot be cast to float");
    }

    default double getAsDouble() {
        throw new NbtException(getClass().getName() + " cannot be cast to double");
    }

    default byte[] getAsByteArray() {
        throw new NbtException(getClass().getName() + " cannot be cast to byte array");
    }

    default String getAsString() {
        throw new NbtException(getClass().getName() + " cannot be cast to byte String");
    }

    default List<Nbt> getAsList() {
        throw new NbtException(getClass().getName() + " cannot be cast to byte List");
    }

    default NbtCompound getAsCompound() {
        throw new NbtException(getClass().getName() + " cannot be cast to Nbt Compound");
    }

    default int[] getAsIntArray() {
        throw new NbtException(getClass().getName() + " cannot be cast to int array");
    }

    default long[] getAsLongArray() {
        throw new NbtException(getClass().getName() + " cannot be cast to long array");
    }
}
