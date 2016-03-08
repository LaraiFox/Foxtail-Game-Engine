package net.laraifox.libdev.math;

public class Vector4f {
	/***
	 * Numeric index of the X component of the vector.
	 */
	public static final int COMPONENT_INDEX_X = 0;
	/***
	 * Numeric index of the Y component of the vector.
	 */
	public static final int COMPONENT_INDEX_Y = 1;
	/***
	 * Numeric index of the Z component of the vector.
	 */
	public static final int COMPONENT_INDEX_Z = 2;
	/***
	 * Numeric index of the W component of the vector.
	 */
	public static final int COMPONENT_INDEX_W = 3;

	/***
	 * The number of components in the vector.
	 */
	public static final int COMPONENT_COUNT = 4;
	/***
	 * The total size in bytes of all components in the vector.
	 */
	public static final int BYTE_COUNT = COMPONENT_COUNT * 4;

	private float x, y, z, w;

	/**
	 * Constructs a new vector setting all components to zero.
	 */
	public Vector4f() {
		this(0.0f, 0.0f, 0.0f, 0.0f);
	}

	/**
	 * Constructs a new vector setting all components to value.
	 * 
	 * @param value
	 *            - the value used to set all components of the vector
	 */
	public Vector4f(float value) {
		this(value, value, value, value);
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
	public Vector4f(float x, float y, float z, float w) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}

	public Vector4f(Vector3f vector) {
		this(vector.getX(), vector.getY(), vector.getZ(), 0.0f);
	}
	
	public Vector4f(Vector3f vector, float w) {
		this(vector.getX(), vector.getY(), vector.getZ(), w);
	}

	/**
	 * Constructs a new vector that is a copy of the vector parameter with all components equal to those of the vector parameter. Note that further changes to
	 * this newly constructed vector will not have any effect on the original vector that was copied.
	 * 
	 * @param vector
	 *            - the vector to be copied
	 */
	public Vector4f(Vector4f vector) {
		this(vector.getX(), vector.getY(), vector.getZ(), vector.getW());
	}

	/***
	 * Creates and returns a new vector of length zero.
	 * 
	 * @return the newly created vector
	 */
	public static Vector4f Zero() {
		return new Vector4f(0.0f, 0.0f, 0.0f, 0.0f);
	}

	/***
	 * Creates and returns a new vector with all components set to one.
	 * 
	 * @return the newly created vector
	 */
	public static Vector4f One() {
		return new Vector4f(1.0f, 1.0f, 1.0f, 1.0f);
	}

	/**
	 * Adds two vectors together in the same way that the non-static {@link #add(Vector4f)} method does however this static version creates a new vector to
	 * store the result and also does not change the values stored in the left or right parameters.
	 * 
	 * @param left
	 *            - the vector on the left side of the equation
	 * @param right
	 *            - the vector on the right side of the equation
	 * @return a new vector with the value of left + right
	 */
	public static Vector4f add(Vector4f left, Vector4f right) {
		return new Vector4f(left.getX() + right.getX(), left.getY() + right.getY(), left.getZ() + right.getZ(), left.getW() + right.getW());
	}

	/**
	 * Subtracts one vector from another in the same way that the non-static {@link #subtract(Vector4f)} method does however this static version creates a new
	 * vector to store the result and also does not change the values stored in the left or right parameters.
	 * 
	 * @param left
	 *            - the vector on the left side of the equation
	 * @param right
	 *            - the vector on the right side of the equation
	 * @return a new vector with the value of left - right
	 */
	public static Vector4f subtract(Vector4f left, Vector4f right) {
		return new Vector4f(left.getX() - right.getX(), left.getY() - right.getY(), left.getZ() - right.getZ(), left.getW() - right.getW());
	}

	/**
	 * Multiplies two vectors together in the same way that the non-static {@link #multiply(Vector4f)} method does however this static version creates a new
	 * vector to store the result and also does not change the values stored in the left or right parameters.
	 * 
	 * @param left
	 *            - the vector on the left side of the equation
	 * @param right
	 *            - the vector on the right side of the equation
	 * @return a new vector with the value of left * right
	 */
	public static Vector4f multiply(Vector4f left, Vector4f right) {
		return new Vector4f(left.getX() * right.getX(), left.getY() * right.getY(), left.getZ() * right.getZ(), left.getW() * right.getW());
	}

	/**
	 * Divides one vector by another in the same way that the non-static {@link #divide(Vector4f)} method does however this static version creates a new vector
	 * to store the result and also does not change the values stored in the left or right parameters.
	 * 
	 * @param left
	 *            - the vector on the left side of the equation
	 * @param right
	 *            - the vector on the right side of the equation
	 * @return a new vector with the value of left / right
	 */
	public static Vector4f divide(Vector4f left, Vector4f right) {
		return new Vector4f(left.getX() / right.getX(), left.getY() / right.getY(), left.getZ() / right.getZ(), left.getW() / right.getW());
	}

