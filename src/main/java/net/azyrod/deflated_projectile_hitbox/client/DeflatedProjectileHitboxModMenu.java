package net.azyrod.deflated_projectile_hitbox.client;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;

import static net.azyrod.deflated_projectile_hitbox.config.DeflatedProjectileConfig.HANDLER;

public class DeflatedProjectileHitboxModMenu implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return (parent) -> HANDLER.generateGui().generateScreen(parent);
    }
}
