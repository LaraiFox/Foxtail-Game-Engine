package net.laraifox.lib._demos;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import net.laraifox.lib.GameObject;
import net.laraifox.lib.collision.QuadTree;
import net.laraifox.lib.display.OpenGLDisplay;
import net.laraifox.lib.geometry.AARectangle;
import net.laraifox.lib.graphics.OrthographicProjection;
import net.laraifox.lib.input.InputHandler;
import net.laraifox.lib.interfaces.IGeometricObject2D;
import net.laraifox.lib.utils.FileUtils;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

public class QuadtreeDemo extends OpenGLDisplay {
	private Random random;
	private QuadTree quadtree;
	private GameObject[] gameObjects;
	private ArrayList<IGeometricObject2D> collisions;

	public QuadtreeDemo() {
		super("Quadtree Test Window");

		setOrthographicProjection(new OrthographicProjection(800, 600, false));
	}

	@Override
	protected void cleanUp() {

	}

	@Override
	protected void initializeVariables() {
		this.random = new Random();
		this.quadtree = new QuadTree(new AARectangle(0, 0, 800, 600));
		this.gameObjects = new GameObject[640];
		for (int i = 0; i < gameObjects.length; i++) {
			gameObjects[i] = new GameObject(random.nextInt(790), random.nextInt(590));
			quadtree.insert(gameObjects[i].getBounds());
		}
		this.collisions = new ArrayList<IGeometricObject2D>();

		AARectangle rect = new AARectangle(400, 300, 800, 600);
		System.out.println("X: " + rect.getX() + ", Y: " + rect.getY());
	}

	@Override
	protected void tick() {

	}

	@Override
	protected void update(float delta) {
		if (InputHandler.isKeyPressed(Keyboard.KEY_R))
			initializeVariables();
		InputHandler.update();

		AARectangle mouseBounds = new AARectangle((int) InputHandler.getMousePosition().getX() - 2, (int) (InputHandler.getMousePosition().getY() - 2), 4, 4);

		collisions.clear();
		quadtree.retrieve(collisions, mouseBounds);
	}

	@Override
	protected void render() {
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
		GL11.glLoadIdentity();

		for (int i = 0; i < gameObjects.length; i++) {
			gameObjects[i].render();
		}

		quadtree.drawQuadtree();

		GL11.glColor3f(0.0f, 1.0f, 0.0f);
		for (int i = 0; i < collisions.size(); i++) {
			IGeometricObject2D object = collisions.get(i);
			GL11.glBegin(GL11.GL_QUADS);
			GL11.glVertex2f(object.getX(), object.getY());
			GL11.glVertex2f(object.getX() + object.getWidth(), object.getY());
			GL11.glVertex2f(object.getX() + object.getWidth(), object.getY() + object.getHeight());
			GL11.glVertex2f(object.getX(), object.getY() + object.getHeight());
			GL11.glEnd();
		}
	}
}
