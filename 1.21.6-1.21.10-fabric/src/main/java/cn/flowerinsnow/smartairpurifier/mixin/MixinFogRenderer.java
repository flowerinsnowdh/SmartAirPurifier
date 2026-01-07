package cn.flowerinsnow.smartairpurifier.mixin;

import cn.flowerinsnow.smartairpurifier.config.Config;
import net.minecraft.block.enums.CameraSubmersionType;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.fog.*;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(FogRenderer.class)
public class MixinFogRenderer {
	@Redirect(
			method = "applyFog(Lnet/minecraft/client/render/Camera;IZLnet/minecraft/client/render/RenderTickCounter;FLnet/minecraft/client/world/ClientWorld;)Lorg/joml/Vector4f;",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/client/render/fog/FogModifier;shouldApply(Lnet/minecraft/block/enums/CameraSubmersionType;Lnet/minecraft/entity/Entity;)Z",
					ordinal = 0
			)
	)
	public boolean setupFogRedirectIsApplicable(FogModifier instance, CameraSubmersionType cameraSubmersionType, Entity entity) {
		return this.redirectIsApplicable(instance, cameraSubmersionType, entity);
	}

	@Redirect(
			method = "getFogColor",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/client/render/fog/FogModifier;shouldApply(Lnet/minecraft/block/enums/CameraSubmersionType;Lnet/minecraft/entity/Entity;)Z",
					ordinal = 0
			)
	)
	public boolean computeFogColorRedirectIsApplicable(FogModifier instance, CameraSubmersionType fogType, Entity entity) {
		return this.redirectIsApplicable(instance, fogType, entity);
	}

	@Unique
	private boolean redirectIsApplicable(FogModifier instance, CameraSubmersionType fogType, Entity entity) {
		if (entity != MinecraftClient.getInstance().player) {
			return instance.shouldApply(fogType, entity);
		}
		switch (instance) {
			case LavaFogModifier ignored -> {
				if (Config.removeFogLava()) {
					return false;
				}
			}
			case PowderSnowFogModifier ignored -> {
				if (Config.removeFogPowderSnow()) {
					return false;
				}
			}
			case BlindnessEffectFogModifier ignored -> {
				if (Config.removeFogBlindness()) {
					return false;
				}
			}
			case DarknessEffectFogModifier ignored -> {
				if (Config.removeFogDarkness()) {
					return false;
				}
			}
			case WaterFogModifier ignored -> {
				if (Config.removeFogWater()) {
					return false;
				}
			}
			case AtmosphericFogModifier ignored -> {
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
			case DimensionOrBossFogModifier ignored -> {
			}
			default -> throw new IllegalStateException("Unexpected value: " + instance);
		}
		return instance.shouldApply(fogType, entity);
	}
}