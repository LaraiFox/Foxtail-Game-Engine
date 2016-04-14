package net.laraifox.libdev.collision;

import laraifox.foxtail.core.GameObject;
import laraifox.foxtail.core.Transform3D;
import laraifox.foxtail.core.math.Vector3f;
import net.laraifox.libdev.interfaces.ICollidable;

public class PlainCollider extends GameObject implements ICollidable {

	public PlainCollider(Transform3D transform, GameObject parent) {
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
