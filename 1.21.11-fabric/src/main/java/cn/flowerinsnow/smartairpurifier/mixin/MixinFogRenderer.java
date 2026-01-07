package cn.flowerinsnow.smartairpurifier.mixin;

import cn.flowerinsnow.smartairpurifier.config.Config;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.fog.FogRenderer;
import net.minecraft.client.renderer.fog.environment.*;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.material.FogType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(FogRenderer.class)
public class MixinFogRenderer {
	@Redirect(
			method = "setupFog",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/client/renderer/fog/environment/FogEnvironment;isApplicable(Lnet/minecraft/world/level/material/FogType;Lnet/minecraft/world/entity/Entity;)Z",
					ordinal = 0
			)
	)
	public boolean setupFogRedirectIsApplicable(FogEnvironment instance, FogType fogType, Entity entity) {
		return this.redirectIsApplicable(instance, fogType, entity);
	}

	@Redirect(
			method = "computeFogColor",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/client/renderer/fog/environment/FogEnvironment;isApplicable(Lnet/minecraft/world/level/material/FogType;Lnet/minecraft/world/entity/Entity;)Z",
					ordinal = 0
			)
	)
	public boolean computeFogColorRedirectIsApplicable(FogEnvironment instance, FogType fogType, Entity entity) {
		return this.redirectIsApplicable(instance, fogType, entity);
	}

	@Unique
	private boolean redirectIsApplicable(FogEnvironment instance, FogType fogType, Entity entity) {
		if (entity != Minecraft.getInstance().player) {
			return instance.isApplicable(fogType, entity);
		}
		switch (instance) {
			case LavaFogEnvironment ignored -> {
				if (Config.removeFogLava()) {
					return false;
				}
			}
			case PowderedSnowFogEnvironment ignored -> {
				if (Config.removeFogPowderSnow()) {
					return false;
				}
			}
			case BlindnessFogEnvironment ignored -> {
				if (Config.removeFogBlindness()) {
					return false;
				}
			}
			case DarknessFogEnvironment ignored -> {
				if (Config.removeFogDarkness()) {
					return false;
				}
			}
			case WaterFogEnvironment ignored -> {
				if (Config.removeFogWater()) {
					return false;
				}
			}
			case AtmosphericFogEnvironment ignored -> {
				switch (fogType) {
					case LAVA -> {
						if (Config.removeFogLava()) {
							return true;
						}
					}
					case WATER -> {
						if (Config.removeFogWater()) {
							return true;
						}
					}
					case POWDER_SNOW -> {
						if (Config.removeFogPowderSnow()) {
							return true;
						}
					}
				}
			}
			default -> throw new IllegalStateException("Unexpected value: " + instance);
		}
		return instance.isApplicable(fogType, entity);
	}
}