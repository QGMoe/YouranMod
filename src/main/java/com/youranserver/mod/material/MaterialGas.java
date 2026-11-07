// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MaterialGas.java

package com.youranserver.mod.material;

import net.minecraft.block.material.*;

public class MaterialGas extends MaterialLiquid
{

    public MaterialGas(MapColor color)
    {
        super(color);
    }

    public static final Material GAS;

    static 
    {
        GAS = new MaterialGas(MapColor.WATER);
    }
}