	public static Vector4f abs(Vector4f vector) {
		return new Vector4f(vector).abs();
	}

	public static Vector4f ceil(Vector4f vector) {
		return new Vector4f(vector).ceil();
	}

	public Vector4f clamp(Vector4f vector, Vector4f min, Vector4f max) {
		return new Vector4f(vector).min(max).max(min);
	}

	public Vector4f clamp(Vector4f vector, float minX, float minY, float minZ, float minW, float maxX, float maxY, float maxZ, float maxW) {
		return new Vector4f(vector).min(maxX, maxY, maxZ, maxW).max(minX, minY, minZ, minW);
	}

	public Vector4f clamp(Vector4f vector, float min, float max) {
		return new Vector4f(vector).min(max).max(min);
	}

	public static Vector4f clampLength(Vector4f vector, float length) {
		return new Vector4f(vector).clampLength(length);
	}

	public static Vector4f floor(Vector4f vector) {
		return new Vector4f(vector).floor();
	}

	public static Vector4f lerp(Vector4f vector, Vector4f destination, float value) {
		return new Vector4f(vector).lerp(destination, value);
	}

	public static Vector4f max(Vector4f a, Vector4f b) {
		return new Vector4f(a).max(b);
	}

	public static Vector4f max(Vector4f a, float x, float y, float z, float w) {
		return new Vector4f(a).max(x, y, z, w);
	}

	public static Vector4f max(Vector4f a, float value) {
		return new Vector4f(a).max(value);
	}

	public static Vector4f min(Vector4f a, Vector4f b) {
		return new Vector4f(a).min(b);
	}

	public static Vector4f min(Vector4f a, float x, float y, float z, float w) {
		return new Vector4f(a).min(x, y, z, w);
	}

	public static Vector4f min(Vector4f a, float value) {
		return new Vector4f(a).min(value);
	}

	public static Vector4f moveTowards(Vector4f vector, Vector4f destination, float value) {
		return new Vector4f(vector).moveTowards(destination, value);
	}

	public static Vector4f negate(Vector4f vector) {
		return new Vector4f(vector).negate();
	}

	public static Vector4f nlerp(Vector4f vector, Vector4f destination, float value) {
		return new Vector4f(vector).nlerp(destination, value);
	}

	public static Vector4f normalize(Vector4f vector) {
		return new Vector4f(vector).normalize();
	}

	public static Vector4f orthoNormalize(Vector4f vector, Vector4f normal) {
		return new Vector4f(vector).orthoNormalize(normal);
	}

	public static Vector4f projectToVector(Vector4f a, Vector4f b) {
		return new Vector4f(a).projectToVector(b);
	}

	public static Vector4f projectToPlane(Vector4f vector, Vector4f normal) {
		return new Vector4f(vector).projectToPlane(normal);
	}

	public static Vector4f round(Vector4f vector) {
		return new Vector4f(vector).round();
	}

	public static Vector4f scale(Vector4f vector, float scalar) {
		return new Vector4f(vector).scale(scalar);
	}

	public static Vector4f slerp(Vector4f vector, Vector4f destination, float value) {
		return new Vector4f(vector).slerp(destination, value);
	}

	public static float distance(Vector4f a, Vector4f b) {
		return a.distance(b);
	}

	public static float distanceSq(Vector4f a, Vector4f b) {
		return a.distanceSq(b);
	}

	public static float dot(Vector4f left, Vector4f right) {
		return left.dot(right);
	}

	public static float length(Vector4f vector) {
		return vector.length();
	}

	public static float lengthSq(Vector4f vector) {
		return vector.lengthSq();
	}

	/**
	 * Adds each component of the vector parameter to each component of the current vector and returns the modified vector.
	 * 
	 * @param vector
	 *            - the vector to add to the current vector
	 * @return this vector after being modified
	 */
	public Vector4f add(Vector4f vector) {
		return this.add(vector.getX(), vector.getY(), vector.getZ(), vector.getW());
	}

	public Vector4f add(float x, float y, float z, float w) {
		this.x += x;
		this.y += y;
		this.z += z;
		this.w += w;

		return this;
	}

	/**
	 * Subtracts each component of the vector parameter from each component of the current vector and returns the modified vector.
	 * 
	 * @param vector
	 *            - the vector to subtract from the current vector
	 * @return this vector after being modified
	 */
	public Vector4f subtract(Vector4f vector) {
		return this.subtract(vector.getX(), vector.getY(), vector.getZ(), vector.getW());
	}

