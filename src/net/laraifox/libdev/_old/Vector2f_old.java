package net.laraifox.libdev._old;

import java.nio.FloatBuffer;

import net.laraifox.lib.math.Vector2i;

import org.lwjgl.BufferUtils;

public class Vector2f_old {
	public static final int ELEMENT_COUNT = 2;
	public static final int ELEMENT_SIZE = 4;
	public static final int VECTOR_SIZE = 8;

	private float x, y;

	public Vector2f_old() {
		this.x = 0;
		this.y = 0;
	}

	public Vector2f_old(float s) {
		this.x = s;
		this.y = s;
	}

	public Vector2f_old(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public Vector2f_old(Vector2f_old v) {
		this.x = v.x;
		this.y = v.y;
	}

	public Vector2f_old(Vector2i v) {
		this.x = v.getX();
		this.y = v.getY();
	}

	public Vector2f_old add(float x, float y) {
		this.x += x;
		this.y += y;
		return this;
	}

	public Vector2f_old add(Vector2f_old v) {
		this.x += v.x;
		this.y += v.y;
		return this;
	}

	public Vector2f_old subtract(float x, float y) {
		this.x -= x;
		this.y -= y;
		return this;
	}

	public Vector2f_old subtract(Vector2f_old v) {
		this.x -= v.x;
		this.y -= v.y;
		return this;
	}

	public Vector2f_old multiply(float x, float y) {
		this.x *= x;
		this.y *= y;
		return this;
	}

	public Vector2f_old multiply(Vector2f_old v) {
		this.x *= v.x;
		this.y *= v.y;
		return this;
	}

	public Vector2f_old scale(float scalar) {
		this.x *= scalar;
		this.y *= scalar;
		return this;
	}

	public float dot(Vector2f_old v) {
		return this.x * v.x + this.y * v.y;
	}

	public Vector2f_old cross() {
		float cx = this.y;
		float cy = -this.x;
		this.x = cx;
		this.y = cy;
		return this;
	}

	public Vector2f_old rotate(float theta) {
		theta = (float) Math.toRadians(theta);
		float sin = (float) Math.sin(theta);
		float cos = (float) Math.cos(theta);
		float rx = (this.x * cos) - (this.y * sin);
		float ry = (this.x * sin) + (this.y * cos);
		this.x = rx;
		this.y = ry;
		return this;
	}

	public Vector2f_old negate() {
		this.x = -this.x;
		this.y = -this.y;
		return this;
	}

	public Vector2f_old normalize() {
		float length = length();
		if (length == 0.0f)
			return this;

		this.x /= length;
		this.y /= length;
		return this;
	}

	public Vector2f_old absolute() {
		this.x = Math.abs(this.x);
		this.y = Math.abs(this.y);
		return this;
	}

	public Vector2f_old project(Vector2f_old v) {
		return scale(v, dot(normalize(v)));
	}

	public Vector2f_old reverse() {
		float x_ = this.y;
		float y_ = this.x;
		this.x = x_;
		this.y = y_;
		return this;
	}

	@Override
	public boolean equals(Object other) {
		if (other instanceof Vector2f_old) {
			Vector2f_old _other = (Vector2f_old) other;

			return (this.x == _other.getX() && this.y == _other.getY());
		}

		return false;
	}

	public float length() {
		return (float) Math.sqrt(x * x + y * y);
	}

	public float lengthSq() {
		return x * x + y * y;
	}

	public float distanceTo(float x, float y) {
		float dx = x - this.x;
		float dy = y - this.y;
		return (float) Math.sqrt(dx * dx + dy * dy);
	}

	public float distanceTo(Vector2f_old v) {
		float dx = v.x - this.x;
		float dy = v.y - this.y;
		return (float) Math.sqrt(dx * dx + dy * dy);
	}

	public float distanceSqTo(float x, float y) {
		float dx = x - this.x;
		float dy = y - this.y;
		return dx * dx + dy * dy;
	}

	public float distanceSqTo(Vector2f_old v) {
		float dx = v.x - this.x;
		float dy = v.y - this.y;
		return dx * dx + dy * dy;
	}

	public FloatBuffer toFloatBuffer() {
		FloatBuffer buffer = BufferUtils.createFloatBuffer(ELEMENT_COUNT);
		buffer.put(x);
		buffer.put(y);
		return buffer;
	}

	@Override
	public String toString() {
		return "[" + x + ", " + y + "]";
	}

	public static Vector2f_old Zero() {
		return new Vector2f_old(0.0f, 0.0f);
	}

	public static Vector2f_old One() {
		return new Vector2f_old(1.0f, 1.0f);
	}

	public static Vector2f_old PositiveX() {
		return new Vector2f_old(1.0f, 0.0f);
	}

	public static Vector2f_old NegativeX() {
		return new Vector2f_old(-1.0f, 0.0f);
	}

	public static Vector2f_old PositiveY() {
		return new Vector2f_old(0.0f, 1.0f);
	}

	public static Vector2f_old NegativeY() {
		return new Vector2f_old(0.0f, -1.0f);
	}

	public static Vector2f_old add(Vector2f_old u, Vector2f_old v) {
		float x = u.getX() + v.getX();
		float y = u.getY() + v.getY();
		return new Vector2f_old(x, y);
	}

	public static Vector2f_old subtract(Vector2f_old u, Vector2f_old v) {
		float x = u.getX() - v.getX();
		float y = u.getY() - v.getY();
		return new Vector2f_old(x, y);
	}

	public static Vector2f_old multiply(Vector2f_old u, Vector2f_old v) {
		float x = u.getX() * v.getX();
		float y = u.getY() * v.getY();
		return new Vector2f_old(x, y);
	}

	public static Vector2f_old scale(Vector2f_old u, float scalar) {
		float x = u.getX() * scalar;
		float y = u.getY() * scalar;
		return new Vector2f_old(x, y);
	}

	public static float dot(Vector2f_old u, Vector2f_old v) {
		return u.getX() * v.getX() + u.getY() * v.getY();
	}

	public static Vector2f_old cross(Vector2f_old u) {
		float x = u.getY();
		float y = -u.getX() + 0.0f;
		return new Vector2f_old(x, y);
	}

	public static Vector2f_old rotate(Vector2f_old u, float theta) {
		theta = (float) Math.toRadians(theta);
		float sin = (float) Math.sin(theta);
		float cos = (float) Math.cos(theta);
		float x = (u.getX() * cos) - (u.getY() * sin);
		float y = (u.getX() * sin) + (u.getY() * cos);
		return new Vector2f_old(x, y);
	}

	public static Vector2f_old negate(Vector2f_old u) {
		float x = -u.getX();
		float y = -u.getY();
		return new Vector2f_old(x, y);
	}

	public static Vector2f_old normalize(Vector2f_old u) {
		float length = (float) Math.sqrt(u.getX() * u.getX() + u.getY() * u.getY());
		if (length == 0.0f)
			return u;

		float x = u.getX() / length;
		float y = u.getY() / length;
		return new Vector2f_old(x, y);
	}

	public static Vector2f_old absolute(Vector2f_old u) {
		float x = Math.abs(u.x);
		float y = Math.abs(u.y);
		return new Vector2f_old(x, y);
	}

	public static Vector2f_old project(Vector2f_old u, Vector2f_old v) {
		return Vector2f_old.scale(v, u.dot(normalize(v)));
	}

	public static Vector2f_old reverse(Vector2f_old u) {
		return new Vector2f_old(u.getY(), u.getX());
	}

	public static float length(Vector2f_old u) {
		return (float) Math.sqrt(u.getX() * u.getX() + u.getY() * u.getY());
	}

	public static float lengthSq(Vector2f_old u) {
		return u.getX() * u.getX() + u.getY() * u.getY();
	}

	public static float distanceBetween(Vector2f_old u, Vector2f_old v) {
		float dx = v.getX() - u.getX();
		float dy = v.getY() - u.getY();
		return (float) Math.sqrt(dx * dx + dy * dy);
	}

	public static float distanceSqBetween(Vector2f_old u, Vector2f_old v) {
		float dx = v.getX() - u.getX();
		float dy = v.getY() - u.getY();
		return dx * dx + dy * dy;
	}

	public static Vector2f_old sum(Vector2f_old... u) {
		float x = 0.0f;
		float y = 0.0f;
		for (int i = 0; i < u.length; i++) {
			x += u[i].getX();
			y += u[i].getY();
		}
		return new Vector2f_old(x, y);
	}

	public Vector2f_old get() {
		return new Vector2f_old(this);
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public Vector2f_old set(float x, float y) {
		this.x = x;
		this.y = y;
		return this;
	}

	public Vector2f_old set(Vector2f_old v) {
		this.x = v.getX();
		this.y = v.getY();
		return this;
	}

	public Vector2f_old set(Vector2i v) {
		this.x = v.getX();
		this.y = v.getY();
		return this;
	}

	public Vector2f_old setX(float x) {
		this.x = x;
		return this;
	}

	public Vector2f_old setY(float y) {
		this.y = y;
		return this;
	}
}
