package laraifox.foxtail.rendering.models;

import java.util.ArrayList;

import laraifox.foxtail.core.math.Vector2f;
import laraifox.foxtail.core.math.Vector3f;

public class IndexedModel {
	private ArrayList<Vector3f> positions;
	private ArrayList<Vector2f> texCoords;
	private ArrayList<Vector3f> normals;
	private ArrayList<Vector3f> tangents;
	private ArrayList<Integer> indices;

	public IndexedModel() {
		this.positions = new ArrayList<Vector3f>();
		this.texCoords = new ArrayList<Vector2f>();
		this.normals = new ArrayList<Vector3f>();
		this.tangents = new ArrayList<Vector3f>();
		this.indices = new ArrayList<Integer>();
	}

	public void calculateTangents() {
		if (positions.size() == 0 || texCoords.size() == 0 || normals.size() == 0)
			return;

		for (int i = 0; i < indices.size() / 3; i++) {
			Vector3f p0 = positions.get(indices.get(i * 3 + 0));
			Vector3f p1 = positions.get(indices.get(i * 3 + 1));
			Vector3f p2 = positions.get(indices.get(i * 3 + 2));

			Vector2f t0 = texCoords.get(indices.get(i * 3 + 0));
			Vector2f t1 = texCoords.get(indices.get(i * 3 + 1));
			Vector2f t2 = texCoords.get(indices.get(i * 3 + 2));

			Vector3f dp1 = Vector3f.subtract(p1, p0);
			Vector3f dp2 = Vector3f.subtract(p2, p0);

			Vector2f dt1 = Vector2f.subtract(t1, t0);
			Vector2f dt2 = Vector2f.subtract(t2, t0);

			float r = 1 / (dt1.getX() * dt2.getY() - dt1.getY() * dt2.getX());

			Vector3f tangent = Vector3f.scale(Vector3f.subtract(Vector3f.scale(dp1, dt2.getY()), Vector3f.scale(dp2, dt1.getY())), r);

			tangents.add(tangent);
			tangents.add(tangent);
			tangents.add(tangent);
		}
		
//		System.out.println("Tangents Calculated");
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

	public ArrayList<Vector3f> getTangents() {
		return tangents;
	}

	public ArrayList<Integer> getIndices() {
		return indices;
	}
}
