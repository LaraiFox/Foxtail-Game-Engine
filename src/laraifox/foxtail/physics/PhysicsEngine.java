package laraifox.foxtail.physics;

import java.util.ArrayList;
import java.util.List;

public class PhysicsEngine {
	private static final List<CollisionComponent> collisionComponents = new ArrayList<CollisionComponent>();
	private static final List<PhysicsComponent> physicsComponents = new ArrayList<PhysicsComponent>();

	private static PhysicsSimulator simulator;

	public static void initialize(PhysicsSimulator simulator) {
		simulator.initialize();
		PhysicsEngine.simulator = simulator;
	}

	public static void addCollisionComponent(CollisionComponent component) {
		PhysicsEngine.collisionComponents.add(component);
	}

	public static void addPhysicsComponent(PhysicsComponent component) {
		PhysicsEngine.physicsComponents.add(component);
	}

}
