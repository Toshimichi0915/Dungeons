package net.toshimichi.dungeons.enchants;

import net.md_5.bungee.api.ChatColor;
import net.toshimichi.dungeons.DungeonsPlugin;
import net.toshimichi.dungeons.lang.Locale;
import net.toshimichi.dungeons.utils.InventoryUtils;
import net.toshimichi.dungeons.utils.Nonce;
import net.toshimichi.dungeons.utils.RomanNumber;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.*;

public class NbtEnchantManager implements EnchantManager {

    private final EnchantDataType enchantDataType;
    private final Set<Enchant> enchants = new HashSet<>();
    private final WeakHashMap<Player, Set<Enchanter>> enchanters = new WeakHashMap<>();

    /**
     * エンチャントの一覧を指定してインスタンスを作成します.
     *
     * @param arr エンチャントの一覧
     */
    public NbtEnchantManager(Enchant... arr) {
        enchantDataType = new EnchantDataType(arr);
        enchants.addAll(Arrays.asList(arr));
    }

    private void addEnchanter(Player player, Enchanter enchanter) {
        Set<Enchanter> set = getEnchanters(player);
        set.add(enchanter);
        enchanters.put(player, set);
    }

    private void removeEnchants(Player player, ItemStack itemStack) {
        Set<Enchanter> set = enchanters.get(player);
        if (set == null) return;
        Iterator<Enchanter> iterator = set.iterator();
        while (iterator.hasNext()) {
            Enchanter enchanter = iterator.next();
            if (!enchanter.getItemStack().equals(itemStack))
                continue;
            enchanter.disable();
            iterator.remove();
        }
    }

    private void applyEnchants(Player player, ItemStack itemStack) {
        getEnchants(itemStack)
                .stream()
                .map(a -> a.getEnchanter(player, itemStack))
                .filter(Objects::nonNull)
                .filter(Enchanter::isAvailable)
                .forEach(a -> {
                    a.enable();
                    addEnchanter(player, a);
                });
    }

    @Override
    public void refresh(Player player) {
        ItemStack[] checkList = InventoryUtils.getPrimaryItemStacks(player);
        Set<ItemStack> removed = new HashSet<>();
        getEnchanters(player).stream()
                .map(Enchanter::getItemStack)
                .forEach(removed::add);
        Set<ItemStack> added = new HashSet<>(Arrays.asList(checkList));
        added.removeAll(removed);
        Arrays.stream(checkList)
                .forEach(removed::remove);
        removed.forEach(a -> removeEnchants(player, a));
        added.forEach(a -> applyEnchants(player, a));
    }

    @Override
    public void disable(Player player) {
        Set<Enchanter> set = enchanters.get(player);
        if (set == null) return;
        for (Enchanter enchanter : set)
            enchanter.disable();
        enchanters.remove(player);
    }

    @Override
    public void tick() {
        for (Set<Enchanter> set : enchanters.values()) {
            set.forEach(Enchanter::tick);
        }
    }

    @Override
    public Set<Enchant> getEnchants(ItemStack itemStack) {
        if (itemStack == null) return new HashSet<>();
        ItemMeta meta = itemStack.getItemMeta();
        if (meta == null) return new HashSet<>();
        PersistentDataContainer container = meta.getPersistentDataContainer();
        if (!container.has(new NamespacedKey(DungeonsPlugin.getPlugin(), "enchants"), enchantDataType))
            return new HashSet<>();
        Enchant[] enchants = container.get(new NamespacedKey(DungeonsPlugin.getPlugin(), "enchants"), enchantDataType);
        return new HashSet<>(Arrays.asList(enchants));
    }

    @Override
    public void refresh(ItemStack itemStack) {
        if (itemStack == null) return;
        ItemMeta meta = itemStack.getItemMeta();
        if (meta == null) return;
        if (!Nonce.hasNonce(itemStack)) return;

        String tierDisplayed;
        ChatColor color;
        int tier = getTier(itemStack);
        if (tier == 0) {
            tierDisplayed = "Fresh";
            color = ChatColor.YELLOW;
        } else if (tier < 0) {
            tierDisplayed = "Mysterious";
            color = ChatColor.AQUA;
        } else {
            tierDisplayed = "Tier " + RomanNumber.convert(tier);
            if (tier == 1)
                color = ChatColor.GREEN;
            else if (tier == 2)
                color = ChatColor.YELLOW;
            else if (tier == 3)
                color = ChatColor.RED;
            else
                color = ChatColor.LIGHT_PURPLE;
        }

        int lives = getLives(itemStack);
        int maxLives = getMaxLives(itemStack);
        int token = getEnchants(itemStack).stream()
                .map(Enchant::getLevel)
                .reduce(Integer::sum).orElse(0);
        long rares = getEnchants(itemStack).stream()
                .filter(p -> p.getTitle() == Title.RARE || p.getTitle() == Title.UNIQUE)
                .count();
        if (rares >= 3) {
            tierDisplayed = "Unthinkable " + tierDisplayed;
        } else if (rares >= 2) {
            if (lives >= 100)
                tierDisplayed = "Overpowered " + tierDisplayed;
            else
                tierDisplayed = "Extraordinary " + tierDisplayed;
        } else if (token >= 8) {
            tierDisplayed = "Legendary " + tierDisplayed;
        } else if (maxLives >= 100) {
            tierDisplayed = "Durable " + tierDisplayed;
        }

        String category = EnchantType.getEnchantType(itemStack).getPrefix();

        ArrayList<String> lore = new ArrayList<>();
        String livesDisplayed;
        if (lives < 0 || getMaxLives(itemStack) < 0)
            livesDisplayed = null;
        else if (lives < 4)
            livesDisplayed = ChatColor.GRAY + "Lives: " + ChatColor.RED + lives + ChatColor.GRAY + "/" + getMaxLives(itemStack);
        else if (lives < 10)
            livesDisplayed = ChatColor.GRAY + "Lives: " + ChatColor.YELLOW + lives + ChatColor.GRAY + "/" + getMaxLives(itemStack);
        else
            livesDisplayed = ChatColor.GRAY + "Lives: " + ChatColor.GREEN + lives + ChatColor.GRAY + "/" + getMaxLives(itemStack);

        if (livesDisplayed != null)
            lore.add(livesDisplayed);
        for (Enchant e : getEnchants(itemStack)) {
            lore.add("");
            lore.add("" + ChatColor.BLUE + e.getName());
            lore.add(ChatColor.GRAY + e.getDescription(getLocale(itemStack)));
        }

        meta.setDisplayName(color + tierDisplayed + " " + category);
        meta.setLore(lore);
        Arrays.stream(Enchantment.values()).forEach(meta::removeEnchant);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addEnchant(Enchantment.MENDING, 1, true);
        for (Enchant e : getEnchants(itemStack)) {
            e.getEnchantments().entrySet().stream()
                    .filter(entry -> entry.getValue() > meta.getEnchantLevel(entry.getKey()))
                    .forEach(entry -> meta.addEnchant(entry.getKey(), entry.getValue(), true));
        }
        itemStack.setItemMeta(meta);
    }

