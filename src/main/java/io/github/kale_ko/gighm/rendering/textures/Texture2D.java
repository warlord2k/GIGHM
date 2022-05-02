package io.github.kale_ko.gighm.rendering.textures;

import static org.lwjgl.opengl.GL20.*;
import java.nio.ByteBuffer;
import javax.validation.constraints.NotNull;
import io.github.kale_ko.gighm.rendering.shaders.Shader;

/**
 * A 2D texture
 * 
 * @since 1.0.0
 */
public class Texture2D {
    /**
     * The width of the texture
     * 
     * @since 1.0.0
     */
    private @NotNull int width;

    /**
     * The height of the texture
     * 
     * @since 1.0.0
     */
    private @NotNull int height;

    /**
     * The raw data of the texture
     * 
     * @since 1.0.0
     */
    private @NotNull ByteBuffer data;

    /**
     * Weather the window is initialized
     * 
     * @since 1.0.0
     */
    private @NotNull boolean initialized = false;

    /**
     * The id of the texture
     * 
     * @since 1.0.0
     */
    private int textureId;

    /**
     * Create a 2D texture
     * 
     * @param width The width of the texture
     * @param height The height of the texture
     * @param data The raw data of the texture
     * 
     * @see Texture2DLoader#loadTexture
     * 
     * @since 1.0.0
     */
    public Texture2D(@NotNull int width, @NotNull int height, @NotNull ByteBuffer data) {
        this.width = width;
        this.height = height;

        this.data = data;
    }

    /**
     * Initialize the texture (Must be called from a {@link Renderer})
     * 
     * @throws RuntimeException If the texture is already initialized
     * 
     * @since 1.0.0
     */
    public void init() {
        if (this.initialized) {
            throw new RuntimeException("The texture is already initialized");
        }

        this.initialized = true;

        textureId = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, textureId);

        glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);

        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, data);
    }

    /**
     * Bind the texture for use (Must be called from a {@link Renderer})
     * 
     * @since 1.0.0
     */
    public void bind(Shader shader) {
        shader.setUniform("sampler", this.textureId);

        glActiveTexture(GL_TEXTURE0 + this.textureId);
        glBindTexture(GL_TEXTURE_2D, this.textureId);
    }

    /**
     * Get weather the texture is initialized
     * 
     * @return Weather the texture is initialized
     * 
     * @since 1.0.0
     */
    public @NotNull boolean getInitialized() {
        return this.initialized;
    }

    /**
     * Get the id of the texture
     * 
     * @return The id of the texture
     * 
     * @since 1.0.0
     */
    public @NotNull int getTextureId() {
        return this.textureId;
    }
}