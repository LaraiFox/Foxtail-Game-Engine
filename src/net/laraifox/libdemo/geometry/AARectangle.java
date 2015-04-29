package net.laraifox.libdemo.geometry;

import net.laraifox.lib.graphics.Transform2D;
import net.laraifox.lib.math.Matrix3f;
import net.laraifox.lib.math.Vector2f;

public class AARectangle extends Rectangle {
	public AARectangle() {
		this(new Transform2D(), Vector2f.Zero());
	}

	public AARectangle(Vector2f size) {
		this(new Transform2D(), size);
	}

	public AARectangle(Vector2f position, Vector2f size) {
		super(new Transform2D(position), size);
	}

	public AARectangle(float width, float height) {
		this(new Transform2D(), new Vector2f(width, height));
	}

	public AARectangle(float x, float y, float width, float height) {
		this(new Transform2D(new Vector2f(x, y)), new Vector2f(width, height));
	}

	public AARectangle(Transform2D transform, Vector2f size) {
		super(transform, size);
	}

	@Override
	public Vector2f[] getPoints() {
		transform.setRotation(0.0f);

		Vector2f[] tranformedVertices = new Vector2f[vertices.length];
		Matrix3f tranformationMatrix = transform.getTransformationMatrix();
		for (int i = 0; i < vertices.length; i++) {
			tranformedVertices[i] = tranformationMatrix.multiply(vertices[i].getPosition());
		}

		return tranformedVertices;
	}

	@Override
	public Vector2f[] getNormals() {
		Vector2f[] normals = new Vector2f[2];
		for (int i = 0; i < normals.length; i++) {
			normals[i] = vertices[i].getNormal();
		}

		return normals;
	}

	@Override
	public float getNegativeX() {
		return vertices[VERTEX_BOTTOM_LEFT].getPosition().getX();
	}

	@Override
	public float getBottom() {
		return vertices[VERTEX_BOTTOM_LEFT].getPosition().getY();
	}

	@Override
	public float getRight() {
		return vertices[VERTEX_TOP_RIGHT].getPosition().getX();
	}

	@Override
	public float getTop() {
		return vertices[VERTEX_TOP_RIGHT].getPosition().getY();
	}

	@Override
	public float getWidth() {
		return getRight() - getNegativeX();
	}

	@Override
	public float getHeight() {
		return getTop() - getBottom();
	}
}
