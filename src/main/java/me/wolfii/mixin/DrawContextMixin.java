package me.wolfii.mixin;

import com.mojang.blaze3d.pipeline.RenderPipeline;
import me.wolfii.DrawContextFloatDrawTexture;
import me.wolfii.SubpixelPositionedTexturedQuadGuiElementRenderState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.render.state.GuiRenderState;
import net.minecraft.client.texture.GuiAtlasManager;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.TextureSetup;
import net.minecraft.util.Identifier;
import org.joml.Matrix3x2f;
import org.joml.Matrix3x2fStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(DrawContext.class)
public class DrawContextMixin implements DrawContextFloatDrawTexture {
    @Shadow
    @Final
    private GuiAtlasManager guiAtlasManager;
    @Shadow
    @Final
    private GuiRenderState state;
    @Shadow
    @Final
    private MinecraftClient client;
    @Shadow
    @Final
    private Matrix3x2fStack matrices;
    @Shadow
    @Final
    private DrawContext.ScissorStack scissorStack;

    @Unique
    public void centered_crosshair$drawGuiTexture(RenderPipeline pipeline, Identifier texture, float x, float y, int width, int height) {
        if (width == 0 || height == 0) return;
        Sprite sprite = this.guiAtlasManager.getSprite(texture);
        this.drawTexturedQuad(
            pipeline,
            sprite.getAtlasId(),
            x,
            x + width,
            y,
            y + height,
            sprite.getMinU() + (0b1 / 32768f),
            sprite.getMaxU() + (0b1 / 32768f),
            sprite.getMinV() - (0b1 / 32768f),
            sprite.getMaxV() - (0b1 / 32768f),
            -1
        );
    }

    @Unique
    void drawTexturedQuad(RenderPipeline pipeline, Identifier sprite, float x1, float x2, float y1, float y2, float u1, float u2, float v1, float v2, int color) {
        this.state.addSimpleElement(new SubpixelPositionedTexturedQuadGuiElementRenderState(
            pipeline,
            TextureSetup.withoutGlTexture(this.client.getTextureManager().getTexture(sprite).getGlTextureView()),
            new Matrix3x2f(this.matrices),
            x1,
            y1,
            x2,
            y2,
            u1,
            u2,
            v1,
            v2,
            color,
            this.scissorStack.peekLast()
        ));
    }
}
