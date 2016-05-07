package laraifox.foxtail.testing.thinmatrixfont;

/**
 * Stores the vertex data for all the quads on which a text will be rendered.
 * 
 * @author Karl
 *
 */
public class TextMeshData {
	private int vaoID;
	private int vertexCount;

	protected TextMeshData(int vaoID, int vertexCount) {
		this.vaoID = vaoID;
		this.vertexCount = vertexCount;
	}

	public int getVaoID() {
		return vaoID;
	}

	public int getVertexCount() {
		return vertexCount;
	}
}
