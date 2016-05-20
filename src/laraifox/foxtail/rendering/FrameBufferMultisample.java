package laraifox.foxtail.rendering;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;

public class FrameBufferMultisample extends FrameBuffer {
	private int multisamples;

	public FrameBufferMultisample(int width, int height, int multisamples) {
		this.width = width;
		this.height = height;

		this.generateTextures = FrameBuffer.GENERATE_NO_TEXTURE;

		this.frameBufferID = GL30.glGenFramebuffers();
		GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, frameBufferID);
		GL11.glDrawBuffer(GL30.GL_COLOR_ATTACHMENT0);

		this.createColorAttachment();
		this.createDepthAttachment();

		FrameBuffer.unbindFrameBuffer();
	}

	private void createColorAttachment() {
		this.colorID = GL30.glGenRenderbuffers();
		GL30.glBindRenderbuffer(GL30.GL_RENDERBUFFER, colorID);
		GL30.glRenderbufferStorageMultisample(GL30.GL_RENDERBUFFER, multisamples, GL11.GL_RGBA, width, height);
		GL30.glFramebufferRenderbuffer(GL30.GL_FRAMEBUFFER, GL30.GL_COLOR_ATTACHMENT0, GL30.GL_RENDERBUFFER, colorID);
	}

	private void createDepthAttachment() {
		this.depthID = GL30.glGenRenderbuffers();
		GL30.glBindRenderbuffer(GL30.GL_RENDERBUFFER, depthID);
		GL30.glRenderbufferStorageMultisample(GL30.GL_RENDERBUFFER, multisamples, GL11.GL_DEPTH_COMPONENT, width, height);
		GL30.glFramebufferRenderbuffer(GL30.GL_FRAMEBUFFER, GL30.GL_DEPTH_ATTACHMENT, GL30.GL_RENDERBUFFER, depthID);
	}
}
