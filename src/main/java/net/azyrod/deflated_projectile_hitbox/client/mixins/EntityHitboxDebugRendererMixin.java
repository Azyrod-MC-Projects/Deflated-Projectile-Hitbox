package net.azyrod.deflated_projectile_hitbox.client.mixins;

import net.minecraft.client.renderer.debug.EntityHitboxDebugRenderer;
import net.minecraft.gizmos.GizmoStyle;
import net.minecraft.gizmos.Gizmos;
import net.minecraft.util.ARGB;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.azyrod.deflated_projectile_hitbox.DeflatedProjectileHitbox.CONFIG;

@Mixin(EntityHitboxDebugRenderer.class)
public class EntityHitboxDebugRendererMixin {
    @Unique
    @Inject(method = "showHitboxes", at = @At("TAIL"))
    private void showInflatedBox(Entity entity, float partialTicks, boolean isServerEntity, CallbackInfo ci) {
        if (!CONFIG.showPickRadius) {
            return;
        }
        if (!(entity instanceof Projectile projectile)) return;

        float pickRadius = projectile.getPickRadius();
        if (pickRadius <= 0.0F) return;

        Vec3 current = entity.getPosition(partialTicks);
        Vec3 offset = current.subtract(entity.position());
        AABB inflated = entity.getBoundingBox().inflate(pickRadius).move(offset);

        Gizmos.cuboid(inflated, GizmoStyle.stroke(ARGB.colorFromFloat(1.0F, 1.0F, 1.0F, 0.0F)));
    }
}
