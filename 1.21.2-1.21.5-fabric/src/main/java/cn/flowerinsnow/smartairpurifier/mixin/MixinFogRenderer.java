package cn.flowerinsnow.smartairpurifier.mixin;

import cn.flowerinsnow.smartairpurifier.config.Config;
import net.minecraft.block.enums.CameraSubmersionType;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.BackgroundRenderer;
import net.minecraft.client.render.Camera;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(BackgroundRenderer.class)
public class MixinFogRenderer {
	@Redirect(
			method = "applyFog",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/client/render/Camera;getSubmersionType()Lnet/minecraft/block/enums/CameraSubmersionType;",
					ordinal = 0
			)
	)
    private static CameraSubmersionType setupFogRedirectGetSubmersionType(Camera instance) {
		return redirectGetSubmersionType(instance);
	}

	@Redirect(
			method = "applyFog",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/client/render/BackgroundRenderer;getFogModifier(Lnet/minecraft/entity/Entity;F)Lnet/minecraft/client/render/BackgroundRenderer$StatusEffectFogModifier;",
					ordinal = 0
			)
	)
    private static BackgroundRenderer.StatusEffectFogModifier setupFogRedirectGetSubmersionType(Entity entity, float tickProgress) {
		return redirectGetFogModifier(entity, tickProgress);
	}

	@Redirect(
			method = "getFogColor",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/client/render/Camera;getSubmersionType()Lnet/minecraft/block/enums/CameraSubmersionType;",
					ordinal = 0
			)
	)
    private static CameraSubmersionType getFogColorRedirectGetSubmersionType(Camera instance) {
		return redirectGetSubmersionType(instance);
	}

	@Redirect(
			method = "getFogColor",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/client/render/BackgroundRenderer;getFogModifier(Lnet/minecraft/entity/Entity;F)Lnet/minecraft/client/render/BackgroundRenderer$StatusEffectFogModifier;",
					ordinal = 0
			)
	)
	private static BackgroundRenderer.StatusEffectFogModifier getFogColorRedirectGetFogModifier(Entity entity, float tickProgress) {
		return redirectGetFogModifier(entity, tickProgress);
	}

	@Unique
	private static CameraSubmersionType redirectGetSubmersionType(Camera camera) {
		CameraSubmersionType cameraSubmersionType = camera.getSubmersionType();
		if (camera.getFocusedEntity() != MinecraftClient.getInstance().player) {
			return cameraSubmersionType;
		}
		switch (cameraSubmersionType) {
			case LAVA -> {
				if (Config.removeFogLava()) {
					return CameraSubmersionType.NONE;
				}
			}
			case WATER -> {
				if (Config.removeFogWater()) {
					return CameraSubmersionType.NONE;
				}
			}
			case POWDER_SNOW -> {
				if (Config.removeFogPowderSnow()) {
					return CameraSubmersionType.NONE;
				}
			}
		}
		return cameraSubmersionType;
	}

	@Unique
	private static BackgroundRenderer.StatusEffectFogModifier redirectGetFogModifier(Entity entity, float tickProgress) {
		BackgroundRenderer.StatusEffectFogModifier statusEffectFogModifier = BackgroundRenderer.getFogModifier(entity, tickProgress);
		if (statusEffectFogModifier == null || entity != MinecraftClient.getInstance().player) {
			return statusEffectFogModifier;
		}
		switch (statusEffectFogModifier) {
			case BackgroundRenderer.BlindnessFogModifier ignored -> {
				if (Config.removeFogBlindness()) {
					return null;
				}
			}
			case BackgroundRenderer.DarknessFogModifier ignored -> {
				if (Config.removeFogDarkness()) {
					return null;
				}
			}
            default -> throw new IllegalStateException("Unexpected value: " + statusEffectFogModifier);
        }
		return statusEffectFogModifier;
	}
}