    @Override
    public void setEnchants(ItemStack itemStack, Enchant... enchants) {
        Nonce.newNonce(itemStack);
        ItemMeta meta = itemStack.getItemMeta();
        if (meta == null) throw new IllegalArgumentException("Cannot get ItemMeta of " + itemStack);
        PersistentDataContainer container = meta.getPersistentDataContainer();
        container.set(new NamespacedKey(DungeonsPlugin.getPlugin(), "enchants"), enchantDataType, enchants);
        itemStack.setItemMeta(meta);

        refresh(itemStack);
    }

    private int getIntData(ItemStack itemStack, String label) {
        if (itemStack == null) return -1;
        if (!itemStack.hasItemMeta()) return -1;
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta == null) return -1;
        PersistentDataContainer container = itemMeta.getPersistentDataContainer();
        Integer tier = container.get(new NamespacedKey(DungeonsPlugin.getPlugin(), label), PersistentDataType.INTEGER);
        if (tier == null) return -1;
        return tier;
    }

    private void setIntData(ItemStack itemStack, String label, int data) {
        if (itemStack == null) return;
        if (!itemStack.hasItemMeta()) return;
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta == null) return;
        PersistentDataContainer container = itemMeta.getPersistentDataContainer();
        container.set(new NamespacedKey(DungeonsPlugin.getPlugin(), label), PersistentDataType.INTEGER, data);
        itemStack.setItemMeta(itemMeta);
    }

    @Override
    public int getTier(ItemStack itemStack) {
        return getIntData(itemStack, "tier");
    }

    @Override
    public void setTier(ItemStack itemStack, int tier) {
        Nonce.newNonce(itemStack);
        setIntData(itemStack, "tier", tier);
        refresh(itemStack);
    }

    @Override
    public int getLives(ItemStack itemStack) {
        return getIntData(itemStack, "lives");
    }

    @Override
    public void setLives(ItemStack itemStack, int lives) {
        Nonce.newNonce(itemStack);
        setIntData(itemStack, "lives", lives);
        refresh(itemStack);
    }

    @Override
    public int getMaxLives(ItemStack itemStack) {
        return getIntData(itemStack, "maxLives");
    }

    @Override
    public void setMaxLives(ItemStack itemStack, int lives) {
        Nonce.newNonce(itemStack);
        setIntData(itemStack, "maxLives", lives);
        refresh(itemStack);
    }

    @Override
    public Locale getLocale(ItemStack itemStack) {
        if (itemStack == null) return null;
        ItemMeta meta = itemStack.getItemMeta();
        if (meta == null) return null;
        PersistentDataContainer container = meta.getPersistentDataContainer();
        String str = container.get(new NamespacedKey(DungeonsPlugin.getPlugin(), "locale"), PersistentDataType.STRING);
        if (str == null) return null;
        return DungeonsPlugin.getLocales().stream().filter(p -> p.get("general.lang.name").equals(str)).findAny().orElse(null);
    }

    @Override
    public void setLocale(ItemStack itemStack, Locale locale) {
        ItemMeta meta = itemStack.getItemMeta();
        if (meta == null) return;
        if (locale != null && locale.get("general.lang.name") != null)
            meta.getPersistentDataContainer().set(new NamespacedKey(DungeonsPlugin.getPlugin(), "locale"), PersistentDataType.STRING, locale.get("general.lang.name"));
        else
            meta.getPersistentDataContainer().remove(new NamespacedKey(DungeonsPlugin.getPlugin(), "locale"));
        itemStack.setItemMeta(meta);

        refresh(itemStack);
    }

    @Override
    public Set<Enchanter> getEnchanters(Player player) {
        Set<Enchanter> set = enchanters.get(player);
        if (set != null) return new HashSet<>(set);
        return new HashSet<>();
    }

    @Override
    public Set<Enchant> getAllEnchants() {
        return new HashSet<>(enchants);
    }

    @Override
    public Enchant getEnchant(int id, int level) {
        return getAllEnchants().stream()
                .filter(p -> p.getId() == id)
                .filter(p -> p.getLevel() == level)
                .findAny().orElse(null);
    }

}
