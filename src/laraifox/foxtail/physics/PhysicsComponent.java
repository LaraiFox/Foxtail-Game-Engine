package laraifox.foxtail.physics;

import laraifox.foxtail.core.GameComponent;

public class PhysicsComponent extends GameComponent {

	public PhysicsComponent() {

	}

	@Override
	protected void onComponentAdded() {
		PhysicsEngine.addPhysicsComponent(this);
	}

	public void simulate(float delta) {

	}

}
