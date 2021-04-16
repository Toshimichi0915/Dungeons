package net.toshimichi.dungeons.commands;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Arguments {

    private final String[] args;

    public Arguments(String[] args) {
        this.args = args;
    }

    private void checkLength(int index, String what) {
        if (args.length <= index)
            throw new CommandException((index + 1) + "番目の引数に" + what + "が指定されていません");
    }

    public int length() {
        return args.length;
    }

    public String[] getRaw() {
        return args;
    }

    public Player getPlayer(int index, String what) {
        checkLength(index, what);
        Player player = Bukkit.getPlayerExact(args[index]);
        if (player == null)
            throw new CommandException(args[index] + "という名前のプレイヤーはオフラインです");
        return player;
    }

    public byte getByte(int index, String what) {
        checkLength(index, what);
        try {
            return Byte.parseByte(args[index]);
        } catch (NumberFormatException e) {
            throw new CommandException(args[index] + "は有効な数値(-128~127)ではありません");
        }
    }

    public short getShort(int index, String what) {
        checkLength(index, what);
        try {
            return Short.parseShort(args[index]);
        } catch (NumberFormatException e) {
            throw new CommandException(args[index] + "は有効な数値(-32768~32767)ではありません");
        }
    }

    public int getInt(int index, String what) {
        checkLength(index, what);
        try {
            return Integer.parseInt(args[index]);
        } catch (NumberFormatException e) {
            throw new CommandException(args[index] + "は有効な数値ではありません");
        }
    }

    public long getLong(int index, String what) {
        checkLength(index, what);
        try {
            return Long.parseLong(args[index]);
        } catch (NumberFormatException e) {
            throw new CommandException(args[index] + "は有効な数値ではありません");
        }
    }

    public float getFloat(int index, String what) {
        checkLength(index, what);
        try {
            return Float.parseFloat(args[index]);
        } catch (NumberFormatException e) {
            throw new CommandException(args[index] + "は有効な小数ではありません");
        }
    }

    public double getDouble(int index, String what) {
        checkLength(index, what);
        try {
            return Double.parseDouble(args[index]);
        } catch (NumberFormatException e) {
            throw new CommandException(args[index] + "は有効な小数ではありません");
        }
    }

    public Player getPlayer(int index) {
        return getPlayer(index, "プレイヤー");
    }

    public byte getByte(int index) {
        return getByte(index, "整数");
    }

    public short getShort(int index) {
        return getShort(index, "整数");
    }

    public int getInt(int index) {
        return getInt(index, "整数");
    }

    public long getLong(int index) {
        return getLong(index, "整数");
    }

    public float getFloat(int index) {
        return getFloat(index, "小数");
    }

    public double getDouble(int index) {
        return getDouble(index, "小数");
    }

    public String getString(int index, String what) {
        checkLength(index, what);
        return args[index];
    }
}
