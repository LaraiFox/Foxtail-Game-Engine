package laraifox.foxtail.core;

public abstract class GameComponent {
	protected GameObject owner;

	public void onComponentAdded(GameObject owner) {
		this.owner = owner;
	}

	public GameObject getGameObject() {
		return owner;
	}
}
