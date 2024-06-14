package qzimyion.betterfireresistance.mixin;

import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import qzimyion.betterfireresistance.config.BFRConfig;

import static net.minecraft.entity.effect.StatusEffects.FIRE_RESISTANCE;

@Mixin(Entity.class)
public abstract class NoFireOnEntityMixin {
    @Shadow private int fireTicks;

    @Shadow private World world;

    @Shadow public abstract Box getBoundingBox();

    @Inject(method = "setFireTicks", at = @At("TAIL"))
    private void setFireTicks(int ticks, CallbackInfo ci) {
        extinguishFire();
    }

    @Inject(method = "baseTick", at = @At("TAIL"))
    private void baseTick(CallbackInfo ci) {
        if (((Entity) (Object) this).isOnFire()) extinguishFire();
    }

    @Unique
    public void extinguishFire() {
        if ((Entity)(Object) this instanceof LivingEntity) {
            if (BFRConfig.defaultFireOffInCreavite && ((Entity) (Object) this) instanceof PlayerEntity && ((PlayerEntity) (Object) this).isCreative()) {
                this.fireTicks = 0;
                return;
            }
            if (((Entity) (Object) this).getFireTicks() > 0 && ((LivingEntity) (Object) this).hasStatusEffect(FIRE_RESISTANCE)
                    && !(this.world.getStatesInBoxIfLoaded(this.getBoundingBox().contract(0.001D)).allMatch((blockState)
                    -> blockState.isIn(BlockTags.FIRE) || blockState.isOf(Blocks.LAVA)))
            ){
                this.fireTicks = 0;
            }

        }
    }
}
