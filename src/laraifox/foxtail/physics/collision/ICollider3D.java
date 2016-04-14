package laraifox.foxtail.physics.collision;

import laraifox.foxtail.core.math.Vector3f;

public interface ICollider3D {
	public Collision collides(ICollider3D other);

	public Collision collides(ICollider3D other, Vector3f thisVelocity, Vector3f otherVelocity);

	public Collision collides(AABBCollider3D other);

	public Collision collides(AABBCollider3D other, Vector3f thisVelocity, Vector3f otherVelocity);

	public Collision collides(CapsuleCollider3D other);

	public Collision collides(CapsuleCollider3D other, Vector3f thisVelocity, Vector3f otherVelocity);

	public Collision collides(ConvexMeshCollider3D other);

	public Collision collides(ConvexMeshCollider3D other, Vector3f thisVelocity, Vector3f otherVelocity);

	public Collision collides(LineCollider3D other);

	public Collision collides(LineCollider3D other, Vector3f thisVelocity, Vector3f otherVelocity);

	public Collision collides(OBBCollider3D other);

	public Collision collides(OBBCollider3D other, Vector3f thisVelocity, Vector3f otherVelocity);

	public Collision collides(PointCollider3D other);

	public Collision collides(PointCollider3D other, Vector3f thisVelocity, Vector3f otherVelocity);

	public Collision collides(PlaneCollider3D other);

	public Collision collides(PlaneCollider3D other, Vector3f thisVelocity, Vector3f otherVelocity);

	public Collision collides(SphereCollider3D other);

	public Collision collides(SphereCollider3D other, Vector3f thisVelocity, Vector3f otherVelocity);
}
