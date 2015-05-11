package net.laraifox.libdev._old;

import java.nio.FloatBuffer;

import net.laraifox.libdev.math.Vector2f;
import net.laraifox.libdev.math.Vector3f;
import net.laraifox.libdev.utils.BufferUtils;

public class Vector4f_old {
	public static final int ELEMENT_COUNT = 4;
	public static final int ELEMENT_SIZE = 4;
	public static final int VECTOR_SIZE = 16;

	private float x, y, z, w;

	public Vector4f_old() {
		this.x = 0.0f;
		this.y = 0.0f;
		this.z = 0.0f;
		this.w = 0.0f;
	}

	public Vector4f_old(float s) {
		this.x = s;
		this.y = s;
		this.z = s;
		this.w = s;
	}

	public Vector4f_old(float x, float y, float z, float w) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}

	public Vector4f_old(Vector3f v, float w) {
		this.x = v.getX();
		this.y = v.getY();
		this.z = v.getZ();
		this.w = w;
	}

	public Vector4f_old(Vector4f_old v) {
		this.x = v.x;
		this.y = v.y;
		this.z = v.z;
		this.w = v.w;
	}

	public Vector4f_old add(float x, float y, float z, float w) {
		this.x += x;
		this.y += y;
		this.z += z;
		this.w += w;
		return this;
	}

	public Vector4f_old add(Vector4f_old v) {
		this.x += v.x;
		this.y += v.y;
		this.z += v.z;
		this.w += v.w;
		return this;
	}

	public Vector4f_old subtract(float x, float y, float z, float w) {
		this.x -= x;
		this.y -= y;
		this.z -= z;
		this.w -= w;
		return this;
	}

	public Vector4f_old subtract(Vector4f_old v) {
		this.x -= v.x;
		this.y -= v.y;
		this.z -= v.z;
		this.w -= v.w;
		return this;
	}

	public Vector4f_old multiply(float x, float y, float z, float w) {
		this.x *= x;
		this.y *= y;
		this.z *= z;
		this.w *= w;
		return this;
	}

	public Vector4f_old multiply(Vector4f_old v) {
		this.x *= v.x;
		this.y *= v.y;
		this.z *= v.z;
		this.w *= v.w;
		return this;
	}

	public Vector4f_old scale(float scalar) {
		this.x *= scalar;
		this.y *= scalar;
		this.z *= scalar;
		this.w *= scalar;
		return this;
	}

	public float dot(Vector4f_old v) {
		return this.x * v.x + this.y * v.y + this.z * v.z + this.w * v.w;
	}

	/**
	 * WARNING: This method is not currently implemented and will always return null. Do not use!
	 * 
	 * @param v
	 * @return
	 */
	public Vector4f_old cross(Vector4f_old v) {
		return null;
	}

	/**
	 * WARNING: This method is not currently implemented and will always return null. Do not use!
	 * 
	 * @param theta
	 * @param v
	 * @return
	 */
	public Vector4f_old rotate(float theta, Vector4f_old v) {
		return null;
	}

	public Vector4f_old negate() {
		this.x = -this.x;
		this.y = -this.y;
		this.z = -this.z;
		this.w = -this.w;
		return this;
	}

	public Vector4f_old normalize() {
		float length = length();
		if (length == 0.0f)
			return this;

		this.x /= length;
		this.y /= length;
		this.z /= length;
		this.w /= length;
		return this;
	}

	public Vector4f_old absolute() {
		this.x = Math.abs(this.x);
		this.y = Math.abs(this.y);
		this.z = Math.abs(this.z);
		this.w = Math.abs(this.w);
		return this;
	}

	public Vector4f_old reverse() {
		float x_ = this.w;
		float y_ = this.z;
		float z_ = this.y;
		float w_ = this.x;
		this.x = x_;
		this.y = y_;
		this.z = z_;
		this.w = w_;
		return this;
	}

	public boolean equals(Vector4f_old v) {
		if (this.x == v.getX() && this.y == v.getY() && this.z == v.getZ() && this.w == v.getW())
			return true;

		return false;
	}

	@Override
	public boolean equals(Object other) {
		if (other instanceof Vector4f_old) {
			Vector4f_old _other = (Vector4f_old) other;

			return (this.x == _other.getX() && this.y == _other.getY() && this.z == _other.getZ() && this.w == _other.getW());
		}

		return false;
	}

	public float length() {
		return (float) Math.sqrt(x * x + y * y + z * z + w * w);
	}

	public float lengthSq() {
		return x * x + y * y + z * z + w * w;
	}

	public float distanceTo(float x, float y, float z, float w) {
		float dx = x - this.x;
		float dy = y - this.y;
		float dz = z - this.z;
		float dw = w - this.w;
		return (float) Math.sqrt(dx * dx + dy * dy + dz * dz + dw * dw);
	}

	public float distanceTo(Vector4f_old v) {
		float dx = v.x - this.x;
		float dy = v.y - this.y;
		float dz = v.z - this.z;
		float dw = v.w - this.w;
		return (float) Math.sqrt(dx * dx + dy * dy + dz * dz + dw * dw);
	}

	public float distanceSqTo(float x, float y, float z, float w) {
		float dx = x - this.x;
		float dy = y - this.y;
		float dz = z - this.z;
		float dw = w - this.w;
		return dx * dx + dy * dy + dz * dz + dw * dw;
	}

	public float distanceSqTo(Vector4f_old v) {
		float dx = v.x - this.x;
		float dy = v.y - this.y;
		float dz = v.z - this.z;
		float dw = v.w - this.w;
		return dx * dx + dy * dy + dz * dz + dw * dw;
	}

	public FloatBuffer toFloatBuffer() {
		FloatBuffer buffer = BufferUtils.createFloatBuffer(ELEMENT_COUNT);
		buffer.put(x);
		buffer.put(y);
		buffer.put(z);
		buffer.put(w);
		return buffer;
	}

	public String toString() {
		return "[" + x + ", " + y + ", " + z + ", " + w + "]";
	}

	public String toString(int dp) {
		if (dp < 0)
			dp = 0;
		float power = (int) Math.pow(10, dp);
		float xx = ((int) (x * power)) / power;
		float yy = ((int) (y * power)) / power;
		float zz = ((int) (z * power)) / power;
		float ww = ((int) (w * power)) / power;
		return "[" + xx + ", " + yy + ", " + zz + ", " + ww + "]";
	}

	public static Vector4f_old Zero() {
		return new Vector4f_old(0.0f, 0.0f, 0.0f, 0.0f);
	}

	public static Vector4f_old One() {
		return new Vector4f_old(1.0f, 1.0f, 1.0f, 1.0f);
	}

	public static Vector4f_old PositiveX() {
		return new Vector4f_old(1.0f, 0.0f, 0.0f, 0.0f);
	}

	public static Vector4f_old NegativeX() {
		return new Vector4f_old(-1.0f, 0.0f, 0.0f, 0.0f);
	}

	public static Vector4f_old PositiveY() {
		return new Vector4f_old(0.0f, 1.0f, 0.0f, 0.0f);
	}

	public static Vector4f_old NegativeY() {
		return new Vector4f_old(0.0f, -1.0f, 0.0f, 0.0f);
	}

	public static Vector4f_old PositiveZ() {
		return new Vector4f_old(0.0f, 0.0f, 1.0f, 0.0f);
	}

	public static Vector4f_old NegativeZ() {
		return new Vector4f_old(0.0f, 0.0f, -1.0f, 0.0f);
	}

	public static Vector4f_old PositiveW() {
		return new Vector4f_old(0.0f, 0.0f, 0.0f, 1.0f);
	}

	public static Vector4f_old NegativeW() {
		return new Vector4f_old(0.0f, 0.0f, 0.0f, -1.0f);
	}

	public static Vector4f_old add(Vector4f_old u, Vector4f_old v) {
		float x = u.getX() + v.getX();
		float y = u.getY() + v.getY();
		float z = u.getZ() + v.getZ();
		float w = u.getW() + v.getW();
		return new Vector4f_old(x, y, z, w);
	}

	public static Vector4f_old subtract(Vector4f_old u, Vector4f_old v) {
		float x = u.getX() - v.getX();
		float y = u.getY() - v.getY();
		float z = u.getZ() - v.getZ();
		float w = u.getW() - v.getW();
		return new Vector4f_old(x, y, z, w);
	}

	public static Vector4f_old multiply(Vector4f_old u, Vector4f_old v) {
		float x = u.getX() * v.getX();
		float y = u.getY() * v.getY();
		float z = u.getZ() * v.getZ();
		float w = u.getW() * v.getW();
		return new Vector4f_old(x, y, z, w);
	}

	public static Vector4f_old scale(Vector4f_old u, float scalar) {
		float x = u.getX() * scalar;
		float y = u.getY() * scalar;
		float z = u.getZ() * scalar;
		float w = u.getW() * scalar;
		return new Vector4f_old(x, y, z, w);
	}

	public static float dot(Vector4f_old u, Vector4f_old v) {
		return u.getX() * v.getX() + u.getY() * v.getY() + u.getZ() * v.getZ() + u.getW() * v.getW();
	}

	/**
	 * WARNING: This method is not currently implemented and will always return null. Do not use!
	 * 
	 * @param u
	 * @param v
	 * @return
	 */
	public static Vector4f_old cross(Vector4f_old u, Vector4f_old v) {
		return null;
	}

	public static Vector4f_old negate(Vector4f_old u) {
		float x = -u.getX();
		float y = -u.getY();
		float z = -u.getZ();
		float w = -u.getW();
		return new Vector4f_old(x, y, z, w);
	}

	public static Vector4f_old normalize(Vector4f_old u) {
		float length = (float) Math.sqrt(u.getX() * u.getX() + u.getY() * u.getY() + u.getZ() * u.getZ());
		if (length == 0.0f)
			return u;

		float x = u.x / length;
		float y = u.y / length;
		float z = u.z / length;
		float w = u.z / length;
		return new Vector4f_old(x, y, z, w);
	}

	public static Vector4f_old absolute(Vector4f_old v) {
		float x = Math.abs(v.x);
		float y = Math.abs(v.y);
		float z = Math.abs(v.z);
		float w = Math.abs(v.w);
		return new Vector4f_old(x, y, z, w);
	}

	public static Vector4f_old reverse(Vector4f_old u) {
		return new Vector4f_old(u.getW(), u.getZ(), u.getY(), u.getX());
	}

	public static float length(Vector4f_old u) {
		return (float) Math.sqrt(u.getX() * u.getX() + u.getY() * u.getY() + u.getZ() * u.getZ() + u.getW() * u.getW());
	}

	public static float lengthSq(Vector4f_old u) {
		return u.getX() * u.getX() + u.getY() * u.getY() + u.getZ() * u.getZ() + u.getW() * u.getW();
	}

	public static float distanceBetween(Vector4f_old u, Vector4f_old v) {
		float dx = v.getX() - u.getX();
		float dy = v.getY() - u.getY();
		float dz = v.getZ() - u.getZ();
		float dw = v.getW() - u.getW();
		return (float) Math.sqrt(dx * dx + dy * dy + dz * dz + dw * dw);
	}

	public static float distanceSqBetween(Vector4f_old u, Vector4f_old v) {
		float dx = v.getX() - u.getX();
		float dy = v.getY() - u.getY();
		float dz = v.getZ() - u.getZ();
		float dw = v.getW() - u.getW();
		return dx * dx + dy * dy + dz * dz + dw * dw;
	}

	public static Vector4f_old sum(Vector4f_old... u) {
		float x = 0.0f;
		float y = 0.0f;
		float z = 0.0f;
		float w = 0.0f;
		for (int i = 0; i < u.length; i++) {
			x += u[i].getX();
			y += u[i].getY();
			z += u[i].getZ();
			w += u[i].getW();
		}
		return new Vector4f_old(x, y, z, w);
	}

	public Vector4f_old get() {
		return new Vector4f_old(this);
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

	public float getW() {
		return w;
	}

	public Vector2f getXY() {
		return new Vector2f(x, y);
	}

	public Vector2f getXZ() {
		return new Vector2f(x, z);
	}

	public Vector2f getXW() {
		return new Vector2f(x, w);
	}

	public Vector2f getYZ() {
		return new Vector2f(y, z);
	}

	public Vector2f getYW() {
		return new Vector2f(y, w);
	}

	public Vector2f getZW() {
		return new Vector2f(z, w);
	}

	public Vector3f getXYZ() {
		return new Vector3f(x, y, z);
	}

	public Vector3f getXYW() {
		return new Vector3f(x, y, w);
	}

	public Vector3f getXZW() {
		return new Vector3f(x, z, w);
	}

	public Vector3f getYZW() {
		return new Vector3f(y, z, w);
	}

	public Vector4f_old set(float s) {
		this.x = s;
		this.y = s;
		this.z = s;
		this.w = s;
		return this;
	}

	public Vector4f_old set(float x, float y, float z, float w) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
		return this;
	}

	public Vector4f_old set(Vector3f v, float w) {
		this.x = v.getX();
		this.y = v.getY();
		this.z = v.getZ();
		this.w = w;
		return this;
	}

	public Vector4f_old set(Vector4f_old v) {
		this.x = v.x;
		this.y = v.y;
		this.z = v.z;
		this.w = v.w;
		return this;
	}

	public Vector4f_old setX(float x) {
		this.x = x;
		return this;
	}

	public Vector4f_old setY(float y) {
		this.y = y;
		return this;
	}

	public Vector4f_old setZ(float z) {
		this.z = z;
		return this;
	}

	public Vector4f_old setW(float w) {
		this.w = w;
		return this;
	}

	public Vector4f_old setXY(Vector2f v) {
		this.x = v.getX();
		this.y = v.getY();
		return this;
	}

	public Vector4f_old setXZ(Vector2f v) {
		this.x = v.getX();
		this.z = v.getY();
		return this;
	}

	public Vector4f_old setXW(Vector2f v) {
		this.x = v.getX();
		this.w = v.getY();
		return this;
	}

	public Vector4f_old setYZ(Vector2f v) {
		this.y = v.getX();
		this.z = v.getY();
		return this;
	}

	public Vector4f_old setYW(Vector2f v) {
		this.y = v.getX();
		this.w = v.getY();
		return this;
	}

	public Vector4f_old setZW(Vector2f v) {
		this.z = v.getX();
		this.w = v.getY();
		return this;
	}

	public Vector4f_old setXYZ(Vector3f v) {
		this.x = v.getX();
		this.y = v.getY();
		this.z = v.getZ();
		return this;
	}

	public Vector4f_old setXYW(Vector3f v) {
		this.x = v.getX();
		this.y = v.getY();
		this.w = v.getZ();
		return this;
	}

	public Vector4f_old setXZW(Vector3f v) {
		this.x = v.getX();
		this.z = v.getY();
		this.w = v.getZ();
		return this;
	}

	public Vector4f_old setYZW(Vector3f v) {
		this.y = v.getX();
		this.z = v.getY();
		this.w = v.getZ();
		return this;
	}
}
