package laraifox.foxtail.physics;

import laraifox.foxtail.core.GameComponent;

public class CollisionComponent extends GameComponent {

	@Override
	protected void onComponentAdded() {
		PhysicsEngine.addCollisionComponent(this);
	}

}
