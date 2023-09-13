package me.wolfii.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import me.wolfii.DrawContextFloatDrawTexture;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(DrawContext.class)
public class DrawContextMixin implements DrawContextFloatDrawTexture {
    @Shadow
    @Final
    private MatrixStack matrices;

    @Unique
    public void centered_crosshair$drawTexture(Identifier texture, float x, float y, float width, float height) {
        float textureSize = 256f;
        this.drawTexturedQuad(texture, x, x + width, y, y + height, width / textureSize + 0.1f/textureSize, height / textureSize + 0.1f/textureSize);
    }

    @Unique
    void drawTexturedQuad(Identifier texture, float x1, float x2, float y1, float y2, float u2, float v2) {
        RenderSystem.setShaderTexture(0, texture);
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        Matrix4f matrix4f = this.matrices.peek().getPositionMatrix();
        BufferBuilder bufferBuilder = Tessellator.getInstance().getBuffer();
        bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE);
        bufferBuilder.vertex(matrix4f, x1, y1, 0f).texture(0f, 0f).next();
        bufferBuilder.vertex(matrix4f, x1, y2, 0f).texture(0f, v2).next();
        bufferBuilder.vertex(matrix4f, x2, y2, 0f).texture(u2, v2).next();
        bufferBuilder.vertex(matrix4f, x2, y1, 0f).texture(u2, 0f).next();
        BufferRenderer.drawWithGlobalProgram(bufferBuilder.end());
    }
}