	public Vector4f subtract(float x, float y, float z, float w) {
		this.x -= x;
		this.y -= y;
		this.z -= z;
		this.w -= w;

		return this;
	}

	/**
	 * Multiplies each component of the current vector with each component of the vector parameter and returns the modified vector.
	 * 
	 * @param vector
	 *            - the vector to multiply the current vector by
	 * @return this vector after being modified
	 */
	public Vector4f multiply(Vector4f vector) {
		return this.multiply(vector.getX(), vector.getY(), vector.getZ(), vector.getW());
	}

	public Vector4f multiply(float x, float y, float z, float w) {
		this.x *= x;
		this.y *= y;
		this.z *= z;
		this.w *= w;

		return this;
	}

	/**
	 * Divides each component of the current vector by each component of the vector parameter and returns the modified vector.
	 * 
	 * @param vector
	 *            - the vector to divide the current vector by
	 * @return this vector after being modified
	 */
	public Vector4f divide(Vector4f vector) {
		return this.divide(vector.getX(), vector.getY(), vector.getZ(), vector.getW());
	}

	public Vector4f divide(float x, float y, float z, float w) {
		this.x /= x;
		this.y /= y;
		this.z /= z;
		this.w /= w;

		return this;
	}

	public Vector4f abs() {
		this.x = Math.abs(this.x);
		this.y = Math.abs(this.y);
		this.z = Math.abs(this.z);
		this.w = Math.abs(this.w);

		return this;
	}

	public Vector4f ceil() {
		this.x = (float) Math.ceil(this.x);
		this.y = (float) Math.ceil(this.y);
		this.z = (float) Math.ceil(this.z);
		this.w = (float) Math.ceil(this.w);

		return this;
	}

	public Vector4f clamp(Vector4f min, Vector4f max) {
		return this.min(max).max(min);
	}

	public Vector4f clamp(float minX, float minY, float minZ, float minW, float maxX, float maxY, float maxZ, float maxW) {
		return this.min(maxX, maxY, maxZ, maxW).max(minX, minY, minZ, minW);
	}

	public Vector4f clamp(float min, float max) {
		return this.min(max).max(min);
	}

	public Vector4f clampLength(float clampLength) {
		float length = this.length();
		if (length > clampLength) {
			float scalar = clampLength / length;

			return this.scale(scalar);
		}

		return this;
	}

	public Vector4f floor() {
		this.x = (float) Math.floor(this.x);
		this.y = (float) Math.floor(this.y);
		this.z = (float) Math.floor(this.z);
		this.w = (float) Math.floor(this.w);

		return this;
	}

	public Vector4f lerp(Vector4f vector, float value) {
		return this.add(Vector4f.subtract(vector, this).scale(value));
	}

	public Vector4f max(Vector4f vector) {
		this.x = Math.max(this.x, vector.getX());
		this.y = Math.max(this.y, vector.getY());
		this.z = Math.max(this.z, vector.getZ());
		this.w = Math.max(this.w, vector.getW());

		return this;
	}

	public Vector4f max(float x, float y, float z, float w) {
		this.x = Math.max(this.x, x);
		this.y = Math.max(this.y, y);
		this.z = Math.max(this.z, z);
		this.w = Math.max(this.w, w);

		return this;
	}

	public Vector4f max(float value) {
		this.x = Math.max(this.x, value);
		this.y = Math.max(this.y, value);
		this.z = Math.max(this.z, value);
		this.w = Math.max(this.w, value);

		return this;
	}

	public Vector4f min(Vector4f vector) {
		this.x = Math.min(this.x, vector.getX());
		this.y = Math.min(this.y, vector.getY());
		this.z = Math.min(this.z, vector.getZ());
		this.w = Math.min(this.w, vector.getW());

		return this;
	}

	public Vector4f min(float x, float y, float z, float w) {
		this.x = Math.min(this.x, x);
		this.y = Math.min(this.y, y);
		this.z = Math.min(this.z, z);
		this.w = Math.min(this.w, w);

		return this;
	}

	public Vector4f min(float value) {
		this.x = Math.min(this.x, value);
		this.y = Math.min(this.y, value);
		this.z = Math.min(this.z, value);
		this.w = Math.min(this.w, value);

		return this;
	}

	public Vector4f moveTowards(Vector4f vector, float value) {
		if (this.distance(vector) <= value) {
			this.set(vector);
		} else {
			this.add(Vector4f.subtract(vector, this).normalize().scale(value));
		}

		return this;
	}

	public Vector4f negate() {
		this.x = -this.x;
		this.y = -this.y;
		this.z = -this.z;
		this.w = -this.w;

		return this;
	}

