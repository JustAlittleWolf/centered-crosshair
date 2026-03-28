package me.wolfii.mixin;

import com.mojang.blaze3d.pipeline.RenderPipeline;
import me.wolfii.DrawContextFloatDrawTexture;
import me.wolfii.SubpixelPositionedTexturedQuadGuiElementRenderState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.render.TextureSetup;
import net.minecraft.client.renderer.state.gui.GuiRenderState;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.Identifier;
import org.joml.Matrix3x2f;
import org.joml.Matrix3x2fStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.Inject;

@Mixin(GuiGraphicsExtractor.class)
public class GuiGraphicsExtractorMixin implements DrawContextFloatDrawTexture {
    @Shadow
    @Final
    private TextureAtlas guiSprites;
    @Shadow
    @Final
	GuiRenderState guiRenderState;
    @Shadow
    @Final
	Minecraft minecraft;
    @Shadow
    @Final
    private Matrix3x2fStack pose;
    @Shadow
    @Final
    private GuiGraphicsExtractor.ScissorStack scissorStack;

    @Unique
    public void centered_crosshair$drawGuiTexture(RenderPipeline pipeline, Identifier texture, float x, float y, int width, int height) {
        if (width == 0 || height == 0) return;
        TextureAtlasSprite sprite = this.guiSprites.getSprite(texture);
        this.drawTexturedQuad(
            pipeline,
            sprite.atlasLocation(),
            x,
            x + width,
            y,
            y + height,
            sprite.getU0() + (0b1 / 32768f),
            sprite.getU1() + (0b1 / 32768f),
            sprite.getV0() - (0b1 / 32768f),
            sprite.getV1() - (0b1 / 32768f),
            -1
        );
    }

    @Unique
    void drawTexturedQuad(RenderPipeline pipeline, Identifier sprite, float x1, float x2, float y1, float y2, float u1, float u2, float v1, float v2, int color) {
		AbstractTexture abstractTexture = this.minecraft.getTextureManager().getTexture(sprite);
        this.guiRenderState.addGuiElement(new SubpixelPositionedTexturedQuadGuiElementRenderState(
            pipeline,
            TextureSetup.singleTexture(abstractTexture.getTextureView(), abstractTexture.getSampler()),
            new Matrix3x2f(this.pose),
            x1,
            y1,
            x2,
            y2,
            u1,
            u2,
            v1,
            v2,
            color,
            this.scissorStack.peek()
        ));
    }
}
