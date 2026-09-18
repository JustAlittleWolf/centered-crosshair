package me.wolfii;

import com.mojang.renderpearl.api.pipeline.RenderPipeline;
import net.minecraft.resources.Identifier;

public interface DrawContextFloatDrawTexture {
    default void centered_crosshair$blitSprite(RenderPipeline renderPipeline, Identifier location, float x, float y, int width, int height) {}
}
