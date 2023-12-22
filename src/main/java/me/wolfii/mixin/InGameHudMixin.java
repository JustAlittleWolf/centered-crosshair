package me.wolfii.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
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
    private static Identifier CROSSHAIR_TEXTURE;

    @Redirect(method = "renderCrosshair", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawGuiTexture(Lnet/minecraft/util/Identifier;IIII)V", ordinal = 0))
    private void drawTextureRedirect(DrawContext instance, Identifier texture, int x, int y, int width, int height) {
        float scaleFactor = (float) MinecraftClient.getInstance().getWindow().getScaleFactor();
        float scaledCenterX = (MinecraftClient.getInstance().getWindow().getFramebufferWidth() / scaleFactor) / 2f;
        float scaledCenterY = (MinecraftClient.getInstance().getWindow().getFramebufferHeight() / scaleFactor) / 2f;
        instance.centered_crosshair$drawTexture(CROSSHAIR_TEXTURE, Math.round((scaledCenterX - 7.5f) * 4) / 4f, Math.round((scaledCenterY - 7.5f) * 4) / 4f, 15, 15);
    }
}