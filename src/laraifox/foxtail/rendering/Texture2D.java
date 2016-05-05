package laraifox.foxtail.rendering;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.EXTTextureFilterAnisotropic;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GLContext;

import laraifox.foxtail.core.Logger;
import laraifox.foxtail.core.math.Vector4f;

public class Texture2D {
	private static final TextureFilter DEFAULT_TEXTURE_FILTER = new TextureFilter();
	private static final int BYTES_PER_PIXEL = 4;

	private int textureID;
	private int width, height;

	public Texture2D(String filepath) {
		this(filepath, DEFAULT_TEXTURE_FILTER);
	}

	public Texture2D(String filepath, TextureFilter textureFilter) {
		try {
			BufferedImage image = ImageIO.read(new File(filepath));

			this.width = image.getWidth();
			this.height = image.getHeight();

			int[] pixels = new int[image.getWidth() * image.getHeight()];
			image.getRGB(0, 0, image.getWidth(), image.getHeight(), pixels, 0, image.getWidth());

			ByteBuffer buffer = BufferUtils.createByteBuffer(pixels.length * BYTES_PER_PIXEL);
			for (int i = 0; i < pixels.length; i++) {
				int pixel = pixels[i];

				buffer.put((byte) ((pixel >> 16) & 0xFF));
				buffer.put((byte) ((pixel >> 8) & 0xFF));
				buffer.put((byte) (pixel & 0xFF));
				buffer.put((byte) ((pixel >> 24) & 0xFF));
			}

			buffer.flip();

			this.createTexture(buffer, textureFilter);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	public Texture2D(ByteBuffer buffer, int width, int height) {
		this.width = width;
		this.height = height;

		this.createTexture(buffer, DEFAULT_TEXTURE_FILTER);
	}

	public Texture2D(ByteBuffer buffer, int width, int height, TextureFilter textureFilter) {
		this.width = width;
		this.height = height;

		this.createTexture(buffer, textureFilter);
	}

	public Texture2D(Vector4f color) {
		this.width = 1;
		this.height = 1;

		ByteBuffer buffer = BufferUtils.createByteBuffer(BYTES_PER_PIXEL);
		buffer.put((byte) (color.getX() * 0xFF));
		buffer.put((byte) (color.getY() * 0xFF));
		buffer.put((byte) (color.getZ() * 0xFF));
		buffer.put((byte) (color.getW() * 0xFF));

		buffer.flip();

		this.createTexture(buffer, DEFAULT_TEXTURE_FILTER);
	}

	@Override
	public void finalize() {
		//		GL11.glDeleteTextures(textureID);
	}

	private void createTexture(ByteBuffer buffer, TextureFilter textureFilter) {
		this.textureID = GL11.glGenTextures();
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureID);

		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, width, height, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buffer);

		textureFilter.apply(GL11.GL_TEXTURE_2D);
		if (textureFilter.getGLTextureMinFilter() == GL11.GL_NEAREST_MIPMAP_NEAREST || textureFilter.getGLTextureMinFilter() == GL11.GL_LINEAR_MIPMAP_NEAREST || //
			textureFilter.getGLTextureMinFilter() == GL11.GL_NEAREST_MIPMAP_LINEAR || textureFilter.getGLTextureMinFilter() == GL11.GL_LINEAR_MIPMAP_LINEAR || //
			textureFilter.getGLTextureMagFilter() == GL11.GL_NEAREST_MIPMAP_NEAREST || textureFilter.getGLTextureMagFilter() == GL11.GL_LINEAR_MIPMAP_NEAREST || //
			textureFilter.getGLTextureMagFilter() == GL11.GL_NEAREST_MIPMAP_LINEAR || textureFilter.getGLTextureMagFilter() == GL11.GL_LINEAR_MIPMAP_LINEAR) {
			GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);
			if (textureFilter.getGLTextureAnisotropy() > 0.0f) {
				if (GLContext.getCapabilities().GL_EXT_texture_filter_anisotropic) {
					float textureAnisotropy = textureFilter.getGLTextureAnisotropy();
					float supportedAnisotropy = GL11.glGetFloat(EXTTextureFilterAnisotropic.GL_MAX_TEXTURE_MAX_ANISOTROPY_EXT);
					if (textureAnisotropy > supportedAnisotropy) {
						Logger.log("Texture anisotropy has been limited to the max supported anisotropy! (" + supportedAnisotropy + ")", "Texture2D", Logger.MESSAGE_LEVEL_WARNING);
						textureAnisotropy = supportedAnisotropy;
					}

					GL11.glTexParameterf(GL11.GL_TEXTURE_2D, EXTTextureFilterAnisotropic.GL_TEXTURE_MAX_ANISOTROPY_EXT, textureAnisotropy);
				} else {
					Logger.log("Anisotropic filtering is not supported!", "Texture2D", Logger.MESSAGE_LEVEL_WARNING);
				}
			}
		}

		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
	}

	public void bind() {
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureID);
	}

	public int getTextureID() {
		return textureID;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
}
