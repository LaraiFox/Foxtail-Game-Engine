package net.laraifox.libdev.math;

/**
 * A three coordinate vector object with methods for basic math operations as well as vector specific operation. Coordinates are stored as three private float
 * components labeled x, y and z. Getter and setter methods can be used to access the components if needed.
 * 
 * @author Larai Fox
 * 
 */
public class Vector2f {
	/***
	 * Numeric index of the X component of the vector.
	 */
	public static final int COMPONENT_INDEX_X = 0;
	/***
	 * Numeric index of the Y component of the vector.
	 */
	public static final int COMPONENT_INDEX_Y = 1;

	/***
	 * The number of components in the vector.
	 */
	public static final int COMPONENT_COUNT = 2;
	/***
	 * The total size in bytes of all components in the vector.
	 */
	public static final int BYTE_COUNT = COMPONENT_COUNT * Float.BYTES;

	private float x, y;

	/**
	 * Constructs a new vector setting all components to zero.
	 */
	public Vector2f() {
		this(0.0f, 0.0f);
	}

	/**
	 * Constructs a new vector setting all components to value.
	 * 
	 * @param value
	 *            - the value used to set all components of the vector
	 */
	public Vector2f(float value) {
		this(value, value);
	}

	/**
	 * Constructs a new vector and sets the X, Y and Z components to the X, Y and Z parameters respectively.
	 * 
	 * @param x
	 *            - the X coordinate of the vector
	 * @param y
	 *            - the Y coordinate of the vector
	 * @param z
	 *            - the Z coordinate of the vector
	 */
	public Vector2f(float x, float y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Constructs a new vector that is a copy of the vector parameter with all components equal to those of the vector parameter. Note that further changes to
	 * this newly constructed vector will not have any effect on the original vector that was copied.
	 * 
	 * @param vector
	 *            - the vector to be copied
	 */
	public Vector2f(Vector2f vector) {
		this(vector.getX(), vector.getY());
	}

	/***
	 * Creates and returns a new vector of length zero.
	 * 
	 * @return the newly created vector
	 */
	public static Vector2f Zero() {
		return new Vector2f(0.0f, 0.0f);
	}

	/***
	 * Creates and returns a new vector with all components set to one.
	 * 
	 * @return the newly created vector
	 */
	public static Vector2f One() {
		return new Vector2f(1.0f, 1.0f);
	}

	/***
	 * Creates and returns a new vector of length one pointing in the negative X direction.
	 * 
	 * @return the newly created vector
	 */
	public static Vector2f Left() {
		return new Vector2f(-1.0f, 0.0f);
	}

	/***
	 * Creates and returns a new vector of length value pointing in the negative X direction. If value is negative the new vector will point in the opposite
	 * direction.
	 * 
	 * @param value
	 *            - the length of the returned vector.
	 * 
	 * @return the newly created vector
	 */
	public static Vector2f Left(float value) {
		return new Vector2f(-value, 0.0f);
	}

	/***
	 * Creates and returns a new vector of length one pointing in the positive X direction.
	 * 
	 * @return the newly created vector
	 */
	public static Vector2f Right() {
		return new Vector2f(1.0f, 0.0f);
	}

	/***
	 * Creates and returns a new vector of length value pointing in the positive X direction. If value is negative the new vector will point in the opposite
	 * direction.
	 * 
	 * @param value
	 *            - the length of the returned vector.
	 * 
	 * @return the newly created vector
	 */
	public static Vector2f Right(float value) {
		return new Vector2f(value, 0.0f);
	}

	/***
	 * Creates and returns a new vector of length one pointing in the negative Y direction.
	 * 
	 * @return the newly created vector
	 */
	public static Vector2f Down() {
		return new Vector2f(0.0f, -1.0f);
	}

	/***
	 * Creates and returns a new vector of length value pointing in the negative Y direction. If value is negative the new vector will point in the opposite
	 * direction.
	 * 
	 * @param value
	 *            - the length of the returned vector.
	 * 
	 * @return the newly created vector
	 */
	public static Vector2f Down(float value) {
		return new Vector2f(0.0f, -value);
	}

	/***
	 * Creates and returns a new vector of length one pointing in the positive Y direction.
	 * 
	 * @return the newly created vector
	 */
	public static Vector2f Up() {
		return new Vector2f(0.0f, 1.0f);
	}

	/***
	 * Creates and returns a new vector of length value pointing in the positive Y direction. If value is negative the new vector will point in the opposite
	 * direction.
	 * 
	 * @param value
	 *            - the length of the returned vector.
	 * 
	 * @return the newly created vector
	 */
	public static Vector2f Up(float value) {
		return new Vector2f(0.0f, value);
	}

	/**
	 * Adds two vectors together in the same way that the non-static {@link #add(Vector2f)} method does however this static version creates a new vector to
	 * store the result and also does not change the values stored in the left or right parameters.
	 * 
	 * @param left
	 *            - the vector on the left side of the equation
	 * @param right
	 *            - the vector on the right side of the equation
	 * @return a new vector with the value of left + right
	 */
	public static Vector2f add(Vector2f left, Vector2f right) {
		return new Vector2f(left.getX() + right.getX(), left.getY() + right.getY());
	}

	/**
	 * Subtracts one vector from another in the same way that the non-static {@link #subtract(Vector2f)} method does however this static version creates a new
	 * vector to store the result and also does not change the values stored in the left or right parameters.
	 * 
	 * @param left
	 *            - the vector on the left side of the equation
	 * @param right
	 *            - the vector on the right side of the equation
	 * @return a new vector with the value of left - right
	 */
	public static Vector2f subtract(Vector2f left, Vector2f right) {
		return new Vector2f(left.getX() - right.getX(), left.getY() - right.getY());
	}

	/**
	 * Multiplies two vectors together in the same way that the non-static {@link #multiply(Vector2f)} method does however this static version creates a new
	 * vector to store the result and also does not change the values stored in the left or right parameters.
	 * 
	 * @param left
	 *            - the vector on the left side of the equation
	 * @param right
	 *            - the vector on the right side of the equation
	 * @return a new vector with the value of left * right
	 */
	public static Vector2f multiply(Vector2f left, Vector2f right) {
		return new Vector2f(left.getX() * right.getX(), left.getY() * right.getY());
	}

	/**
	 * Divides one vector by another in the same way that the non-static {@link #divide(Vector2f)} method does however this static version creates a new vector
	 * to store the result and also does not change the values stored in the left or right parameters.
	 * 
	 * @param left
	 *            - the vector on the left side of the equation
	 * @param right
	 *            - the vector on the right side of the equation
	 * @return a new vector with the value of left / right
	 */
	public static Vector2f divide(Vector2f left, Vector2f right) {
		return new Vector2f(left.getX() / right.getX(), left.getY() / right.getY());
	}

	public static Vector2f abs(Vector2f vector) {
		return new Vector2f(vector).abs();
	}

	public static Vector2f ceil(Vector2f vector) {
		return new Vector2f(vector).ceil();
	}

	public Vector2f clamp(Vector2f vector, Vector2f min, Vector2f max) {
		return new Vector2f(vector).min(max).max(min);
	}

	public Vector2f clamp(Vector2f vector, float minX, float minY, float maxX, float maxY) {
		return new Vector2f(vector).min(maxX, maxY).max(minX, minY);
	}

	public Vector2f clamp(Vector2f vector, float min, float max) {
		return new Vector2f(vector).min(max).max(min);
	}

	public static Vector2f clampLength(Vector2f vector, float length) {
		return new Vector2f(vector).clampLength(length);
	}

	public static Vector2f cross(Vector2f vector) {
		return new Vector2f(vector).cross();
	}

	public static Vector2f floor(Vector2f vector) {
		return new Vector2f(vector).floor();
	}

	public static Vector2f lerp(Vector2f vector, Vector2f destination, float value) {
		return new Vector2f(vector).lerp(destination, value);
	}

	public static Vector2f max(Vector2f a, Vector2f b) {
		return new Vector2f(a).max(b);
	}

	public static Vector2f max(Vector2f a, float x, float y) {
		return new Vector2f(a).max(x, y);
	}

	public static Vector2f max(Vector2f a, float value) {
		return new Vector2f(a).max(value);
	}

	public static Vector2f min(Vector2f a, Vector2f b) {
		return new Vector2f(a).min(b);
	}

	public static Vector2f min(Vector2f a, float x, float y) {
		return new Vector2f(a).min(x, y);
	}

	public static Vector2f min(Vector2f a, float value) {
		return new Vector2f(a).min(value);
	}

	public static Vector2f moveTowards(Vector2f vector, Vector2f destination, float value) {
		return new Vector2f(vector).moveTowards(destination, value);
	}

	public static Vector2f negate(Vector2f vector) {
		return new Vector2f(vector).negate();
	}

	public static Vector2f nlerp(Vector2f vector, Vector2f destination, float value) {
		return new Vector2f(vector).nlerp(destination, value);
	}

	public static Vector2f normalize(Vector2f vector) {
		return new Vector2f(vector).normalize();
	}

	public static Vector2f orthoNormalize(Vector2f vector, Vector2f normal) {
		return new Vector2f(vector).orthoNormalize(normal);
	}

	public static Vector2f projectToVector(Vector2f a, Vector2f b) {
		return new Vector2f(a).projectToVector(b);
	}

	public static Vector2f projectToPlane(Vector2f vector, Vector2f normal) {
		return new Vector2f(vector).projectToPlane(normal);
	}

	public static Vector2f reflect(Vector2f vector, Vector2f normal) {
		return new Vector2f(vector).reflect(normal);
	}

	public static Vector2f rotate(Vector2f vector, float angle) {
		return new Vector2f(vector).rotate(angle);
	}

	public static Vector2f rotateTowards(Vector2f vector, Vector2f destination, float value) {
		return new Vector2f(vector).rotateTowards(destination, value);
	}

	public static Vector2f round(Vector2f vector) {
		return new Vector2f(vector).round();
	}

	public static Vector2f scale(Vector2f vector, float scalar) {
		return new Vector2f(vector).scale(scalar);
	}

	public static Vector2f slerp(Vector2f vector, Vector2f destination, float value) {
		return new Vector2f(vector).slerp(destination, value);
	}

	// TODO: Also make work? :D
	// public static Vector2f transform(Vector2f vector, Matrix4f matrix) {
	// return new Vector2f(vector).transform(matrix);
	// }

	public static float angle(Vector2f a, Vector2f b) {
		return a.angle(b);
	}

	public static float distance(Vector2f a, Vector2f b) {
		return a.distance(b);
	}

	public static float distanceSq(Vector2f a, Vector2f b) {
		return a.distanceSq(b);
	}

	public static float dot(Vector2f left, Vector2f right) {
		return left.dot(right);
	}

	public static float length(Vector2f vector) {
		return vector.length();
	}

	public static float lengthSq(Vector2f vector) {
		return vector.lengthSq();
	}

	/**
	 * Adds each component of the vector parameter to each component of the current vector and returns the modified vector.
	 * 
	 * @param vector
	 *            - the vector to add to the current vector
	 * @return this vector after being modified
	 */
	public Vector2f add(Vector2f vector) {
		return this.add(vector.getX(), vector.getY());
	}

	public Vector2f add(float x, float y) {
		this.x += x;
		this.y += y;

		return this;
	}

	/**
	 * Subtracts each component of the vector parameter from each component of the current vector and returns the modified vector.
	 * 
	 * @param vector
	 *            - the vector to subtract from the current vector
	 * @return this vector after being modified
	 */
	public Vector2f subtract(Vector2f vector) {
		return this.subtract(vector.getX(), vector.getY());
	}

	public Vector2f subtract(float x, float y) {
		this.x -= x;
		this.y -= y;

		return this;
	}

	/**
	 * Multiplies each component of the current vector with each component of the vector parameter and returns the modified vector.
	 * 
	 * @param vector
	 *            - the vector to multiply the current vector by
	 * @return this vector after being modified
	 */
	public Vector2f multiply(Vector2f vector) {
		return this.multiply(vector.getX(), vector.getY());
	}

	public Vector2f multiply(float x, float y) {
		this.x *= x;
		this.y *= y;

		return this;
	}

	/**
	 * Divides each component of the current vector by each component of the vector parameter and returns the modified vector.
	 * 
	 * @param vector
	 *            - the vector to divide the current vector by
	 * @return this vector after being modified
	 */
	public Vector2f divide(Vector2f vector) {
		return this.divide(vector.getX(), vector.getY());
	}

	public Vector2f divide(float x, float y) {
		this.x /= x;
		this.y /= y;

		return this;
	}

	public Vector2f abs() {
		this.x = Math.abs(this.x);
		this.y = Math.abs(this.y);

		return this;
	}

	public Vector2f ceil() {
		this.x = (float) Math.ceil(this.x);
		this.y = (float) Math.ceil(this.y);

		return this;
	}

	public Vector2f clamp(Vector2f min, Vector2f max) {
		return this.min(max).max(min);
	}

	public Vector2f clamp(float minX, float minY, float maxX, float maxY) {
		return this.min(maxX, maxY).max(minX, minY);
	}

	public Vector2f clamp(float min, float max) {
		return this.min(max).max(min);
	}

	public Vector2f clampLength(float clampLength) {
		float length = this.length();
		if (length > clampLength) {
			float scalar = clampLength / length;

			return this.scale(scalar);
		}

		return this;
	}

	public Vector2f cross() {
		float cx = this.y;
		float cy = -this.x;
		this.x = cx;
		this.y = cy;
		return this;
	}

	public Vector2f floor() {
		this.x = (float) Math.floor(this.x);
		this.y = (float) Math.floor(this.y);

		return this;
	}

	public Vector2f lerp(Vector2f vector, float value) {
		return this.add(Vector2f.subtract(vector, this).scale(value));
	}

	public Vector2f max(Vector2f vector) {
		this.x = Math.max(this.x, vector.getX());
		this.y = Math.max(this.y, vector.getY());

		return this;
	}

	public Vector2f max(float x, float y) {
		this.x = Math.max(this.x, x);
		this.y = Math.max(this.y, y);

		return this;
	}

	public Vector2f max(float value) {
		this.x = Math.max(this.x, value);
		this.y = Math.max(this.y, value);

		return this;
	}

	public Vector2f min(Vector2f vector) {
		this.x = Math.min(this.x, vector.getX());
		this.y = Math.min(this.y, vector.getY());

		return this;
	}

	public Vector2f min(float x, float y) {
		this.x = Math.min(this.x, x);
		this.y = Math.min(this.y, y);

		return this;
	}

	public Vector2f min(float value) {
		this.x = Math.min(this.x, value);
		this.y = Math.min(this.y, value);

		return this;
	}

	public Vector2f moveTowards(Vector2f vector, float value) {
		if (this.distance(vector) <= value) {
			this.set(vector);
		} else {
			this.add(Vector2f.subtract(vector, this).normalize().scale(value));
		}

		return this;
	}

	public Vector2f negate() {
		this.x = -this.x;
		this.y = -this.y;

		return this;
	}

	public Vector2f nlerp(Vector2f vector, float value) {
		return this.lerp(vector, value).normalize();
	}

	public Vector2f normalize() {
		if (this.isZero())
			return this;

		float length = this.length();

		this.x /= length;
		this.y /= length;

		return this;
	}

	public Vector2f orthoNormalize(Vector2f normal) {
		return projectToPlane(normal).normalize();
	}

	public Vector2f projectToVector(Vector2f vector) {
		Vector2f normal = Vector2f.normalize(vector);

		return this.set(Vector2f.scale(normal, this.dot(normal)));
	}

	public Vector2f projectToPlane(Vector2f normal) {
		return this.subtract(Vector2f.projectToVector(this, normal));
	}

	public Vector2f reflect(Vector2f normal) {
		return this.subtract(Vector2f.scale(normal, this.dot(normal) * 2.0f));
	}

	public Vector2f rotate(float angle) {
		angle = (float) Math.toRadians(angle);

		float sin = (float) Math.sin(angle);
		float cos = (float) Math.cos(angle);

		float rx = (this.x * cos) - (this.y * sin);
		float ry = (this.x * sin) + (this.y * cos);

		this.x = rx;
		this.y = ry;

		return this;
	}

	public Vector2f rotateTowards(Vector2f vector, float value) {
		if (this.angle(vector) <= value) {
			this.set(vector);
		} else {
			this.rotate(value);
		}

		return this;
	}

	public Vector2f round() {
		this.x = (float) Math.round(this.x);
		this.y = (float) Math.round(this.y);

		return this;
	}

	public Vector2f scale(float scalar) {
		this.x *= scalar;
		this.y *= scalar;

		return this;
	}

	public Vector2f slerp(Vector2f vector, float value) {
		float dot = this.dot(vector);
		if (dot < -1.0f)
			dot = -1.0f;
		else if (dot > 1.0f)
			dot = 1.0f;

		float theta = (float) (Math.acos(dot) * value);
		Vector2f RelativeVec = Vector2f.subtract(vector, Vector2f.scale(this, dot)).normalize();

		return this.scale((float) Math.cos(theta)).add(RelativeVec.scale((float) Math.sin(theta)));
	}

	// TODO: Make work? :D
	// public Vector2f transform(Matrix4f matrix) {
	// return this.set(matrix.multiply(this));
	// }

	public float angle(Vector2f vector) {
		return (float) Math.toDegrees(Math.acos(this.dot(vector) / Vector2f.dot(Vector2f.normalize(this), Vector2f.normalize(vector))));
	}

	public float distance(Vector2f vector) {
		return (float) Math.sqrt(this.distanceSq(vector));
	}

	public float distanceSq(Vector2f vector) {
		float dx = vector.getX() - this.x;
		float dy = vector.getY() - this.y;

		return dx * dx + dy * dy;
	}

	public float dot(Vector2f vector) {
		return this.x * vector.getX() + this.y * vector.getY();
	}

	public float length() {
		return (float) Math.sqrt(this.lengthSq());
	}

	public float lengthSq() {
		return x * x + y * y;
	}

	@Override
	public boolean equals(Object object) {
		if (object instanceof Vector2f) {
			return this.isEqual((Vector2f) object);
		}

		return false;
	}

	public boolean isEqual(Vector2f vector) {
		return (this.x == vector.getX() && this.y == vector.getY());
	}

	public boolean isNormalized() {
		float length = this.length();

		return (length >= 0.99999f && length <= 1.00001f);
	}

	public boolean isZero() {
		return this.isEqual(Vector2f.Zero());
	}

	public float[] toArray() {
		return new float[] {
				this.x, this.y
		};
	}

	@Override
	public String toString() {
		return new String("[ " + x + ", " + y + " ]");
	}

	public Vector2f get() {
		return new Vector2f(this);
	}

	public float get(int i) {
		if (i == COMPONENT_INDEX_X)
			return x;
		else if (i == COMPONENT_INDEX_Y)
			return y;
		else
			throw new ArrayIndexOutOfBoundsException("No component exists at index " + i + ".");
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public Vector2f getXY() {
		return new Vector2f(x, y);
	}

	public Vector2f getYX() {
		return new Vector2f(y, x);
	}

	public Vector2f set(Vector2f vector) {
		this.x = vector.getX();
		this.y = vector.getY();

		return this;
	}

	public Vector2f set(int i, float value) {
		if (i == COMPONENT_INDEX_X)
			this.x = value;
		else if (i == COMPONENT_INDEX_Y)
			this.y = value;
		else
			throw new ArrayIndexOutOfBoundsException("No component exists at index " + i + ".");

		return this;
	}

	public Vector2f setLength(float length) {
		return this.scale(length / this.length());
	}

	public Vector2f setX(float x) {
		this.x = x;

		return this;
	}

	public Vector2f setY(float y) {
		this.y = y;

		return this;
	}

	public Vector2f setXY(Vector2f vector) {
		return this.setXY(vector.getX(), vector.getY());
	}

	public Vector2f setXY(float x, float y) {
		this.x = x;
		this.y = y;

		return this;
	}

	public Vector2f setYX(Vector2f vector) {
		return this.setYX(vector.getX(), vector.getY());
	}

	public Vector2f setYX(float y, float x) {
		this.y = y;
		this.x = x;

		return this;
	}
}
