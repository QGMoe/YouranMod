package moe.qingu.youran.logic;

import java.util.HashMap;
import java.util.Map;

/**
 * 往一张宏表（Patchouli 书的 {@code Book.macros}）里写服务器发来的值，断开时恢复成原来的值（book.json 里写的默认值）。
 * 第一次写之前记下这些宏原来的值；原来没有的宏，恢复时删掉。
 */
public final class MacroOverlay {
    private final Map<String, String> target;
    private Map<String, String> originals;

    public MacroOverlay(Map<String, String> target) {
        this.target = target;
    }

    public void apply(Map<String, String> values) {
        if (originals == null) {
            originals = new HashMap<>();
        }
        for (Map.Entry<String, String> e : values.entrySet()) {
            if (!originals.containsKey(e.getKey())) {
                originals.put(e.getKey(), target.get(e.getKey()));
            }
            target.put(e.getKey(), e.getValue());
        }
    }

    public void restore() {
        if (originals == null) {
            return;
        }
        for (Map.Entry<String, String> e : originals.entrySet()) {
            if (e.getValue() == null) {
                target.remove(e.getKey());
            } else {
                target.put(e.getKey(), e.getValue());
            }
        }
    }
}
