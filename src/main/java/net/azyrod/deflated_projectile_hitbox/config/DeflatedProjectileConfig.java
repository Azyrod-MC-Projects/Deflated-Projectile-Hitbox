package net.azyrod.deflated_projectile_hitbox.config;

import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.autogen.AutoGen;
import dev.isxander.yacl3.config.v2.api.autogen.Boolean;
import dev.isxander.yacl3.config.v2.api.autogen.EnumCycler;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import dev.isxander.yacl3.platform.YACLPlatform;
import net.azyrod.deflated_projectile_hitbox.DeflatedProjectileHitbox;

public class DeflatedProjectileConfig {
    public static final ConfigClassHandler<DeflatedProjectileConfig> HANDLER = ConfigClassHandler.createBuilder(DeflatedProjectileConfig.class)
            .id(DeflatedProjectileHitbox.id("config"))
            .serializer(config -> GsonConfigSerializerBuilder.create(config)
                    .setPath(YACLPlatform.getConfigDir().resolve("deflated_projectile_hitbox.json"))
                    .build())
            .build();

    @SerialEntry
    @AutoGen(category = "general")
    @EnumCycler
    public ProjectileHitboxMode projectileHitboxMode = ProjectileHitboxMode.BOUNDING_BOX;

    @SerialEntry
    @AutoGen(category = "general")
    @Boolean
    public boolean showPickRadius = false;
}
