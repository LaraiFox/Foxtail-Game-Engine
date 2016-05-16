package laraifox.foxtail.rendering;

import java.nio.ByteBuffer;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.EXTTextureFilterAnisotropic;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GLContext;

import laraifox.foxtail.core.Logger;

public class FrameBuffer {
	public static final int GENERATE_NO_TEXTURE = 0;
	public static final int GENERATE_COLOR_TEXTURE = 1;
	public static final int GENERATE_DEPTH_TEXTURE = 2;
	public static final int GENERATE_COLOR_DEPTH_TEXTURE = 3;

	private int frameBufferID;
	private int colorID, depthID;
	private int width, height;
	private int generateTextures;

	public FrameBuffer(int width, int height) {
		this(width, height, FrameBuffer.GENERATE_COLOR_DEPTH_TEXTURE);
	}

	public FrameBuffer(int width, int height, int generateTextures) {
		this.width = width;
		this.height = height;

		this.generateTextures = generateTextures;

		this.frameBufferID = GL30.glGenFramebuffers();
		GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, frameBufferID);
		GL11.glDrawBuffer(GL30.GL_COLOR_ATTACHMENT0);

		if ((generateTextures & FrameBuffer.GENERATE_COLOR_TEXTURE) == FrameBuffer.GENERATE_COLOR_TEXTURE) {
			this.createColorTexture(TextureFilter.DEFAULT_FILTER);
		} else {
			this.createColorAttachment();
		}

		if ((generateTextures & FrameBuffer.GENERATE_DEPTH_TEXTURE) == FrameBuffer.GENERATE_DEPTH_TEXTURE) {
			this.createDepthTexture(TextureFilter.DEFAULT_FILTER);
		} else {
			this.createDepthAttachment();
		}

		FrameBuffer.unbindFrameBuffer();
	}

	public static void unbindFrameBuffer() {
		GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, 0);
		GL11.glViewport(0, 0, Display.getWidth(), Display.getHeight());
	}

	public static void unbindTexture() {
		Texture2D.unbind(0);
	}

	public static void unbindTexture(int i) {
		GL13.glActiveTexture(GL13.GL_TEXTURE0 + i);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
	}

	public void bindFrameBuffer() {
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
		GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, frameBufferID);
		GL11.glViewport(0, 0, width, height);
	}

	public void bindColorTexture() {
		this.bindColorTexture(0);
	}

	public void bindColorTexture(int i) {
		GL13.glActiveTexture(GL13.GL_TEXTURE0 + i);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, colorID);
	}

	public void bindDepthTexture() {
		this.bindDepthTexture(0);
	}

	public void bindDepthTexture(int i) {
		GL13.glActiveTexture(GL13.GL_TEXTURE0 + i);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, depthID);
	}

	public void cleanUp() {
		GL30.glDeleteFramebuffers(frameBufferID);

		if ((generateTextures & FrameBuffer.GENERATE_COLOR_TEXTURE) == FrameBuffer.GENERATE_COLOR_TEXTURE) {
			GL11.glDeleteTextures(colorID);
		} else {
			GL30.glDeleteRenderbuffers(colorID);
		}

		if ((generateTextures & FrameBuffer.GENERATE_DEPTH_TEXTURE) == FrameBuffer.GENERATE_DEPTH_TEXTURE) {
			GL11.glDeleteTextures(depthID);
		} else {
			GL30.glDeleteRenderbuffers(depthID);
		}
	}

	private void createColorTexture(TextureFilter textureFilter) {
		this.colorID = GL11.glGenTextures();
		this.createTexture(colorID, GL11.GL_RGBA, textureFilter);
	}

	private void createDepthTexture(TextureFilter textureFilter) {
		this.depthID = GL11.glGenTextures();
		this.createTexture(depthID, GL11.GL_DEPTH_COMPONENT, textureFilter);
	}

	private void createTexture(int id, int format, TextureFilter textureFilter) {
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, id);

		ByteBuffer buffer = null;
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, format, width, height, 0, format, GL11.GL_UNSIGNED_BYTE, buffer);

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
						Logger.log("Texture anisotropy has been limited to the max supported anisotropy! (" + supportedAnisotropy + ")", "FrameBuffer", Logger.MESSAGE_LEVEL_WARNING);
						textureAnisotropy = supportedAnisotropy;
					}

					GL11.glTexParameterf(GL11.GL_TEXTURE_2D, EXTTextureFilterAnisotropic.GL_TEXTURE_MAX_ANISOTROPY_EXT, textureAnisotropy);
				} else {
					Logger.log("Anisotropic filtering is not supported!", "FrameBuffer", Logger.MESSAGE_LEVEL_WARNING);
				}
			}
		}

		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
	}

	private void createColorAttachment() {
		this.colorID = GL30.glGenRenderbuffers();
		GL30.glBindRenderbuffer(GL30.GL_RENDERBUFFER, colorID);
		GL30.glRenderbufferStorage(GL30.GL_RENDERBUFFER, GL11.GL_RGBA, width, height);
		GL30.glFramebufferRenderbuffer(GL30.GL_FRAMEBUFFER, GL30.GL_COLOR_ATTACHMENT0, GL30.GL_RENDERBUFFER, colorID);
	}

	private void createDepthAttachment() {
		this.depthID = GL30.glGenRenderbuffers();
		GL30.glBindRenderbuffer(GL30.GL_RENDERBUFFER, depthID);
		GL30.glRenderbufferStorage(GL30.GL_RENDERBUFFER, GL11.GL_DEPTH_COMPONENT, width, height);
		GL30.glFramebufferRenderbuffer(GL30.GL_FRAMEBUFFER, GL30.GL_DEPTH_ATTACHMENT, GL30.GL_RENDERBUFFER, depthID);
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
	}
}
