package me.wolfii;

import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;

import java.util.function.Function;

public interface DrawContextFloatDrawTexture {
    default void centered_crosshair$drawTexture(Function<Identifier, RenderLayer> renderLayers, Identifier texture, float x, float y, int width, int height) {}
}
