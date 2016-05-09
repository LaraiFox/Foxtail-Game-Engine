package laraifox.foxtail.physics;

import java.util.List;
import java.util.Map;

public abstract class PhysicsSimulator {
	public abstract void initialize();

	public abstract void simulate(List<PhysicsComponent> physicscomponents, float delta);
}
