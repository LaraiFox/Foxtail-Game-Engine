package net.laraifox.libdemo.collision;

import net.laraifox.libdemo.math.Vector3f;
import net.laraifox.libdemo.interfaces.IGeometricObject;

public class GeomProjection1D {
	private Vector3f axis;
	private float min, max;

	public GeomProjection1D(IGeometricObject object, Vector3f axis) {
		Vector3f[] verticesArray = object.getPoints();

		this.axis = axis;
		this.min = axis.dot(verticesArray[0]);
		this.max = min;

		for (int i = 1; i < verticesArray.length; i++) {
			float dot = axis.dot(verticesArray[i]);

			if (dot < min)
				min = dot;
			else if (dot > max)
				max = dot;
		}
	}

	public GeomProjection1D(GeomProjection1D p) {
		this.min = p.getMin();
		this.max = p.getMax();
	}

	public void expand(float velocityProjection) {
		if (velocityProjection < 0) {
			min += velocityProjection;
		} else {
			max += velocityProjection;
		}
	}

	public void contract(float velocityProjection) {
		if (velocityProjection < 0) {
			min -= velocityProjection;
		} else {
			max -= velocityProjection;
		}
	}

	public boolean overlaps(GeomProjection1D p) {
		if ((p.getMin() <= this.getMax() && this.getMax() <= p.getMax()) || (this.getMin() <= p.getMax() && p.getMax() <= this.getMax()))
			return true;

		return false;
	}

	public float getOverlap(GeomProjection1D p) {
		if (this.getMin() < p.getMin())
			return this.getMax() - p.getMin();
		else
			return p.getMax() - this.getMin();
	}

	@Override
	public boolean equals(Object other) {
		if (other instanceof GeomProjection1D) {
			GeomProjection1D _other = (GeomProjection1D) other;

			return (this.axis.equals(_other.getAxis()) && this.min == _other.getMin() && this.max == _other.getMax());
		}

		return false;
	}

	@Override
	public String toString() {
		return new String("[" + min + ", " + max + "]");
	}

	public Vector3f getAxis() {
		return axis;
	}

	public float getMin() {
		return min;
	}

	public float getMax() {
		return max;
	}
}
