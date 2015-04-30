package net.laraifox.libdev.collision;

import net.laraifox.libdev.math.Vector3f;

public class CollisionData {
	private boolean contained;
	private boolean colliding;
	private float collisionPoint;
	private float minTranslationX;
	private float minTranslationY;
	private float minTranslationZ;
	private Vector3f minTranslation;

	public CollisionData(boolean contained, boolean colliding, float collisionPoint, float minTranslationX, float minTranslationY, float minTranslationZ, Vector3f minTranslation) {
		this.contained = contained;
		this.colliding = colliding;
		this.collisionPoint = collisionPoint;
		this.minTranslationX = minTranslationX;
		this.minTranslationY = minTranslationY;
		this.minTranslationZ = minTranslationZ;
		this.minTranslation = minTranslation;
	}

	public boolean isContained() {
		return contained;
	}

	public boolean isColliding() {
		return colliding;
	}

	public float getCollisionPoint() {
		return collisionPoint;
	}

	public float getMinTranslationX() {
		return minTranslationX;
	}

	public float getMinTranslationY() {
		return minTranslationY;
	}

	public float getMinTranslationZ() {
		return minTranslationZ;
	}

	public Vector3f getMinTranslation() {
		return minTranslation;
	}
}
