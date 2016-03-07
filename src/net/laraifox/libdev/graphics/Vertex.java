package net.laraifox.libdev.graphics;

import net.laraifox.libdev.math.Vector2f;
import net.laraifox.libdev.math.Vector3f;

public class Vertex {
	/***
	 * The number of components in the vertex.
	 */
	public static final int COMPONENT_COUNT = 11;
	/***
	 * The total size in bytes of all components in the vertex.
	 */
	public static final int BYTE_COUNT = COMPONENT_COUNT * 4;

	private Vector3f position;
	private Vector2f texCoord;
	private Vector3f normal;
	private Vector3f tangent;

	public Vertex(Vector2f position, Vector2f textureCoord, Vector2f normal, Vector2f tangent) {
		this(new Vector3f(position), textureCoord, new Vector3f(normal), new Vector3f(tangent));
	}

	public Vertex(Vector3f position, Vector2f textureCoord, Vector3f normal, Vector3f tangent) {
		this.position = position;
		this.texCoord = textureCoord;
		this.normal = normal;
		this.tangent = tangent;
	}

	public Vector3f getPosition() {
		return position;
	}

	public Vector2f getPosition2D() {
		return position.getXY();
	}

	public Vector2f getTexCoord() {
		return texCoord;
	}

	public Vector3f getNormal() {
		return normal;
	}

	public Vector2f getNormal2D() {
		return normal.getXY();
	}

	public Vector3f getTangent() {
		return tangent;
	}

	public Vector2f getTangent2D() {
		return tangent.getXY();
	}

	public void setPosition(Vector3f position) {
		this.position = position;
	}

	public void setPosition(Vector2f position) {
		this.position = new Vector3f(position);
	}

	public void setTexCoord(Vector2f texCoord) {
		this.texCoord = texCoord;
	}

	public void setNormal(Vector3f normal) {
		this.normal = normal;
	}

	public void setNormal(Vector2f normal) {
		this.normal = new Vector3f(normal);
	}

	public void setTangent(Vector3f tangent) {
		this.tangent = tangent;
	}

	public void setTangent(Vector2f tangent) {
		this.tangent = new Vector3f(tangent);
	}
}
