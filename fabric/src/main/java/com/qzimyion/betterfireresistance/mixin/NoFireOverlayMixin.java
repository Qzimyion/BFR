package com.qzimyion.betterfireresistance.mixin;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ScreenEffectRenderer;
import net.minecraft.world.effect.MobEffects;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Environment(EnvType.CLIENT)
@Mixin(ScreenEffectRenderer.class)
public class NoFireOverlayMixin {

    @WrapWithCondition(method = "renderScreenEffect",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/ScreenEffectRenderer;" +
                            "renderFire(Lnet/minecraft/client/Minecraft;" +
                            "Lcom/mojang/blaze3d/vertex/PoseStack;)V"))
    private static boolean noFireOverlay(Minecraft client, PoseStack poseStack) {
        return client.player == null || !client.player.hasEffect(MobEffects.FIRE_RESISTANCE);
    }
}
