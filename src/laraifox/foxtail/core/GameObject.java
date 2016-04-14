package laraifox.foxtail.core;

import java.util.ArrayList;


public class GameObject {
	protected Transform3D transform;

	protected GameObject parent;
	protected ArrayList<GameObject> children;

	public GameObject(Transform3D transform, GameObject parent) {
		this.transform = transform;

		this.parent = parent;
		this.children = new ArrayList<GameObject>();
	}

	public void addComponent(GameObject child) {
		children.add(child);
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
		return transform;
	}

	public GameObject getParent() {
		return parent;
	}

	public ArrayList<GameObject> getChildren() {
		return children;
	}
}
