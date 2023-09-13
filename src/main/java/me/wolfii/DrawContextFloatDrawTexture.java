package me.wolfii;

import net.minecraft.util.Identifier;

public interface DrawContextFloatDrawTexture {
    default void centered_crosshair$drawTexture(Identifier texture, float x, float y, float width, float height) {}
}
