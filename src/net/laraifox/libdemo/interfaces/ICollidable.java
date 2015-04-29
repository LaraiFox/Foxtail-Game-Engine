package net.laraifox.libdemo.interfaces;

import net.laraifox.libdemo.collision.AABBCollider;
import net.laraifox.libdemo.collision.CapsuleCollider;
import net.laraifox.libdemo.collision.CollisionData;
import net.laraifox.libdemo.collision.ConvexMeshCollider;
import net.laraifox.libdemo.collision.OBBCollider;
import net.laraifox.libdemo.collision.PlainCollider;
import net.laraifox.libdemo.collision.SphereCollider;
import net.laraifox.libdemo.math.Vector3f;

public interface ICollidable {
	public CollisionData collides(AABBCollider otherCollider);

	public CollisionData collides(AABBCollider otherCollider, Vector3f thisVelocity, Vector3f otherVelocity);

	public CollisionData collides(CapsuleCollider otherCollider);

	public CollisionData collides(CapsuleCollider otherCollider, Vector3f thisVelocity, Vector3f otherVelocity);

	public CollisionData collides(ConvexMeshCollider otherCollider);

	public CollisionData collides(ConvexMeshCollider otherCollider, Vector3f thisVelocity, Vector3f otherVelocity);

	public CollisionData collides(OBBCollider otherCollider);

	public CollisionData collides(OBBCollider otherCollider, Vector3f thisVelocity, Vector3f otherVelocity);

	public CollisionData collides(PlainCollider otherCollider);

	public CollisionData collides(PlainCollider otherCollider, Vector3f thisVelocity, Vector3f otherVelocity);

	public CollisionData collides(SphereCollider otherCollider);

	public CollisionData collides(SphereCollider otherCollider, Vector3f thisVelocity, Vector3f otherVelocity);
}
