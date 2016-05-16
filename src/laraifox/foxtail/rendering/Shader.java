package laraifox.foxtail.rendering;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import laraifox.foxtail.core.BufferUtils;
import laraifox.foxtail.core.Logger;
import laraifox.foxtail.core.ResourceManager;
import laraifox.foxtail.core.math.Matrix4f;
import laraifox.foxtail.core.math.Vector2f;
import laraifox.foxtail.core.math.Vector3f;
import laraifox.foxtail.core.math.Vector4f;

public class Shader {
	private class GLSLStruct {
		public String type;
		public String name;

		public GLSLStruct(String type, String name) {
			this.type = type;
			this.name = name;
		}
	}

	private static final int SHADER_SOURCE_INDEX_VERTEX = 0;
	private static final int SHADER_SOURCE_INDEX_TESSELLATION = 1;
	private static final int SHADER_SOURCE_INDEX_GEOMETRY = 2;
	private static final int SHADER_SOURCE_INDEX_FRAGMENT = 3;
	private static final int SHADER_SOURCE_COUNT = 4;

	private static final String GLSL_KEYWORD_ATTRIBUTE = new String("attribute");
	private static final String GLSL_KEYWORD_STRUCT = new String("struct");
	private static final String GLSL_KEYWORD_UNIFORM = new String("uniform");

	private static final String SHADER_NAME_PREFIX = new String("Shader-");

	private static final String SHADER_KEYWORD_PROGRAM = new String("Program");
	private static final String SHADER_KEYWORD_ALTERNATE = new String("Alternate");
	private static final String SHADER_KEYWORD_VERTEX = new String("GLSLVertex");
	private static final String SHADER_KEYWORD_TESSELLATION = new String("GLSLTessellation");
	private static final String SHADER_KEYWORD_GEOMETRY = new String("GLSLGeometry");
	private static final String SHADER_KEYWORD_FRAGMENT = new String("GLSLFragment");
	private static final String SHADER_KEYWORD_PIXEL = new String("GLSLPixel");
	private static final String SHADER_KEYWORD_END = new String("GLSLEnd");

	public static boolean logUnrecognizedUniformCalls = false;
	public static boolean exitOnShaderError = false;

	private HashMap<String, Integer> uniforms;

	private String shaderName;
	private int id;

	private Shader() {
		this.uniforms = new HashMap<String, Integer>();
	}

