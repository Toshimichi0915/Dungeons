package net.toshimichi.dungeons.utils;

import java.util.Arrays;
import java.util.HashMap;

/**
 * 文字列の表示上の長さを調べます.
 */
public enum CharLengths {
    SPACE(' ', 3),
    SURPRISE('!', 1),
    DOUBLE_QUOTE('"', 3),
    SHARP('#', 5),
    DOLLAR('$', 5),
    PERCENT('%', 5),
    AND('&', 59),
    BRACKET_BEGIN('(', 3),
    BRACKET_END(')', 3),
    ASTERISK('*', 3),
    PLUS('+', 5),
    COMMA(',', 1),
    HYPHEN('-', 5),
    DOT('.', 1),
    SLASH('/', 5),
    ZERO('0', 5),
    ONE('1', 5),
    TWO('2', 5),
    THREE('3', 5),
    FOUR('4', 5),
    FIVE('5', 5),
    SIX('6', 5),
    SEVEN('7', 5),
    EIGHT('8', 5),
    NINE('9', 5),
    COLON(':', 1),
    SEMICOLON(';', 1),
    LESS_THAN('<', 4),
    EQUAL('=', 5),
    GREATER_THAN('>', 5),
    QUESTION('?', 5),
    AT('@', 6),
    LARGE_A('A', 5),
    LARGE_B('B', 5),
    LARGE_C('C', 5),
    LARGE_D('D', 5),
    LARGE_E('E', 5),
    LARGE_F('F', 5),
    LARGE_G('G', 5),
    LARGE_H('H', 5),
    LARGE_I('I', 3),
    LARGE_J('J', 5),
    LARGE_K('K', 5),
    LARGE_L('L', 6),
    LARGE_M('M', 5),
    LARGE_N('N', 5),
    LARGE_O('O', 5),
    LARGE_P('P', 5),
    LARGE_Q('Q', 5),
    LARGE_R('R', 5),
    LARGE_S('S', 5),
    LARGE_T('T', 5),
    LARGE_U('U', 5),
    LARGE_V('V', 5),
    LARGE_W('W', 5),
    LARGE_X('X', 5),
    LARGE_Y('Y', 5),
    LARGE_Z('Z', 5),
    SQUARE_BRACKET_BEGIN('[', 3),
    BACKSLASH('\\', 5),
    SQUARE_BRACKET_END(']', 3),
    CARET('^', 5),
    UNDERSCORE('_', 5),
    BACK_QUOTE('`', 2),
    A('a', 5),
    B('b', 5),
    C('c', 5),
    D('d', 5),
    E('e', 5),
    F('f', 4),
    G('g', 5),
    H('h', 5),
    I('i', 1),
    J('j', 5),
    K('k', 4),
    L('l', 2),
    M('m', 5),
    N('n', 5),
    O('o', 5),
    P('p', 5),
    Q('q', 5),
    R('r', 5),
    S('s', 5),
    T('t', 3),
    U('u', 5),
    V('v', 5),
    W('w', 5),
    X('x', 5),
    Y('y', 5),
    Z('z', 5),
    CURLY_BRACKET_BEGIN('{', 3),
    PIPE('|', 1),
    CURLY_BRACKET_END('}', 3),
    TILDE('~', 6);


    private static final HashMap<Character, Integer> map;

    static {
        map = new HashMap<>();
        Arrays.stream(values()).forEach(a -> map.put(a.c, a.length));
    }

    private final char c;
    private final int length;

    CharLengths(char c, int length) {
        this.c = c;
        this.length = length;
    }

    /**
     * 文字列の長さを調べます.
     * @param str 調べる文字列
     * @return 表示上のピクセル単位の長さ
     */
    public static int getLength(String str) {
        if (str.isEmpty()) return 0;
        char[] array = str.toCharArray();

        int result = 0;
        for (int i = 0; i < array.length; i++) {
            result += 1;
            Integer length = map.get(array[i]);
            if (length != null) result += length;
            else result += 8; //平仮名, 漢字もしくはカタカナ
        }
        return result - 1;
    }

}
