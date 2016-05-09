package laraifox.foxtail.physics;

import java.util.List;

import laraifox.foxtail.physics.collision.ICollider3D;

public class DefaultPhysicsSimulator3D extends PhysicsSimulator {

	@Override
	public void initialize() {

	}

	@Override
	public void simulate(List<PhysicsComponent> physicsComponents, float delta) {
		for (int i = 0; i < physicsComponents.size(); i++) {
			PhysicsComponent iComponent = physicsComponents.get(i);
			for (int j = i; j < physicsComponents.size(); j++) {
				PhysicsComponent jComponent = physicsComponents.get(j);
				this.integrate(iComponent, jComponent, delta);
			}
		}
	}

	private void integrate(PhysicsComponent iComponent, PhysicsComponent jComponent, float delta) {
		ICollider3D iCollider = iComponent.getCollider();
		ICollider3D jCollider = jComponent.getCollider();
		if (iCollider == null || jCollider == null) {
			iComponent.integrate(delta);
			jComponent.integrate(delta);

			return;
		}
	}

}
