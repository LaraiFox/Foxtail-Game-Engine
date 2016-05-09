package laraifox.foxtail.rendering;

import java.util.Map;

public abstract class Renderer {
	public abstract void initialize();

	public abstract void render(Camera camera, Map<Integer, RenderBatch> renderBatchMap, float delta);
}
