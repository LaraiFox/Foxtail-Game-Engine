package net.laraifox.libdev.graphics;

import net.laraifox.libdev.math.Matrix4f;
import net.laraifox.libdev.math.Quaternion;
import net.laraifox.libdev.math.Vector3f;

public class Camera {
	private Transform3D transform;

	private Matrix4f projectionMatrix;

	public Camera(Matrix4f projectionMatrix) {
		this(new Transform3D(), projectionMatrix);
	}

	public Camera(Transform3D transform, Matrix4f projectionMatrix) {
		this.transform = transform;
		this.projectionMatrix = projectionMatrix;
	}

	public void translate(Vector3f translation) {
		transform.translate(translation);
	}

	public void rotate(Vector3f axis, float theta) {
		transform.rotate(axis, theta);
	}

	public void lookAt(Vector3f location, Vector3f up) {
		Vector3f normDirection = Vector3f.subtract(transform.getTranslation(), location).normalize();

		Vector3f alpha = Vector3f.cross(normDirection, Vector3f.NegativeZ()).normalize();
		float phi = (float) Math.acos(Vector3f.dot(Vector3f.NegativeZ(), normDirection));

		Vector3f beta = Vector3f.cross(alpha, Vector3f.NegativeZ()).normalize();
		if (Vector3f.dot(beta, normDirection) < 0) {
			phi = -phi;
		}

		transform.setRotation(new Quaternion(alpha, phi));
	}

	public Matrix4f getProjectionMatrix() {
		return projectionMatrix;
	}

	public Matrix4f getViewMatrix() {
		Matrix4f translationMatrix = Matrix4f.Translation(Vector3f.negate(transform.getTranslation()));
		Matrix4f rotationMatrix = Quaternion.conjugate(transform.getRotation()).toRotationMatrix();

		return rotationMatrix.multiply(translationMatrix);
	}

	public Matrix4f getViewProjectionMatrix() {
		return getProjectionMatrix().multiply(getViewMatrix());
	}

	public Vector3f getPosition() {
		return new Vector3f(transform.getTranslation());
	}

	public void setPosition(Vector3f position) {
		this.transform.setTranslation(position);
	}

	public Quaternion getRotation() {
		return new Quaternion(transform.getRotation());
	}

	public void setRotation(Quaternion rotation) {
		this.transform.setRotation(rotation);
	}

	public Vector3f getForward() {
		return transform.getRotation().getForward();
	}

	public Vector3f getUpward() {
		return transform.getRotation().getUpward();
	}

	public Vector3f getRight() {
		return transform.getRotation().getRight();
	}
}
