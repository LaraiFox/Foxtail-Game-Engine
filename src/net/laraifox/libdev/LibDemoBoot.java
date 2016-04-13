package net.laraifox.libdev;

import java.nio.ByteBuffer;

import net.laraifox.lib.display.OpenGLDisplay;
import net.laraifox.libdev.math.Vector3f;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL32;

public class LibDemoBoot extends OpenGLDisplay {
	Vector3f lerp = new Vector3f(-1, 0.25f, 0);
	Vector3f nlerp = new Vector3f(-1, 0.25f, 0);
	Vector3f slerp = new Vector3f(-1, -0.25f, 0);
	Vector3f movto = new Vector3f(-1, -0.25f, 0);

	int fbo;
	int texture;

	public static void main(String[] args) throws LWJGLException {
		LibDemoBoot test = new LibDemoBoot();
		test.setWidth(1600);
		test.setHeight(900);
		test.initialize();
		test.start();

		// testFunction();

		// OpenGLDisplay display = new GraphicsDemo();
		// display.initialize();
		// display.start();
	}

	private static void testFunction() {
		final float increment = 0.1f;

		float percentComplete = 0.0f;
		float alpha = increment / (100 - -100);

		float longestLengthSqN = 0.0f;
		float longestLengthSqP = 0.0f;
		float longestLengthN = 0.0f;
		float longestLengthP = 0.0f;

		for (float i = -100; i < 100; i += increment) {
			for (float j = -100; j < 100; j += increment) {
				for (float k = -100; k < 100; k += increment) {
					Vector3f vector = new Vector3f(k, j, i);

					if (!vector.isZero()) {
						vector.normalize();

						float lengthSq = vector.lengthSq() - 1.0f;
						float length = vector.length() - 1.0f;

						longestLengthSqN = Math.min(longestLengthSqN, lengthSq);
						longestLengthSqP = Math.max(longestLengthSqP, lengthSq);
						longestLengthN = Math.min(longestLengthN, length);
						longestLengthP = Math.max(longestLengthP, length);
					}
				}
			}

			percentComplete += alpha;
			System.out.println((percentComplete * 100.0f) + "% Complete");
		}

		System.out.println("");
		System.out.println(longestLengthSqN + ", " + longestLengthSqP + "  Sq Lengths");
		System.out.println(longestLengthN + ", " + longestLengthP + "  Lengths");
	}

	@Override
	protected void cleanUp() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void initializeVariables() {
		this.fbo = createFrameBuffer();
		this.texture = createTextureAttachment((int) width, (int) height);
	}

	private int createFrameBuffer() {
		int frameBuffer = GL30.glGenFramebuffers();
		// generate name for frame buffer
		GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, frameBuffer);
		// create the framebuffer
		GL11.glDrawBuffer(GL30.GL_COLOR_ATTACHMENT0);
		// indicate that we will always render to color attachment 0
		return frameBuffer;
	}

	private int createTextureAttachment(int width, int height) {
		int texture = GL11.glGenTextures();
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture);
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, width, height, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, (ByteBuffer) null);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
		GL32.glFramebufferTexture(GL30.GL_FRAMEBUFFER, GL30.GL_COLOR_ATTACHMENT0, texture, 0);
		return texture;
	}

	@Override
	protected void render() {
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

		GL11.glDisable(GL11.GL_TEXTURE_2D);

		this.bindFrameBuffer();

		GL11.glPointSize(2.0f);

		GL11.glBegin(GL11.GL_POINTS);
		GL11.glColor4f(0.0f, 1.0f, 0.0f, 0.5f);
		GL11.glVertex2f(lerp.getX(), lerp.getY());
		GL11.glColor4f(1.0f, 1.0f, 0.0f, 0.5f);
		GL11.glVertex2f(nlerp.getX(), nlerp.getY());
		GL11.glColor4f(1.0f, 0.5f, 0.0f, 0.5f);
		GL11.glVertex2f(slerp.getX(), slerp.getY());
		GL11.glColor4f(0.8f, 0.8f, 1.0f, 0.5f);
		GL11.glVertex2f(movto.getX(), movto.getY());
		GL11.glEnd();

		this.unbindFrameBuffer();

		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture);

		GL11.glEnable(GL11.GL_TEXTURE_2D);

		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);

		GL11.glBegin(GL11.GL_QUADS);
		GL11.glTexCoord2f(0.0f, 0.0f);
		GL11.glVertex2f(-1.0f, -1.0f);
		GL11.glTexCoord2f(1.0f, 0.0f);
		GL11.glVertex2f(1.0f, -1.0f);
		GL11.glTexCoord2f(1.0f, 1.0f);
		GL11.glVertex2f(1.0f, 1.0f);
		GL11.glTexCoord2f(0.0f, 1.0f);
		GL11.glVertex2f(-1.0f, 1.0f);
		GL11.glEnd();
	}

	public void bindFrameBuffer() {
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
		GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, fbo);
		GL11.glViewport(0, 0, (int) width, (int) height);
	}

	public void unbindFrameBuffer() {
		GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, 0);
		GL11.glViewport(0, 0, Display.getWidth(), Display.getHeight());
	}

	@Override
	protected void tick() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void update(float arg0) {
		lerp.lerp(new Vector3f(1, 0.25f, 0), 0.01f);
		nlerp.nlerp(new Vector3f(1, 0.25f, 0), 0.01f);
		slerp.slerp(new Vector3f(1, -0.25f, 0), 0.01f);
		movto.moveTowards(new Vector3f(1, -0.25f, 0), 0.01f);
	}
}
