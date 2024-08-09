package com.example.examplemod.mixin;

import com.example.examplemod.ExampleMod;
import com.example.examplemod.FogConfig;
import com.mojang.blaze3d.shaders.FogShape;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Camera;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.renderer.FogRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FogRenderer.class)
public class ExampleMixin {

    @Inject(at = @At("TAIL"), method = "setupFog")
    private static void foger(Camera camera, FogRenderer.FogMode fogMode, float g, boolean bl, float h, CallbackInfo ci) {
        FogConfig config = FogConfig.loadConfig();

        RenderSystem.setShaderFogStart(config.getFogStart());
        RenderSystem.setShaderFogEnd(config.getFogEnd());
        RenderSystem.setShaderFogShape(config.getFogShape());
    }
}
