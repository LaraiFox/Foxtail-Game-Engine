package laraifox.foxtail.rendering;

import java.util.ArrayList;
import java.util.List;

public class RenderBatch {
	private List<RenderComponent> staticComponents;
	private List<RenderComponent> dynamicComponents;

	public RenderBatch() {
		this.staticComponents = new ArrayList<RenderComponent>();
		this.dynamicComponents = new ArrayList<RenderComponent>();
	}

	public void addRenderComponent(RenderComponent component) {
		if (component.isStatic()) {
			staticComponents.add(component);
		} else {
			dynamicComponents.add(component);
		}
	}

	public boolean removeRenderComponent(RenderComponent component) {
		if (component.isStatic()) {
			staticComponents.remove(component);

			return staticComponents.isEmpty();
		} else {
			dynamicComponents.remove(component);

			return dynamicComponents.isEmpty();
		}
	}

	public List<RenderComponent> getStaticComponents() {
		return staticComponents;
	}

	public List<RenderComponent> getDynamicComponents() {
		return dynamicComponents;
	}
}
