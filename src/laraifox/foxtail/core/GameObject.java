package laraifox.foxtail.core;

import java.util.ArrayList;
import java.util.List;

public class GameObject {
	protected Transform3D transform;

	protected GameObject parent;
	protected List<GameObject> children;
	protected List<GameComponent> components;

	public GameObject(Transform3D transform) {
		this.transform = transform;

		this.parent = null;
		this.children = new ArrayList<GameObject>();
		this.components = new ArrayList<GameComponent>();
	}

	public void addChild(GameObject child) {
		child.onObjectAdded(this);
		children.add(child);
	}

	public void addComponent(GameComponent component) {
		component.onComponentAdded(this);
		components.add(component);
	}

	protected void onObjectAdded(GameObject parent) {
		this.parent = parent;
	}

	public void update(float delta) {
		for (GameObject child : children) {
			child.update(delta);
		}
	}

	public void render() {
		for (GameObject child : children) {
			child.render();
		}
	}

	public Transform3D getTransform() {
		Transform3D result = new Transform3D(transform);
		if (parent != null) {
			result.transform(parent.getTransform());
		}

		return result;
	}

	public GameObject getParent() {
		return parent;
	}

	public List<GameObject> getChildren() {
		return children;
	}

	public List<GameComponent> getComponents() {
		return components;
	}
}
