package com.shakdel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nhoryzon.mc.farmersdelight.item.ConsumableItem;
import com.nhoryzon.mc.farmersdelight.registry.EffectsRegistry;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ShakshukaDelight implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("shakdel");
    public static final Item SHAKSHUKA = new ConsumableItem(new FabricItemSettings().maxCount(16).recipeRemainder(Items.BOWL)
    		.food(new FoodComponent.Builder().hunger(10).saturationModifier(0.6f).statusEffect(new StatusEffectInstance(
    				EffectsRegistry.NOURISHMENT.get(), 3600), 1).build()),true);
    public static final Item BUG = new Item(new FabricItemSettings().food(new FoodComponent.Builder().hunger(1)
    		.saturationModifier(0.6f).alwaysEdible().snack().statusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, 600), 1)
    		.statusEffect(new StatusEffectInstance(StatusEffects.POISON, 600), 1)
    		.statusEffect(new StatusEffectInstance(StatusEffects.HUNGER, 600), 1)
    		.statusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 600), 1)
    		.statusEffect(new StatusEffectInstance(StatusEffects.DARKNESS, 600, 2), 1).build()));
    public static final Item SCRIPT = new ScriptItem(new FabricItemSettings());
    public static final Item CONSOLE = new ConsoleItem(new FabricItemSettings());
    public static final ItemGroup SHAKSHUKA_DELIGHT = FabricItemGroup.builder().icon(() -> new ItemStack(SHAKSHUKA))
    		.displayName(Text.translatable("itemGroup.shakdel.shakshuka_delight")).build();
	@Override
	public void onInitialize() {
		Registry.register(Registries.ITEM, new Identifier("shakdel", "shakshuka"), SHAKSHUKA);
		Registry.register(Registries.ITEM, new Identifier("shakdel", "bug"), BUG);
		Registry.register(Registries.ITEM, new Identifier("shakdel", "script"), SCRIPT);
		Registry.register(Registries.ITEM, new Identifier("shakdel", "console"), CONSOLE);
		Registry.register(Registries.ITEM_GROUP, new Identifier("shakdel", "shakshuka_delight"), SHAKSHUKA_DELIGHT);
		ItemGroupEvents.modifyEntriesEvent(Registries.ITEM_GROUP.getKey(SHAKSHUKA_DELIGHT).get())
				.register(content -> content.addAfter(Items.RABBIT_STEW,SHAKSHUKA));
	}
}