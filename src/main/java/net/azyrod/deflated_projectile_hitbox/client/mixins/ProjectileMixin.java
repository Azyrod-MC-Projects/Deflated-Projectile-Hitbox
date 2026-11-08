package net.azyrod.deflated_projectile_hitbox.client.mixins;

import net.azyrod.deflated_projectile_hitbox.DeflatedProjectileHitbox;
import net.azyrod.deflated_projectile_hitbox.config.ProjectileHitboxMode;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.projectile.LlamaSpit;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.minecraft.world.entity.projectile.arrow.ThrownTrident;
import net.minecraft.world.entity.projectile.hurtingprojectile.windcharge.BreezeWindCharge;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;

@Mixin(Projectile.class)
public abstract class ProjectileMixin {
    @Unique
    Map<Class<?>, Float> PROJECTILE_TO_MAX_POWER = Map.of(
        LlamaSpit.class, 1.5F,
        AbstractArrow.class, 1.6F,
        ThrownTrident.class, 1.6f,
        BreezeWindCharge.class, 0.7F
    );

    @Unique
    Float max_power = null;

    @Shadow
    public abstract boolean isPickable();

    @Inject(method = "shoot(DDDFF)V", at = @At("HEAD"))
    public void saveShootPow(double xd, double yd, double zd, float pow, float uncertainty, CallbackInfo ci) {
        if (pow != 0.0F) {
            this.max_power = pow;
        }
    }

    @Inject(method = "getPickRadius()F", at = @At("HEAD"), cancellable = true)
    public void getPickRadius(CallbackInfoReturnable<Float> cir) {
        ProjectileHitboxMode mode = DeflatedProjectileHitbox.CONFIG.projectileHitboxMode;

        if (mode == ProjectileHitboxMode.VANILLA || !this.isPickable()) {
            return;
        }

        Projectile projectile = (Projectile) (Object) this;
        Vec3 movement = projectile.getDeltaMovement();

        if (mode == ProjectileHitboxMode.BOUNDING_BOX || mode == ProjectileHitboxMode.MOVING_ONLY && movement.equals(Vec3.ZERO)) {
            cir.setReturnValue(0.0F);
            return;
        }

        if (mode == ProjectileHitboxMode.LINEAR_SPEED) {
            if (this.max_power == null) {
                this.max_power = PROJECTILE_TO_MAX_POWER.getOrDefault(this.getClass(), 1.0F);
            }
            cir.setReturnValue((float) Mth.clampedMap(movement.length(), 0.0F, max_power, 0.0F, 1.0F));
        }
    }
}
