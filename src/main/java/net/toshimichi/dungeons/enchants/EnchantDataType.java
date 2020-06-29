package net.toshimichi.dungeons.enchants;

import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataType;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class EnchantDataType implements PersistentDataType<byte[], Enchant[]> {

    private final Enchant[] enchants;

    public EnchantDataType(Enchant... enchants) {
        this.enchants = enchants;
    }

    @Override
    public Class<byte[]> getPrimitiveType() {
        return byte[].class;
    }

    @Override
    public Class<Enchant[]> getComplexType() {
        return Enchant[].class;
    }

    @Override
    public byte[] toPrimitive(Enchant[] enchants, PersistentDataAdapterContext persistentDataAdapterContext) {
        ByteBuffer buffer = ByteBuffer.allocate(enchants.length * 8);
        for (Enchant enchant : enchants) {
            buffer.putInt(enchant.getId());
            buffer.putInt(enchant.getLevel());
        }
        return buffer.array();
    }

    @Override
    public Enchant[] fromPrimitive(byte[] bytes, PersistentDataAdapterContext persistentDataAdapterContext) {
        Enchant[] result = new Enchant[bytes.length / 8];
        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        int i = 0;
        while(i < result.length) {
            int id = buffer.getInt();
            int level = buffer.getInt();
            result[i++] = Arrays.stream(enchants)
                    .filter(p->p.getId() == id && p.getLevel() == level)
                    .findAny()
                    .orElse(null);
        }
        HashSet<Enchant> set = new HashSet<>();
        Arrays.stream(result).filter(Objects::nonNull).forEach(set::add);
        return set.toArray(new Enchant[0]);
    }
}
