package laraifox.foxtail.rendering;

import laraifox.foxtail.core.GameComponent;
import laraifox.foxtail.core.GameObject;
import laraifox.foxtail.rendering.models.Mesh;

public class RenderComponent extends GameComponent {
	protected Material material;
	protected Mesh mesh;

	protected boolean isStatic;

	public RenderComponent(Material material, Mesh mesh, boolean isStatic) {
		this.material = material;
		this.mesh = mesh;

		this.isStatic = isStatic;
	}

	@Override
	public void onComponentAdded(GameObject owner) {
		super.onComponentAdded(owner);
		RenderingEngine.addRenderComponent(this);
	}

	public void render(Shader shader, float delta) {

	}

	public boolean isStatic() {
		return isStatic;
	}

	public int getMeshID() {
		return mesh.getID();
	}

	public Material getMaterial() {
		// AUTO: Auto-generated method stub
		return null;
	}

	public Mesh getMesh() {
		// AUTO: Auto-generated method stub
		return null;
	}
}
