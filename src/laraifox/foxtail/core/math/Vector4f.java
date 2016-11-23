package laraifox.foxtail.core.math;

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
	public static final int BYTE_COUNT = COMPONENT_COUNT * Float.BYTES;

	private float x, y, z, w;

	/**
	 * Constructs a new vector setting all components to zero.
	 */
	public Vector4f() {
		this(0.0f, 0.0f, 0.0f, 0.0f);
	}

	public Vector4f(float value) {
		this(value, value, value, value);
	}

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

	public Vector4f(org.lwjgl.util.vector.Vector4f vector) {
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

	public static Vector4f clamp(Vector4f vector, Vector4f min, Vector4f max) {
		return new Vector4f(vector).min(max).max(min);
	}

	public static Vector4f clamp(Vector4f vector, float minX, float minY, float minZ, float minW, float maxX, float maxY, float maxZ, float maxW) {
		return new Vector4f(vector).min(maxX, maxY, maxZ, maxW).max(minX, minY, minZ, minW);
	}

	public static Vector4f clamp(Vector4f vector, float min, float max) {
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
		return (this.x == 0.0f && this.y == 0.0f && this.z == 0.0f && this.w == 0.0f);
	}

	@Override
	public String toString() {
		return new String("[ " + x + ", " + y + ", " + z + ", " + w + " ]");
	}

	public float[] get() {
		return new float[] {
				x, y, z, w
		};
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

	public final float getX() {
		return x;
	}

	public final float getY() {
		return y;
	}

	public final float getZ() {
		return z;
	}

	public final float getW() {
		return w;
	}

	public final Vector2f getXX() {
		return new Vector2f(x, x);
	}

	public final Vector2f getXY() {
		return new Vector2f(x, y);
	}

	public final Vector2f getXZ() {
		return new Vector2f(x, z);
	}

	public final Vector2f getXW() {
		return new Vector2f(x, w);
	}

	public final Vector2f getYX() {
		return new Vector2f(y, x);
	}

	public final Vector2f getYY() {
		return new Vector2f(y, y);
	}

	public final Vector2f getYZ() {
		return new Vector2f(y, z);
	}

	public final Vector2f getYW() {
		return new Vector2f(y, w);
	}

	public final Vector2f getZX() {
		return new Vector2f(z, x);
	}

	public final Vector2f getZY() {
		return new Vector2f(z, y);
	}

	public final Vector2f getZZ() {
		return new Vector2f(z, z);
	}

	public final Vector2f getZW() {
		return new Vector2f(z, w);
	}

	public final Vector2f getWX() {
		return new Vector2f(w, x);
	}

	public final Vector2f getWY() {
		return new Vector2f(w, y);
	}

	public final Vector2f getWZ() {
		return new Vector2f(w, z);
	}

	public final Vector2f getWW() {
		return new Vector2f(w, w);
	}

	public final Vector3f getXXX() {
		return new Vector3f(x, x, x);
	}

	public final Vector3f getXXY() {
		return new Vector3f(x, x, y);
	}

	public final Vector3f getXXZ() {
		return new Vector3f(x, x, z);
	}

	public final Vector3f getXXW() {
		return new Vector3f(x, x, w);
	}

	public final Vector3f getXYX() {
		return new Vector3f(x, y, x);
	}

	public final Vector3f getXYY() {
		return new Vector3f(x, y, y);
	}

	public final Vector3f getXYZ() {
		return new Vector3f(x, y, z);
	}

	public final Vector3f getXYW() {
		return new Vector3f(x, y, w);
	}

	public final Vector3f getXZX() {
		return new Vector3f(x, z, x);
	}

	public final Vector3f getXZY() {
		return new Vector3f(x, z, y);
	}

	public final Vector3f getXZZ() {
		return new Vector3f(x, z, z);
	}

	public final Vector3f getXZW() {
		return new Vector3f(x, z, w);
	}

	public final Vector3f getXWX() {
		return new Vector3f(x, w, x);
	}

	public final Vector3f getXWY() {
		return new Vector3f(x, w, y);
	}

	public final Vector3f getXWZ() {
		return new Vector3f(x, w, z);
	}

	public final Vector3f getXWW() {
		return new Vector3f(x, w, w);
	}

	public final Vector3f getYXX() {
		return new Vector3f(y, x, x);
	}

	public final Vector3f getYXY() {
		return new Vector3f(y, x, y);
	}

	public final Vector3f getYXZ() {
		return new Vector3f(y, x, z);
	}

	public final Vector3f getYXW() {
		return new Vector3f(y, x, w);
	}

	public final Vector3f getYYX() {
		return new Vector3f(y, y, x);
	}

	public final Vector3f getYYY() {
		return new Vector3f(y, y, y);
	}

	public final Vector3f getYYZ() {
		return new Vector3f(y, y, z);
	}

	public final Vector3f getYYW() {
		return new Vector3f(y, y, w);
	}

	public final Vector3f getYZX() {
		return new Vector3f(y, z, x);
	}

	public final Vector3f getYZY() {
		return new Vector3f(y, z, y);
	}

	public final Vector3f getYZZ() {
		return new Vector3f(y, z, z);
	}

	public final Vector3f getYZW() {
		return new Vector3f(y, z, w);
	}

	public final Vector3f getYWX() {
		return new Vector3f(y, w, x);
	}

	public final Vector3f getYWY() {
		return new Vector3f(y, w, y);
	}

	public final Vector3f getYWZ() {
		return new Vector3f(y, w, z);
	}

	public final Vector3f getYWW() {
		return new Vector3f(y, w, w);
	}

	public final Vector3f getZXX() {
		return new Vector3f(z, x, x);
	}

	public final Vector3f getZXY() {
		return new Vector3f(z, x, y);
	}

	public final Vector3f getZXZ() {
		return new Vector3f(z, x, z);
	}

	public final Vector3f getZXW() {
		return new Vector3f(z, x, w);
	}

	public final Vector3f getZYX() {
		return new Vector3f(z, y, x);
	}

	public final Vector3f getZYY() {
		return new Vector3f(z, y, y);
	}

	public final Vector3f getZYZ() {
		return new Vector3f(z, y, z);
	}

	public final Vector3f getZYW() {
		return new Vector3f(z, y, w);
	}

	public final Vector3f getZZX() {
		return new Vector3f(z, z, x);
	}

	public final Vector3f getZZY() {
		return new Vector3f(z, z, y);
	}

	public final Vector3f getZZZ() {
		return new Vector3f(z, z, z);
	}

	public final Vector3f getZZW() {
		return new Vector3f(z, z, w);
	}

	public final Vector3f getZWX() {
		return new Vector3f(z, w, x);
	}

	public final Vector3f getZWY() {
		return new Vector3f(z, w, y);
	}

	public final Vector3f getZWZ() {
		return new Vector3f(z, w, z);
	}

	public final Vector3f getZWW() {
		return new Vector3f(z, w, w);
	}

	public final Vector3f getWXX() {
		return new Vector3f(w, x, x);
	}

	public final Vector3f getWXY() {
		return new Vector3f(w, x, y);
	}

	public final Vector3f getWXZ() {
		return new Vector3f(w, x, z);
	}

	public final Vector3f getWXW() {
		return new Vector3f(w, x, w);
	}

	public final Vector3f getWYX() {
		return new Vector3f(w, y, x);
	}

	public final Vector3f getWYY() {
		return new Vector3f(w, y, y);
	}

	public final Vector3f getWYZ() {
		return new Vector3f(w, y, z);
	}

	public final Vector3f getWYW() {
		return new Vector3f(w, y, w);
	}

	public final Vector3f getWZX() {
		return new Vector3f(w, z, x);
	}

	public final Vector3f getWZY() {
		return new Vector3f(w, z, y);
	}

	public final Vector3f getWZZ() {
		return new Vector3f(w, z, z);
	}

	public final Vector3f getWZW() {
		return new Vector3f(w, z, w);
	}

	public final Vector3f getWWX() {
		return new Vector3f(w, w, x);
	}

	public final Vector3f getWWY() {
		return new Vector3f(w, w, y);
	}

	public final Vector3f getWWZ() {
		return new Vector3f(w, w, z);
	}

	public final Vector3f getWWW() {
		return new Vector3f(w, w, w);
	}

	public final Vector4f getXXXX() {
		return new Vector4f(x, x, x, x);
	}

	public final Vector4f getXXXY() {
		return new Vector4f(x, x, x, y);
	}

	public final Vector4f getXXXZ() {
		return new Vector4f(x, x, x, z);
	}

	public final Vector4f getXXXW() {
		return new Vector4f(x, x, x, w);
	}

	public final Vector4f getXXYX() {
		return new Vector4f(x, x, y, x);
	}

	public final Vector4f getXXYY() {
		return new Vector4f(x, x, y, y);
	}

	public final Vector4f getXXYZ() {
		return new Vector4f(x, x, y, z);
	}

	public final Vector4f getXXYW() {
		return new Vector4f(x, x, y, w);
	}

	public final Vector4f getXXZX() {
		return new Vector4f(x, x, z, x);
	}

	public final Vector4f getXXZY() {
		return new Vector4f(x, x, z, y);
	}

	public final Vector4f getXXZZ() {
		return new Vector4f(x, x, z, z);
	}

	public final Vector4f getXXZW() {
		return new Vector4f(x, x, z, w);
	}

	public final Vector4f getXXWX() {
		return new Vector4f(x, x, w, x);
	}

	public final Vector4f getXXWY() {
		return new Vector4f(x, x, w, y);
	}

	public final Vector4f getXXWZ() {
		return new Vector4f(x, x, w, z);
	}

	public final Vector4f getXXWW() {
		return new Vector4f(x, x, w, w);
	}

	public final Vector4f getXYXX() {
		return new Vector4f(x, y, x, x);
	}

	public final Vector4f getXYXY() {
		return new Vector4f(x, y, x, y);
	}

	public final Vector4f getXYXZ() {
		return new Vector4f(x, y, x, z);
	}

	public final Vector4f getXYXW() {
		return new Vector4f(x, y, x, w);
	}

	public final Vector4f getXYYX() {
		return new Vector4f(x, y, y, x);
	}

	public final Vector4f getXYYY() {
		return new Vector4f(x, y, y, y);
	}

	public final Vector4f getXYYZ() {
		return new Vector4f(x, y, y, z);
	}

	public final Vector4f getXYYW() {
		return new Vector4f(x, y, y, w);
	}

	public final Vector4f getXYZX() {
		return new Vector4f(x, y, z, x);
	}

	public final Vector4f getXYZY() {
		return new Vector4f(x, y, z, y);
	}

	public final Vector4f getXYZZ() {
		return new Vector4f(x, y, z, z);
	}

	public final Vector4f getXYZW() {
		return new Vector4f(x, y, z, w);
	}

	public final Vector4f getXYWX() {
		return new Vector4f(x, y, w, x);
	}

	public final Vector4f getXYWY() {
		return new Vector4f(x, y, w, y);
	}

	public final Vector4f getXYWZ() {
		return new Vector4f(x, y, w, z);
	}

	public final Vector4f getXYWW() {
		return new Vector4f(x, y, w, w);
	}

	public final Vector4f getXZXX() {
		return new Vector4f(x, z, x, x);
	}

	public final Vector4f getXZXY() {
		return new Vector4f(x, z, x, y);
	}

	public final Vector4f getXZXZ() {
		return new Vector4f(x, z, x, z);
	}

	public final Vector4f getXZXW() {
		return new Vector4f(x, z, x, w);
	}

	public final Vector4f getXZYX() {
		return new Vector4f(x, z, y, x);
	}

	public final Vector4f getXZYY() {
		return new Vector4f(x, z, y, y);
	}

	public final Vector4f getXZYZ() {
		return new Vector4f(x, z, y, z);
	}

	public final Vector4f getXZYW() {
		return new Vector4f(x, z, y, w);
	}

	public final Vector4f getXZZX() {
		return new Vector4f(x, z, z, x);
	}

	public final Vector4f getXZZY() {
		return new Vector4f(x, z, z, y);
	}

	public final Vector4f getXZZZ() {
		return new Vector4f(x, z, z, z);
	}

	public final Vector4f getXZZW() {
		return new Vector4f(x, z, z, w);
	}

	public final Vector4f getXZWX() {
		return new Vector4f(x, z, w, x);
	}

	public final Vector4f getXZWY() {
		return new Vector4f(x, z, w, y);
	}

	public final Vector4f getXZWZ() {
		return new Vector4f(x, z, w, z);
	}

	public final Vector4f getXZWW() {
		return new Vector4f(x, z, w, w);
	}

	public final Vector4f getXWXX() {
		return new Vector4f(x, w, x, x);
	}

	public final Vector4f getXWXY() {
		return new Vector4f(x, w, x, y);
	}

	public final Vector4f getXWXZ() {
		return new Vector4f(x, w, x, z);
	}

	public final Vector4f getXWXW() {
		return new Vector4f(x, w, x, w);
	}

	public final Vector4f getXWYX() {
		return new Vector4f(x, w, y, x);
	}

	public final Vector4f getXWYY() {
		return new Vector4f(x, w, y, y);
	}

	public final Vector4f getXWYZ() {
		return new Vector4f(x, w, y, z);
	}

	public final Vector4f getXWYW() {
		return new Vector4f(x, w, y, w);
	}

	public final Vector4f getXWZX() {
		return new Vector4f(x, w, z, x);
	}

	public final Vector4f getXWZY() {
		return new Vector4f(x, w, z, y);
	}

	public final Vector4f getXWZZ() {
		return new Vector4f(x, w, z, z);
	}

	public final Vector4f getXWZW() {
		return new Vector4f(x, w, z, w);
	}

	public final Vector4f getXWWX() {
		return new Vector4f(x, w, w, x);
	}

	public final Vector4f getXWWY() {
		return new Vector4f(x, w, w, y);
	}

	public final Vector4f getXWWZ() {
		return new Vector4f(x, w, w, z);
	}

	public final Vector4f getXWWW() {
		return new Vector4f(x, w, w, w);
	}

	public final Vector4f getYXXX() {
		return new Vector4f(y, x, x, x);
	}

	public final Vector4f getYXXY() {
		return new Vector4f(y, x, x, y);
	}

	public final Vector4f getYXXZ() {
		return new Vector4f(y, x, x, z);
	}

	public final Vector4f getYXXW() {
		return new Vector4f(y, x, x, w);
	}

	public final Vector4f getYXYX() {
		return new Vector4f(y, x, y, x);
	}

	public final Vector4f getYXYY() {
		return new Vector4f(y, x, y, y);
	}

	public final Vector4f getYXYZ() {
		return new Vector4f(y, x, y, z);
	}

	public final Vector4f getYXYW() {
		return new Vector4f(y, x, y, w);
	}

	public final Vector4f getYXZX() {
		return new Vector4f(y, x, z, x);
	}

	public final Vector4f getYXZY() {
		return new Vector4f(y, x, z, y);
	}

	public final Vector4f getYXZZ() {
		return new Vector4f(y, x, z, z);
	}

	public final Vector4f getYXZW() {
		return new Vector4f(y, x, z, w);
	}

	public final Vector4f getYXWX() {
		return new Vector4f(y, x, w, x);
	}

	public final Vector4f getYXWY() {
		return new Vector4f(y, x, w, y);
	}

	public final Vector4f getYXWZ() {
		return new Vector4f(y, x, w, z);
	}

	public final Vector4f getYXWW() {
		return new Vector4f(y, x, w, w);
	}

	public final Vector4f getYYXX() {
		return new Vector4f(y, y, x, x);
	}

	public final Vector4f getYYXY() {
		return new Vector4f(y, y, x, y);
	}

	public final Vector4f getYYXZ() {
		return new Vector4f(y, y, x, z);
	}

	public final Vector4f getYYXW() {
		return new Vector4f(y, y, x, w);
	}

	public final Vector4f getYYYX() {
		return new Vector4f(y, y, y, x);
	}

	public final Vector4f getYYYY() {
		return new Vector4f(y, y, y, y);
	}

	public final Vector4f getYYYZ() {
		return new Vector4f(y, y, y, z);
	}

	public final Vector4f getYYYW() {
		return new Vector4f(y, y, y, w);
	}

	public final Vector4f getYYZX() {
		return new Vector4f(y, y, z, x);
	}

	public final Vector4f getYYZY() {
		return new Vector4f(y, y, z, y);
	}

	public final Vector4f getYYZZ() {
		return new Vector4f(y, y, z, z);
	}

	public final Vector4f getYYZW() {
		return new Vector4f(y, y, z, w);
	}

	public final Vector4f getYYWX() {
		return new Vector4f(y, y, w, x);
	}

	public final Vector4f getYYWY() {
		return new Vector4f(y, y, w, y);
	}

	public final Vector4f getYYWZ() {
		return new Vector4f(y, y, w, z);
	}

	public final Vector4f getYYWW() {
		return new Vector4f(y, y, w, w);
	}

	public final Vector4f getYZXX() {
		return new Vector4f(y, z, x, x);
	}

	public final Vector4f getYZXY() {
		return new Vector4f(y, z, x, y);
	}

	public final Vector4f getYZXZ() {
		return new Vector4f(y, z, x, z);
	}

	public final Vector4f getYZXW() {
		return new Vector4f(y, z, x, w);
	}

	public final Vector4f getYZYX() {
		return new Vector4f(y, z, y, x);
	}

	public final Vector4f getYZYY() {
		return new Vector4f(y, z, y, y);
	}

	public final Vector4f getYZYZ() {
		return new Vector4f(y, z, y, z);
	}

	public final Vector4f getYZYW() {
		return new Vector4f(y, z, y, w);
	}

	public final Vector4f getYZZX() {
		return new Vector4f(y, z, z, x);
	}

	public final Vector4f getYZZY() {
		return new Vector4f(y, z, z, y);
	}

	public final Vector4f getYZZZ() {
		return new Vector4f(y, z, z, z);
	}

	public final Vector4f getYZZW() {
		return new Vector4f(y, z, z, w);
	}

	public final Vector4f getYZWX() {
		return new Vector4f(y, z, w, x);
	}

	public final Vector4f getYZWY() {
		return new Vector4f(y, z, w, y);
	}

	public final Vector4f getYZWZ() {
		return new Vector4f(y, z, w, z);
	}

	public final Vector4f getYZWW() {
		return new Vector4f(y, z, w, w);
	}

	public final Vector4f getYWXX() {
		return new Vector4f(y, w, x, x);
	}

	public final Vector4f getYWXY() {
		return new Vector4f(y, w, x, y);
	}

	public final Vector4f getYWXZ() {
		return new Vector4f(y, w, x, z);
	}

	public final Vector4f getYWXW() {
		return new Vector4f(y, w, x, w);
	}

	public final Vector4f getYWYX() {
		return new Vector4f(y, w, y, x);
	}

	public final Vector4f getYWYY() {
		return new Vector4f(y, w, y, y);
	}

	public final Vector4f getYWYZ() {
		return new Vector4f(y, w, y, z);
	}

	public final Vector4f getYWYW() {
		return new Vector4f(y, w, y, w);
	}

	public final Vector4f getYWZX() {
		return new Vector4f(y, w, z, x);
	}

	public final Vector4f getYWZY() {
		return new Vector4f(y, w, z, y);
	}

	public final Vector4f getYWZZ() {
		return new Vector4f(y, w, z, z);
	}

	public final Vector4f getYWZW() {
		return new Vector4f(y, w, z, w);
	}

	public final Vector4f getYWWX() {
		return new Vector4f(y, w, w, x);
	}

	public final Vector4f getYWWY() {
		return new Vector4f(y, w, w, y);
	}

	public final Vector4f getYWWZ() {
		return new Vector4f(y, w, w, z);
	}

	public final Vector4f getYWWW() {
		return new Vector4f(y, w, w, w);
	}

	public final Vector4f getZXXX() {
		return new Vector4f(z, x, x, x);
	}

	public final Vector4f getZXXY() {
		return new Vector4f(z, x, x, y);
	}

	public final Vector4f getZXXZ() {
		return new Vector4f(z, x, x, z);
	}

	public final Vector4f getZXXW() {
		return new Vector4f(z, x, x, w);
	}

	public final Vector4f getZXYX() {
		return new Vector4f(z, x, y, x);
	}

	public final Vector4f getZXYY() {
		return new Vector4f(z, x, y, y);
	}

	public final Vector4f getZXYZ() {
		return new Vector4f(z, x, y, z);
	}

	public final Vector4f getZXYW() {
		return new Vector4f(z, x, y, w);
	}

	public final Vector4f getZXZX() {
		return new Vector4f(z, x, z, x);
	}

	public final Vector4f getZXZY() {
		return new Vector4f(z, x, z, y);
	}

	public final Vector4f getZXZZ() {
		return new Vector4f(z, x, z, z);
	}

	public final Vector4f getZXZW() {
		return new Vector4f(z, x, z, w);
	}

	public final Vector4f getZXWX() {
		return new Vector4f(z, x, w, x);
	}

	public final Vector4f getZXWY() {
		return new Vector4f(z, x, w, y);
	}

	public final Vector4f getZXWZ() {
		return new Vector4f(z, x, w, z);
	}

	public final Vector4f getZXWW() {
		return new Vector4f(z, x, w, w);
	}

	public final Vector4f getZYXX() {
		return new Vector4f(z, y, x, x);
	}

	public final Vector4f getZYXY() {
		return new Vector4f(z, y, x, y);
	}

	public final Vector4f getZYXZ() {
		return new Vector4f(z, y, x, z);
	}

	public final Vector4f getZYXW() {
		return new Vector4f(z, y, x, w);
	}

	public final Vector4f getZYYX() {
		return new Vector4f(z, y, y, x);
	}

	public final Vector4f getZYYY() {
		return new Vector4f(z, y, y, y);
	}

	public final Vector4f getZYYZ() {
		return new Vector4f(z, y, y, z);
	}

	public final Vector4f getZYYW() {
		return new Vector4f(z, y, y, w);
	}

	public final Vector4f getZYZX() {
		return new Vector4f(z, y, z, x);
	}

	public final Vector4f getZYZY() {
		return new Vector4f(z, y, z, y);
	}

	public final Vector4f getZYZZ() {
		return new Vector4f(z, y, z, z);
	}

	public final Vector4f getZYZW() {
		return new Vector4f(z, y, z, w);
	}

	public final Vector4f getZYWX() {
		return new Vector4f(z, y, w, x);
	}

	public final Vector4f getZYWY() {
		return new Vector4f(z, y, w, y);
	}

	public final Vector4f getZYWZ() {
		return new Vector4f(z, y, w, z);
	}

	public final Vector4f getZYWW() {
		return new Vector4f(z, y, w, w);
	}

	public final Vector4f getZZXX() {
		return new Vector4f(z, z, x, x);
	}

	public final Vector4f getZZXY() {
		return new Vector4f(z, z, x, y);
	}

	public final Vector4f getZZXZ() {
		return new Vector4f(z, z, x, z);
	}

	public final Vector4f getZZXW() {
		return new Vector4f(z, z, x, w);
	}

	public final Vector4f getZZYX() {
		return new Vector4f(z, z, y, x);
	}

	public final Vector4f getZZYY() {
		return new Vector4f(z, z, y, y);
	}

	public final Vector4f getZZYZ() {
		return new Vector4f(z, z, y, z);
	}

	public final Vector4f getZZYW() {
		return new Vector4f(z, z, y, w);
	}

	public final Vector4f getZZZX() {
		return new Vector4f(z, z, z, x);
	}

	public final Vector4f getZZZY() {
		return new Vector4f(z, z, z, y);
	}

	public final Vector4f getZZZZ() {
		return new Vector4f(z, z, z, z);
	}

	public final Vector4f getZZZW() {
		return new Vector4f(z, z, z, w);
	}

	public final Vector4f getZZWX() {
		return new Vector4f(z, z, w, x);
	}

	public final Vector4f getZZWY() {
		return new Vector4f(z, z, w, y);
	}

	public final Vector4f getZZWZ() {
		return new Vector4f(z, z, w, z);
	}

	public final Vector4f getZZWW() {
		return new Vector4f(z, z, w, w);
	}

	public final Vector4f getZWXX() {
		return new Vector4f(z, w, x, x);
	}

	public final Vector4f getZWXY() {
		return new Vector4f(z, w, x, y);
	}

	public final Vector4f getZWXZ() {
		return new Vector4f(z, w, x, z);
	}

	public final Vector4f getZWXW() {
		return new Vector4f(z, w, x, w);
	}

	public final Vector4f getZWYX() {
		return new Vector4f(z, w, y, x);
	}

	public final Vector4f getZWYY() {
		return new Vector4f(z, w, y, y);
	}

	public final Vector4f getZWYZ() {
		return new Vector4f(z, w, y, z);
	}

	public final Vector4f getZWYW() {
		return new Vector4f(z, w, y, w);
	}

	public final Vector4f getZWZX() {
		return new Vector4f(z, w, z, x);
	}

	public final Vector4f getZWZY() {
		return new Vector4f(z, w, z, y);
	}

	public final Vector4f getZWZZ() {
		return new Vector4f(z, w, z, z);
	}

	public final Vector4f getZWZW() {
		return new Vector4f(z, w, z, w);
	}

	public final Vector4f getZWWX() {
		return new Vector4f(z, w, w, x);
	}

	public final Vector4f getZWWY() {
		return new Vector4f(z, w, w, y);
	}

	public final Vector4f getZWWZ() {
		return new Vector4f(z, w, w, z);
	}

	public final Vector4f getZWWW() {
		return new Vector4f(z, w, w, w);
	}

	public final Vector4f getWXXX() {
		return new Vector4f(w, x, x, x);
	}

	public final Vector4f getWXXY() {
		return new Vector4f(w, x, x, y);
	}

	public final Vector4f getWXXZ() {
		return new Vector4f(w, x, x, z);
	}

	public final Vector4f getWXXW() {
		return new Vector4f(w, x, x, w);
	}

	public final Vector4f getWXYX() {
		return new Vector4f(w, x, y, x);
	}

	public final Vector4f getWXYY() {
		return new Vector4f(w, x, y, y);
	}

	public final Vector4f getWXYZ() {
		return new Vector4f(w, x, y, z);
	}

	public final Vector4f getWXYW() {
		return new Vector4f(w, x, y, w);
	}

	public final Vector4f getWXZX() {
		return new Vector4f(w, x, z, x);
	}

	public final Vector4f getWXZY() {
		return new Vector4f(w, x, z, y);
	}

	public final Vector4f getWXZZ() {
		return new Vector4f(w, x, z, z);
	}

	public final Vector4f getWXZW() {
		return new Vector4f(w, x, z, w);
	}

	public final Vector4f getWXWX() {
		return new Vector4f(w, x, w, x);
	}

	public final Vector4f getWXWY() {
		return new Vector4f(w, x, w, y);
	}

	public final Vector4f getWXWZ() {
		return new Vector4f(w, x, w, z);
	}

	public final Vector4f getWXWW() {
		return new Vector4f(w, x, w, w);
	}

	public final Vector4f getWYXX() {
		return new Vector4f(w, y, x, x);
	}

	public final Vector4f getWYXY() {
		return new Vector4f(w, y, x, y);
	}

	public final Vector4f getWYXZ() {
		return new Vector4f(w, y, x, z);
	}

	public final Vector4f getWYXW() {
		return new Vector4f(w, y, x, w);
	}

	public final Vector4f getWYYX() {
		return new Vector4f(w, y, y, x);
	}

	public final Vector4f getWYYY() {
		return new Vector4f(w, y, y, y);
	}

	public final Vector4f getWYYZ() {
		return new Vector4f(w, y, y, z);
	}

	public final Vector4f getWYYW() {
		return new Vector4f(w, y, y, w);
	}

	public final Vector4f getWYZX() {
		return new Vector4f(w, y, z, x);
	}

	public final Vector4f getWYZY() {
		return new Vector4f(w, y, z, y);
	}

	public final Vector4f getWYZZ() {
		return new Vector4f(w, y, z, z);
	}

	public final Vector4f getWYZW() {
		return new Vector4f(w, y, z, w);
	}

	public final Vector4f getWYWX() {
		return new Vector4f(w, y, w, x);
	}

	public final Vector4f getWYWY() {
		return new Vector4f(w, y, w, y);
	}

	public final Vector4f getWYWZ() {
		return new Vector4f(w, y, w, z);
	}

	public final Vector4f getWYWW() {
		return new Vector4f(w, y, w, w);
	}

	public final Vector4f getWZXX() {
		return new Vector4f(w, z, x, x);
	}

	public final Vector4f getWZXY() {
		return new Vector4f(w, z, x, y);
	}

	public final Vector4f getWZXZ() {
		return new Vector4f(w, z, x, z);
	}

	public final Vector4f getWZXW() {
		return new Vector4f(w, z, x, w);
	}

	public final Vector4f getWZYX() {
		return new Vector4f(w, z, y, x);
	}

	public final Vector4f getWZYY() {
		return new Vector4f(w, z, y, y);
	}

	public final Vector4f getWZYZ() {
		return new Vector4f(w, z, y, z);
	}

	public final Vector4f getWZYW() {
		return new Vector4f(w, z, y, w);
	}

	public final Vector4f getWZZX() {
		return new Vector4f(w, z, z, x);
	}

	public final Vector4f getWZZY() {
		return new Vector4f(w, z, z, y);
	}

	public final Vector4f getWZZZ() {
		return new Vector4f(w, z, z, z);
	}

	public final Vector4f getWZZW() {
		return new Vector4f(w, z, z, w);
	}

	public final Vector4f getWZWX() {
		return new Vector4f(w, z, w, x);
	}

	public final Vector4f getWZWY() {
		return new Vector4f(w, z, w, y);
	}

	public final Vector4f getWZWZ() {
		return new Vector4f(w, z, w, z);
	}

	public final Vector4f getWZWW() {
		return new Vector4f(w, z, w, w);
	}

	public final Vector4f getWWXX() {
		return new Vector4f(w, w, x, x);
	}

	public final Vector4f getWWXY() {
		return new Vector4f(w, w, x, y);
	}

	public final Vector4f getWWXZ() {
		return new Vector4f(w, w, x, z);
	}

	public final Vector4f getWWXW() {
		return new Vector4f(w, w, x, w);
	}

	public final Vector4f getWWYX() {
		return new Vector4f(w, w, y, x);
	}

	public final Vector4f getWWYY() {
		return new Vector4f(w, w, y, y);
	}

	public final Vector4f getWWYZ() {
		return new Vector4f(w, w, y, z);
	}

	public final Vector4f getWWYW() {
		return new Vector4f(w, w, y, w);
	}

	public final Vector4f getWWZX() {
		return new Vector4f(w, w, z, x);
	}

	public final Vector4f getWWZY() {
		return new Vector4f(w, w, z, y);
	}

	public final Vector4f getWWZZ() {
		return new Vector4f(w, w, z, z);
	}

	public final Vector4f getWWZW() {
		return new Vector4f(w, w, z, w);
	}

	public final Vector4f getWWWX() {
		return new Vector4f(w, w, w, x);
	}

	public final Vector4f getWWWY() {
		return new Vector4f(w, w, w, y);
	}

	public final Vector4f getWWWZ() {
		return new Vector4f(w, w, w, z);
	}

	public final Vector4f getWWWW() {
		return new Vector4f(w, w, w, w);
	}

	public Vector4f set(Vector4f vector) {
		return this.set(vector.getX(), vector.getY(), vector.getZ(), vector.getW());
	}

	public Vector4f set(float x, float y, float z, float w) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;

		return this;
	}

	public Vector4f set(float[] values) {
		this.x = values[COMPONENT_INDEX_X];
		this.y = values[COMPONENT_INDEX_Y];
		this.z = values[COMPONENT_INDEX_Z];
		this.w = values[COMPONENT_INDEX_W];

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
