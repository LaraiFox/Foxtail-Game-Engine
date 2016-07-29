package laraifox.foxtail.rendering;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.EXTTextureFilterAnisotropic;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GLContext;

import laraifox.foxtail.core.Logger;

public class TextureCube {
	private static final int BYTES_PER_PIXEL = 4;

	private int textureID;

	public TextureCube(String[] filepaths) {
		this(filepaths, TextureFilter.DEFAULT_FILTER);
	}

	public TextureCube(String[] filepaths, TextureFilter textureFilter) {
		try {
			this.textureID = GL11.glGenTextures();
			GL13.glActiveTexture(GL13.GL_TEXTURE0);
			GL11.glBindTexture(GL13.GL_TEXTURE_CUBE_MAP, textureID);

			for (int i = 0; i < filepaths.length; i++) {
				BufferedImage image = ImageIO.read(new File(filepaths[i]));

				int width = image.getWidth();
				int height = image.getHeight();

				int[] pixels = new int[image.getWidth() * image.getHeight()];
				image.getRGB(0, 0, image.getWidth(), image.getHeight(), pixels, 0, image.getWidth());

				ByteBuffer buffer = BufferUtils.createByteBuffer(pixels.length * BYTES_PER_PIXEL);
				for (int j = 0; j < pixels.length; j++) {
					int pixel = pixels[j];

					buffer.put((byte) ((pixel >> 16) & 0xFF));
					buffer.put((byte) ((pixel >> 8) & 0xFF));
					buffer.put((byte) (pixel & 0xFF));
					buffer.put((byte) ((pixel >> 24) & 0xFF));
				}

				buffer.flip();

				GL11.glTexImage2D(GL13.GL_TEXTURE_CUBE_MAP_POSITIVE_X + i, 0, textureFilter.getTextureInternalFormat(), width, height, 0, textureFilter.getTextureFormat(), GL11.GL_UNSIGNED_BYTE,
						buffer);
			}

			textureFilter.apply(GL13.GL_TEXTURE_CUBE_MAP);
			if (textureFilter.getTextureMinFilter() == GL11.GL_NEAREST_MIPMAP_NEAREST || textureFilter.getTextureMinFilter() == GL11.GL_LINEAR_MIPMAP_NEAREST || //
				textureFilter.getTextureMinFilter() == GL11.GL_NEAREST_MIPMAP_LINEAR || textureFilter.getTextureMinFilter() == GL11.GL_LINEAR_MIPMAP_LINEAR || //
				textureFilter.getTextureMagFilter() == GL11.GL_NEAREST_MIPMAP_NEAREST || textureFilter.getTextureMagFilter() == GL11.GL_LINEAR_MIPMAP_NEAREST || //
				textureFilter.getTextureMagFilter() == GL11.GL_NEAREST_MIPMAP_LINEAR || textureFilter.getTextureMagFilter() == GL11.GL_LINEAR_MIPMAP_LINEAR) {
				GL30.glGenerateMipmap(GL13.GL_TEXTURE_CUBE_MAP);

				if (textureFilter.getTextureAnisotropy() > 0.0f) {
					if (GLContext.getCapabilities().GL_EXT_texture_filter_anisotropic) {
						float textureAnisotropy = textureFilter.getTextureAnisotropy();
						float supportedAnisotropy = GL11.glGetFloat(EXTTextureFilterAnisotropic.GL_MAX_TEXTURE_MAX_ANISOTROPY_EXT);
						if (textureAnisotropy > supportedAnisotropy) {
							Logger.log("Texture anisotropy has been limited to the max supported anisotropy! (" + supportedAnisotropy + ")", "TextureCube", Logger.MESSAGE_LEVEL_WARNING);
							textureAnisotropy = supportedAnisotropy;
						}

						GL11.glTexParameterf(GL11.GL_TEXTURE_2D, EXTTextureFilterAnisotropic.GL_TEXTURE_MAX_ANISOTROPY_EXT, textureAnisotropy);
					} else {
						Logger.log("Anisotropic filtering is not supported!", "TextureCube", Logger.MESSAGE_LEVEL_WARNING);
					}
				}
			}

			GL11.glBindTexture(GL13.GL_TEXTURE_CUBE_MAP, 0);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	public static void unbind() {
		Texture2D.unbind(0);
	}

	public static void unbind(int i) {
		GL13.glActiveTexture(GL13.GL_TEXTURE0 + i);
		GL11.glBindTexture(GL13.GL_TEXTURE_CUBE_MAP, 0);
	}

	public void bind() {
		this.bind(0);
	}

	public void bind(int i) {
		GL13.glActiveTexture(GL13.GL_TEXTURE0 + i);
		GL11.glBindTexture(GL13.GL_TEXTURE_CUBE_MAP, textureID);
	}

	public void cleanUp() {
		GL11.glDeleteTextures(textureID);
	}
}
