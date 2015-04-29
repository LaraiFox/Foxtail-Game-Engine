package net.laraifox.libdemo.interfaces;

import net.laraifox.libdemo.math.Vector3f;

public interface IGeometricObject {
	public Vector3f[] getPoints();

	public Vector3f[] getEdgeNormals();

	public float getX();

	public float getY();

	public float getZ();

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
