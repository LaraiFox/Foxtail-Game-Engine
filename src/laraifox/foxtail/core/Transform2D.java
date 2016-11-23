package laraifox.foxtail.core;

import laraifox.foxtail.core.math.MathUtils;
import laraifox.foxtail.core.math.Matrix4f;
import laraifox.foxtail.core.math.Vector2f;

public class Transform2D {
	private Vector2f translation;
	private float rotation;
	private Vector2f scale;

	public Transform2D() {
		this(Vector2f.Zero(), 0.0f, Vector2f.One());
	}

	public Transform2D(Vector2f translation) {
		this(translation, 0.0f, Vector2f.One());
	}

	public Transform2D(float rotation) {
		this(Vector2f.Zero(), rotation, Vector2f.One());
	}

	public Transform2D(Vector2f translation, float rotation) {
		this(translation, rotation, Vector2f.One());
	}

	public Transform2D(Vector2f translation, Vector2f scale) {
		this(translation, 0.0f, scale);
	}

	public Transform2D(float rotation, Vector2f scale) {
		this(Vector2f.Zero(), rotation, scale);
	}

	public Transform2D(Vector2f translation, float rotation, Vector2f scale) {
		this.translation = new Vector2f(translation);
		this.rotation = rotation;
		this.scale = new Vector2f(scale);
	}

	public Transform2D(Transform2D transform) {
		this.translation = new Vector2f(transform.translation);
		this.rotation = transform.rotation;
		this.scale = new Vector2f(transform.scale);
	}

	private Transform2D interpolate(Transform2D transform, float value) {
		this.translation.lerp(transform.getTranslation(), value);
		this.rotation = MathUtils.lerp(this.rotation, transform.getRotation(), value);
		this.scale.lerp(transform.getScale(), value);

		return this;
	}

	public Transform2D scaleTransform(float value) {
		this.translation.scale(value);
		this.rotation *= value;
		this.scale.scale(value);

		return this;
	}

	public Transform2D transform(Transform2D transform) {
		translate(transform.getTranslation());
		rotate(transform.getRotation());
		scale(transform.getScale());

		return this;
	}

	public void translate(float x, float y) {
		translation.add(x, y);
	}

	public void translate(Vector2f value) {
		translation.add(value);
	}

	public void rotate(float theta) {
		rotation += theta;
	}

	public void scale(float value) {
		scale.scale(value);
	}

	public void scale(float x, float y) {
		scale.multiply(x, y);
	}

	public void scale(Vector2f value) {
		scale.multiply(value);
	}

	public Matrix4f getTransformationMatrix() {
		Matrix4f translationMatrix = this.getTranslationMatrix();
		Matrix4f rotationMatrix = this.getRotationMatrix();
		Matrix4f scaleMatrix = this.getScaleMatrix();

		return translationMatrix.multiply(rotationMatrix.multiply(scaleMatrix));
	}

	public Matrix4f getTranslationMatrix() {
		return Matrix4f.Translation(translation.getX(), translation.getY(), 0.0f);
	}

	public Matrix4f getRotationMatrix() {
		return Matrix4f.Rotation(0.0f, 0.0f, rotation);
	}

	public Matrix4f getScaleMatrix() {
		return Matrix4f.Scale(scale.getX(), scale.getY(), 1.0f);
	}

	public Vector2f getTranslation() {
		return translation;
	}

	public void setTranslation(Vector2f translation) {
		this.translation = translation;
	}

	public void setTranslation(float x, float y) {
		this.translation = new Vector2f(x, y);
	}

	public float getRotation() {
		return rotation;
	}

	public void setRotation(float rotation) {
		this.rotation = rotation;
	}

	// public Vector2f getLeft() {
	// return rotation.getLeft();
	// }
	//
	// public Vector2f getRight() {
	// return rotation.getRight();
	// }
	//
	// public Vector2f getUpward() {
	// return rotation.getUp();
	// }
	//
	// public Vector2f getDownward() {
	// return rotation.getDown();
	// }

	public Vector2f getScale() {
		return scale;
	}

	public void setScale(Vector2f scale) {
		this.scale = scale;
	}

	public void setScale(float x, float y) {
		this.scale = new Vector2f(x, y);
	}

	public void setScale(float scale) {
		this.scale = new Vector2f(scale, scale);
	}

	public static Transform2D interpolate(Transform2D left, Transform2D right, float value) {
		return new Transform2D(left).interpolate(right, value);
	}

	public static Transform2D transform(Transform2D left, Transform2D right) {
		return new Transform2D(left).transform(right);
	}
}
