package net.laraifox.lib.collision;

import net.laraifox.lib.core.GameObject;
import net.laraifox.lib.graphics.Transform3D;
import net.laraifox.lib.interfaces.ICollidable;
import net.laraifox.lib.math.Vector3f;

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
