package net.azyrod.deflated_projectile_hitbox;

import net.azyrod.deflated_projectile_hitbox.config.DeflatedProjectileConfig;
import net.fabricmc.api.ModInitializer;
import net.minecraft.resources.Identifier;

public class DeflatedProjectileHitbox implements ModInitializer {
    public static final String MOD_ID = "deflated_projectile_hitbox";
    public static DeflatedProjectileConfig CONFIG = null;

    public static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(MOD_ID, path);
    }

    @Override
    public void onInitialize() {
        if (DeflatedProjectileConfig.HANDLER.load()) {
            // We don't need to log since YACL already does it
        }
        CONFIG = DeflatedProjectileConfig.HANDLER.instance();
    }
}
