package laraifox.foxtail.rendering.models;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

import laraifox.foxtail.core.math.Vector2f;
import laraifox.foxtail.core.math.Vector3f;

public class OBJModel {
	private class OBJIndex {
		public int vertexIndex;
		public int texCoordIndex;
		public int normalIndex;
	}

	private ArrayList<Vector3f> positions;
	private ArrayList<Vector2f> texCoords;
	private ArrayList<Vector3f> normals;
	private ArrayList<OBJIndex> indices;

	private boolean hasTexCoords;
	private boolean hasNormals;

	public OBJModel(String filename) {
		this.positions = new ArrayList<Vector3f>();
		this.texCoords = new ArrayList<Vector2f>();
		this.normals = new ArrayList<Vector3f>();
		this.indices = new ArrayList<OBJIndex>();

		this.hasTexCoords = false;
		this.hasNormals = false;

		BufferedReader meshReader = null;

		try {
			meshReader = new BufferedReader(new FileReader(filename));
			String line = new String();

			while ((line = meshReader.readLine()) != null) {
				String[] tokens = line.split(" ");
				tokens = removeEmptyStrings(tokens);

				if (tokens.length == 0 || tokens[0].equals("#")) {
					continue;
				} else if (tokens[0].equals("v")) {
					positions.add(new Vector3f(Float.valueOf(tokens[1]), Float.valueOf(tokens[2]), Float.valueOf(tokens[3])));
				} else if (tokens[0].equals("vt")) {
					texCoords.add(new Vector2f(Float.valueOf(tokens[1]), Float.valueOf(tokens[2])));
				} else if (tokens[0].equals("vn")) {
					normals.add(new Vector3f(Float.valueOf(tokens[1]), Float.valueOf(tokens[2]), Float.valueOf(tokens[3])));
				} else if (tokens[0].equals("f")) {
					for (int i = 0; i < tokens.length - 3; i++) {
						indices.add(parseOBJIndex(tokens[1]));
						indices.add(parseOBJIndex(tokens[2 + i]));
						indices.add(parseOBJIndex(tokens[3 + i]));
					}
				}
			}

			meshReader.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	private static String[] removeEmptyStrings(String[] data) {
		ArrayList<String> nonEmptyStrings = new ArrayList<String>();

		for (int i = 0; i < data.length; i++) {
			if (!data[i].equals("") && data[i] != null) {
				nonEmptyStrings.add(data[i]);
			}
		}

		String[] result = new String[nonEmptyStrings.size()];
		nonEmptyStrings.toArray(result);

		return result;
	}

	public IndexedModel toIndexedModel() {
		IndexedModel result = new IndexedModel();

		for (int i = 0; i < indices.size(); i++) {
			OBJIndex currentIndex = indices.get(i);

			Vector3f currentPosition = positions.get(currentIndex.vertexIndex);
			Vector2f currentTexCoord = new Vector2f();
			Vector3f currentNormal = new Vector3f();

			if (hasTexCoords)
				currentTexCoord = texCoords.get(currentIndex.texCoordIndex);
			if (hasNormals)
				currentNormal = normals.get(currentIndex.normalIndex);

			result.getPositions().add(currentPosition);
			result.getTexCoords().add(currentTexCoord);
			result.getNormals().add(currentNormal);
			result.getIndices().add(i);
		}

		return result;
	}

	private OBJIndex parseOBJIndex(String token) {
		String[] values = token.split("/");

		OBJIndex result = new OBJIndex();
		result.vertexIndex = Integer.parseInt(values[0]) - 1;

		if (values.length > 1) {
			// TODO: Add support for multiple meshes inside a single model;
			if (!values[1].isEmpty() && values[1] != null) {
				hasTexCoords = true;
				result.texCoordIndex = Integer.parseInt(values[1]) - 1;
			} else {
				result.texCoordIndex = 0;
			}

			if (values.length > 2) {
				if (!values[2].isEmpty() && values[2] != null) {
					hasNormals = true;
					result.normalIndex = Integer.parseInt(values[2]) - 1;
				} else {
					result.normalIndex = 0;
				}
			}
		}

		return result;
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

	public ArrayList<OBJIndex> getIndices() {
		return indices;
	}
}
