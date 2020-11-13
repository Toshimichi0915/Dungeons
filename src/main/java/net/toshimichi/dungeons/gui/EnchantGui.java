package net.toshimichi.dungeons.gui;

import net.toshimichi.dungeons.DungeonsPlugin;
import net.toshimichi.dungeons.misc.Stash;
import net.toshimichi.dungeons.utils.*;
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

import java.io.IOException;
import java.util.List;

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
        if (well.isEmpty())
            return null;
        return well.get(0);
    }

    private void setMysticWell(Player player, ItemStack itemStack) throws IOException {
        if (itemStack != null)
            DungeonsPlugin.getStash().setItemStacks(player.getUniqueId(), "mystic_well", itemStack);
        else
            DungeonsPlugin.getStash().clearStash(player.getUniqueId(), "mystic_well");
    }

    private String getString(String key, Player player, ChatColor color) {
        LocaleBuilder builder = new LocaleBuilder(key).player(player);
        if (color != null)
            builder.replace("{color}", color.toString());
        return builder.build();
    }

    @Override
    public String getTitle(Player player) {
        return getString("mysticwell.title", player, null);
    }

    @Override
    public GuiItem[] getItems() {
        GuiItem[] items = new GuiItem[45];
        for (int i : indexes) {
            ItemStack glass = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
            ItemStackUtils.setDisplay(glass, getString("mysticwell.not_set", player, ChatColor.GRAY));
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
                p.sendMessage(getString("mysticwell.full", p, null));
            } catch (IOException e) {
                e.printStackTrace();
                p.sendMessage(getString("mysticwell.unknown", p, null));
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
                p.sendMessage(getString("mysticwell.unknown", p, null));
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
            state = EnchantState.getEnchantState(player);
            ItemStack itemStack;
            try {
                itemStack = getMysticWell(player);
            } catch (IOException e) {
                e.printStackTrace();
                itemStack = new ItemStack(Material.BEDROCK);
                ItemStackUtils.setDisplay(itemStack, getString("mysticwell.unknown", player, null));
            }
            inv.setItem(20, itemStack);

            for (int index : indexes) {
                ItemStack glass = inv.getItem(index);
                String display;
                if (state == EnchantState.NOT_SET)
                    display = getString("mysticwell.not_set", player, ChatColor.GRAY);
                else
                    display = getString("mysticwell.set", player, ChatColor.GRAY);
                ItemStackUtils.setDisplay(glass, display);
                inv.setItem(index, glass);
            }

            ItemStack button = new ItemStack(state.getMaterial());
            int cost = EnchantUtils.getCost(itemStack);
            int money = DungeonsPlugin.getEconomy().getMoney(player.getUniqueId());
            int tier = DungeonsPlugin.getEnchantManager().getTier(itemStack);
            ChatColor chatColor;
            if (tier == 0) chatColor = ChatColor.GREEN;
            else if (tier == 1) chatColor = ChatColor.YELLOW;
            else if (tier == 2) chatColor = ChatColor.RED;
            else chatColor = ChatColor.LIGHT_PURPLE;
            LocaleBuilder builder = new LocaleBuilder(state.getKey()).player(player)
                    .replace("{cost}", Integer.toString(cost))
                    .replace("{gold}", Integer.toString(cost - money))
                    .replace("{color}", chatColor.toString());
            if (tier >= 0)
                builder.replace("{tier}", chatColor + RomanNumber.convert(tier + 1));
            ItemStackUtils.setDisplay(button, builder.build());
            inv.setItem(25, button);
            updateGui = false;
        }

        if (counter % 10 != 0) return;
        int index = (counter / 10) % indexes.length;
        int oldIndex = index - 1;
        if (oldIndex < 0)
            oldIndex = indexes.length - 1;

        ItemStack oldItem = inv.getItem(indexes[oldIndex]);
        ItemStack item = inv.getItem(indexes[index]);
        oldItem.setType(Material.GRAY_STAINED_GLASS_PANE);
        if (state != EnchantState.NOT_SET) {
            ItemStackUtils.setDisplay(oldItem, getString("mysticwell.set", player, ChatColor.GRAY));
            ItemStackUtils.setDisplay(item, getString("mysticwell.set", player, ChatColor.LIGHT_PURPLE));
            item.setType(Material.RED_STAINED_GLASS_PANE);
        } else {
            ItemStackUtils.setDisplay(oldItem, getString("mysticwell.not_set", player, ChatColor.GRAY));
            ItemStackUtils.setDisplay(item, getString("mysticwell.not_set", player, ChatColor.RED));
            item.setType(Material.PINK_STAINED_GLASS_PANE);
        }

    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if (!player.getInventory().equals(e.getClickedInventory())) return;
        if (e.getCurrentItem() == null) return;
        e.setCancelled(true);
        try {
            if (getMysticWell(player) != null) {
                player.sendMessage(getString("mysticwell.already_set", player, null));
                return;
            }
            setMysticWell(player, e.getCurrentItem());
            updateGui = true;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        InventoryUtils.reduce(player.getInventory(), e.getCurrentItem());
    }
}
