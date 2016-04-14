package net.laraifox.libdev.collision;

import laraifox.foxtail.core.GameObject;
import laraifox.foxtail.core.Transform3D;
import laraifox.foxtail.core.math.Vector3f;
import net.laraifox.libdev.interfaces.ICollidable;

public class AABBCollider extends GameObject implements ICollidable {
	public AABBCollider(float x, float y, float z, float width, float height, float length, GameObject parent) {
		super(new Transform3D(new Vector3f(x, y, z), new Vector3f(width, height, length).abs()), parent);
	}

	public CollisionData collides(AABBCollider otherCollider) {
		// TODO Auto-generated method stub

		return this.collides(otherCollider, Vector3f.Zero(), Vector3f.Zero());
	}

	public CollisionData collides(AABBCollider otherCollider, Vector3f thisVelocity, Vector3f otherVelocity) {
		Vector3f combinedVelocity = Vector3f.add(thisVelocity, otherVelocity);

		if (combinedVelocity.length() == 0.0f) {
			// The AABBColliders don't change position relative to each other so simple AABB collision checks are all that's needed

			Vector3f thisHalfSize = Vector3f.scale(this.getTransform().getScale(), 0.5f);
			Vector3f thisMin = Vector3f.subtract(this.getTransform().getTranslation(), thisHalfSize);
			Vector3f thisMax = Vector3f.add(this.getTransform().getTranslation(), thisHalfSize);

			Vector3f otherHalfSize = Vector3f.scale(otherCollider.getTransform().getScale(), 0.5f);
			Vector3f otherMin = Vector3f.subtract(otherCollider.getTransform().getTranslation(), otherHalfSize);
			Vector3f otherMax = Vector3f.add(otherCollider.getTransform().getTranslation(), otherHalfSize);

			if (thisMax.getX() < otherMin.getX() || thisMax.getY() < otherMin.getY() || thisMax.getZ() < otherMin.getZ() || //
				otherMax.getX() < thisMin.getX() || otherMax.getY() < thisMin.getY() || otherMax.getZ() < thisMin.getZ()) {
				return new CollisionData(false, 1.0f, 0.0f, 0.0f, 0.0f, Vector3f.Zero());
			} else {
				return new CollisionData(true, 0.0f, 0.0f, 0.0f, 0.0f, Vector3f.Zero());
			}
		} else {
			// The AABBColliders change position relative to each other so more complex collision checks with swept AABB are needed
		}

		return null;
	}

	public CollisionData collides(CapsuleCollider otherCollider) {
		// TODO Auto-generated method stub
		return null;
	}

	public CollisionData collides(CapsuleCollider otherCollider, Vector3f thisVelocity, Vector3f otherVelocity) {
		// TODO Auto-generated method stub
		return null;
	}

	public CollisionData collides(ConvexMeshCollider otherCollider) {
		// TODO Auto-generated method stub
		return null;
	}

	public CollisionData collides(ConvexMeshCollider otherCollider, Vector3f thisVelocity, Vector3f otherVelocity) {
		// TODO Auto-generated method stub
		return null;
	}

	public CollisionData collides(OBBCollider otherCollider) {
		// TODO Auto-generated method stub
		return null;
	}

	public CollisionData collides(OBBCollider otherCollider, Vector3f thisVelocity, Vector3f otherVelocity) {
		// TODO Auto-generated method stub
		return null;
	}

	public CollisionData collides(PlainCollider otherCollider) {
		// TODO Auto-generated method stub
		return null;
	}

	public CollisionData collides(PlainCollider otherCollider, Vector3f thisVelocity, Vector3f otherVelocity) {
		// TODO Auto-generated method stub
		return null;
	}

	public CollisionData collides(SphereCollider otherCollider) {
		// TODO Auto-generated method stub
		return null;
	}

	public CollisionData collides(SphereCollider otherCollider, Vector3f thisVelocity, Vector3f otherVelocity) {
		// TODO Auto-generated method stub
		return null;
	}

	public float getX() {
		return transform.getTranslation().getX();
	}

	public float getY() {
		return transform.getTranslation().getY();
	}

	public float getZ() {
		return transform.getTranslation().getZ();
	}

	public float getWidth() {
		return transform.getScale().getX();
	}

	public float getHeight() {
		return transform.getScale().getY();
	}

	public float getLength() {
		return transform.getScale().getZ();
	}
}
