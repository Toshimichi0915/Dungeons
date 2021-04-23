package net.toshimichi.dungeons.enchants.wand.world;

import net.toshimichi.dungeons.enchants.Title;
import net.toshimichi.dungeons.lang.Locale;
import net.toshimichi.dungeons.utils.LocaleBuilder;
import org.bukkit.inventory.ItemStack;

public class Upstairs0Enchant extends RoomEnchant {
    @Override
    public int getId() {
        return 33;
    }

    @Override
    public String getName() {
        return "Tower Dungeon: Upstairs 0";
    }

    @Override
    public int getLevel() {
        return 1;
    }

    @Override
    public int getRarity() {
        return 0;
    }

    @Override
    public String getDescription(ItemStack itemStack, Locale locale) {
        return new LocaleBuilder("enchant.upstairs_0").locale(locale).build();
    }

    @Override
    public Title getTitle() {
        return Title.COMMON;
    }

    @Override
    public String getRoomFactoryId() {
        return "upstairs_0";
    }
}
