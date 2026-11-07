package moe.qingu.youran.logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 配置里“键=值”形式的列表（如 {@code scp:old_man=10}、{@code 23000=100}）。按最后一个 {@code =} 分开，
 * 所以键里可以有冒号。格式不对的项跳过，记在 {@link Result#errors} 里。
 */
public final class KeyValues {
    private KeyValues() {
    }

    /** 解析结果：按原顺序的有效项与无效项的说明 */
    public static final class Result<T> {
        public final List<T> values;
        public final List<String> errors;

        Result(List<T> values, List<String> errors) {
            this.values = Collections.unmodifiableList(values);
            this.errors = Collections.unmodifiableList(errors);
        }
    }

    /** 值的转换，失败时抛 {@link IllegalArgumentException}（消息写原因） */
    public interface Converter<T> {
        T convert(String key, String value);
    }

    public static <T> Result<T> parse(String[] lines, Converter<T> converter) {
        List<T> values = new ArrayList<>();
        List<String> errors = new ArrayList<>();
        for (String raw : lines) {
            String line = raw == null ? "" : raw.trim();
            int eq = line.lastIndexOf('=');
            if (eq <= 0 || eq == line.length() - 1) {
                errors.add("“" + line + "”不是“键=值”");
                continue;
            }
            String key = line.substring(0, eq).trim();
            String value = line.substring(eq + 1).trim();
            try {
                values.add(converter.convert(key, value));
            } catch (IllegalArgumentException e) {
                errors.add("“" + line + "”：" + e.getMessage());
            }
        }
        return new Result<>(values, errors);
    }

    static int parseInt(String value, int min) {
        int n;
        try {
            n = Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("“" + value + "”不是整数");
        }
        if (n < min) {
            throw new IllegalArgumentException(n + " 小于 " + min);
        }
        return n;
    }

    static long parseLong(String value) {
        try {
            return Long.parseLong(value);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("“" + value + "”不是整数");
        }
    }
}
