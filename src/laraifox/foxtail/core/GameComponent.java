package laraifox.foxtail.core;

public abstract class GameComponent {
	protected GameObject owner;

	protected abstract void onComponentAdded();

	public void onComponentAdded(GameObject owner) {
		this.owner = owner;
		this.onComponentAdded();
	}

	public GameObject getGameObject() {
		return owner;
	}
}
