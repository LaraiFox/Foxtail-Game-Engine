package laraifox.foxtail.physics;

import java.util.ArrayList;
import java.util.List;

public class PhysicsEngine {
	private static final List<PhysicsComponent> physicsComponents = new ArrayList<PhysicsComponent>();

	//	private static final Octree staticCollisionTree = new Octree();

	private static PhysicsSimulator simulator;

	public static void initialize() {
		PhysicsEngine.initialize(new DefaultPhysicsSimulator3D());
	}

	public static void initialize(PhysicsSimulator simulator) {
		simulator.initialize();
		PhysicsEngine.simulator = simulator;
	}

	public static void addPhysicsComponent(PhysicsComponent physicsComponent) {
		PhysicsEngine.physicsComponents.add(physicsComponent);
	}

	public static void simulate(float delta) {
		PhysicsEngine.simulator.simulate(physicsComponents, delta);
	}

	public static void updateStaticCollisionTree() {

	}
}