	public Shader(String vertexFilepath, String fragmentFilepath, boolean bindAttributes) {
		this();

		try {
			this.createShader(vertexFilepath, fragmentFilepath, ResourceManager.loadShaderSource(vertexFilepath), ResourceManager.loadShaderSource(fragmentFilepath), bindAttributes);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Shader(String shaderFilepath) {
		this(shaderFilepath, false);
	}

	public Shader(String shaderFilepath, boolean bindAttributes) {
		this();

		try {
			this.createShader(shaderFilepath, bindAttributes);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void finalize() {
		GL20.glDeleteProgram(id);
	}

	private void createShader(String shaderFilepath, boolean bindAttributes) throws IOException {
		String shaderSourceRaw = ResourceManager.loadShaderSource(shaderFilepath);

		int shaderNameStart = shaderSourceRaw.indexOf(SHADER_KEYWORD_PROGRAM) + SHADER_KEYWORD_PROGRAM.length();
		while (shaderSourceRaw.charAt(shaderNameStart - 1) != '\"')
			shaderNameStart++;

		int shaderNameEnd = shaderNameStart + 1;
		while (shaderSourceRaw.charAt(shaderNameEnd) != '\"')
			shaderNameEnd++;

		this.shaderName = shaderSourceRaw.substring(shaderNameStart, shaderNameEnd);
		if (shaderName.isEmpty()) {
			shaderName = SHADER_NAME_PREFIX + shaderFilepath;
		} else {
			shaderName = SHADER_NAME_PREFIX + shaderName;
		}

		List<Integer> orderedList = new ArrayList<Integer>(4);

		int glslVertexStart = shaderSourceRaw.indexOf(SHADER_KEYWORD_VERTEX);
		orderedList.add(glslVertexStart);
		if (glslVertexStart < 0) {
			this.onShaderErrorOccurred(shaderSourceRaw, shaderFilepath, "Unable to find vertex shader in source!");
		}
		while (shaderSourceRaw.charAt(glslVertexStart - 1) != '\n')
			glslVertexStart++;

		orderedList.add(glslVertexStart);

		int glslFragmentStart = shaderSourceRaw.indexOf(SHADER_KEYWORD_FRAGMENT);
		orderedList.add(glslFragmentStart);
		if (glslFragmentStart < 0) {
			glslFragmentStart = shaderSourceRaw.indexOf(SHADER_KEYWORD_PIXEL);
			if (glslFragmentStart < 0) {
				this.onShaderErrorOccurred(shaderSourceRaw, shaderFilepath, "Unable to find fragment shader in source!");
			}
		}
		while (shaderSourceRaw.charAt(glslFragmentStart - 1) != '\n')
			glslFragmentStart++;

		orderedList.add(glslFragmentStart);

		int glslTessellationStart = shaderSourceRaw.indexOf(SHADER_KEYWORD_TESSELLATION);
		orderedList.add(glslTessellationStart);
		if (glslTessellationStart < 0) {
			Logger.log("Unable to find tessellation shader in source!", shaderName, Logger.MESSAGE_LEVEL_DEFAULT);
			glslTessellationStart = Integer.MAX_VALUE - SHADER_SOURCE_INDEX_TESSELLATION;
		} else {
			while (shaderSourceRaw.charAt(glslTessellationStart - 1) != '\n')
				glslTessellationStart++;
		}

		orderedList.add(glslTessellationStart);

		int glslGeometryStart = shaderSourceRaw.indexOf(SHADER_KEYWORD_GEOMETRY);
		orderedList.add(glslGeometryStart);
		if (glslGeometryStart < 0) {
			Logger.log("Unable to find geometry shader in source!", shaderName, Logger.MESSAGE_LEVEL_DEFAULT);
			glslGeometryStart = Integer.MAX_VALUE - SHADER_SOURCE_INDEX_GEOMETRY;
		} else {
			while (shaderSourceRaw.charAt(glslGeometryStart - 1) != '\n')
				glslGeometryStart++;
		}

		orderedList.add(glslGeometryStart);

		Collections.sort(orderedList);
		Iterator<Integer> iterator = orderedList.iterator();
		while (iterator.hasNext()) {
			Integer integer = iterator.next();
			if (integer >= shaderSourceRaw.length() || integer < 0) {
				iterator.remove();
			}
		}

		int glslShaderEnd = shaderSourceRaw.indexOf(SHADER_KEYWORD_END, orderedList.get(orderedList.size() - 1));
		if (glslShaderEnd < 0) {
			this.onShaderErrorOccurred(shaderSourceRaw, shaderFilepath, "Unable to find end of shader source!");
		}

		orderedList.add(glslShaderEnd);

		String[] shaderSources = new String[SHADER_SOURCE_COUNT];
		for (int i = 0; i < orderedList.size() - 1; i++) {
			int sourceStart = orderedList.get(i);
			int sourceEnd = orderedList.get(i + 1);

			String clippedSource = shaderSourceRaw.substring(sourceStart, sourceEnd);

			if (sourceStart == glslVertexStart) {
				shaderSources[SHADER_SOURCE_INDEX_VERTEX] = this.shaderSourcePadding(shaderSourceRaw, clippedSource, sourceStart);
			} else if (sourceStart == glslFragmentStart) {
				shaderSources[SHADER_SOURCE_INDEX_FRAGMENT] = this.shaderSourcePadding(shaderSourceRaw, clippedSource, sourceStart);
			} else if (sourceStart == glslGeometryStart) {
				shaderSources[SHADER_SOURCE_INDEX_GEOMETRY] = this.shaderSourcePadding(shaderSourceRaw, clippedSource, sourceStart);
			} else if (sourceStart == glslTessellationStart) {
				shaderSources[SHADER_SOURCE_INDEX_TESSELLATION] = this.shaderSourcePadding(shaderSourceRaw, clippedSource, sourceStart);
			}
		}

		try {
			this.createShader(shaderFilepath, shaderFilepath, shaderSources[SHADER_SOURCE_INDEX_VERTEX], shaderSources[SHADER_SOURCE_INDEX_FRAGMENT], bindAttributes);
		} catch (Exception e) {
			int alternateShaderPathStart = shaderSourceRaw.indexOf(SHADER_KEYWORD_ALTERNATE) + SHADER_KEYWORD_ALTERNATE.length();
			while (shaderSourceRaw.charAt(alternateShaderPathStart - 1) != '\"')
				alternateShaderPathStart++;

			int alternateShaderPathEnd = alternateShaderPathStart + 1;
			while (shaderSourceRaw.charAt(alternateShaderPathEnd) != '\"')
				alternateShaderPathEnd++;

			String alternateShaderPath = shaderSourceRaw.substring(alternateShaderPathStart, alternateShaderPathEnd);

			if (alternateShaderPath.isEmpty()) {
				this.onShaderErrorOccurred(shaderSourceRaw, shaderFilepath, e.getMessage());
			} else {
				if (!alternateShaderPath.startsWith("\\") && !alternateShaderPath.startsWith("/")) {
					alternateShaderPath = System.getProperty("file.separator") + alternateShaderPath;
				}

				Logger.log("Unable to compile shader '" + shaderFilepath + "', going to alternate shader '" + alternateShaderPath + "'.\n" + e.getMessage(), shaderName, Logger.MESSAGE_LEVEL_ERROR);

				this.createShader(new File(shaderFilepath).getParent() + alternateShaderPath, bindAttributes);
			}
		}

		//		for (int i = 0; i < SHADER_SOURCE_COUNT; i++) {
		//			System.out.println("________________________________________________________________________________");
		//			if (shaderSources[i] != null) {
		//				System.out.println(shaderSources[i]);
		//			}
		//		}
		//		System.exit(1);

		//		int vertexIndex = shaderSourceRaw.indexOf("\n", shaderSourceRaw.indexOf("GLSLVertex"));
		//		int vertexEnd = shaderSourceRaw.indexOf("GLSLFragment");
		//		int fragmentIndex = shaderSourceRaw.indexOf("\n", vertexEnd);
		//		int endIndex = shaderSourceRaw.indexOf("GLSLEnd");
		//
		//		try {
		//			return this.createShader(shaderFilepath, shaderFilepath, shaderSourceRaw.substring(vertexIndex, vertexEnd), shaderSourceRaw.substring(fragmentIndex, endIndex), bindAttributes);
		//		} catch (Exception e) {
		//			String alternateShaderPath = shaderSourceRaw.substring(shaderSourceRaw.indexOf("\"", shaderSourceRaw.indexOf(SHADER_KEYWORD_ALTERNATE)) + 1, shaderSourceRaw.indexOf("\n", shaderSourceRaw
		//					.indexOf(SHADER_KEYWORD_ALTERNATE)) - 1);
		//
		//			if (alternateShaderPath.isEmpty() && Shader.exitOnShaderError) {
		//				Logger.log("Unable to compile shader '" + shaderFilepath + "', going to Error shader.\n" + e.getMessage(), shaderName, Logger.MESSAGE_LEVEL_ERROR);
		//
		//				return this.createShader(ResourceManager.getFoxtailResourcePath("shaders/Error.shader"), bindAttributes);
		//			} else if (!alternateShaderPath.startsWith("\\") && !alternateShaderPath.startsWith("/")) {
		//				alternateShaderPath = System.getProperty("file.separator") + alternateShaderPath;
		//			}
		//
		//			Logger.log("Unable to compile shader '" + shaderFilepath + "', going to fallback shader '" + alternateShaderPath + "'.\n" + e.getMessage(), shaderName, Logger.MESSAGE_LEVEL_ERROR);
		//
		//			return this.createShader(new File(shaderFilepath).getParent() + alternateShaderPath, bindAttributes);
		//		}

		Logger.lineBreak(Logger.MESSAGE_LEVEL_DEFAULT);
	}

	private String shaderSourcePadding(String originalSource, String clippedSource, int endIndex) {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < originalSource.length() && i < endIndex; i++) {
			if (originalSource.charAt(i) == '\n') {
				builder.append("\n");
			}
		}
		builder.append(clippedSource);

		return builder.toString();
	}

	private void onShaderErrorOccurred(String shaderSourceRaw, String shaderFilepath, String errorMessage) {
		Logger.log("Unable to compile shader '" + shaderFilepath + "'.\n" + errorMessage, shaderName, Logger.MESSAGE_LEVEL_ERROR);
		
		if (exitOnShaderError) {
			System.exit(1);
		}

		try {
			this.createShader(ResourceManager.getFoxtailResourcePath("shaders/Error.shader"), false);
		} catch (IOException e) {
			Logger.log("Unable to compile Error shader, trying legacy version.\n" + e.getMessage(), shaderName, Logger.MESSAGE_LEVEL_ERROR);
			
			try {
				this.createShader(ResourceManager.getFoxtailResourcePath("shaders/legacy/Error.shader"), true);
			} catch (IOException e1) {
				Logger.log("Unable to compile legacy Error shader.\n" + e1.getMessage(), shaderName, Logger.MESSAGE_LEVEL_CRITICAL);
				System.exit(1);
			}
		}
	}

	private void createShader(String vertexFilepath, String fragmentFilepath, String vertexShaderSrc, String fragmentShaderSrc, boolean bindAttributes) throws Exception {
		this.id = GL20.glCreateProgram();

		int vertexShader = GL20.glCreateShader(GL20.GL_VERTEX_SHADER);
		GL20.glShaderSource(vertexShader, vertexShaderSrc);
		GL20.glCompileShader(vertexShader);

		int vertexCompileStatus = GL20.glGetShaderi(vertexShader, GL20.GL_COMPILE_STATUS);
		if (vertexCompileStatus == GL11.GL_FALSE) {
			String infoLog = GL20.glGetShaderInfoLog(vertexShader, GL20.glGetShaderi(vertexShader, GL20.GL_INFO_LOG_LENGTH));
			throw new Exception("Vertex Shader '" + vertexFilepath + "' failed to compile!\n" + infoLog);
		}

		GL20.glAttachShader(id, vertexShader);

		int fragmentShader = GL20.glCreateShader(GL20.GL_FRAGMENT_SHADER);
		GL20.glShaderSource(fragmentShader, fragmentShaderSrc);
		GL20.glCompileShader(fragmentShader);

		int fragmentCompileStatus = GL20.glGetShaderi(fragmentShader, GL20.GL_COMPILE_STATUS);
		if (fragmentCompileStatus == GL11.GL_FALSE) {
			String infoLog = GL20.glGetShaderInfoLog(fragmentShader, GL20.glGetShaderi(fragmentShader, GL20.GL_INFO_LOG_LENGTH));
			throw new Exception("Fragment Shader '" + fragmentFilepath + "' failed to compile!\n" + infoLog);
		}

		GL20.glAttachShader(id, fragmentShader);

		GL20.glLinkProgram(id);

		int programLinkStatus = GL20.glGetProgrami(id, GL20.GL_LINK_STATUS);
		if (programLinkStatus == GL11.GL_FALSE) {
			String infoLog = GL20.glGetProgramInfoLog(fragmentShader, GL20.glGetProgrami(id, GL20.GL_INFO_LOG_LENGTH));
			throw new Exception("Program '" + vertexFilepath + "' : '" + fragmentFilepath + "' failed to link!\n" + infoLog);
		}

		GL20.glValidateProgram(id);

		GL20.glDeleteShader(vertexShader);
		GL20.glDeleteShader(fragmentShader);

		if (bindAttributes) {
			bindAllAttributes(vertexShaderSrc);
		}

		retrieveUniformLocations(vertexShaderSrc);
		retrieveUniformLocations(fragmentShaderSrc);
	}

	private void bindAllAttributes(String shaderSrc) {
		int currentAttribIndex = 0;
		int attributeStartLocation = shaderSrc.indexOf(GLSL_KEYWORD_ATTRIBUTE);
		while (attributeStartLocation != -1) {
			if (attributeStartLocation == 0 || !Character.isWhitespace(shaderSrc.charAt(attributeStartLocation - 1)) && shaderSrc.charAt(attributeStartLocation - 1) != ';' || !Character.isWhitespace(
					shaderSrc.charAt(attributeStartLocation + GLSL_KEYWORD_ATTRIBUTE.length()))) {
				continue;
			}

			int startIndex = attributeStartLocation + GLSL_KEYWORD_ATTRIBUTE.length() + 1;
			int endIndex = shaderSrc.indexOf(";", startIndex);

			String attributeLine = shaderSrc.substring(startIndex, endIndex);

			String attributeName = attributeLine.substring(attributeLine.indexOf(" ") + 1, attributeLine.length()).trim();

			this.bindAttributeLocation(currentAttribIndex, attributeName);
			System.out.println("Bound attribute '" + attributeName + "' to index: " + currentAttribIndex);
			currentAttribIndex++;

			attributeStartLocation = shaderSrc.indexOf(GLSL_KEYWORD_ATTRIBUTE, attributeStartLocation + GLSL_KEYWORD_ATTRIBUTE.length());
		}
	}

	// private HashMap<String, ArrayList<GLSLStruct>> findUniformStructs(String shaderText)
	// {
	// HashMap<String, ArrayList<GLSLStruct>> result = new HashMap<String, ArrayList<GLSLStruct>>();
	//
	// final String STRUCT_KEYWORD = "struct";
	// int structStartLocation = shaderText.indexOf(STRUCT_KEYWORD);
	// while(structStartLocation != -1)
	// {
	// if(!(structStartLocation != 0
	// && (Character.isWhitespace(shaderText.charAt(structStartLocation - 1)) || shaderText.charAt(structStartLocation - 1) == ';')
	// && Character.isWhitespace(shaderText.charAt(structStartLocation + STRUCT_KEYWORD.length())))) {
	// structStartLocation = shaderText.indexOf(STRUCT_KEYWORD, structStartLocation + STRUCT_KEYWORD.length());
	// continue;
	// }
	//
	// int nameBegin = structStartLocation + STRUCT_KEYWORD.length() + 1;
	// int braceBegin = shaderText.indexOf("{", nameBegin);
	// int braceEnd = shaderText.indexOf("}", braceBegin);
	//
	// String structName = shaderText.substring(nameBegin, braceBegin).trim();
	// ArrayList<GLSLStruct> glslStructs = new ArrayList<GLSLStruct>();
	//
	// int componentSemicolonPos = shaderText.indexOf(";", braceBegin);
	// while(componentSemicolonPos != -1 && componentSemicolonPos < braceEnd)
	// {
	// int componentNameEnd = componentSemicolonPos + 1;
	//
	// while(Character.isWhitespace(shaderText.charAt(componentNameEnd - 1)) || shaderText.charAt(componentNameEnd - 1) == ';')
	// componentNameEnd--;
	//
	// int componentNameStart = componentSemicolonPos;
	//
	// while(!Character.isWhitespace(shaderText.charAt(componentNameStart - 1)))
	// componentNameStart--;
	//
	// int componentTypeEnd = componentNameStart;
	//
	// while(Character.isWhitespace(shaderText.charAt(componentTypeEnd - 1)))
	// componentTypeEnd--;
	//
	// int componentTypeStart = componentTypeEnd;
	//
	// while(!Character.isWhitespace(shaderText.charAt(componentTypeStart - 1)))
	// componentTypeStart--;
	//
	// String componentName = shaderText.substring(componentNameStart, componentNameEnd);
	// String componentType = shaderText.substring(componentTypeStart, componentTypeEnd);
	//
	// GLSLStruct glslStruct = new GLSLStruct(componentName, componentType);
	//
	// glslStructs.add(glslStruct);
	//
	// componentSemicolonPos = shaderText.indexOf(";", componentSemicolonPos + 1);
	// }
	//
	// result.put(structName, glslStructs);
	//
	// structStartLocation = shaderText.indexOf(STRUCT_KEYWORD, structStartLocation + STRUCT_KEYWORD.length());
	// }
	//
	// return result;
	// }

	private HashMap<String, ArrayList<GLSLStruct>> findUniformStructs(String shaderSrc) {
		HashMap<String, ArrayList<GLSLStruct>> result = new HashMap<String, ArrayList<GLSLStruct>>();

		int structStartLocation = shaderSrc.indexOf(GLSL_KEYWORD_STRUCT);
		while (structStartLocation != -1) {
			if (structStartLocation == 0 || !Character.isWhitespace(shaderSrc.charAt(structStartLocation - 1)) && shaderSrc.charAt(structStartLocation - 1) != ';' || !Character.isWhitespace(shaderSrc
					.charAt(structStartLocation + GLSL_KEYWORD_STRUCT.length()))) {
				continue;
			}

			int nameStartIndex = structStartLocation + GLSL_KEYWORD_STRUCT.length() + 1;
			int braceStartIndex = shaderSrc.indexOf("{", nameStartIndex);
			int braceEndIndex = shaderSrc.indexOf("}", braceStartIndex);

			String structName = shaderSrc.substring(nameStartIndex, braceStartIndex).trim();

			ArrayList<GLSLStruct> structElements = new ArrayList<GLSLStruct>();

			int lineSemicolonIndex = shaderSrc.indexOf(";", braceStartIndex);
			while (lineSemicolonIndex != -1 && lineSemicolonIndex < braceEndIndex) {
				// int elementNameStartIndex = lineSemicolonIndex;
				// while (!Character.isWhitespace(shaderSrc.charAt(elementNameStartIndex - 1))) {
				// elementNameStartIndex--;
				// }
				//
				// int elementTypeEndIndex = elementNameStartIndex - 1;
				// int elementTypeStartIndex = elementTypeEndIndex;
				// while (!Character.isWhitespace(shaderSrc.charAt(elementTypeStartIndex - 1))) {
				// elementTypeStartIndex--;
				// }

				int componentNameEnd = lineSemicolonIndex + 1;

				while (Character.isWhitespace(shaderSrc.charAt(componentNameEnd - 1)) || shaderSrc.charAt(componentNameEnd - 1) == ';')
					componentNameEnd--;

				int componentNameStart = lineSemicolonIndex;

				while (!Character.isWhitespace(shaderSrc.charAt(componentNameStart - 1)))
					componentNameStart--;

				int componentTypeEnd = componentNameStart;

				while (Character.isWhitespace(shaderSrc.charAt(componentTypeEnd - 1)))
					componentTypeEnd--;

				int componentTypeStart = componentTypeEnd;

				while (!Character.isWhitespace(shaderSrc.charAt(componentTypeStart - 1)))
					componentTypeStart--;

				String elementType = shaderSrc.substring(componentTypeStart, componentTypeEnd).trim();
				String elementName = shaderSrc.substring(componentNameStart, componentNameEnd).trim();

				structElements.add(new GLSLStruct(elementType, elementName));

				lineSemicolonIndex = shaderSrc.indexOf(";", lineSemicolonIndex + 1);
			}

			result.put(structName, structElements);

			structStartLocation = shaderSrc.indexOf(GLSL_KEYWORD_STRUCT, structStartLocation + GLSL_KEYWORD_STRUCT.length());
		}

		return result;
	}

	private void retrieveUniformLocations(String shaderSrc) {
		HashMap<String, ArrayList<GLSLStruct>> structs = this.findUniformStructs(shaderSrc);

		int uniformStartLocation = shaderSrc.indexOf(GLSL_KEYWORD_UNIFORM);
		while (uniformStartLocation != -1) {
			if (uniformStartLocation == 0 || !Character.isWhitespace(shaderSrc.charAt(uniformStartLocation - 1)) && shaderSrc.charAt(uniformStartLocation - 1) != ';' || !Character.isWhitespace(
					shaderSrc.charAt(uniformStartLocation + GLSL_KEYWORD_UNIFORM.length()))) {
				continue;
			}

			int startIndex = uniformStartLocation + GLSL_KEYWORD_UNIFORM.length() + 1;
			int endIndex = shaderSrc.indexOf(";", startIndex);

			String uniformLine = shaderSrc.substring(startIndex, endIndex);

			String uniformType = uniformLine.substring(0, uniformLine.indexOf(" ")).trim();
			String uniformName = uniformLine.substring(uniformLine.indexOf(" ") + 1, uniformLine.length()).trim();
			if (uniformType.endsWith("]")) {
				int arrayEndIndex = uniformType.length() - 1;
				int arrayStartIndex = arrayEndIndex;
				while (uniformType.charAt(arrayStartIndex - 1) != '[')
					arrayStartIndex--;

				String arraySizeString = uniformType.substring(arrayStartIndex, arrayEndIndex).trim();
				int arraySize = 0;
				try {
					arraySize = Integer.valueOf(arraySizeString);
				} catch (NumberFormatException e) {
					int arraySizeVariableStart = shaderSrc.indexOf(arraySizeString);
					while (!Character.isDigit(shaderSrc.charAt(arraySizeVariableStart)))
						arraySizeVariableStart++;

					int arraySizeVariableEnd = arraySizeVariableStart + 1;
					while (Character.isDigit(shaderSrc.charAt(arraySizeVariableEnd)))
						arraySizeVariableEnd++;

					String arraySizeVariable = shaderSrc.substring(arraySizeVariableStart, arraySizeVariableEnd);

					arraySize = Integer.valueOf(arraySizeVariable);
				}

				uniformType = uniformType.substring(0, arrayStartIndex - 1).trim();

				for (int i = 0; i < arraySize; i++) {
					this.addUniform(uniformName + "[" + i + "]", uniformType, structs);
				}
			} else if (uniformName.endsWith("]")) {
				int arrayEndIndex = uniformName.length() - 1;
				int arrayStartIndex = arrayEndIndex;
				while (uniformName.charAt(arrayStartIndex - 1) != '[')
					arrayStartIndex--;

				String arraySizeString = uniformName.substring(arrayStartIndex, arrayEndIndex).trim();
				int arraySize = 0;
				try {
					arraySize = Integer.valueOf(arraySizeString);
				} catch (NumberFormatException e) {
					int arraySizeVariableStart = shaderSrc.indexOf(arraySizeString);
					while (!Character.isDigit(shaderSrc.charAt(arraySizeVariableStart)))
						arraySizeVariableStart++;

					int arraySizeVariableEnd = arraySizeVariableStart + 1;
					while (Character.isDigit(shaderSrc.charAt(arraySizeVariableEnd)))
						arraySizeVariableEnd++;

					String arraySizeVariable = shaderSrc.substring(arraySizeVariableStart, arraySizeVariableEnd);

					arraySize = Integer.valueOf(arraySizeVariable);
				}

				uniformName = uniformName.substring(0, arrayStartIndex - 1).trim();

				for (int i = 0; i < arraySize; i++) {
					this.addUniform(uniformName + "[" + i + "]", uniformType, structs);
				}
			} else {
				this.addUniform(uniformName, uniformType, structs);
			}

			uniformStartLocation = shaderSrc.indexOf(GLSL_KEYWORD_UNIFORM, uniformStartLocation + GLSL_KEYWORD_UNIFORM.length());
		}
	}

	private void addUniform(String uniformName, String uniformType, HashMap<String, ArrayList<GLSLStruct>> structs) {
		boolean addThis = true;
		ArrayList<GLSLStruct> structComponents = structs.get(uniformType);

		if (structComponents != null) {
			addThis = false;
			for (GLSLStruct struct : structComponents) {
				addUniform(uniformName + "." + struct.name, struct.type, structs);
			}
		}

		if (!addThis)
			return;

		int uniformLocation = GL20.glGetUniformLocation(id, uniformName);

		if (uniformLocation == 0xFFFFFFFF) {
			Logger.log("Uniform '" + uniformName + "' is not used in shader and has been removed!", shaderName, Logger.MESSAGE_LEVEL_WARNING);
			// System.err.println("Error: Could not find uniform: " + uniformType + " " + uniformName);
			// new Exception().printStackTrace();
			// System.exit(1);
		}

		uniforms.put(uniformName, uniformLocation);
	}

	// private void addUniform(String uniformType, String uniformName, HashMap<String, ArrayList<GLSLStruct>> structs) {
	// ArrayList<GLSLStruct> structElements = structs.get(uniformType);
	//
	// if (structElements != null) {
	// for (GLSLStruct structElement : structElements) {
	// this.addUniform(structElement.type, uniformName + "." + structElement.name, structs);
	// }
	// } else if (!uniforms.containsKey(uniformName)) {
	// int uniformLocation = GL20.glGetUniformLocation(id, uniformName);
	// if (uniformLocation == -1) {
	// System.err.println("ERROR! Could not find location for uniform: " + uniformName);
	// System.exit(1);
	// }
	//
	// uniforms.put(uniformName, uniformLocation);
	// }
	// }

	public void bind() {
		GL20.glUseProgram(id);
	}

	public void bindAttributeLocation(int index, String name) {
		GL20.glBindAttribLocation(id, index, name);
	}

	public void setUniform(String name, int value) {
		GL20.glUniform1i(getUniformLocation(name), value);
	}

	public void setUniform(String name, float value) {
		GL20.glUniform1f(getUniformLocation(name), value);
	}

	public void setUniform(String name, Vector2f value) {
		GL20.glUniform2f(getUniformLocation(name), value.getX(), value.getY());
	}

	public void setUniform(String name, Vector3f value) {
		GL20.glUniform3f(getUniformLocation(name), value.getX(), value.getY(), value.getZ());
	}

	public void setUniform(String name, Vector4f value) {
		GL20.glUniform4f(getUniformLocation(name), value.getX(), value.getY(), value.getZ(), value.getW());
	}

	public void setUniform(String name, Matrix4f value) {
		GL20.glUniformMatrix4(getUniformLocation(name), false, BufferUtils.createFloatBuffer(value, true));
	}

	public void setUniform(String name, Matrix4f value, boolean transpose) {
		GL20.glUniformMatrix4(getUniformLocation(name), transpose, BufferUtils.createFloatBuffer(value, true));
	}

	private int getUniformLocation(String name) {
		Integer uniformLocation = uniforms.get(name);
		if (uniformLocation == null) {
			uniformLocation = -1;
		}

		return uniformLocation;
	}

	public static void unbind() {
		GL20.glUseProgram(0);
	}
}
