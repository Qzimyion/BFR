package qzimyion.betterfireresistance.mixin.client;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameOverlayRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.effect.StatusEffects;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Environment(value= EnvType.CLIENT)
@Mixin(InGameOverlayRenderer.class)
public class NoFireOverlayMixin {


    @WrapWithCondition(method = "renderOverlays",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/hud/InGameOverlayRenderer;" +
                            "renderFireOverlay(Lnet/minecraft/client/MinecraftClient;" +
                            "Lnet/minecraft/client/util/math/MatrixStack;)V"))
    private static boolean noFireOverlay(MinecraftClient client, MatrixStack matrices) {
        return client.player == null || !client.player.hasStatusEffect(StatusEffects.FIRE_RESISTANCE);
    }

}
