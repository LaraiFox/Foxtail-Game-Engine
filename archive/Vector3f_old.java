package net.laraifox.libdev._old;

import java.nio.FloatBuffer;

import laraifox.foxtail.core.math.Quaternion;
import laraifox.foxtail.core.math.Vector2f;
import net.laraifox.lib.math.Vector3i;
import net.laraifox.libdev.utils.BufferUtils;

public class Vector3f_old {
	public static final int ELEMENT_COUNT = 3;
	public static final int ELEMENT_SIZE = 4;
	public static final int VECTOR_SIZE = 12;

	private float x, y, z;

	public Vector3f_old() {
		this.x = 0;
		this.y = 0;
		this.z = 0;
	}

	public Vector3f_old(float s) {
		this.x = s;
		this.y = s;
		this.z = s;
	}

	public Vector3f_old(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Vector3f_old(Vector3f_old v) {
		this.x = v.x;
		this.y = v.y;
		this.z = v.z;
	}

	public Vector3f_old(Vector2f v, float z) {
		this.x = v.getX();
		this.y = v.getY();
		this.z = z;
	}

	public Vector3f_old add(float x, float y, float z) {
		this.x += x;
		this.y += y;
		this.z += z;
		return this;
	}

	public Vector3f_old add(Vector3f_old v) {
		this.x += v.x;
		this.y += v.y;
		this.z += v.z;
		return this;
	}

	public Vector3f_old subtract(float x, float y, float z) {
		this.x -= x;
		this.y -= y;
		this.z -= z;
		return this;
	}

	public Vector3f_old subtract(Vector3f_old v) {
		this.x -= v.x;
		this.y -= v.y;
		this.z -= v.z;
		return this;
	}

	public Vector3f_old multiply(float x, float y, float z) {
		this.x *= x;
		this.y *= y;
		this.z *= z;
		return this;
	}

	public Vector3f_old multiply(Vector3f_old v) {
		this.x *= v.x;
		this.y *= v.y;
		this.z *= v.z;
		return this;
	}

	public Vector3f_old scale(float scalar) {
		this.x *= scalar;
		this.y *= scalar;
		this.z *= scalar;
		return this;
	}

	public float dot(Vector3f_old v) {
		return this.x * v.x + this.y * v.y + this.z * v.z;
	}

	public Vector3f_old cross(Vector3f_old v) {
		float cx = this.getY() * v.getZ() - this.getZ() * v.getY();
		float cy = this.getZ() * v.getX() - this.getX() * v.getZ();
		float cz = this.getX() * v.getY() - this.getY() * v.getX();
		this.x = cx;
		this.y = cy;
		this.z = cz;
		return this;
	}

	public Vector3f_old project(Vector3f_old normal) {
		return this.set(Vector3f_old.scale(normal, this.dot(normal)));
	}

	public Vector3f_old rotate(Vector3f_old v, float theta) {
		float sineHalfTheta = (float) Math.sin(Math.toRadians(theta / 2.0f));
		float cosineHalfTheta = (float) Math.cos(Math.toRadians(theta / 2.0f));

		float rx = v.getX() * sineHalfTheta;
		float ry = v.getY() * sineHalfTheta;
		float rz = v.getZ() * sineHalfTheta;
		float rw = cosineHalfTheta;

		Quaternion rotation = new Quaternion(rx, ry, rz, rw);
		Quaternion conjugate = Quaternion.conjugate(rotation);
		Quaternion result = rotation.multiply(this).multiply(conjugate);

		this.x = result.getX();
		this.y = result.getY();
		this.z = result.getZ();

		return this;
	}

	public Vector3f_old rotate(Quaternion rotation) {
		Quaternion conjugate = Quaternion.conjugate(rotation);

		Quaternion result = rotation.multiply(this).multiply(conjugate);

		this.x = result.getX();
		this.y = result.getY();
		this.z = result.getZ();

		return this;
	}

	public Vector3f_old negate() {
		this.x = -this.x;
		this.y = -this.y;
		this.z = -this.z;
		return this;
	}

	public Vector3f_old normalize() {
		float length = length();
		if (length == 0.0f)
			return this;

		this.x /= length;
		this.y /= length;
		this.z /= length;
		return this;
	}

	public Vector3f_old orthonormalize(Vector3f_old v) {
		Vector3f_old n = Vector3f_old.cross(this, v);
		this.set(Vector3f_old.cross(n, this).normalize());

		return this;
	}

	public Vector3f_old absolute() {
		this.x = Math.abs(this.x);
		this.y = Math.abs(this.y);
		this.z = Math.abs(this.z);
		return this;
	}

	public Vector3f_old clamp(Vector3f_old v) {
		if (this.getX() < -v.getX())
			this.x = -v.getX();
		else if (this.getX() > v.getX())
			this.x = v.getX();

		if (this.getY() < -v.getY())
			this.y = -v.getY();
		else if (this.getY() > v.getY())
			this.y = v.getY();

		if (this.getZ() < -v.getZ())
			this.z = -v.getZ();
		else if (this.getZ() > v.getZ())
			this.z = v.getZ();

		return this;
	}

	public Vector3f_old clamp(Vector3f_old min, Vector3f_old max) {
		if (this.getX() < min.getX())
			this.x = min.getX();
		else if (this.getX() > max.getX())
			this.x = max.getX();

		if (this.getY() < min.getY())
			this.y = min.getY();
		else if (this.getY() > max.getY())
			this.y = max.getY();

		if (this.getZ() < min.getZ())
			this.z = min.getZ();
		else if (this.getZ() > max.getZ())
			this.z = max.getZ();

		return this;
	}

	public Vector3f_old reverse() {
		float x_ = this.z;
		float y_ = this.y;
		float z_ = this.x;
		this.x = x_;
		this.y = y_;
		this.z = z_;
		return this;
	}

	@Override
	public boolean equals(Object other) {
		if (other instanceof Vector3f_old) {
			Vector3f_old _other = (Vector3f_old) other;

			return (this.x == _other.getX() && this.y == _other.getY() && this.z == _other.getZ());
		}

		return false;
	}

	public float length() {
		return (float) Math.sqrt(x * x + y * y + z * z);
	}

	public float lengthSq() {
		return x * x + y * y + z * z;
	}

	public float distanceTo(float x, float y, float z) {
		float dx = x - this.x;
		float dy = y - this.y;
		float dz = z - this.z;
		return (float) Math.sqrt(dx * dx + dy * dy + dz * dz);
	}

	public float distanceTo(Vector3f_old v) {
		float dx = v.x - this.x;
		float dy = v.y - this.y;
		float dz = v.z - this.z;
		return (float) Math.sqrt(dx * dx + dy * dy + dz * dz);
	}

	public float distanceSqTo(float x, float y, float z) {
		float dx = x - this.x;
		float dy = y - this.y;
		float dz = z - this.z;
		return dx * dx + dy * dy + dz * dz;
	}

	public float distanceSqTo(Vector3f_old v) {
		float dx = v.x - this.x;
		float dy = v.y - this.y;
		float dz = v.z - this.z;
		return dx * dx + dy * dy + dz * dz;
	}

	public FloatBuffer toFloatBuffer() {
		FloatBuffer buffer = BufferUtils.createFloatBuffer(ELEMENT_COUNT);
		buffer.put(x);
		buffer.put(y);
		buffer.put(z);
		return buffer;
	}

	public String toString() {
		return "[" + x + ", " + y + ", " + z + "]";
	}

	public String toString(int dp) {
		if (dp < 0)
			dp = 0;
		float power = (int) Math.pow(10, dp);
		float xx = ((int) (x * power)) / power;
		float yy = ((int) (y * power)) / power;
		float zz = ((int) (z * power)) / power;
		return "[" + xx + ", " + yy + ", " + zz + "]";
	}

	public static Vector3f_old Zero() {
		return new Vector3f_old(0.0f, 0.0f, 0.0f);
	}

	public static Vector3f_old One() {
		return new Vector3f_old(1.0f, 1.0f, 1.0f);
	}

	public static Vector3f_old PositiveX() {
		return new Vector3f_old(1.0f, 0.0f, 0.0f);
	}

	public static Vector3f_old NegativeX() {
		return new Vector3f_old(-1.0f, 0.0f, 0.0f);
	}

	public static Vector3f_old PositiveY() {
		return new Vector3f_old(0.0f, 1.0f, 0.0f);
	}

	public static Vector3f_old NegativeY() {
		return new Vector3f_old(0.0f, -1.0f, 0.0f);
	}

	public static Vector3f_old PositiveZ() {
		return new Vector3f_old(0.0f, 0.0f, 1.0f);
	}

	public static Vector3f_old NegativeZ() {
		return new Vector3f_old(0.0f, 0.0f, -1.0f);
	}

	public static Vector3f_old add(Vector3f_old u, Vector3f_old v) {
		float x = u.getX() + v.getX();
		float y = u.getY() + v.getY();
		float z = u.getZ() + v.getZ();
		return new Vector3f_old(x, y, z);
	}

	public static Vector3f_old subtract(Vector3f_old u, Vector3f_old v) {
		float x = u.getX() - v.getX();
		float y = u.getY() - v.getY();
		float z = u.getZ() - v.getZ();
		return new Vector3f_old(x, y, z);
	}

	public static Vector3f_old multiply(Vector3f_old u, Vector3f_old v) {
		float x = u.getX() * v.getX();
		float y = u.getY() * v.getY();
		float z = u.getZ() * v.getZ();
		return new Vector3f_old(x, y, z);
	}

	public static Vector3f_old scale(Vector3f_old u, float scalar) {
		float x = u.getX() * scalar;
		float y = u.getY() * scalar;
		float z = u.getZ() * scalar;
		return new Vector3f_old(x, y, z);
	}

	public static float dot(Vector3f_old u, Vector3f_old v) {
		return u.getX() * v.getX() + u.getY() * v.getY() + u.getZ() * v.getZ();
	}

	public static Vector3f_old cross(Vector3f_old u, Vector3f_old v) {
		float x = u.getY() * v.getZ() - u.getZ() * v.getY();
		float y = u.getZ() * v.getX() - u.getX() * v.getZ();
		float z = u.getX() * v.getY() - u.getY() * v.getX();
		return new Vector3f_old(x, y, z);
	}

	public static Vector3f_old rotate(Vector3f_old u, Vector3f_old v, float theta) {
		float sineHalfTheta = (float) Math.sin(Math.toRadians(theta / 2.0f));
		float cosineHalfTheta = (float) Math.cos(Math.toRadians(theta / 2.0f));

		float rx = v.getX() * sineHalfTheta;
		float ry = v.getY() * sineHalfTheta;
		float rz = v.getZ() * sineHalfTheta;
		float rw = cosineHalfTheta;

		Quaternion rotation = new Quaternion(rx, ry, rz, rw);
		Quaternion conjugate = Quaternion.conjugate(rotation);
		Quaternion result = rotation.multiply(u).multiply(conjugate);

		return new Vector3f_old(result.getX(), result.getY(), result.getZ());
	}

	public static Vector3f_old rotate(Vector3f_old u, Quaternion rotation) {
		Quaternion conjugate = Quaternion.conjugate(rotation);

		Quaternion result = rotation.multiply(u).multiply(conjugate);

		return new Vector3f_old(result.getX(), result.getY(), result.getZ());
	}

	public static Vector3f_old negate(Vector3f_old u) {
		float x = -u.getX();
		float y = -u.getY();
		float z = -u.getZ();
		return new Vector3f_old(x, y, z);
	}

	public static Vector3f_old normalize(Vector3f_old u) {
		float length = (float) Math.sqrt(u.getX() * u.getX() + u.getY() * u.getY() + u.getZ() * u.getZ());
		if (length == 0.0f)
			return u;

		float x = u.x / length;
		float y = u.y / length;
		float z = u.z / length;
		return new Vector3f_old(x, y, z);
	}

	public static Vector3f_old orthonormalize(Vector3f_old u, Vector3f_old v) {
		Vector3f_old u_ = Vector3f_old.normalize(u);
		Vector3f_old v_ = Vector3f_old.normalize(v);
		Vector3f_old n = Vector3f_old.cross(u_, v_);

		return Vector3f_old.cross(n, v_).normalize();
	}

	public static Vector3f_old absolute(Vector3f_old v) {
		float x = Math.abs(v.x);
		float y = Math.abs(v.y);
		float z = Math.abs(v.z);
		return new Vector3f_old(x, y, z);
	}

	public static Vector3f_old clamp(Vector3f_old v, Vector3f_old min, Vector3f_old max) {
		Vector3f_old result = new Vector3f_old(v);

		if (result.getX() < min.getX())
			result.x = min.getX();
		else if (result.getX() > max.getX())
			result.x = max.getX();

		if (result.getY() < min.getY())
			result.y = min.getY();
		else if (result.getY() > max.getY())
			result.y = max.getY();

		if (result.getZ() < min.getZ())
			result.z = min.getZ();
		else if (result.getZ() > max.getZ())
			result.z = max.getZ();

		return result;
	}

	public static Vector3f_old reverse(Vector3f_old u) {
		return new Vector3f_old(u.getZ(), u.getY(), u.getX());
	}

	public static float length(Vector3f_old u) {
		return (float) Math.sqrt(u.getX() * u.getX() + u.getY() * u.getY() + u.getZ() * u.getZ());
	}

	public static float lengthSq(Vector3f_old u) {
		return u.getX() * u.getX() + u.getY() * u.getY() + u.getZ() * u.getZ();
	}

	public static float distanceBetween(Vector3f_old u, Vector3f_old v) {
		float dx = v.getX() - u.getX();
		float dy = v.getY() - u.getY();
		float dz = v.getZ() - u.getZ();
		return (float) Math.sqrt(dx * dx + dy * dy + dz * dz);
	}

	public static float distanceSqBetween(Vector3f_old u, Vector3f_old v) {
		float dx = v.getX() - u.getX();
		float dy = v.getY() - u.getY();
		float dz = v.getZ() - u.getZ();
		return dx * dx + dy * dy + dz * dz;
	}

	public static Vector3f_old sum(Vector3f_old... u) {
		float x = 0.0f;
		float y = 0.0f;
		float z = 0.0f;
		for (int i = 0; i < u.length; i++) {
			x += u[i].getX();
			y += u[i].getY();
			z += u[i].getZ();
		}
		return new Vector3f_old(x, y, z);
	}

	public Vector3f_old get() {
		return new Vector3f_old(this);
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public float getZ() {
		return z;
	}

	public Vector2f getXY() {
		return new Vector2f(x, y);
	}

	public Vector2f getXZ() {
		return new Vector2f(x, z);
	}

	public Vector2f getYZ() {
		return new Vector2f(y, z);
	}

	public Vector3f_old set(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
		return this;
	}

	public Vector3f_old set(Vector3f_old v) {
		this.x = v.x;
		this.y = v.y;
		this.z = v.z;
		return this;
	}

	public Vector3f_old set(Vector3i v) {
		this.x = v.getX();
		this.y = v.getY();
		this.z = v.getZ();
		return this;
	}

	public Vector3f_old setX(float x) {
		this.x = x;
		return this;
	}

	public Vector3f_old setY(float y) {
		this.y = y;
		return this;
	}

	public Vector3f_old setZ(float z) {
		this.z = z;
		return this;
	}

	public Vector3f_old setXY(Vector2f v) {
		this.x = v.getX();
		this.y = v.getY();
		return this;
	}

	public Vector3f_old setXZ(Vector2f v) {
		this.x = v.getX();
		this.z = v.getY();
		return this;
	}

	public Vector3f_old setYZ(Vector2f v) {
		this.y = v.getX();
		this.z = v.getY();
		return this;
	}
}
