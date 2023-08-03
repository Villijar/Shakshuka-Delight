package com.shakdel;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class ConsoleItem extends Item{
	public ConsoleItem(Settings settings) {
		super(settings);
	}
	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		if (world.isClient()) {
			ClientImplManager.run(this, "openConsole");
		}
		return TypedActionResult.success(user.getStackInHand(hand), false);
	}
}
