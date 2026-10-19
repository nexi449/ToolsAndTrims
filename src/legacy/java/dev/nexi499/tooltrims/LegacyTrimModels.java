package dev.nexi499.tooltrims;

import java.util.List;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.armortrim.ArmorTrim;
import dev.nexi499.tooltrims.mixin.ItemPropertiesAccessor;

public final class LegacyTrimModels implements ClientModInitializer {
	private static final List<String> PATTERNS = List.of(
			"bolt", "coast", "dune", "eye", "flow", "host", "raiser", "rib", "sentry",
			"shaper", "silence", "snout", "spire", "tide", "vex", "ward", "wayfinder", "wild");
	private static final List<String> MATERIALS = List.of(
			"amethyst", "copper", "diamond", "emerald", "gold", "iron",
			"lapis", "netherite", "quartz", "redstone");
	private static final ResourceLocation TRIM_PROPERTY =
			ResourceLocation.fromNamespaceAndPath("tooltrims", "trim");
	private static final List<Item> TOOLS = List.of(
			Items.WOODEN_SWORD, Items.STONE_SWORD, Items.IRON_SWORD, Items.GOLDEN_SWORD,
			Items.DIAMOND_SWORD, Items.NETHERITE_SWORD,
			Items.WOODEN_PICKAXE, Items.STONE_PICKAXE, Items.IRON_PICKAXE, Items.GOLDEN_PICKAXE,
			Items.DIAMOND_PICKAXE, Items.NETHERITE_PICKAXE,
			Items.WOODEN_AXE, Items.STONE_AXE, Items.IRON_AXE, Items.GOLDEN_AXE,
			Items.DIAMOND_AXE, Items.NETHERITE_AXE,
			Items.WOODEN_SHOVEL, Items.STONE_SHOVEL, Items.IRON_SHOVEL, Items.GOLDEN_SHOVEL,
			Items.DIAMOND_SHOVEL, Items.NETHERITE_SHOVEL,
			Items.WOODEN_HOE, Items.STONE_HOE, Items.IRON_HOE, Items.GOLDEN_HOE,
			Items.DIAMOND_HOE, Items.NETHERITE_HOE);

	@Override
	public void onInitializeClient() {
		for (Item item : TOOLS) {
			ItemPropertiesAccessor.tooltrims$register(
					item, TRIM_PROPERTY, (stack, level, entity, seed) -> getTrimIndex(stack));
		}
	}

	private static float getTrimIndex(net.minecraft.world.item.ItemStack stack) {
		ArmorTrim trim = stack.get(DataComponents.TRIM);
		if (trim == null) {
			return 0.0F;
		}

		String pattern = trim.pattern().unwrapKey()
				.map(key -> key.location().getPath())
				.orElse("");
		String material = trim.material().unwrapKey()
				.map(key -> key.location().getPath())
				.orElse("");
		int patternIndex = PATTERNS.indexOf(pattern);
		int materialIndex = MATERIALS.indexOf(material);
		if (patternIndex < 0 || materialIndex < 0) {
			return 0.0F;
		}
		return 1 + patternIndex * MATERIALS.size() + materialIndex;
	}
}
