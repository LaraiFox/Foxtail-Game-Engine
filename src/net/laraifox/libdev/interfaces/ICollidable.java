package net.laraifox.libdev.interfaces;

import net.laraifox.libdev.collision.AABBCollider;
import net.laraifox.libdev.collision.CapsuleCollider;
import net.laraifox.libdev.collision.CollisionData;
import net.laraifox.libdev.collision.ConvexMeshCollider;
import net.laraifox.libdev.collision.OBBCollider;
import net.laraifox.libdev.collision.PlainCollider;
import net.laraifox.libdev.collision.SphereCollider;
import net.laraifox.libdev.math.Vector3f;

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
