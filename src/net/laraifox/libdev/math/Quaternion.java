package net.laraifox.libdev.math;

// TODO: http://www.itu.dk/people/erikdam/DOWNLOAD/98-5.pdf

public class Quaternion {
	private float w, x, y, z;

	public Quaternion() {
		this.w = 1.0f;
		this.x = 0.0f;
		this.y = 0.0f;
		this.z = 0.0f;
	}

	public Quaternion(float w, float x, float y, float z) {
		this.w = w;
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Quaternion(Quaternion quaternion) {
		this.w = quaternion.getW();
		this.x = quaternion.getX();
		this.y = quaternion.getY();
		this.z = quaternion.getZ();
	}

	public static Quaternion AxisAngle(Vector3f axis, float angle) {
		float halfAngle = (float) Math.toRadians(angle / 2.0f);
		float sine = (float) Math.sin(halfAngle);
		float cosine = (float) Math.cos(halfAngle);

		return new Quaternion(cosine, axis.getX() * sine, axis.getY() * sine, axis.getZ() * sine);
	}

	public static Quaternion Euler(float x, float y, float z) {
		return Quaternion.Identity();
	}

	public static Quaternion FromToRotation(Vector3f a, Vector3f b) {
		return Quaternion.Identity();
	}

	public static Quaternion Identity() {
		return new Quaternion(1.0f, 0.0f, 0.0f, 0.0f);
	}

	public static Quaternion LookAt(Vector3f forward, Vector3f up) {
		return Quaternion.Identity();
	}

	public static Quaternion add(Quaternion left, Quaternion right) {
		return new Quaternion(left).add(right);
	}

	public static Quaternion subtract(Quaternion left, Quaternion right) {
		return new Quaternion(left).subtract(right);
	}

	public static Quaternion multiply(Quaternion left, Quaternion right) {
		return new Quaternion(left).multiply(right);
	}

	public static Quaternion multiply(Quaternion left, Vector3f right) {
		return new Quaternion(left).multiply(right);
	}

	public static Quaternion divide(Quaternion left, Quaternion right) {
		return new Quaternion(left).divide(right);
	}

	public static Quaternion divide(Quaternion left, Vector3f right) {
		return new Quaternion(left).divide(right);
	}

	public static Quaternion conjugate(Quaternion quaternion) {
		return new Quaternion(quaternion).conjugate();
	}

	public static Quaternion inverse(Quaternion quaternion) {
		return new Quaternion(quaternion).inverse();
	}

	public static Quaternion lerp(Quaternion quaternion, Quaternion destination, float value) {
		return new Quaternion(quaternion).lerp(destination, value);
	}

	public static Quaternion nlerp(Quaternion quaternion, Quaternion destination, float value) {
		return new Quaternion(quaternion).nlerp(destination, value);
	}

	public static Quaternion normalize(Quaternion quaternion) {
		return new Quaternion(quaternion).normalize();
	}

	public static Quaternion rotateTowards(Quaternion quaternion, Quaternion destination, float value) {
		return new Quaternion(quaternion).rotateTowards(destination, value);
	}

	public static Quaternion scale(Quaternion quaternion, float scalar) {
		return new Quaternion(quaternion).scale(scalar);
	}

	public static Quaternion slerp(Quaternion quaternion, Quaternion destination, float value) {
		return new Quaternion(quaternion).slerp(destination, value);
	}

	public static float angle(Quaternion a, Quaternion b) {
		return new Quaternion(a).angle(b);
	}

	public static float dot(Quaternion left, Quaternion right) {
		return new Quaternion(left).dot(right);
	}

	public static float length(Quaternion quaternion) {
		return new Quaternion(quaternion).length();
	}

	public static float lengthSq(Quaternion quaternion) {
		return new Quaternion(quaternion).lengthSq();
	}

	public Quaternion add(Quaternion quaternion) {
		return this;
	}

	public Quaternion subtract(Quaternion quaternion) {
		return this;
	}

	public Quaternion multiply(Quaternion quaternion) {
		// TODO: Make this method modify the current object!

		float w_ = this.w * quaternion.getW() - this.x * quaternion.getX() - this.y * quaternion.getY() - this.z * quaternion.getZ();
		float x_ = this.x * quaternion.getW() + this.w * quaternion.getX() + this.y * quaternion.getZ() - this.z * quaternion.getY();
		float y_ = this.y * quaternion.getW() + this.w * quaternion.getY() + this.z * quaternion.getX() - this.x * quaternion.getZ();
		float z_ = this.z * quaternion.getW() + this.w * quaternion.getZ() + this.x * quaternion.getY() - this.y * quaternion.getX();

		return new Quaternion(x_, y_, z_, w_);
	}

	public Quaternion multiply(Vector3f vector) {
		// TODO: Make this method modify the current object!

		float w_ = -this.x * vector.getX() - this.y * vector.getY() - this.z * vector.getZ();
		float x_ = this.w * vector.getX() + this.y * vector.getZ() - this.z * vector.getY();
		float y_ = this.w * vector.getY() + this.z * vector.getX() - this.x * vector.getZ();
		float z_ = this.w * vector.getZ() + this.x * vector.getY() - this.y * vector.getX();

		return new Quaternion(x_, y_, z_, w_);
	}

	public Quaternion divide(Quaternion quaternion) {
		// TODO: Make this method modify the current object!

		return this;
	}

	public Quaternion divide(Vector3f vector) {
		// TODO: Make this method modify the current object!

		return this;
	}

	public Quaternion conjugate() {
		this.x = -this.x;
		this.y = -this.y;
		this.z = -this.z;

		return this;
	}

	public Quaternion inverse() {
		// TODO: Add functionality to this method!

		return this;
	}

	public Quaternion lerp(Quaternion quaternion, float value) {
		return this.add(Quaternion.subtract(quaternion, this).scale(value));
	}

	public Quaternion nlerp(Quaternion quaternion, float value) {
		return this.lerp(quaternion, value).normalize();
	}

	public Quaternion normalize() {
		float length = length();

		this.x /= length;
		this.y /= length;
		this.z /= length;
		this.w /= length;

		return this;
	}

	public Quaternion rotateTowards(Quaternion destination, float value) {
		// TODO: Add functionality to this method!

		return this;
	}

	public Quaternion scale(float scalar) {
		return this;
	}

	public Quaternion slerp(Quaternion destination, float value) {
		// TODO: Add functionality to this method!

		return this;
	}

	public float angle(Quaternion quaternion) {
		// TODO: Add functionality to this method!

		return 0.0f;
	}

	public float dot(Quaternion quaternion) {
		return this.w * quaternion.w + this.x * quaternion.x + this.y * quaternion.y + this.z * quaternion.z;
	}

	public float length() {
		return (float) Math.sqrt(this.lengthSq());
	}

	public float lengthSq() {
		return w * w + x * x + y * y + z * z;
	}

	@Override
	public boolean equals(Object other) {
		if (other instanceof Quaternion) {
			return this.isEqual((Quaternion) other);
		}

		return false;
	}

	public boolean isEqual(Quaternion other) {
		return (this.w == other.getW() && this.x == other.getX() && this.y == other.getY() && this.z == other.getZ());
	}

	public boolean isIdentity() {
		return this.isEqual(Quaternion.Identity());
	}

	public boolean isNormalized() {
		float length = this.length();

		return (length >= 0.99999f && length <= 1.00001f);
	}

	@Override
	public String toString() {
		return new String("{" + w + ", [" + x + ", " + y + ", " + z + "] }");
	}

	public Matrix4f toRotationMatrix() {
		Vector3f forward = new Vector3f(2.0f * (x * z - w * y), 2.0f * (y * z + w * x), 1.0f - 2.0f * (x * x + y * y));
		Vector3f up = new Vector3f(2.0f * (x * y + w * z), 1.0f - 2.0f * (x * x + z * z), 2.0f * (y * z - w * x));
		Vector3f right = new Vector3f(1.0f - 2.0f * (y * y + z * z), 2.0f * (x * y - w * z), 2.0f * (x * z + w * y));

		return Matrix4f.Rotation(forward, up, right);
	}

	public Vector3f getLeft() {
		return Vector3f.Left().rotate(this);
	}

	public Vector3f getRight() {
		return Vector3f.Right().rotate(this);
	}

	public Vector3f getDown() {
		return Vector3f.Down().rotate(this);
	}

	public Vector3f getUp() {
		return Vector3f.Up().rotate(this);
	}

	public Vector3f getBack() {
		return Vector3f.Back().rotate(this);
	}

	public Vector3f getForward() {
		return Vector3f.Forward().rotate(this);
	}

	public Quaternion get() {
		return new Quaternion(this);
	}

	public float getW() {
		return w;
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

	public void set(Quaternion quaternion) {
		this.w = quaternion.getW();
		this.x = quaternion.getX();
		this.y = quaternion.getY();
		this.z = quaternion.getZ();
	}

	public void setW(float w) {
		this.w = w;
	}

	public void setX(float x) {
		this.x = x;
	}

	public void setY(float y) {
		this.y = y;
	}

	public void setZ(float z) {
		this.z = z;
	}
}
