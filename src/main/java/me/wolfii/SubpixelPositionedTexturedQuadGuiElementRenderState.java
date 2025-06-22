package me.wolfii;

import com.mojang.blaze3d.pipeline.RenderPipeline;
import net.minecraft.client.gui.ScreenRect;
import net.minecraft.client.gui.render.state.SimpleGuiElementRenderState;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.texture.TextureSetup;
import org.joml.Matrix3x2f;

public record SubpixelPositionedTexturedQuadGuiElementRenderState(
    RenderPipeline pipeline,
    TextureSetup textureSetup,
    Matrix3x2f pose,
    float x1,
    float y1,
    float x2,
    float y2,
    float u1,
    float u2,
    float v1,
    float v2,
    int color,
    ScreenRect scissorArea,
    ScreenRect bounds
) implements SimpleGuiElementRenderState {
    public SubpixelPositionedTexturedQuadGuiElementRenderState(
        RenderPipeline pipeline,
        TextureSetup textureSetup,
        Matrix3x2f pose,
        float x1,
        float y1,
        float x2,
        float y2,
        float u1,
        float u2,
        float v1,
        float v2,
        int color,
        ScreenRect scissorArea
    ) {
        this(pipeline, textureSetup, pose, x1, y1, x2, y2, u1, u2, v1, v2, color, scissorArea, createBounds(x1, y1, x2, y2, pose, scissorArea));
    }

    @Override
    public void setupVertices(VertexConsumer vertices, float depth) {
        vertices.vertex(this.pose(), this.x1(), this.y1(), depth).texture(this.u1(), this.v1()).color(this.color());
        vertices.vertex(this.pose(), this.x1(), this.y2(), depth).texture(this.u1(), this.v2()).color(this.color());
        vertices.vertex(this.pose(), this.x2(), this.y2(), depth).texture(this.u2(), this.v2()).color(this.color());
        vertices.vertex(this.pose(), this.x2(), this.y1(), depth).texture(this.u2(), this.v1()).color(this.color());
    }

    private static ScreenRect createBounds(float x1, float y1, float x2, float y2, Matrix3x2f pose, ScreenRect scissorArea) {
        ScreenRect screenRect = new ScreenRect(Math.round(x1), Math.round(y1), Math.round(x2 - x1), Math.round(y2 - y1)).transformEachVertex(pose);
        return scissorArea != null ? scissorArea.intersection(screenRect) : screenRect;
    }
}
