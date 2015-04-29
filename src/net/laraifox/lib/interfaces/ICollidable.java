package net.laraifox.lib.interfaces;

import net.laraifox.lib.collision.AABBCollider;
import net.laraifox.lib.collision.CapsuleCollider;
import net.laraifox.lib.collision.CollisionData;
import net.laraifox.lib.collision.ConvexMeshCollider;
import net.laraifox.lib.collision.OBBCollider;
import net.laraifox.lib.collision.PlainCollider;
import net.laraifox.lib.collision.SphereCollider;
import net.laraifox.lib.math.Vector3f;

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
