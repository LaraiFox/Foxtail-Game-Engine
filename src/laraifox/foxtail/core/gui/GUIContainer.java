package laraifox.foxtail.core.gui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GUIContainer extends GUIComponent {
	private List<GUIComponent> subcomponents;
	private Map<String, Integer> subcomponentMap;

	public GUIContainer() {
		this.subcomponents = new ArrayList<GUIComponent>();
		this.subcomponentMap = new HashMap<String, Integer>();
	}

	public boolean addComponent(GUIComponent component) {
		if (!this.subcomponents.contains(component)) {
			this.subcomponentMap.put(component.getName(), this.subcomponents.size());
			this.subcomponents.add(component);

			component.setParent(this);

			return true;
		}

		return false;
	}

	public boolean removeComponent(GUIComponent component) {
		if (this.subcomponents.contains(component)) {
			this.subcomponentMap.remove(component.getName());
			this.subcomponents.remove(component);

			component.setParent(null);

			return true;
		}

		return false;
	}

	public boolean removeComponent(String name) {
		if (this.subcomponentMap.containsKey(name)) {
			int index = this.subcomponentMap.get(name);
			GUIComponent component = this.subcomponents.get(index);

			this.subcomponentMap.remove(component.getName());
			this.subcomponents.remove(component);

			component.setParent(null);

			return true;
		}

		return false;
	}

	public boolean removeComponent(int index) {
		if (index >= 0 && index < this.subcomponents.size()) {
			GUIComponent component = this.subcomponents.get(index);

			this.subcomponentMap.remove(component.getName());
			this.subcomponents.remove(component);

			component.setParent(null);

			return true;
		}

		return false;
	}

}
