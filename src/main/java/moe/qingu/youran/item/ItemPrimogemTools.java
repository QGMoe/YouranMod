package moe.qingu.youran.item;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import moe.qingu.youran.registry.YouranTab;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraftforge.common.util.EnumHelper;

import java.util.Set;

/**
 * 来自异世之镐、斧、铲。材料都是挖掘等级 4、耐久 4000、效率 20、附魔能力 50，伤害各不同；没有修复材料（不能在铁砧里修）。
 * 与 1.3.4 相同的细节：
 * <ul>
 *   <li>铲的工具类型写的是 {@code spade}（原版铲是 {@code shovel}）；挖掘等级仍按原版铲的类型算，挖掘速度按原版铲的方块表；</li>
 *   <li>斧不是原版的斧（{@code ItemAxe}），没有工具类型（{@code axe} 的挖掘等级是 -1），木头、植物、藤蔓类材质按效率挖，
 *   另有一张方块表；不能破盾。</li>
 * </ul>
 */
public final class ItemPrimogemTools {
    private ItemPrimogemTools() {
    }

    /** 镐：伤害 1+5=6，攻速 -2.8 */
    public static Item pickaxe() {
        return new ItemPickaxe(EnumHelper.addToolMaterial("PRIMOGEM_PICKAXE", 4, 4000, 20f, 5f, 50)) {
            {
                this.attackSpeed = -2.8f;
                setRegistryName("primogem_pickaxe");
                setTranslationKey("primogem_pickaxe");
                setCreativeTab(YouranTab.tab);
            }

            @Override
            public Set<String> getToolClasses(ItemStack stack) {
                return ImmutableSet.of("pickaxe");
            }
        };
    }

    /** 铲：伤害 1.5+4=5.5，攻速 -3 */
    public static Item shovel() {
        return new ItemSpade(EnumHelper.addToolMaterial("PRIMOGEM_SHOVEL", 4, 4000, 20f, 4f, 50)) {
            {
                this.attackSpeed = -3f;
                setRegistryName("primogem_shovel");
                setTranslationKey("primogem_shovel");
                setCreativeTab(YouranTab.tab);
            }

            @Override
            public Set<String> getToolClasses(ItemStack stack) {
                return ImmutableSet.of("spade");
            }
        };
    }

    /** 斧：伤害 7，攻速 -2.8 */
    public static Item axe() {
        return new PrimogemAxe();
    }

    private static final class PrimogemAxe extends ItemTool {
        private static final Set<Block> EFFECTIVE_ON = Sets.newHashSet(Blocks.PLANKS, Blocks.BOOKSHELF, Blocks.LOG, Blocks.LOG2,
                Blocks.CHEST, Blocks.PUMPKIN, Blocks.LIT_PUMPKIN, Blocks.MELON_BLOCK, Blocks.LADDER, Blocks.WOODEN_BUTTON,
                Blocks.WOODEN_PRESSURE_PLATE);

        PrimogemAxe() {
            super(EnumHelper.addToolMaterial("PRIMOGEM_AXE", 4, 4000, 20f, 7f, 50), EFFECTIVE_ON);
            this.attackDamage = 7f;
            this.attackSpeed = -2.8f;
            setRegistryName("primogem_axe");
            setTranslationKey("primogem_axe");
            setCreativeTab(YouranTab.tab);
        }

        @Override
        public float getDestroySpeed(ItemStack stack, IBlockState state) {
            Material material = state.getMaterial();
            return material != Material.WOOD && material != Material.PLANTS && material != Material.VINE
                    ? super.getDestroySpeed(stack, state)
                    : this.efficiency;
        }
    }
}
