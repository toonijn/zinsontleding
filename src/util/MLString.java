package util;

import java.util.Arrays;

/**
 * Created by Toon Baeyens
 * <p>
 * MultiLineString
 */
public class MLString {
    private final int length;
    private final int rows;
    private final String[] lines;

    public MLString(String... lines) {
        this(true, lines);
    }

    private MLString(boolean check, String... lines) {
        this.lines = lines;
        this.rows = lines.length;
        length = lines.length == 0 ? 0 : lines[0].length();
        if (check)
            for (String line : lines)
                if (line.length() != length)
                    throw new IllegalArgumentException();
    }

    private static String empty(int length) {
        return fill(length, ' ');
    }

    private static String fill(int length, char v) {
        char[] empty = new char[length];
        Arrays.fill(empty, v);
        return new String(empty);
    }

    public String getRow(int i) {
        if (i < rows)
            return lines[i];
        return empty(length);
    }

    public MLString concat(MLString c) {
        int rows = Math.max(this.rows, c.rows);
        String[] ls = new String[rows];
        for (int i = 0; i < rows; ++i)
            ls[i] = getRow(i) + c.getRow(i);
        return new MLString(false, ls);
    }

    public MLString add(MLString c) {
        String[] ls = new String[this.rows + c.rows];

        String rpad = empty(Math.max((c.length - length) / 2, 0));
        String lpad = empty(Math.max((c.length - length + 1) / 2, 0));

        for (int i = 0; i < rows; ++i)
            ls[i] = lpad + getRow(i) + rpad;

        rpad = empty(Math.max((length - c.length) / 2, 0));
        lpad = empty(Math.max((length - c.length + 1) / 2, 0));
        for (int i = 0; i < c.rows; ++i)
            ls[rows + i] = lpad + c.getRow(i) + rpad;

        return new MLString(true, ls);
    }

    @Override
    public String toString() {
        if (rows == 0)
            return "";
        StringBuilder sb = new StringBuilder();
        sb.append(lines[0]);
        for (int i = 1; i < rows; ++i)
            sb.append('\n').append(lines[i]);
        return sb.toString();
    }

    public MLString concat(String s) {
        return concat(new MLString(s));
    }

    public MLString addLine() {
        return add(fill(length, '-'));
    }

    public MLString addLine(int minLength) {
        return add(' ' + fill(Math.max(minLength, length), '-') + ' ');
    }

    public MLString addLine(String a) {
        return addLine(a.length()).add(a);
    }

    public MLString add(String c) {
        return add(new MLString(c));
    }
}
