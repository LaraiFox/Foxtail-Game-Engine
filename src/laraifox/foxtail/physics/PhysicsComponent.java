package laraifox.foxtail.physics;

import laraifox.foxtail.core.GameComponent;
import laraifox.foxtail.core.GameObject;
import laraifox.foxtail.core.Transform3D;
import laraifox.foxtail.core.math.Vector3f;
import laraifox.foxtail.physics.collision.ICollider3D;

public class PhysicsComponent extends GameComponent {
	private Transform3D previousTransform;
	private Transform3D currentTransform;
	private Transform3D deltaTransform;

	private ICollider3D collider;

	public PhysicsComponent(Vector3f velocity, ICollider3D collider) {
		this.deltaTransform = new Transform3D(velocity);

		this.collider = collider;
	}

	@Override
	public void onComponentAdded(GameObject owner) {
		super.onComponentAdded(owner);
		PhysicsEngine.addPhysicsComponent(this);
	}

	public void integrate(float delta) {
		//		this.previousTransform = super.owner.getPreviousTransform();
		//		this.currentTransform = super.owner.getCurrentTransform();

		Transform3D integratedTransform = new Transform3D(this.deltaTransform);

		super.owner.updateTransform(integratedTransform.scaleTransform(delta));
	}

	public ICollider3D getCollider() {
		return collider;
	}
}
