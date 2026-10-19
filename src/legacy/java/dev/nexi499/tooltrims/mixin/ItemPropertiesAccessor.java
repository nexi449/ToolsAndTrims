package dev.nexi499.tooltrims.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import net.minecraft.client.renderer.item.ClampedItemPropertyFunction;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

@Mixin(ItemProperties.class)
public interface ItemPropertiesAccessor {
	@Invoker("register")
	static void tooltrims$register(Item item, ResourceLocation id, ClampedItemPropertyFunction property) {
		throw new AssertionError();
	}
}
