package com.notcharrow.tnttweaks.mixin;

import com.notcharrow.tnttweaks.config.ConfigManager;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.TntEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Block.class)
public class BlockMixin {
	@Inject(at = @At("HEAD"), method = "onPlaced")
	private void init(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack, CallbackInfo ci) {
		if (state.getBlock() == Blocks.TNT && ConfigManager.config.modEnabled) {
			world.removeBlock(pos, false);
			TntEntity tnt = EntityType.TNT.create(world, SpawnReason.MOB_SUMMONED);
			if (tnt != null) {
				tnt.refreshPositionAfterTeleport(pos.getX(), pos.getY(), pos.getZ());
				tnt.setFuse(ConfigManager.config.fuseTime);
				if (ConfigManager.config.placerImmunity) {
					StatusEffectInstance resistance = new StatusEffectInstance(StatusEffects.RESISTANCE, 1, 5, false, false);
					placer.addStatusEffect(resistance);
				}
				world.spawnEntity(tnt);
			}
		}
	}
}