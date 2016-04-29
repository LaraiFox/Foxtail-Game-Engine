package laraifox.foxtail.rendering;

import laraifox.foxtail.core.math.Vector2f;
import laraifox.foxtail.core.math.Vector3f;

public class Vertex {
	public static final int POSITION_OFFSET = 0 * Float.BYTES;
	public static final int TEXCOORD_OFFSET = 3 * Float.BYTES;
	public static final int NORMAL_OFFSET = 5 * Float.BYTES;
	public static final int TANGENT_OFFSET = 8 * Float.BYTES;
	public static final int BINORMAL_OFFSET = 11 * Float.BYTES;
	/***
	 * The number of components in the vertex.
	 */
	public static final int COMPONENT_COUNT = 14;
	/***
	 * The total size in bytes of all components in the vertex.
	 */
	public static final int BYTE_COUNT = COMPONENT_COUNT * Float.BYTES;

	private Vector3f position;
	private Vector2f texCoord;
	private Vector3f normal;
	private Vector3f tangent;
	private Vector3f binormal;

	public Vertex(Vector3f position, Vector2f textureCoord, Vector3f normal, Vector3f tangent, Vector3f binormal) {
		this.position = position;
		this.texCoord = textureCoord;
		this.normal = normal;
		this.tangent = tangent;
		this.binormal = binormal;
	}

	public Vector3f getPosition() {
		return position;
	}

	public void setPosition(Vector3f position) {
		this.position = position;
	}

	public Vector2f getTexCoord() {
		return texCoord;
	}

	public void setTexCoord(Vector2f texCoord) {
		this.texCoord = texCoord;
	}

	public Vector3f getNormal() {
		return normal;
	}

	public void setNormal(Vector3f normal) {
		this.normal = normal;
	}

	public Vector3f getTangent() {
		return tangent;
	}

	public void setTangent(Vector3f tangent) {
		this.tangent = tangent;
	}

	public Vector3f getBinormal() {
		return binormal;
	}

	public void setBinormal(Vector3f binormal) {
		this.binormal = binormal;
	}
}
