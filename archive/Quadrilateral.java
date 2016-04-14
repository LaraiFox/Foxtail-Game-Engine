package net.laraifox.libdev.geometry;

import laraifox.foxtail.core.math.Vector2f;
import net.laraifox.lib.geometry.Quadrilateral;
import net.laraifox.lib.geometry.Shape2D;
import net.laraifox.lib.graphics.Transform2D;
import net.laraifox.lib.graphics.Vertex2D;

public class Quadrilateral extends Shape2D {
	public static final int VERTEX_BOTTOM_LEFT = 0;
	public static final int VERTEX_BOTTOM_RIGHT = 1;
	public static final int VERTEX_TOP_RIGHT = 2;
	public static final int VERTEX_TOP_LEFT = 3;

	public static final int VERTEX_COUNT = 4;

	public Quadrilateral() {
		this(new Transform2D(), Vector2f.Zero(), Vector2f.Zero(), Vector2f.Zero(), Vector2f.Zero());
	}

	public Quadrilateral(Transform2D transform) {
		this(transform, Vector2f.Zero(), Vector2f.Zero(), Vector2f.Zero(), Vector2f.Zero());
	}

	public Quadrilateral(Vector2f bottomLeft, Vector2f bottomRight, Vector2f topRight, Vector2f topLeft) {
		this(new Transform2D(), bottomLeft, bottomRight, topRight, topLeft);
	}

	public Quadrilateral(Transform2D transform, Vector2f bottomLeft, Vector2f bottomRight, Vector2f topRight, Vector2f topLeft) {
		super(transform, new Vector2f[] { bottomLeft, bottomRight, topRight, topLeft });
	}

	public Quadrilateral(Transform2D transform, Vector2f[] points) {
		super(transform);

		this.vertices = new Vertex2D[VERTEX_COUNT];
		for (int i = 0; i < VERTEX_COUNT; i++) {
			if (i >= points.length)
				this.vertices[i] = new Vertex2D();

			this.vertices[i] = new Vertex2D(points[i]);
		}
		calculateEdgeNormals();
	}

	public Quadrilateral(Quadrilateral quadrilateral) {
		super(quadrilateral.transform, quadrilateral.vertices);
	}
}
