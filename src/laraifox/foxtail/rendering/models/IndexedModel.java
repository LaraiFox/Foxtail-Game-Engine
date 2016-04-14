package laraifox.foxtail.rendering.models;

import java.util.ArrayList;

import laraifox.foxtail.core.math.Vector2f;
import laraifox.foxtail.core.math.Vector3f;

public class IndexedModel {
	private ArrayList<Vector3f> positions;
	private ArrayList<Vector2f> texCoords;
	private ArrayList<Vector3f> normals;
	private ArrayList<Integer> indices;

	public IndexedModel() {
		this.positions = new ArrayList<Vector3f>();
		this.texCoords = new ArrayList<Vector2f>();
		this.normals = new ArrayList<Vector3f>();
		this.indices = new ArrayList<Integer>();
	}

	public ArrayList<Vector3f> getPositions() {
		return positions;
	}

	public ArrayList<Vector2f> getTexCoords() {
		return texCoords;
	}

	public ArrayList<Vector3f> getNormals() {
		return normals;
	}

	public ArrayList<Integer> getIndices() {
		return indices;
	}
}
