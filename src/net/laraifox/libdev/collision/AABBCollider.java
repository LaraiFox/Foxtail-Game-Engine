package net.laraifox.libdev.collision;

import net.laraifox.libdev.core.GameObject;
import net.laraifox.libdev.core.Transform3D;
import net.laraifox.libdev.interfaces.ICollidable;
import net.laraifox.libdev.math.Vector3f;

public class AABBCollider extends GameObject implements ICollidable {
	public AABBCollider(float x, float y, float z, float width, float height, float length, GameObject parent) {
		super(new Transform3D(new Vector3f(x, y, z), new Vector3f(width, height, length)), parent);
	}

	public AABBCollider(Transform3D transform, GameObject parent) {
		super(transform, parent);
	}

	public CollisionData collides(AABBCollider otherCollider) {
		// TODO Auto-generated method stub
		return null;
	}

	public CollisionData collides(AABBCollider otherCollider, Vector3f thisVelocity, Vector3f otherVelocity) {
		// TODO Auto-generated method stub
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
