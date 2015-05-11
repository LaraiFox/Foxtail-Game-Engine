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
	 * The size in bytes of each component in the vector.
	 */
	public static final int COMPONENT_SIZE = 4;

	/***
	 * The total size in bytes of all components in the vector.
	 */
	public static final int TOTAL_SIZE = COMPONENT_COUNT * COMPONENT_SIZE;

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

	// TODO: Add all remaining mathematical methods here;

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
