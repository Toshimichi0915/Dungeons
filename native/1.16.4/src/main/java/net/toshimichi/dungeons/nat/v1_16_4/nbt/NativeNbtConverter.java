package net.toshimichi.dungeons.nat.v1_16_4.nbt;

import net.minecraft.server.v1_16_R3.*;
import net.toshimichi.dungeons.nat.api.nbt.*;

import java.util.Map;

public class NativeNbtConverter implements NbtConverter {
    @Override
    public NBTBase toNative(Nbt nbt) {
        if (nbt == null)
            return null;

        if (nbt instanceof NbtCompound) {
            NbtCompound cast = (NbtCompound) nbt;
            NBTTagCompound c = new NBTTagCompound();
            for (Map.Entry<String, Nbt> entry : cast.entrySet()) {
                c.set(entry.getKey(), toNative(entry.getValue()));
            }
            return c;
        } else if (nbt instanceof NbtList) {
            NbtList cast = (NbtList) nbt;
            NBTTagList l = new NBTTagList();
            cast.forEach(a -> l.add(toNative(a)));
            return l;
        }

        if (nbt instanceof NbtDouble)
            return NBTTagDouble.a(nbt.getAsDouble());
        else if (nbt instanceof NbtFloat)
            return NBTTagFloat.a(nbt.getAsFloat());
        else if (nbt instanceof NbtLong)
            return NBTTagLong.a(nbt.getAsLong());
        else if (nbt instanceof NbtInt)
            return NBTTagInt.a(nbt.getAsInt());
        else if (nbt instanceof NbtShort)
            return NBTTagShort.a(nbt.getAsShort());
        else if (nbt instanceof NbtByte)
            return NBTTagByte.a(nbt.getAsByte());
        else if (nbt instanceof NbtString)
            return NBTTagString.a(nbt.getAsString());
        else if (nbt instanceof NbtIntArray)
            return new NBTTagIntArray(nbt.getAsIntArray());
        else if (nbt instanceof NbtByteArray)
            return new NBTTagByteArray(nbt.getAsByteArray());
        else if (nbt instanceof NbtEnd)
            return NBTTagEnd.b;
        else
            throw new NbtException("Unknown NBT Tag: " + nbt.getClass().getName());
    }

    @Override
    public Nbt fromNative(NBTBase base) {
        if (base == null)
            return null;
        else if (base instanceof NBTTagCompound)
            return new NativeNbtCompound(this, (NBTTagCompound) base);
        else if (base instanceof NBTTagList)
            return new NativeNbtList(this, (NBTTagList) base);
        else if (base instanceof NBTTagDouble)
            return new NativeNbtDouble((NBTTagDouble) base);
        else if (base instanceof NBTTagFloat)
            return new NativeNbtFloat((NBTTagFloat) base);
        else if (base instanceof NBTTagLong)
            return new NativeNbtLong((NBTTagLong) base);
        else if (base instanceof NBTTagInt)
            return new NativeNbtInt((NBTTagInt) base);
        else if (base instanceof NBTTagShort)
            return new NativeNbtShort((NBTTagShort) base);
        else if (base instanceof NBTTagByte)
            return new NativeNbtByte((NBTTagByte) base);
        else if (base instanceof NBTTagString)
            return new NativeNbtString((NBTTagString) base);
        else if (base instanceof NBTTagIntArray)
            return new NativeNbtIntArray((NBTTagIntArray) base);
        else if (base instanceof NBTTagByteArray)
            return new NativeNbtByteArray((NBTTagByteArray) base);
        else if (base instanceof NBTTagEnd)
            return new LocalNbtEnd();
        else
            throw new NbtException("Unknown NBT Tag: " + base.getClass().getName());
    }
}
