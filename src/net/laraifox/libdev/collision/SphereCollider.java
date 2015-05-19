package net.laraifox.libdev.collision;

import net.laraifox.libdev.core.GameObject;
import net.laraifox.libdev.core.Transform3D;
import net.laraifox.libdev.interfaces.ICollidable;
import net.laraifox.libdev.math.Vector3f;

public class SphereCollider extends GameObject implements ICollidable {

	public SphereCollider(Transform3D transform, GameObject parent) {
		super(transform, parent);
		// TODO Auto-generated constructor stub
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

}
