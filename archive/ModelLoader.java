package laraifox.foxtail.rendering.models;

import java.util.ArrayList;

import laraifox.foxtail.core.ArrayUtils;
import laraifox.foxtail.core.math.Vector3f;
import laraifox.foxtail.rendering.Vertex;

/**
 * @deprecated use {@link AssetLoader} instead.
 */
@Deprecated
public class ModelLoader {
	public static Model loadMesh(String filename) {
		String[] splitArray = filename.split("\\.");
		String extention = splitArray[splitArray.length - 1];

		Model result = null;

		if (extention.endsWith("obj")) {
			result = loadOBJMesh(filename);
		} else {
			System.err.println("ERROR: File format not supported for model @" + filename);
			new Exception().printStackTrace();
			System.exit(1);
		}

		return result;
	}

	private static Model loadOBJMesh(String filename) {
		OBJModel objModel = new OBJModel(filename);
		IndexedModel indexedModel = objModel.toIndexedModel();

		ArrayList<Vertex> vertices = new ArrayList<Vertex>();

		for (int i = 0; i < indexedModel.getPositions().size(); i++) {
			vertices.add(new Vertex(indexedModel.getPositions().get(i), indexedModel.getTexCoords().get(i), indexedModel.getNormals().get(i), new Vector3f(), new Vector3f()));
		}
		Vertex[] vertexData = new Vertex[vertices.size()];
		vertices.toArray(vertexData);

		Integer[] indexData = new Integer[indexedModel.getIndices().size()];
		indexedModel.getIndices().toArray(indexData);

		return new Model(vertexData, ArrayUtils.toIntArray(indexData));
	}
}
