package moe.qingu.youran.logic;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * 两端都要用的配置值：服务端从配置文件读，玩家登录时发给客户端（见 {@code net.SyncSettingsMessage}）。
 * <p>
 * 包括手册书与指令里的链接、碰到汞 / 镓 / 氯气的效果与防护、掉进汞 / 镓里会爆炸的活泼金属锭、来自异世之剑的缓慢、
 * 血红宝石与奇怪的工具每次用掉的耐久、矿泉水与饮用水的饱食度。默认值（{@link #DEFAULTS}）等于 1.3.4 写死的值。
 * <p>
 * 收到的值逐项校验（{@link #sanitize}）：不合规的那一项用默认值，原因记在 problems 里。服务器是可信的，校验只是为了
 * 配置写错时客户端不出错（比如药水等级超出范围、链接里有会破坏手册书格式的字符）。
 */
public final class SharedSettings {
    /** 编码格式的版本；两端是同一个 jar（FML 握手要求版本相同），不同时说明包坏了 */
    static final int FORMAT = 1;
    /** 列表最多几项、文字最长几个字符（防止坏包让客户端分配很大的内存） */
    static final int MAX_LIST = 64;
    static final int MAX_TEXT = 256;
    static final int MAX_URL = 512;
    /** 药水等级 0 起算；网络上按一个字节发，超过 127 客户端会看成负数 */
    static final int MAX_AMPLIFIER = 127;
    static final int MAX_TICKS = 1_000_000;
    static final float MAX_EXPLOSION = 20f;
    static final int MAX_DURABILITY_COST = 10_000;
    static final int MAX_HUNGER = 20;
    static final float MAX_SATURATION = 10f;

    private static final Pattern REGISTRY_NAME = Pattern.compile("[a-z0-9_.-]+:[a-z0-9_./-]+");
    private static final Pattern ORE_NAME = Pattern.compile("[A-Za-z0-9_.:-]+");

    // ---- 链接（/youran 的指令与手册书） ----
    public final String websiteUrl;
    public final String websiteText;
    public final String manualUrl;
    public final String manualText;
    public final String mcmodUrl;
    public final String mcmodText;
    public final String rulesUrl;
    public final String qqGroup;
    public final String qqGroupUrl;

    // ---- 汞、镓、氯气 ----
    public final int mercurySlownessAmplifier;
    public final int mercurySlownessTicks;
    public final int galliumSlownessAmplifier;
    public final int galliumSlownessTicks;
    public final int chlorinePoisonAmplifier;
    public final int chlorinePoisonTicks;
    /** 戴在头盔栏就不受氯气影响的物品（注册名） */
    public final List<String> chlorineHelmets;
    /** 穿在胸甲栏就不受氯气影响的物品（注册名） */
    public final List<String> chlorineChestplates;
    /** 掉进汞、镓里会爆炸的锭：矿物词典名 → 爆炸威力（按配置里的顺序） */
    public final Map<String, Float> reactiveIngots;

    // ---- 物品 ----
    public final double swordSlownessChance;
    public final int swordSlownessTicks;
    public final int swordSlownessAmplifier;
    public final int redBloodGemDurabilityCost;
    public final int homeToolDurabilityCost;
    public final int mineralWaterHunger;
    public final float mineralWaterSaturation;
    public final int drinkingWaterHunger;
    public final float drinkingWaterSaturation;

    public static final SharedSettings DEFAULTS = new Builder().build();

    private SharedSettings(Builder b) {
        websiteUrl = b.websiteUrl;
        websiteText = b.websiteText;
        manualUrl = b.manualUrl;
        manualText = b.manualText;
        mcmodUrl = b.mcmodUrl;
        mcmodText = b.mcmodText;
        rulesUrl = b.rulesUrl;
        qqGroup = b.qqGroup;
        qqGroupUrl = b.qqGroupUrl;
        mercurySlownessAmplifier = b.mercurySlownessAmplifier;
        mercurySlownessTicks = b.mercurySlownessTicks;
        galliumSlownessAmplifier = b.galliumSlownessAmplifier;
        galliumSlownessTicks = b.galliumSlownessTicks;
        chlorinePoisonAmplifier = b.chlorinePoisonAmplifier;
        chlorinePoisonTicks = b.chlorinePoisonTicks;
        chlorineHelmets = Collections.unmodifiableList(new ArrayList<>(b.chlorineHelmets));
        chlorineChestplates = Collections.unmodifiableList(new ArrayList<>(b.chlorineChestplates));
        reactiveIngots = Collections.unmodifiableMap(new LinkedHashMap<>(b.reactiveIngots));
        swordSlownessChance = b.swordSlownessChance;
        swordSlownessTicks = b.swordSlownessTicks;
        swordSlownessAmplifier = b.swordSlownessAmplifier;
        redBloodGemDurabilityCost = b.redBloodGemDurabilityCost;
        homeToolDurabilityCost = b.homeToolDurabilityCost;
        mineralWaterHunger = b.mineralWaterHunger;
        mineralWaterSaturation = b.mineralWaterSaturation;
        drinkingWaterHunger = b.drinkingWaterHunger;
        drinkingWaterSaturation = b.drinkingWaterSaturation;
    }

    /** 可变的构造器；字段初值就是默认值 */
    public static final class Builder {
        public String websiteUrl = "https://youran.qingu.moe/";
        public String websiteText = "youran.qingu.moe";
        public String manualUrl = "https://docs.youran.qingu.moe/";
        public String manualText = "docs.youran.qingu.moe";
        public String mcmodUrl = "https://play.mcmod.cn/sv20185890.html";
        public String mcmodText = "play.mcmod.cn/sv20185890.html";
        public String rulesUrl = "https://docs.qq.com/doc/DVHpzQUdQSlFuT0ha";
        public String qqGroup = "953168624";
        public String qqGroupUrl = "https://jq.qq.com/?_wv=1027&k=Hwv4w2NU";

        public int mercurySlownessAmplifier = 5;
        public int mercurySlownessTicks = 1;
        public int galliumSlownessAmplifier = 3;
        public int galliumSlownessTicks = 1;
        public int chlorinePoisonAmplifier = 2;
        public int chlorinePoisonTicks = 300;
        public List<String> chlorineHelmets = Collections.singletonList("ic2:hazmat_helmet");
        public List<String> chlorineChestplates = Collections.singletonList("scp:hazard_suit");
        public Map<String, Float> reactiveIngots = defaultIngots();

        public double swordSlownessChance = 0.5;
        public int swordSlownessTicks = 5;
        public int swordSlownessAmplifier = 2;
        public int redBloodGemDurabilityCost = 1;
        public int homeToolDurabilityCost = 1;
        public int mineralWaterHunger = 1;
        public float mineralWaterSaturation = 0.3f;
        public int drinkingWaterHunger = 0;
        public float drinkingWaterSaturation = 0.2f;

        public SharedSettings build() {
            return new SharedSettings(this);
        }

        private static Map<String, Float> defaultIngots() {
            Map<String, Float> m = new LinkedHashMap<>();
            m.put("ingotSodium", 2.0f);
            m.put("ingotMagnesium", 2.0f);
            m.put("ingotPotassium", 1.5f);
            m.put("ingotCalcium", 1.5f);
            m.put("ingotAluminum", 1.0f);
            return m;
        }
    }

    // ---- 用法 ----

    /** 氯气的防护：头盔栏或胸甲栏的物品在表里（注册名；空栏是 minecraft:air） */
    public boolean protectsFromChlorine(String head, String chest) {
        return ChlorineProtection.isProtected(head, chest, chlorineHelmets, chlorineChestplates);
    }

    /** 掉进汞、镓里的物品的爆炸威力；不爆炸时返回 -1（见 {@link ReactiveIngots#explosionStrength}） */
    public float explosionStrength(List<String> oreNames) {
        return ReactiveIngots.explosionStrength(oreNames, reactiveIngots);
    }

    /** 手册书（Patchouli 的 youranmod:guide）里的宏：宏名 → 值。宏名与 book.json 的 macros 一致 */
    public Map<String, String> bookMacros() {
        Map<String, String> m = new LinkedHashMap<>();
        m.put("$(yr_site)", websiteUrl);
        m.put("$(yr_site_text)", websiteText);
        m.put("$(yr_docs)", manualUrl);
        m.put("$(yr_docs_text)", manualText);
        m.put("$(yr_mcmod)", mcmodUrl);
        m.put("$(yr_rules)", rulesUrl);
        m.put("$(yr_qq)", qqGroup);
        m.put("$(yr_qq_link)", qqGroupUrl);
        return m;
    }

    // ---- 校验 ----

    /** 逐项校验：不合规的项用默认值，原因加进 problems（每条写明是哪一项） */
    public static SharedSettings sanitize(SharedSettings in, List<String> problems) {
        SharedSettings d = DEFAULTS;
        Builder b = new Builder();
        b.websiteUrl = url("websiteUrl", in.websiteUrl, d.websiteUrl, problems);
        b.websiteText = text("websiteText", in.websiteText, d.websiteText, problems);
        b.manualUrl = url("manualUrl", in.manualUrl, d.manualUrl, problems);
        b.manualText = text("manualText", in.manualText, d.manualText, problems);
        b.mcmodUrl = url("mcmodUrl", in.mcmodUrl, d.mcmodUrl, problems);
        b.mcmodText = text("mcmodText", in.mcmodText, d.mcmodText, problems);
        b.rulesUrl = url("rulesUrl", in.rulesUrl, d.rulesUrl, problems);
        b.qqGroup = text("qqGroup", in.qqGroup, d.qqGroup, problems);
        b.qqGroupUrl = url("qqGroupUrl", in.qqGroupUrl, d.qqGroupUrl, problems);

        b.mercurySlownessAmplifier = range("mercurySlownessAmplifier", in.mercurySlownessAmplifier, 0, MAX_AMPLIFIER, d.mercurySlownessAmplifier, problems);
        b.mercurySlownessTicks = range("mercurySlownessTicks", in.mercurySlownessTicks, 1, MAX_TICKS, d.mercurySlownessTicks, problems);
        b.galliumSlownessAmplifier = range("galliumSlownessAmplifier", in.galliumSlownessAmplifier, 0, MAX_AMPLIFIER, d.galliumSlownessAmplifier, problems);
        b.galliumSlownessTicks = range("galliumSlownessTicks", in.galliumSlownessTicks, 1, MAX_TICKS, d.galliumSlownessTicks, problems);
        b.chlorinePoisonAmplifier = range("chlorinePoisonAmplifier", in.chlorinePoisonAmplifier, 0, MAX_AMPLIFIER, d.chlorinePoisonAmplifier, problems);
        b.chlorinePoisonTicks = range("chlorinePoisonTicks", in.chlorinePoisonTicks, 1, MAX_TICKS, d.chlorinePoisonTicks, problems);
        b.chlorineHelmets = registryNames("chlorineProtectionHelmets", in.chlorineHelmets, d.chlorineHelmets, problems);
        b.chlorineChestplates = registryNames("chlorineProtectionChestplates", in.chlorineChestplates, d.chlorineChestplates, problems);
        b.reactiveIngots = ingots(in.reactiveIngots, d.reactiveIngots, problems);

        b.swordSlownessChance = chance("swordSlownessChance", in.swordSlownessChance, d.swordSlownessChance, problems);
        b.swordSlownessTicks = range("swordSlownessTicks", in.swordSlownessTicks, 1, MAX_TICKS, d.swordSlownessTicks, problems);
        b.swordSlownessAmplifier = range("swordSlownessAmplifier", in.swordSlownessAmplifier, 0, MAX_AMPLIFIER, d.swordSlownessAmplifier, problems);
        b.redBloodGemDurabilityCost = range("redBloodGemDurabilityCost", in.redBloodGemDurabilityCost, 0, MAX_DURABILITY_COST, d.redBloodGemDurabilityCost, problems);
        b.homeToolDurabilityCost = range("homeToolDurabilityCost", in.homeToolDurabilityCost, 0, MAX_DURABILITY_COST, d.homeToolDurabilityCost, problems);
        b.mineralWaterHunger = range("mineralWaterHunger", in.mineralWaterHunger, 0, MAX_HUNGER, d.mineralWaterHunger, problems);
        b.mineralWaterSaturation = saturation("mineralWaterSaturation", in.mineralWaterSaturation, d.mineralWaterSaturation, problems);
        b.drinkingWaterHunger = range("drinkingWaterHunger", in.drinkingWaterHunger, 0, MAX_HUNGER, d.drinkingWaterHunger, problems);
        b.drinkingWaterSaturation = saturation("drinkingWaterSaturation", in.drinkingWaterSaturation, d.drinkingWaterSaturation, problems);
        return b.build();
    }

    private static String url(String name, String v, String def, List<String> problems) {
        String why = null;
        if (v == null || v.isEmpty()) {
            why = "是空的";
        } else if (v.length() > MAX_URL) {
            why = "超过 " + MAX_URL + " 个字符";
        } else if (!(v.startsWith("https://") || v.startsWith("http://"))) {
            why = "不是 http:// 或 https:// 开头";
        } else {
            for (int i = 0; i < v.length(); i++) {
                char c = v.charAt(i);
                // 括号与 $ 会破坏手册书的 $(l:地址) 写法；§ 是格式代码
                if (Character.isWhitespace(c) || Character.isISOControl(c) || c == '$' || c == '(' || c == ')' || c == '§') {
                    why = "含有不能用的字符“" + (Character.isWhitespace(c) || Character.isISOControl(c) ? "空白" : String.valueOf(c)) + "”";
                    break;
                }
            }
        }
        return why == null ? v : rejected(name, quote(v), why, def, problems);
    }

    private static String text(String name, String v, String def, List<String> problems) {
        String why = null;
        if (v == null || v.isEmpty()) {
            why = "是空的";
        } else if (v.length() > MAX_TEXT) {
            why = "超过 " + MAX_TEXT + " 个字符";
        } else if (v.contains("$(")) {
            why = "含有“$(”（会被手册书当成格式命令）";
        } else {
            for (int i = 0; i < v.length(); i++) {
                if (Character.isISOControl(v.charAt(i))) {
                    why = "含有换行等控制字符";
                    break;
                }
            }
        }
        return why == null ? v : rejected(name, quote(v), why, def, problems);
    }

    private static int range(String name, int v, int min, int max, int def, List<String> problems) {
        if (v >= min && v <= max) {
            return v;
        }
        return rejected(name, String.valueOf(v), "不在 " + min + "–" + max + " 之间", def, problems);
    }

    private static double chance(String name, double v, double def, List<String> problems) {
        if (v >= 0 && v <= 1) {
            return v;
        }
        return rejected(name, String.valueOf(v), "不在 0–1 之间", def, problems);
    }

    private static float saturation(String name, float v, float def, List<String> problems) {
        if (v >= 0 && v <= MAX_SATURATION) {
            return v;
        }
        return rejected(name, String.valueOf(v), "不在 0–" + MAX_SATURATION + " 之间", def, problems);
    }

    private static List<String> registryNames(String name, List<String> v, List<String> def, List<String> problems) {
        if (v.size() > MAX_LIST) {
            return rejected(name, v.size() + " 项", "超过 " + MAX_LIST + " 项", def, problems);
        }
        for (String s : v) {
            if (s == null || !REGISTRY_NAME.matcher(s).matches()) {
                return rejected(name, String.valueOf(v), quote(s) + "不是物品注册名（如 scp:hazard_suit）", def, problems);
            }
        }
        return v;
    }

    private static Map<String, Float> ingots(Map<String, Float> v, Map<String, Float> def, List<String> problems) {
        String name = "reactiveIngots";
        if (v.size() > MAX_LIST) {
            return rejected(name, v.size() + " 项", "超过 " + MAX_LIST + " 项", def, problems);
        }
        for (Map.Entry<String, Float> e : v.entrySet()) {
            String key = e.getKey();
            Float s = e.getValue();
            if (key == null || key.length() > MAX_TEXT || !ORE_NAME.matcher(key).matches()) {
                return rejected(name, String.valueOf(v), quote(key) + "不是矿物词典名", def, problems);
            }
            if (s == null || !(s >= 0 && s <= MAX_EXPLOSION)) {
                return rejected(name, String.valueOf(v), key + " 的爆炸威力不在 0–" + MAX_EXPLOSION + " 之间", def, problems);
            }
        }
        return v;
    }

    private static <T> T rejected(String name, String value, String why, T def, List<String> problems) {
        problems.add(name + " = " + value + "：" + why + "，改用默认值 " + def);
        return def;
    }

    private static String quote(String s) {
        return "“" + s + "”";
    }

    // ---- 编码 ----

    /** 编成字节（发给客户端） */
    public byte[] encode() {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        try (DataOutputStream out = new DataOutputStream(bytes)) {
            out.writeByte(FORMAT);
            for (String s : Arrays.asList(websiteUrl, websiteText, manualUrl, manualText, mcmodUrl, mcmodText, rulesUrl, qqGroup, qqGroupUrl)) {
                out.writeUTF(s);
            }
            for (int n : new int[]{mercurySlownessAmplifier, mercurySlownessTicks, galliumSlownessAmplifier, galliumSlownessTicks,
                    chlorinePoisonAmplifier, chlorinePoisonTicks}) {
                out.writeInt(n);
            }
            writeList(out, chlorineHelmets);
            writeList(out, chlorineChestplates);
            out.writeShort(reactiveIngots.size());
            for (Map.Entry<String, Float> e : reactiveIngots.entrySet()) {
                out.writeUTF(e.getKey());
                out.writeFloat(e.getValue());
            }
            out.writeDouble(swordSlownessChance);
            out.writeInt(swordSlownessTicks);
            out.writeInt(swordSlownessAmplifier);
            out.writeInt(redBloodGemDurabilityCost);
            out.writeInt(homeToolDurabilityCost);
            out.writeInt(mineralWaterHunger);
            out.writeFloat(mineralWaterSaturation);
            out.writeInt(drinkingWaterHunger);
            out.writeFloat(drinkingWaterSaturation);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
        return bytes.toByteArray();
    }

    /**
     * 从字节解出来并校验（{@link #sanitize}）。字节本身坏了（截断、格式版本不对、多出来的字节、列表长度不对）时整个用默认值。
     * 问题加进 problems。
     */
    public static SharedSettings decode(byte[] data, List<String> problems) {
        if (data == null) {
            problems.add("包是空的，全部用默认值");
            return DEFAULTS;
        }
        Builder b = new Builder();
        try (DataInputStream in = new DataInputStream(new ByteArrayInputStream(data))) {
            int format = in.readUnsignedByte();
            if (format != FORMAT) {
                problems.add("格式版本是 " + format + "，这一端是 " + FORMAT + "，全部用默认值");
                return DEFAULTS;
            }
            b.websiteUrl = in.readUTF();
            b.websiteText = in.readUTF();
            b.manualUrl = in.readUTF();
            b.manualText = in.readUTF();
            b.mcmodUrl = in.readUTF();
            b.mcmodText = in.readUTF();
            b.rulesUrl = in.readUTF();
            b.qqGroup = in.readUTF();
            b.qqGroupUrl = in.readUTF();
            b.mercurySlownessAmplifier = in.readInt();
            b.mercurySlownessTicks = in.readInt();
            b.galliumSlownessAmplifier = in.readInt();
            b.galliumSlownessTicks = in.readInt();
            b.chlorinePoisonAmplifier = in.readInt();
            b.chlorinePoisonTicks = in.readInt();
            b.chlorineHelmets = readList(in);
            b.chlorineChestplates = readList(in);
            int n = readCount(in);
            Map<String, Float> ingots = new LinkedHashMap<>();
            for (int i = 0; i < n; i++) {
                ingots.put(in.readUTF(), in.readFloat());
            }
            b.reactiveIngots = ingots;
            b.swordSlownessChance = in.readDouble();
            b.swordSlownessTicks = in.readInt();
            b.swordSlownessAmplifier = in.readInt();
            b.redBloodGemDurabilityCost = in.readInt();
            b.homeToolDurabilityCost = in.readInt();
            b.mineralWaterHunger = in.readInt();
            b.mineralWaterSaturation = in.readFloat();
            b.drinkingWaterHunger = in.readInt();
            b.drinkingWaterSaturation = in.readFloat();
            if (in.read() != -1) {
                problems.add("包的末尾多出了字节，全部用默认值");
                return DEFAULTS;
            }
        } catch (IOException e) {
            problems.add("包读不出来（" + e + "），全部用默认值");
            return DEFAULTS;
        }
        return sanitize(b.build(), problems);
    }

    private static void writeList(DataOutputStream out, List<String> list) throws IOException {
        out.writeShort(list.size());
        for (String s : list) {
            out.writeUTF(s);
        }
    }

    private static List<String> readList(DataInputStream in) throws IOException {
        int n = readCount(in);
        List<String> list = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            list.add(in.readUTF());
        }
        return list;
    }

    private static int readCount(DataInputStream in) throws IOException {
        int n = in.readUnsignedShort();
        if (n > MAX_LIST) {
            throw new IOException("列表有 " + n + " 项，超过 " + MAX_LIST);
        }
        return n;
    }

    // ---- 比较（测试用） ----

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof SharedSettings)) {
            return false;
        }
        return Arrays.equals(encode(), ((SharedSettings) o).encode());
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(encode());
    }

    @Override
    public String toString() {
        return "SharedSettings" + Objects.toString(bookMacros()) + reactiveIngots;
    }
}
