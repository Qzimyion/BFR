package com.qzimyion.betterfireresistance.mixin;

import com.qzimyion.betterfireresistance.BFRConfig;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public abstract class NoFireOverPlayerMixin {

    @Shadow
    public abstract Level level();

    @Shadow public abstract AABB getBoundingBox();

    @Shadow private int remainingFireTicks;

    @Inject(method = "setRemainingFireTicks", at = @At("TAIL"))
    private void setFireTicks(int ticks, CallbackInfo ci) {
        multiLoader_Template$extinguishFire();
    }

    @Inject(method = "baseTick", at = @At("TAIL"))
    private void baseTick(CallbackInfo ci) {
        if (((Entity) (Object) this).isOnFire()) multiLoader_Template$extinguishFire();
    }

    @Unique
    public void multiLoader_Template$extinguishFire() {
        if ((Entity)(Object) this instanceof LivingEntity) {
            if (BFRConfig.defaultFireOffInCreavite &&((Entity) (Object) this) instanceof Player && ((Player) (Object) this).isCreative()) {
                this.remainingFireTicks = 0;
                return;
            }
            if (((Entity) (Object) this).getRemainingFireTicks() > 0 && ((LivingEntity) (Object) this).hasEffect(MobEffects.FIRE_RESISTANCE)
                    && !(this.level().getBlockStatesIfLoaded(this.getBoundingBox().contract(0.001D, 0.001D, 0.001D)).allMatch((blockState)
                    -> blockState.is(BlockTags.FIRE) || blockState.is(Blocks.LAVA)))
            ){
                this.remainingFireTicks = 0;
            }

        }
    }
}
