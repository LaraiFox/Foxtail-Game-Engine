package laraifox.foxtail;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import laraifox.foxtail.core.ArrayUtils;
import laraifox.foxtail.core.Logger;
import laraifox.foxtail.core.math.Vector3f;
import laraifox.foxtail.rendering.Vertex;
import laraifox.foxtail.rendering.models.IndexedModel;
import laraifox.foxtail.rendering.models.Mesh;
import laraifox.foxtail.rendering.models.OBJModel;

public class AssetLoader {
	public static final String USER_APPDATA_DIRECTORY;

	static {
		String operatingSystem = System.getProperty("os.name").toLowerCase();
		String fileSeparator = System.getProperty("file.separator");
		if (operatingSystem.contains("win")) {
			USER_APPDATA_DIRECTORY = new String(System.getProperty("user.home") + fileSeparator + "AppData" + fileSeparator + "Roaming" + fileSeparator);
		} else if (operatingSystem.contains("mac")) {
			USER_APPDATA_DIRECTORY = new String(System.getProperty("user.home") + fileSeparator + "Library" + fileSeparator);
		} else if (operatingSystem.contains("lin")) {
			USER_APPDATA_DIRECTORY = new String(System.getProperty("user.home") + fileSeparator + ".Foxtail" + fileSeparator);
		} else {
			USER_APPDATA_DIRECTORY = new String(System.getProperty("user.home") + fileSeparator);
		}
	}

	private static String programFolder = new String("");
	private static String internalPrefix = new String("");

	private AssetLoader() {
	}

	public static void initialize(String programFolder, String internalPrefix) {
		AssetLoader.programFolder = programFolder;
		AssetLoader.internalPrefix = internalPrefix;
	}

	private static final String formatFilepath(String filepath) {
		//		if (useIndevPrefix) {
		//			filepath = indevDirectoryPrefix + filepath;
		//		}

		return filepath;
	}

	public static String loadFile(String filepath) throws IOException {
		filepath = AssetLoader.formatFilepath(filepath);

		//		InputStream inputStream = AssetLoader.class.getResourceAsStream(filepath);
		BufferedReader reader = new BufferedReader(new FileReader(new File(filepath)));

		StringBuilder result = new StringBuilder();

		String line = new String();
		while ((line = reader.readLine()) != null) {
			result.append(line).append("\n");
		}

		reader.close();

		return result.toString();
	}

	public static final Mesh loadModel(String filepath) {
		filepath = AssetLoader.formatFilepath(filepath);

		String extention = filepath.substring(filepath.lastIndexOf('.'));

		try {
			if (extention.endsWith("obj")) {
				return AssetLoader.loadOBJMesh(filepath);
			} else {
				Logger.log("File format not supported for model '" + filepath + "'", "AssetLoader", Logger.MESSAGE_LEVEL_CRITICAL);
				Logger.log("Supported file formats are: WaveFront OBJ (.obj)", "AssetLoader", Logger.MESSAGE_LEVEL_CRITICAL);
				System.exit(1);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.exit(1);
		}

		return null;
	}

	private static Mesh loadOBJMesh(String filepath) throws FileNotFoundException {
		//		InputStream inputStream = AssetLoader.class.getResourceAsStream(filepath);
		BufferedReader reader = new BufferedReader(new FileReader(new File(filepath)));

		OBJModel objModel = new OBJModel(reader);
		IndexedModel indexedModel = objModel.toIndexedModel();

		ArrayList<Vertex> vertices = new ArrayList<Vertex>();

		for (int i = 0; i < indexedModel.getPositions().size(); i++) {
			vertices.add(new Vertex(indexedModel.getPositions().get(i), indexedModel.getTexCoords().get(i), indexedModel.getNormals().get(i), indexedModel.getTangents().get(i), new Vector3f()));
		}
		Vertex[] vertexData = new Vertex[vertices.size()];
		vertices.toArray(vertexData);

		Integer[] indexData = new Integer[indexedModel.getIndices().size()];
		indexedModel.getIndices().toArray(indexData);

		return new Mesh(vertexData, ArrayUtils.toIntArray(indexData));
	}
}
