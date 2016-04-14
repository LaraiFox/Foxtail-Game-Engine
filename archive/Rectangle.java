package net.laraifox.libdev.geometry;

import laraifox.foxtail.core.math.Vector2f;
import net.laraifox.lib.geometry.Quadrilateral;
import net.laraifox.lib.graphics.Transform2D;
import net.laraifox.lib.graphics.Vertex2D;

public class Rectangle extends Quadrilateral {
	public Rectangle() {
		this(new Transform2D(), new Vector2f());
	}

	public Rectangle(Vector2f size) {
		this(new Transform2D(), size);
	}

	public Rectangle(Transform2D transform, Vector2f size) {
		this.transform = transform;

		float halfWidth = size.getX() / 2.0f;
		float halfHeight = size.getY() / 2.0f;
		this.vertices = new Vertex2D[VERTEX_COUNT];
		vertices[VERTEX_BOTTOM_LEFT] = new Vertex2D(new Vector2f(-halfWidth, -halfHeight));
		vertices[VERTEX_BOTTOM_RIGHT] = new Vertex2D(new Vector2f(halfWidth, -halfHeight));
		vertices[VERTEX_TOP_RIGHT] = new Vertex2D(new Vector2f(halfWidth, halfHeight));
		vertices[VERTEX_TOP_LEFT] = new Vertex2D(new Vector2f(-halfWidth, halfHeight));
		calculateEdgeNormals();
	}

	public Vector2f[] getNormals() {
		Vector2f[] normals = new Vector2f[2];
		for (int i = 0; i < normals.length; i++) {
			normals[i] = Vector2f.rotate(vertices[i].getNormal(), transform.getRotation());
		}

		return normals;
	}
}
