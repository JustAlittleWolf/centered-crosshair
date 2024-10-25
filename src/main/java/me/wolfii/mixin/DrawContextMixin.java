package me.wolfii.mixin;

import me.wolfii.DrawContextFloatDrawTexture;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.*;
import net.minecraft.client.texture.GuiAtlasManager;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import java.util.function.Function;

@Mixin(DrawContext.class)
public class DrawContextMixin implements DrawContextFloatDrawTexture {
    @Shadow
    @Final
    private MatrixStack matrices;
    @Shadow
    @Final
    private VertexConsumerProvider.Immediate vertexConsumers;
    @Shadow
    @Final
    private GuiAtlasManager guiAtlasManager;

    @Unique
    public void centered_crosshair$drawTexture(Function<Identifier, RenderLayer> renderLayers, Identifier texture, float x, float y, int width, int height) {
        Sprite sprite = this.guiAtlasManager.getSprite(texture);
        this.drawTexturedQuad(renderLayers, sprite.getAtlasId(), x, x + width, y, y + height, sprite.getMinU() + (0b1 / 32768f), sprite.getMaxU() + (0b1 / 32768f), sprite.getMinV() - (0b1 / 32768f), sprite.getMaxV() - (0b1 / 32768f), -1);
    }

    @Unique
    void drawTexturedQuad(Function<Identifier, RenderLayer> renderLayers, Identifier texture, float x1, float x2, float y1, float y2, float u1, float u2, float v1, float v2, int color) {
        RenderLayer renderLayer = renderLayers.apply(texture);
        Matrix4f matrix4f = this.matrices.peek().getPositionMatrix();
        VertexConsumer bufferBuilder = this.vertexConsumers.getBuffer(renderLayer);
        bufferBuilder.vertex(matrix4f, x1, y1, 0f).texture(u1, v1).color(color);
        bufferBuilder.vertex(matrix4f, x1, y2, 0f).texture(u1, v2).color(color);
        bufferBuilder.vertex(matrix4f, x2, y2, 0f).texture(u2, v2).color(color);
        bufferBuilder.vertex(matrix4f, x2, y1, 0f).texture(u2, v1).color(color);
    }
}
