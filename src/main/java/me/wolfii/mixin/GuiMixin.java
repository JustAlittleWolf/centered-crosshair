package me.wolfii.mixin;

import com.mojang.blaze3d.pipeline.RenderPipeline;
import me.wolfii.DrawContextFloatDrawTexture;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Gui.class)
public class GuiMixin {
    @Redirect(
        method = "extractCrosshair",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;blitSprite(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/resources/Identifier;IIII)V", ordinal = 0
        )
    )
    private void drawTextureRedirect(GuiGraphicsExtractor instance, RenderPipeline pipeline, Identifier sprite, int x, int y, int width, int height) {
        float scaleFactor = (float) Minecraft.getInstance().getWindow().getGuiScale();
        float scaledCenterX = (Minecraft.getInstance().getWindow().getWidth() / scaleFactor) / 2f;
        float scaledCenterY = (Minecraft.getInstance().getWindow().getHeight() / scaleFactor) / 2f;
        ((DrawContextFloatDrawTexture) instance).centered_crosshair$drawGuiTexture(
            pipeline,
            sprite,
            Math.round((scaledCenterX - 7.5f) * 4) / 4f,
            Math.round((scaledCenterY - 7.5f) * 4) / 4f,
            15,
            15
        );
    }
}