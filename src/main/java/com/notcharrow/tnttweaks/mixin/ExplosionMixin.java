package com.notcharrow.tnttweaks.mixin;

import com.notcharrow.tnttweaks.config.ConfigManager;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.TntEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import net.minecraft.world.explosion.ExplosionBehavior;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Iterator;
import java.util.Map;
import java.util.Optional;

@Mixin(ExplosionBehavior.class)
public abstract class ExplosionMixin {
	@Inject(at = @At("HEAD"), method = "getKnockbackModifier", cancellable = true)
	private void modifyKnockback(Entity entity, CallbackInfoReturnable<Float> cir) {
		if (ConfigManager.config.modEnabled && ConfigManager.config.modifyKnockback) {
			cir.setReturnValue(ConfigManager.config.knockbackModifier);
		}
	}

	@Inject(at = @At("HEAD"), method = "getBlastResistance", cancellable = true)
	private void modifyBlastResistance(Explosion explosion, BlockView world, BlockPos pos, BlockState blockState, FluidState fluidState, CallbackInfoReturnable<Optional<Float>> cir) {
		if (ConfigManager.config.modEnabled) {
			if (!ConfigManager.config.breakBlocks) {
				cir.setReturnValue(Optional.of(Float.MAX_VALUE));
			} else if(ConfigManager.config.ignoreBlastResistance) {
				cir.setReturnValue(Optional.of(0.0f));
			}
		}
	}
}