	public Vector4f nlerp(Vector4f vector, float value) {
		return this.lerp(vector, value).normalize();
	}

	public Vector4f normalize() {
		if (this.isZero())
			return this;

		float length = this.length();

		this.x /= length;
		this.y /= length;
		this.z /= length;
		this.w /= length;

		return this;
	}

	public Vector4f orthoNormalize(Vector4f normal) {
		return projectToPlane(normal).normalize();
	}

	public Vector4f projectToVector(Vector4f vector) {
		Vector4f normal = Vector4f.normalize(vector);

		return this.set(Vector4f.scale(normal, this.dot(normal)));
	}

	public Vector4f projectToPlane(Vector4f normal) {
		return this.subtract(Vector4f.projectToVector(this, normal));
	}

	public Vector4f round() {
		this.x = (float) Math.round(this.x);
		this.y = (float) Math.round(this.y);
		this.z = (float) Math.round(this.z);
		this.w = (float) Math.round(this.w);

		return this;
	}

	public Vector4f scale(float scalar) {
		this.x *= scalar;
		this.y *= scalar;
		this.z *= scalar;
		this.w *= scalar;

		return this;
	}

	public Vector4f slerp(Vector4f vector, float value) {
		float dot = this.dot(vector);
		if (dot < -1.0f)
			dot = -1.0f;
		else if (dot > 1.0f)
			dot = 1.0f;

		float theta = (float) (Math.acos(dot) * value);
		Vector4f RelativeVec = Vector4f.subtract(vector, Vector4f.scale(this, dot)).normalize();

		return this.scale((float) Math.cos(theta)).add((RelativeVec.scale((float) Math.sin(theta))));
	}

	public float distance(Vector4f vector) {
		return (float) Math.sqrt(this.distanceSq(vector));
	}

	public float distanceSq(Vector4f vector) {
		float dx = vector.getX() - this.x;
		float dy = vector.getY() - this.y;
		float dz = vector.getZ() - this.z;
		float dw = vector.getW() - this.w;

		return dx * dx + dy * dy + dz * dz + dw * dw;
	}

	public float dot(Vector4f vector) {
		return this.x * vector.getX() + this.y * vector.getY() + this.z * vector.getZ() + this.w * vector.getW();
	}

	public float length() {
		return (float) Math.sqrt(this.lengthSq());
	}

	public float lengthSq() {
		return x * x + y * y + z * z + w * w;
	}

	@Override
	public boolean equals(Object object) {
		if (object instanceof Vector4f) {
			return this.isEqual((Vector4f) object);
		}

		return false;
	}

	public boolean isEqual(Vector4f vector) {
		return (this.x == vector.getX() && this.y == vector.getY() && this.z == vector.getZ() && this.w == vector.getW());
	}

	public boolean isNormalized() {
		float length = this.length();

		return (length >= 0.99999f && length <= 1.00001f);
	}

	public boolean isZero() {
		return this.isEqual(Vector4f.Zero());
	}

	public float[] toArray() {
		return new float[] {
				this.x, this.y, this.z, this.w
		};
	}

	@Override
	public String toString() {
		return new String("[ " + x + ", " + y + ", " + z + ", " + w + " ]");
	}

	public Vector4f get() {
		return new Vector4f(this);
	}

	public float get(int i) {
		if (i == COMPONENT_INDEX_X)
			return x;
		else if (i == COMPONENT_INDEX_Y)
			return y;
		else if (i == COMPONENT_INDEX_Z)
			return z;
		else if (i == COMPONENT_INDEX_W)
			return w;
		else
			throw new ArrayIndexOutOfBoundsException("No component exists at index " + i + ".");
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

	public Vector4f set(Vector4f vector) {
		this.x = vector.getX();
		this.y = vector.getY();
		this.z = vector.getZ();
		this.w = vector.getW();

		return this;
	}

	public Vector4f set(int i, float value) {
		if (i == COMPONENT_INDEX_X)
			this.x = value;
		else if (i == COMPONENT_INDEX_Y)
			this.y = value;
		else if (i == COMPONENT_INDEX_Z)
			this.z = value;
		else if (i == COMPONENT_INDEX_W)
			this.w = value;
		else
			throw new ArrayIndexOutOfBoundsException("No component exists at index " + i + ".");

		return this;
	}

	public Vector4f setLength(float length) {
		return this.scale(length / this.length());
	}

	public Vector4f setX(float x) {
		this.x = x;

		return this;
	}

	public Vector4f setY(float y) {
		this.y = y;

		return this;
	}

	public Vector4f setZ(float z) {
		this.z = z;

		return this;
	}

	public Vector4f setW(float w) {
		this.w = w;

		return this;
	}

}
