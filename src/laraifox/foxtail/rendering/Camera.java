package laraifox.foxtail.rendering;

import laraifox.foxtail.core.GameComponent;
import laraifox.foxtail.core.GameObject;
import laraifox.foxtail.core.Transform3D;
import laraifox.foxtail.core.math.Matrix4f;
import laraifox.foxtail.core.math.Quaternion;
import laraifox.foxtail.core.math.Vector3f;

public class Camera extends GameObject {
	private Matrix4f projectionMatrix;

	public Camera(Matrix4f projectionMatrix) {
		this(new Transform3D(), projectionMatrix);
	}

	public Camera(Transform3D transform, Matrix4f projectionMatrix) {
		super(transform);
		
		this.currentTransform = transform;
		this.projectionMatrix = projectionMatrix;
	}

	public void translate(Vector3f translation) {
		currentTransform.translate(translation);
	}

	public void translate(Vector3f direction, float scale) {
		currentTransform.translate(Vector3f.scale(direction, scale));
	}

	public void rotate(Vector3f axis, float theta) {
		currentTransform.rotate(axis, theta);
	}

	public void lookAt(Vector3f location, Vector3f up) {
		// Vector3f normDirection = Vector3f.subtract(transform.getTranslation(), location).normalize();
		//
		// Vector3f alpha = Vector3f.cross(normDirection, Vector3f.NegativeZ()).normalize();
		// float phi = (float) Math.acos(Vector3f.dot(Vector3f.NegativeZ(), normDirection));
		//
		// Vector3f beta = Vector3f.cross(alpha, Vector3f.NegativeZ()).normalize();
		// if (Vector3f.dot(beta, normDirection) < 0) {
		// phi = -phi;
		// }
		//
		// transform.setRotation(new Quaternion(alpha, phi));
		//
		// /////////////////////////////////////////////////////////////////////////////////////////////////
		//

		Vector3f forwardVector = Vector3f.subtract(currentTransform.getTranslation(), location);
		Vector3f horizonalVector = Vector3f.normalize(new Vector3f(forwardVector.getX(), 0.0f, forwardVector.getZ()));
		float dot = Vector3f.dot(Vector3f.Back(), horizonalVector);

		float rotationAngle = (float) Math.toDegrees(Math.acos(dot));
		if (horizonalVector.getX() < 0.0f)
			rotationAngle = -rotationAngle;

		Vector3f rotationAxis = Vector3f.cross(new Vector3f(horizonalVector.getZ(), 0.0f, -horizonalVector.getX()), horizonalVector).normalize();
		Quaternion yaw = Quaternion.AxisAngle(rotationAxis, rotationAngle).normalize();

		if (forwardVector.getY() != 0.0f) {
			float pitchDot = Vector3f.dot(yaw.getBack(), Vector3f.normalize(forwardVector));

			float pitchAngle = (float) Math.toDegrees(Math.acos(pitchDot));
			if (forwardVector.getY() < 0.0f)
				pitchAngle = -pitchAngle;

			Quaternion pitch = Quaternion.AxisAngle(yaw.getRight(), pitchAngle).normalize();

			currentTransform.setRotation(pitch.multiply(yaw));
		} else {
			currentTransform.setRotation(yaw);
		}

		// Vector3f forward = Vector3f.subtract(location, transform.getTranslation()).normalize();
		// Vector3f upward = Vector3f.orthonormalize(up, forward); // Keeps up the same, make forward orthogonal to up
		// Vector3f right = Vector3f.cross(up, forward);
		//
		// float w = (float) (Math.sqrt(1.0f + right.getX() + upward.getY() + forward.getZ()) * 0.5f);
		// float w4_recip = 1.0f / (4.0f * w);
		// float x = (forward.getY() - upward.getZ()) * w4_recip;
		// float y = (right.getZ() - forward.getX()) * w4_recip;
		// float z = (upward.getX() - right.getY()) * w4_recip;
		//
		// transform.setRotation(new Quaternion(x, y, z, w).conjugate().normalize());
	}

	public Matrix4f getProjectionMatrix() {
		return projectionMatrix;
	}

	public Matrix4f getViewMatrix() {
		Matrix4f translationMatrix = Matrix4f.Translation(Vector3f.negate(currentTransform.getTranslation()));
		Matrix4f rotationMatrix = Matrix4f.Rotation(Quaternion.conjugate(currentTransform.getRotation()));

		return rotationMatrix.multiply(translationMatrix);
	}

	public Matrix4f getViewProjectionMatrix() {
		return Matrix4f.multiply(projectionMatrix, this.getViewMatrix());
	}

	public Vector3f getPosition() {
		return new Vector3f(currentTransform.getTranslation());
	}

	public void setPosition(Vector3f position) {
		this.currentTransform.setTranslation(position);
	}

	public Quaternion getRotation() {
		return new Quaternion(currentTransform.getRotation());
	}

	public void setRotation(Quaternion rotation) {
		this.currentTransform.setRotation(rotation);
	}

	public Vector3f getForward() {
		return currentTransform.getRotation().getForward();
	}

	public Vector3f getUp() {
		return currentTransform.getRotation().getUp();
	}

	public Vector3f getRight() {
		return currentTransform.getRotation().getRight();
	}
}
