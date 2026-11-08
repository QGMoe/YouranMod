package moe.qingu.youran.client;

import moe.qingu.youran.YouranMod;
import moe.qingu.youran.logic.MacroOverlay;
import net.minecraft.util.ResourceLocation;

import java.util.Map;

/**
 * 把服务器配置的链接写进手册书（Patchouli 的 {@code youranmod:guide}）的宏表 {@code Book.macros}。
 * Patchouli 每次显示一页（打开书、翻页）时才解析正文，解析前对宏表做 {@code String.replace}（{@code BookTextParser.parse}），
 * 所以改了宏表，下一次显示就是新的值。
 * <p>
 * 不对 Patchouli 加编译期依赖：按类名找 {@code vazkii.patchouli.common.book.BookRegistry.INSTANCE.books}；
 * 没装 Patchouli 或找不到这本书时跳过，只记一次日志。宏原来的值（book.json 里写的默认值）由 {@link MacroOverlay} 记下，断开时恢复。
 */
final class PatchouliLinks {
    private static final String REGISTRY = "vazkii.patchouli.common.book.BookRegistry";
    private static final ResourceLocation BOOK = new ResourceLocation(YouranMod.MODID, "guide");

    private static MacroOverlay overlay;
    private static boolean unavailable;

    private PatchouliLinks() {
    }

    static void apply(Map<String, String> macros) {
        MacroOverlay o = overlay();
        if (o != null) {
            o.apply(macros);
        }
    }

    static void restore() {
        if (overlay != null) {
            overlay.restore();
        }
    }

    @SuppressWarnings("unchecked")
    private static MacroOverlay overlay() {
        if (overlay != null || unavailable) {
            return overlay;
        }
        try {
            Class<?> registryClass = Class.forName(REGISTRY);
            Object registry = registryClass.getField("INSTANCE").get(null);
            Map<ResourceLocation, Object> books = (Map<ResourceLocation, Object>) registryClass.getField("books").get(registry);
            Object book = books.get(BOOK);
            if (book == null) {
                unavailable = true;
                YouranMod.LOG.warn("Patchouli 里没有手册书 {}，书里的链接不跟服务器配置走", BOOK);
                return null;
            }
            overlay = new MacroOverlay((Map<String, String>) book.getClass().getField("macros").get(book));
        } catch (ClassNotFoundException e) {
            unavailable = true;
            YouranMod.LOG.info("没装 Patchouli，跳过手册书的链接");
        } catch (ReflectiveOperationException | RuntimeException e) {
            unavailable = true;
            YouranMod.LOG.warn("找不到 Patchouli 的书（版本不对？），书里的链接不跟服务器配置走", e);
        }
        return overlay;
    }
}
