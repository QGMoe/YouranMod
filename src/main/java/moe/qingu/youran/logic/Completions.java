package moe.qingu.youran.logic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/** /youran 的 Tab 补全（服主 11-07 加） */
public final class Completions {
    /** 子命令，按字母顺序（同帮助）；test 什么也不做，不补 */
    static final List<String> SUBCOMMANDS = Collections.unmodifiableList(Arrays.asList(
            "about", "back", "help", "maincity", "saveD", "serverhelp", "temperature", "tp", "website"));

    private Completions() {
    }

    /**
     * @param args      已输入的参数（最后一个是正在输入的那个）
     * @param canSaveD  能不能用 saveD（权限等级 2；不能用时不补它，同帮助里不显示它）
     * @param points    配置里的传送点名
     */
    public static List<String> youran(String[] args, boolean canSaveD, List<String> points) {
        if (args.length == 1) {
            List<String> options = new ArrayList<>(SUBCOMMANDS);
            if (!canSaveD) {
                options.remove("saveD");
            }
            return matching(args[0], options);
        }
        if (args.length == 2 && "tp".equals(args[0])) {
            List<String> options = new ArrayList<>(Arrays.asList("help", "list"));
            options.addAll(points);
            return matching(args[1], options);
        }
        return Collections.emptyList();
    }

    /** 以 prefix 开头的（不分大小写，同原版 CommandBase.getListOfStringsMatchingLastWord） */
    static List<String> matching(String prefix, List<String> options) {
        List<String> result = new ArrayList<>();
        for (String o : options) {
            if (o.regionMatches(true, 0, prefix, 0, prefix.length())) {
                result.add(o);
            }
        }
        return result;
    }
}
