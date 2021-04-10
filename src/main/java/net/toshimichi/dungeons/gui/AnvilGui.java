package net.toshimichi.dungeons.gui;

import net.toshimichi.dungeons.Dungeons;
import net.toshimichi.dungeons.misc.Stash;
import net.toshimichi.dungeons.utils.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitTask;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;

public class AnvilGui implements Gui, Listener {

    private static final int[] leftIndexes = {12, 21, 30, 29, 28, 19, 10, 11};
    private static final int[] rightIndexes = {14, 15, 16, 25, 34, 33, 32, 23};
    private static final int[] resultIndexes = {22, 21, 30, 39, 40, 41, 32, 23};
    private int counter;
    private BukkitTask updater;
    private Player player;
    private Inventory inventory;

    private boolean updateGui;
    private boolean forceUpdate;
    private AnvilState state;
    private ItemStack left;
    private ItemStack right;
    private ItemStack result;

    @Override
    public String getTitle(Player player) {
        return new LocaleBuilder("anvil.title").player(player).build();
    }

    private String getString(String key, Player player, ChatColor color) {
        LocaleBuilder builder = new LocaleBuilder(key).player(player);
        if (color != null)
            builder.replace("{color}", color.toString());
        return builder.build();
    }

    private void setItems(GuiItem[] items, int[] indexes) {
        for (int i : indexes) {
            ItemStack glass = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
            items[i] = new PlainGuiItem(glass, new NullListener());
        }
    }

    private GuiItemListener newAnvilListener(String key, Supplier<ItemStack> target) {
        return (p, g, i) -> {
            ItemStack get = target.get();
            if (get == null) return;
            if (p.getInventory().addItem(get).size() != 0) {
                p.sendMessage(getString("anvil.full", p, null));
                return;
            }
            Bukkit.getScheduler().runTaskAsynchronously(Dungeons.getInstance().getPlugin(), () -> {
                try {
                    Dungeons.getInstance().getStash().clearStash(p.getUniqueId(), key);
                } catch (IOException e) {
                    e.printStackTrace();
                    Bukkit.getScheduler().runTask(Dungeons.getInstance().getPlugin(), () -> {
                        InventoryUtils.reduce(p.getInventory(), get);
                        p.sendMessage(getString("mysticwell.unknown", p, null));
                    });
                }
                Bukkit.getScheduler().runTask(Dungeons.getInstance().getPlugin(), () -> {
                    p.playSound(p.getLocation(), Sound.ENTITY_ITEM_PICKUP, 1, 0.5F);
                    forceUpdate = true;
                });
            });
        };
    }

    @Override
    public GuiItem[] getItems() {
        GuiItem[] items = new GuiItem[54];
        setItems(items, leftIndexes);
        setItems(items, rightIndexes);
        setItems(items, resultIndexes);

        items[13] = new PlainGuiItem(new ItemStack(Material.AIR), (p, g, i) -> {
            if (left == null || right == null) return;
            if (state != AnvilState.AVAILABLE) return;
            ItemStack result = AnvilUtils.combine(left, right);
            Dungeons.getInstance().getEnchantManager().setLocale(result, Dungeons.getInstance().getLocaleManager().getLocale(p));
            Bukkit.getScheduler().runTaskAsynchronously(Dungeons.getInstance().getPlugin(), () -> {
                try {
                    Dungeons.getInstance().getStash().clearStash(p.getUniqueId(), "anvil_left");
                    Dungeons.getInstance().getStash().clearStash(p.getUniqueId(), "anvil_right");
                    Dungeons.getInstance().getStash().setItemStacks(p.getUniqueId(), "anvil_result", result);
                } catch (IOException e) {
                    e.printStackTrace();
                    Bukkit.getScheduler().runTask(Dungeons.getInstance().getPlugin(), () ->
                            p.sendMessage(getString("anvil.unknown", p, null)));
                }
                Bukkit.getScheduler().runTask(Dungeons.getInstance().getPlugin(), () -> {
                    forceUpdate = true;
                    player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_USE, 1, 1);
                });
            });
        });
        items[20] = new PlainGuiItem(new ItemStack(Material.AIR), newAnvilListener("anvil_left", () -> left));
        items[24] = new PlainGuiItem(new ItemStack(Material.AIR), newAnvilListener("anvil_right", () -> right));
        items[31] = new PlainGuiItem(new ItemStack(Material.AIR), newAnvilListener("anvil_result", () -> result));

