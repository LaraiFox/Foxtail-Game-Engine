package laraifox.foxtail.rendering.geometry;

import laraifox.foxtail.core.math.Vector3f;
import laraifox.foxtail.rendering.Vertex;
import laraifox.foxtail.rendering.models.Model;

public interface IGeometricObject {
	public Model getModel();

	public Vertex[] getVertices();

	public Vector3f[] getPoints();

	public Vector3f[] getTexCoords();

	public Vector3f[] getNormals();

	public Vector3f[] getTangents();

	public float getMinX();

	public float getMinY();

	public float getMinZ();

	public float getMaxX();

	public float getMaxY();

	public float getMaxZ();

	public float getWidth();

	public float getHeight();

	public float getLength();

}
