package laraifox.foxtail.core;

import laraifox.foxtail.core.math.Matrix4f;
import laraifox.foxtail.core.math.Quaternion;
import laraifox.foxtail.core.math.Vector3f;

public class Transform3D {
	private Vector3f translation;
	private Quaternion rotation;
	private Vector3f scale;

	public Transform3D() {
		this(Vector3f.Zero(), new Quaternion(), Vector3f.One());
	}

	public Transform3D(Vector3f translation) {
		this(translation, new Quaternion(), Vector3f.One());
	}

	public Transform3D(Quaternion rotation) {
		this(Vector3f.Zero(), rotation, Vector3f.One());
	}

	public Transform3D(Vector3f translation, Quaternion rotation) {
		this(translation, rotation, Vector3f.One());
	}

	public Transform3D(Vector3f translation, Vector3f scale) {
		this(translation, new Quaternion(), scale);
	}

	public Transform3D(Quaternion rotation, Vector3f scale) {
		this(Vector3f.Zero(), rotation, scale);
	}

	public Transform3D(Vector3f translation, Quaternion rotation, Vector3f scale) {
		this.translation = new Vector3f(translation);
		this.rotation = new Quaternion(rotation);
		this.scale = new Vector3f(scale);
	}

	public Transform3D(Transform3D transform) {
		this.translation = new Vector3f(transform.translation);
		this.rotation = new Quaternion(transform.rotation);
		this.scale = new Vector3f(transform.scale);
	}

	private Transform3D interpolate(Transform3D transform, float value) {
		this.translation.lerp(transform.getTranslation(), value);
		this.rotation.slerp(transform.getRotation(), value);
		this.scale.lerp(transform.getScale(), value);

		return this;
	}

	public Transform3D scaleTransform(float value) {
		this.translation.scale(value);
		this.rotation.scale(value);
		this.scale.scale(value);
		
		return this;
	}

	public Transform3D transform(Transform3D transform) {
		translate(transform.getTranslation());
		rotate(transform.getRotation());
		scale(transform.getScale());

		return this;
	}

	public void translate(float x, float y, float z) {
		translation.add(x, y, z);
	}

	public void translate(Vector3f value) {
		translation.add(value);
	}

	public void rotate(Vector3f axis, float theta) {
		rotation = Quaternion.AxisAngle(axis, theta).multiply(rotation).normalize();
	}

	public void rotate(Quaternion quaternion) {
		rotation = new Quaternion(quaternion).multiply(rotation).normalize();
	}

	public void scale(float value) {
		scale.scale(value);
	}

	public void scale(float x, float y, float z) {
		scale.multiply(x, y, z);
	}

	public void scale(Vector3f value) {
		scale.multiply(value);
	}

	public Matrix4f getTransformationMatrix() {
		Matrix4f translationMatrix = Matrix4f.Translation(translation);
		Matrix4f rotationMatrix = Matrix4f.Rotation(rotation);
		Matrix4f scaleMatrix = Matrix4f.Scale(scale.getX(), scale.getY(), scale.getZ());

		return translationMatrix.multiply(rotationMatrix.multiply(scaleMatrix));
	}

	public Matrix4f getTranslationMatrix() {
		return Matrix4f.Translation(translation);
	}

	public Matrix4f getRotationMatrix() {
		return Matrix4f.Rotation(rotation);
	}

	public Matrix4f getScaleMatrix() {
		return Matrix4f.Scale(scale.getX(), scale.getY(), scale.getZ());
	}

	public Vector3f getTranslation() {
		return translation;
	}

	public void setTranslation(Vector3f translation) {
		this.translation = translation;
	}

	public void setTranslation(float x, float y, float z) {
		this.translation = new Vector3f(x, y, z);
	}

	public Quaternion getRotation() {
		return rotation;
	}

	public void setRotation(Quaternion rotation) {
		this.rotation = rotation;
	}

	public Vector3f getLeft() {
		return rotation.getLeft();
	}

	public Vector3f getRight() {
		return rotation.getRight();
	}

	public Vector3f getUpward() {
		return rotation.getUp();
	}

	public Vector3f getDownward() {
		return rotation.getDown();
	}

	public Vector3f getForward() {
		return rotation.getForward();
	}

	public Vector3f getBackward() {
		return rotation.getBack();
	}

	public void setRotation(float x, float y, float z, float w) {
		this.rotation = new Quaternion(x, y, z, w);
	}

	public Vector3f getScale() {
		return scale;
	}

	public void setScale(Vector3f scale) {
		this.scale = scale;
	}

	public void setScale(float x, float y, float z) {
		this.scale = new Vector3f(x, y, z);
	}

	public void setScale(float scale) {
		this.scale = new Vector3f(scale, scale, scale);
	}

	public static Transform3D interpolate(Transform3D left, Transform3D right, float value) {
		return new Transform3D(left).interpolate(right, value);
	}

	public static Transform3D transform(Transform3D left, Transform3D right) {
		return new Transform3D(left).transform(right);
	}
}
