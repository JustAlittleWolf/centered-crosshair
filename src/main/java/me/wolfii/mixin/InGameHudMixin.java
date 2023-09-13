package me.wolfii.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(InGameHud.class)
public class InGameHudMixin {
    @Shadow
    @Final
    private static Identifier ICONS;

    @Redirect(method = "renderCrosshair", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawTexture(Lnet/minecraft/util/Identifier;IIIIII)V"))
    private void drawTextureRedirect(DrawContext instance, Identifier texture, int x, int y, int u, int v, int width, int height) {
        float scaleFactor = (float) MinecraftClient.getInstance().getWindow().getScaleFactor();
        float scaledCenterX = (MinecraftClient.getInstance().getWindow().getFramebufferWidth() / scaleFactor) / 2f;
        float scaledCenterY = (MinecraftClient.getInstance().getWindow().getFramebufferHeight() / scaleFactor) / 2f;
        instance.centered_crosshair$drawTexture(ICONS, Math.round((scaledCenterX - 7.5f) * 4) / 4f, Math.round((scaledCenterY - 7.5f) * 4) / 4f, 15f, 15f);
    }

    @Redirect(method="renderCrosshair", at=@At(value="INVOKE", target = "Lnet/minecraft/client/util/math/MatrixStack;translate(FFF)V"))
    private void matrixstackTranslateRedirect(MatrixStack instance, float x, float y, float z) {
        float scaleFactor = (float) MinecraftClient.getInstance().getWindow().getScaleFactor();
        float scaledCenterX = (MinecraftClient.getInstance().getWindow().getFramebufferWidth() / scaleFactor) / 2f;
        float scaledCenterY = (MinecraftClient.getInstance().getWindow().getFramebufferHeight() / scaleFactor) / 2f;
        instance.translate(scaledCenterX, scaledCenterY, 0f);
    }
}