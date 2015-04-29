package net.laraifox.libdemo;

import net.laraifox.libdemo.geometry.AARectangle;

import org.lwjgl.opengl.GL11;

public class GameObject {
	private static final int SIZE = 3;

	private AARectangle bounds;

	public GameObject(int x, int y) {
		this.bounds = new AARectangle(x, y, SIZE, SIZE);
	}

	public void render() {
		GL11.glColor3f(1.0f, 0.0f, 0.0f);
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glVertex2f(bounds.getX(), bounds.getY());
		GL11.glVertex2f(bounds.getX() + bounds.getWidth(), bounds.getY());
		GL11.glVertex2f(bounds.getX() + bounds.getWidth(), bounds.getY() + bounds.getHeight());
		GL11.glVertex2f(bounds.getX(), bounds.getY() + bounds.getHeight());
		GL11.glEnd();
	}

	public AARectangle getBounds() {
		return bounds;
	}
}
