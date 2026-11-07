package com.youranserver.mod.procedure;

import java.util.Map;

import com.youranserver.mod.ElementsYouranmodMod;

@ElementsYouranmodMod.ModElement.Tag
public class ProcedureSummonMineralWater extends ElementsYouranmodMod.ModElement {
	public ProcedureSummonMineralWater(ElementsYouranmodMod instance) {
		super(instance, 53);
	}

	public static boolean executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("y") == null) {
			System.err.println("Failed to load dependency y for procedure SummonMineralWater!");
			return false;
		}
		int y = (int) dependencies.get("y");
		boolean YesOrNo = false;
		if ((y == 0)) {
			YesOrNo = (boolean) (false);
		}
		return (false);
	}
}
