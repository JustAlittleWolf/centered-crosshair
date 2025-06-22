package me.wolfii;

import com.mojang.blaze3d.pipeline.RenderPipeline;
import net.minecraft.util.Identifier;

public interface DrawContextFloatDrawTexture {
    default void centered_crosshair$drawGuiTexture(RenderPipeline pipeline, Identifier texture, float x, float y, int width, int height) {}
}
