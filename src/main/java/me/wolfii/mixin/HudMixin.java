package me.wolfii.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.renderpearl.api.pipeline.RenderPipeline;
import me.wolfii.DrawContextFloatDrawTexture;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.Hud;
import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Hud.class)
public class HudMixin {
    @WrapOperation(
        method = "extractCrosshair",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;blitSprite(Lcom/mojang/renderpearl/api/pipeline/RenderPipeline;Lnet/minecraft/resources/Identifier;IIII)V", ordinal = 0
        )
    )
    private void centerTheCwosshairPwease(GuiGraphicsExtractor instance, RenderPipeline renderPipeline, Identifier location, int x, int y, int width, int height, Operation<Void> original) {
        float scaleFactor = (float) Minecraft.getInstance().getWindow().getGuiScale();
        float scaledCenterX = (Minecraft.getInstance().getWindow().getWidth() / scaleFactor) / 2f;
        float scaledCenterY = (Minecraft.getInstance().getWindow().getHeight() / scaleFactor) / 2f;
        ((DrawContextFloatDrawTexture) instance).centered_crosshair$blitSprite(
            renderPipeline,
            location,
            Math.round((scaledCenterX - 7.5f) * 4) / 4f,
            Math.round((scaledCenterY - 7.5f) * 4) / 4f,
            15,
            15
        );
    }
}