package net.toshimichi.dungeons.gui;

import net.toshimichi.dungeons.DungeonsPlugin;
import net.toshimichi.dungeons.misc.Stash;
import net.toshimichi.dungeons.utils.EnchantUtils;
import net.toshimichi.dungeons.utils.InventoryUtils;
import net.toshimichi.dungeons.utils.LocaleBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class EnchantGui implements Gui, Listener {

    private static final int[] indexes = {10, 11, 12, 21, 30, 29, 28, 19};
    private int counter;
    private boolean updateGui;
    private EnchantState state;
    private Player player;
    private Inventory inventory;

    private ItemStack getMysticWell(Player player) throws IOException {
        Stash stash = DungeonsPlugin.getStash();
        List<ItemStack> well = stash.getItemStacks(player.getUniqueId(), "mystic_well");
        if (well.size() < 1)
            return null;
        return well.get(0);
    }

    private void setMysticWell(Player player, ItemStack itemStack) throws IOException {
        if (itemStack != null)
            DungeonsPlugin.getStash().setItemStacks(player.getUniqueId(), "mystic_well", itemStack);
        else
            DungeonsPlugin.getStash().clearStash(player.getUniqueId(), "mystic_well");
    }

    private void updateEnchantState(Player player) {
        ItemStack mysticWell;
        try {
            mysticWell = getMysticWell(player);
        } catch (IOException e) {
            e.printStackTrace();
            state = EnchantState.ERROR;
            return;
        }
        if (mysticWell == null) {
            state = EnchantState.NOT_SET;
            return;
        }
        int tier = DungeonsPlugin.getEnchantManager().getTier(mysticWell);
        if (tier < 0) {
            state = EnchantState.INVALID;
            return;
        } else if (tier == 3) {
            state = EnchantState.MAXED_OUT;
            return;
        }
        if (EnchantUtils.getCost(mysticWell) > DungeonsPlugin.getEconomy().getMoney(player.getUniqueId())) {
            state = EnchantState.NO_GOLD;
            return;
        }

        state = EnchantState.AVAILABLE;
    }

    private void setDisplayColor(ItemStack itemStack, ChatColor color) {
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(color + ChatColor.stripColor(meta.getDisplayName()));
        List<String> lore = meta.getLore();
        if (lore != null)
            meta.setLore(lore.stream().map(m -> color + ChatColor.stripColor(m)).collect(Collectors.toList()));
        itemStack.setItemMeta(meta);
    }

    @Override
    public String getTitle() {
        return "Mystic Well";
    }

    @Override
    public GuiItem[] getItems() {
        GuiItem[] items = new GuiItem[45];
        for (int i : indexes) {
            ItemStack glass = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
            ItemMeta glassMeta = glass.getItemMeta();
            String title = ChatColor.GRAY + new LocaleBuilder("mysticwell.click1").player(player).build();
            glassMeta.setDisplayName(title);
            glass.setItemMeta(glassMeta);
            items[i] = new PlainGuiItem(glass, new NullListener());
        }

        items[20] = new PlainGuiItem(new ItemStack(Material.AIR), (p, g, i) -> {
            try {
                ItemStack itemStack = getMysticWell(p);
                if (itemStack == null) return;
                if (p.getInventory().addItem(itemStack).size() == 0) {
                    setMysticWell(p, null);
                    updateGui = true;
                    return;
                }
                String message = new LocaleBuilder("mysticwell.inventory_full").player(p).build();
                p.sendMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
                String message = new LocaleBuilder("mysticwell.unknown").player(p).build();
                p.sendMessage(message);
            }
        });

        items[25] = new PlainGuiItem(new ItemStack(Material.AIR), (p, g, i) -> {
            try {
                ItemStack itemStack = getMysticWell(p);
                if (itemStack == null) return;
                if (state != EnchantState.AVAILABLE) return;
                DungeonsPlugin.getEconomy().withdraw(p.getUniqueId(), EnchantUtils.getCost(itemStack));
                EnchantUtils.enchant(itemStack);
                updateGui = true;
            } catch (IOException e) {
                e.printStackTrace();
                String message = new LocaleBuilder("mysticwell.unknown").player(p).build();
                p.sendMessage(message);
            }
        });
        return items;

    }

    @Override
    public void onOpen(Player player, Inventory inventory) {
        this.player = player;
        this.inventory = inventory;
        Bukkit.getPluginManager().registerEvents(this, DungeonsPlugin.getPlugin());
        updateGui = true;
    }

    @Override
    public void onClose() {
        HandlerList.unregisterAll(this);
    }

    @Override
    public void next(Player player, Gui gui, Inventory inv) {
        counter++;
        if (updateGui) {
            updateEnchantState(player);
            ItemStack itemStack;
            try {
                itemStack = getMysticWell(player);
            } catch (IOException e) {
                e.printStackTrace();
                itemStack = new ItemStack(Material.BEDROCK);
                ItemMeta meta = itemStack.getItemMeta();
                String display = new LocaleBuilder("mysticwell.unknown").player(player).build();
                meta.setDisplayName(display);
                itemStack.setItemMeta(meta);
            }
            inv.setItem(20, itemStack);

            for (int index : indexes) {
                ItemStack glass = inv.getItem(index);
                ItemMeta glassMeta = glass.getItemMeta();
                String title;
                ArrayList<String> lore = new ArrayList<>();
                if (state == EnchantState.NOT_SET) {
                    title = ChatColor.GRAY + new LocaleBuilder("mysticwell.click1").player(player).build();
                } else {
                    title = ChatColor.GRAY + new LocaleBuilder("mysticwell.click2").player(player).build();
                    lore.add(ChatColor.GRAY + new LocaleBuilder("mysticwell.getback").player(player).build());
                }
                glassMeta.setDisplayName(title);
                glassMeta.setLore(lore);
                glass.setItemMeta(glassMeta);
                inv.setItem(index, glass);
            }

            ItemStack button;
            if (state == EnchantState.AVAILABLE)
                button = new ItemStack(Material.ENCHANTING_TABLE);
            else
                button = new ItemStack(Material.RED_CONCRETE);
            inv.setItem(25, button);
            updateGui = false;
        }

        if (counter % 10 != 0) return;
        int index = (counter / 10) % indexes.length;
        int oldIndex = index - 1;
        if (oldIndex < 0)
            oldIndex = indexes.length - 1;

        setDisplayColor(inv.getItem(indexes[oldIndex]), ChatColor.GRAY);
        inv.getItem(indexes[oldIndex]).setType(Material.GRAY_STAINED_GLASS_PANE);
        if (state != EnchantState.NOT_SET) {
            setDisplayColor(inv.getItem(indexes[index]), ChatColor.RED);
            inv.getItem(indexes[index]).setType(Material.RED_STAINED_GLASS_PANE);
        } else {
            setDisplayColor(inv.getItem(indexes[index]), ChatColor.LIGHT_PURPLE);
            inv.getItem(indexes[index]).setType(Material.PINK_STAINED_GLASS_PANE);
        }

    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if (!player.getInventory().equals(e.getClickedInventory())) return;
        if (e.getCurrentItem() == null) return;
        e.setCancelled(true);
        try {
            if (getMysticWell(player) != null) {
                String message = new LocaleBuilder("mysticwell.already_set").player(player).build();
                player.sendMessage(message);
                return;
            }
            setMysticWell(player, e.getCurrentItem());
            updateGui = true;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        InventoryUtils.reduce(player.getInventory(), e.getCurrentItem());
    }

    private enum EnchantState {
        AVAILABLE, INVALID, ERROR, MAXED_OUT, NO_GOLD, NOT_SET
    }
}
