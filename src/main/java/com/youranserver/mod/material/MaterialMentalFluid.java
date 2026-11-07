// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MaterialMentalFluid.java

package com.youranserver.mod.material;

import net.minecraft.block.material.*;

public class MaterialMentalFluid extends MaterialLiquid
{

    public MaterialMentalFluid(MapColor color)
    {
        super(color);
    }

    public static final Material MentalFluid;

    static 
    {
        MentalFluid = new MaterialMentalFluid(MapColor.IRON);
    }
}
