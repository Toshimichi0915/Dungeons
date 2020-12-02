package net.toshimichi.dungeons.enchants;

import net.toshimichi.dungeons.nat.api.nbt.*;
import net.toshimichi.dungeons.nbt.NbtMapper;
import net.toshimichi.dungeons.nbt.NbtSerializer;

public class EnchantNbtSerializer implements NbtSerializer<Enchant[]> {

    private final EnchantManager manager;

    public EnchantNbtSerializer(EnchantManager manager) {
        this.manager = manager;
    }

    @Override
    public Nbt serialize(Enchant[] obj, NbtMapper mapper) {
        LocalNbtList list = new LocalNbtList();
        for (Enchant e : obj) {
            LocalNbtCompound sub = new LocalNbtCompound();
            sub.put("id", new LocalNbtInt(e.getId()));
            sub.put("level", new LocalNbtInt(e.getLevel()));
            list.add(sub);
        }
        return list;
    }

    @Override
    public Enchant[] deserialize(Nbt nbt, NbtMapper mapper) {
        try {
            NbtList list = nbt.getAsList();
            Enchant[] result = new Enchant[list.size()];
            for (int i = 0; i < result.length; i++) {
                NbtCompound compound = list.get(i).getAsCompound();
                int id = compound.get("id").getAsInt();
                int level = compound.get("level").getAsInt();
                result[i] = manager.getEnchant(id, level);
            }
            return result;
        } catch (NbtException e) {
            return null;
        }
    }

    @Override
    public Class<Enchant[]> getType() {
        return Enchant[].class;
    }
}