        return items;
    }

    @Override
    public void onOpen(Player player, Inventory inventory) {
        this.player = player;
        this.inventory = inventory;
        Bukkit.getPluginManager().registerEvents(this, Dungeons.getInstance().getPlugin());
        updateGui = true;
        updater = Bukkit.getScheduler().runTaskTimerAsynchronously(Dungeons.getInstance().getPlugin(), () -> {
            if (counter % 200 != 0 && !forceUpdate) return;
            forceUpdate = false;
            AnvilState state = AnvilState.getAnvilState(player);
            AtomicReference<ItemStack> left = new AtomicReference<>();
            AtomicReference<ItemStack> right = new AtomicReference<>();
            AtomicReference<ItemStack> result = new AtomicReference<>();
            Stash stash = Dungeons.getInstance().getStash();
            List<ItemStack> leftList = null;
            List<ItemStack> rightList = null;
            List<ItemStack> resultList = null;
            try {
                leftList = stash.getItemStacks(player.getUniqueId(), "anvil_left");
                rightList = stash.getItemStacks(player.getUniqueId(), "anvil_right");
                resultList = stash.getItemStacks(player.getUniqueId(), "anvil_result");
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (leftList != null && !leftList.isEmpty())
                left.set(leftList.get(0));
            if (rightList != null && !rightList.isEmpty())
                right.set(rightList.get(0));
            if (resultList != null && !resultList.isEmpty())
                result.set(resultList.get(0));


            Bukkit.getScheduler().runTask(Dungeons.getInstance().getPlugin(), () -> {
                this.state = state;
                this.left = left.get();
                this.right = right.get();
                this.result = result.get();
                updateGui = true;
            });
        }, 1, 1);
    }

    @Override
    public void onClose() {
        HandlerList.unregisterAll(this);
        if (updater != null)
            updater.cancel();
    }

    private void update(int[] indexes) {
        for (int index : indexes) {
            ItemStack glass = inventory.getItem(index);
            String key;
            if (state == AnvilState.LEFT_NOT_SET)
                key = "anvil.left_not_set";
            else if (state == AnvilState.RIGHT_NOT_SET)
                key = "anvil.right_not_set";
            else
                key = "anvil.set";
            ItemStackUtils.setDisplay(glass, getString(key, player, ChatColor.GRAY));
        }
    }

    private void animate(int[] indexes) {
        int index = (counter / 10) % indexes.length;
        int oldIndex = index - 1;
        if (oldIndex < 0)
            oldIndex = indexes.length - 1;

        ItemStack oldItem = inventory.getItem(indexes[oldIndex]);
        ItemStack item = inventory.getItem(indexes[index]);
        String key;
        boolean set;
        if (state == AnvilState.LEFT_NOT_SET) {
            key = "anvil.left_not_set";
            set = true;
        } else if (state == AnvilState.RIGHT_NOT_SET) {
            key = "anvil.right_not_set";
            set = true;
        } else {
            key = "anvil.set";
            set = false;
        }
        oldItem.setType(Material.GRAY_STAINED_GLASS_PANE);
        ItemStackUtils.setDisplay(oldItem, getString(key, player, ChatColor.GRAY));
        if (set) {
            item.setType(Material.LIGHT_BLUE_STAINED_GLASS_PANE);
            ItemStackUtils.setDisplay(item, getString(key, player, ChatColor.AQUA));
        } else {
            item.setType(Material.BLUE_STAINED_GLASS_PANE);
            ItemStackUtils.setDisplay(item, getString(key, player, ChatColor.BLUE));
        }
    }

    @Override
    public void next(Player player, Gui gui, Inventory inv) {
        counter++;
        if (updateGui) {
            updateGui = false;
            inv.setItem(20, left);
            inv.setItem(24, right);
            inv.setItem(31, result);
            update(leftIndexes);
            update(rightIndexes);
            update(resultIndexes);
            state = AnvilState.getAnvilState(player);
        }
        ItemStack combineButton = new ItemStack(state.getMaterial());
        LocaleBuilder builder = new LocaleBuilder(state.getKey()).player(player);
        builder.replace("{xp}", Integer.toString(15 - player.getLevel()));
        if (left != null && right != null) {
            ItemStack preview = AnvilUtils.combine(left, right);
            String text = ItemStackUtils.getDisplay(preview);
            int length = Arrays.stream(text.split("\n"))
                    .map(CharLengths::getLength)
                    .max(Integer::compareTo).orElse(20);
            StringBuilder space = new StringBuilder();
            for (int i = 0; i < (length + 2) / 4; i++)
                space.append(' ');
            builder.replace("{space}", space.toString());
            builder.replace("{result}", text);
        }
        ItemStackUtils.setDisplay(combineButton, builder.build());
        inv.setItem(13, combineButton);

        animate(leftIndexes);
        animate(resultIndexes);
        animate(rightIndexes);
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if (!player.getInventory().equals(e.getClickedInventory())) return;
        if (e.getCurrentItem() == null) return;
        e.setCancelled(true);
        if (left != null && right != null) {
            player.sendMessage(getString("anvil.already_set", player, null));
            return;
        }
        Bukkit.getScheduler().runTaskAsynchronously(Dungeons.getInstance().getPlugin(), () -> {
            try {
                String target;
                if (left == null) target = "anvil_left";
                else target = "anvil_right";
                Dungeons.getInstance().getStash().setItemStacks(player.getUniqueId(), target, e.getCurrentItem());
            } catch (IOException ex) {
                ex.printStackTrace();
                return;
            }
            Bukkit.getScheduler().runTask(Dungeons.getInstance().getPlugin(), () -> {
                player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 1, 0.5F);
                InventoryUtils.reduce(player.getInventory(), e.getCurrentItem());
                forceUpdate = true;
            });
        });
    }
}

