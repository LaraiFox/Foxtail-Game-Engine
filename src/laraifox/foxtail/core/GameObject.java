package laraifox.foxtail.core;

import java.util.ArrayList;
import java.util.List;

public class GameObject {
	protected Transform3D currentTransform, previousTransform;

	protected GameObject parent;
	protected List<GameObject> children;
	protected List<GameComponent> components;

	public GameObject(Transform3D transform) {
		this.currentTransform = transform;
		this.previousTransform = currentTransform;

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

	public void updateTransform(Transform3D deltaTransform) {
		this.previousTransform = currentTransform;
		this.currentTransform = Transform3D.transform(currentTransform, deltaTransform);
	}

	public Transform3D getInterpolatedTransform(float value) {
		return Transform3D.interpolate(this.getPreviousTransform(), this.getCurrentTransform(), value);

		//		Transform3D result = Transform3D.interpolate(previousTransform, currentTransform, value);
		//		if (parent != null) {
		//			result.transform(parent.getInterpolatedTransform(value));
		//		}
		//
		//		return result;
	}

	public Transform3D getCurrentTransform() {
		if (parent == null) {
			return currentTransform;
		}
		
		return Transform3D.transform(currentTransform, parent.getCurrentTransform());
	}

	public Transform3D getPreviousTransform() {
		if (parent == null) {
			return previousTransform;
		}
		
		return Transform3D.transform(previousTransform, parent.getPreviousTransform());
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
