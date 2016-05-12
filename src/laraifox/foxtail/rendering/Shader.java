package laraifox.foxtail.rendering;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
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

	public static boolean logUnrecognizedUniformCalls = false;

	private HashMap<String, Integer> uniforms;
	private List<String> checkedUniforms;

	private int id;

	private String shaderName;

	private Shader() {
		this.uniforms = new HashMap<String, Integer>();
		this.checkedUniforms = new ArrayList<String>();
	}

	public Shader(String vertexFilepath, String fragmentFilepath, boolean bindAttributes) throws Exception {
		this();

		this.createShader(vertexFilepath, fragmentFilepath, ResourceManager.loadFile(vertexFilepath), ResourceManager.loadFile(fragmentFilepath), bindAttributes);
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

	private int createShader(String shaderFilepath, boolean bindAttributes) throws IOException {
		String shaderSrc = ResourceManager.loadFile(shaderFilepath);

		this.shaderName = shaderSrc.substring(shaderSrc.indexOf("\"") + 1, shaderSrc.indexOf("\"", shaderSrc.indexOf("\"") + 1));

		int vertexIndex = shaderSrc.indexOf("\n", shaderSrc.indexOf("GLSLVertex"));
		int vertexEnd = shaderSrc.indexOf("GLSLFragment");
		int fragmentIndex = shaderSrc.indexOf("\n", vertexEnd);
		int endIndex = shaderSrc.indexOf("GLSLEnd");

		try {
			return this.createShader(shaderFilepath, shaderFilepath, shaderSrc.substring(vertexIndex, vertexEnd), shaderSrc.substring(fragmentIndex, endIndex), bindAttributes);
		} catch (Exception e) {
			String fallbackPath = shaderSrc.substring(shaderSrc.indexOf("\"", shaderSrc.indexOf("Fallback")) + 1, shaderSrc.indexOf("\n", shaderSrc.indexOf("Fallback")) - 1);

			if (fallbackPath.isEmpty()) {
				Logger.log("Unable to compile shader '" + shaderFilepath + "', going to Error shader.\n" + e.getMessage(), "Shader-" + shaderName, Logger.MESSAGE_LEVEL_ERROR);

				return this.createShader("src/laraifox/foxtail/rendering/shaders/Error.shader", bindAttributes);
			} else if (!fallbackPath.startsWith("\\") && !fallbackPath.startsWith("/")) {
				fallbackPath = System.getProperty("file.separator") + fallbackPath;
			}

			Logger.log("Unable to compile shader '" + shaderFilepath + "', going to fallback shader '" + fallbackPath + "'.\n" + e.getMessage(), "Shader-" + shaderName, Logger.MESSAGE_LEVEL_ERROR);

			return this.createShader(new File(shaderFilepath).getParent() + fallbackPath, bindAttributes);
		}
	}

	private int createShader(String vertexFilepath, String fragmentFilepath, String vertexShaderSrc, String fragmentShaderSrc, boolean bindAttributes) throws Exception {
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
			bindAllAttribs(vertexShaderSrc);
		}

		getUniformLocations(vertexShaderSrc);
		getUniformLocations(fragmentShaderSrc);

		return id;
	}

	private void bindAllAttribs(String shaderSrc) {
		final String ATTRIBUTE_KEYWORD = new String("attribute");

		int currentAttribIndex = 0;
		int attributeStartLocation = shaderSrc.indexOf(ATTRIBUTE_KEYWORD);
		while (attributeStartLocation != -1) {
			if (attributeStartLocation == 0 || !Character.isWhitespace(shaderSrc.charAt(attributeStartLocation - 1)) && shaderSrc.charAt(attributeStartLocation - 1) != ';' || !Character.isWhitespace(
					shaderSrc.charAt(attributeStartLocation + ATTRIBUTE_KEYWORD.length()))) {
				continue;
			}

			int startIndex = attributeStartLocation + ATTRIBUTE_KEYWORD.length() + 1;
			int endIndex = shaderSrc.indexOf(";", startIndex);

			String attributeLine = shaderSrc.substring(startIndex, endIndex);

			String attributeName = attributeLine.substring(attributeLine.indexOf(" ") + 1, attributeLine.length()).trim();

			this.bindAttribLocation(currentAttribIndex, attributeName);
			System.out.println("Bound attribute '" + attributeName + "' to index: " + currentAttribIndex);
			currentAttribIndex++;

			attributeStartLocation = shaderSrc.indexOf(ATTRIBUTE_KEYWORD, attributeStartLocation + ATTRIBUTE_KEYWORD.length());
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

		final String STRUCT_KEYWORD = new String("struct");

		int structStartLocation = shaderSrc.indexOf(STRUCT_KEYWORD);
		while (structStartLocation != -1) {
			if (structStartLocation == 0 || !Character.isWhitespace(shaderSrc.charAt(structStartLocation - 1)) && shaderSrc.charAt(structStartLocation - 1) != ';' || !Character.isWhitespace(shaderSrc
					.charAt(structStartLocation + STRUCT_KEYWORD.length()))) {
				continue;
			}

			int nameStartIndex = structStartLocation + STRUCT_KEYWORD.length() + 1;
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

			structStartLocation = shaderSrc.indexOf(STRUCT_KEYWORD, structStartLocation + STRUCT_KEYWORD.length());
		}

		return result;
	}

	private void getUniformLocations(String shaderSrc) {
		HashMap<String, ArrayList<GLSLStruct>> structs = this.findUniformStructs(shaderSrc);

		final String UNIFORM_KEYWORD = new String("uniform");

		int uniformStartLocation = shaderSrc.indexOf(UNIFORM_KEYWORD);
		while (uniformStartLocation != -1) {
			if (uniformStartLocation == 0 || !Character.isWhitespace(shaderSrc.charAt(uniformStartLocation - 1)) && shaderSrc.charAt(uniformStartLocation - 1) != ';' || !Character.isWhitespace(
					shaderSrc.charAt(uniformStartLocation + UNIFORM_KEYWORD.length()))) {
				continue;
			}

			int startIndex = uniformStartLocation + UNIFORM_KEYWORD.length() + 1;
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

			uniformStartLocation = shaderSrc.indexOf(UNIFORM_KEYWORD, uniformStartLocation + UNIFORM_KEYWORD.length());
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
			Logger.log("Uniform '" + uniformName + "' is not used in shader and has been removed!", "Shader-" + shaderName, Logger.MESSAGE_LEVEL_WARNING);
			//			System.err.println("Error: Could not find uniform: " + uniformType + " " + uniformName);
			//			new Exception().printStackTrace();
			//			System.exit(1);
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

	public void bindAttribLocation(int index, String name) {
		GL20.glBindAttribLocation(id, index, name);
	}

	public void setUniform(String name, int value) {
		Integer uniformLocation = uniforms.get(name);
		if (!checkUniform(name, uniformLocation)) {
			return;
		}
		GL20.glUniform1i(uniforms.get(name), value);
	}

	public void setUniform(String name, float value) {
		Integer uniformLocation = uniforms.get(name);
		if (!checkUniform(name, uniformLocation)) {
			return;
		}
		GL20.glUniform1f(uniforms.get(name), value);
	}

	public void setUniform(String name, Vector2f value) {
		Integer uniformLocation = uniforms.get(name);
		if (!checkUniform(name, uniformLocation)) {
			return;
		}
		GL20.glUniform2f(uniforms.get(name), value.getX(), value.getY());
	}

	public void setUniform(String name, Vector3f value) {
		Integer uniformLocation = uniforms.get(name);
		if (!checkUniform(name, uniformLocation)) {
			return;
		}
		GL20.glUniform3f(uniforms.get(name), value.getX(), value.getY(), value.getZ());
	}

	public void setUniform(String name, Vector4f value) {
		Integer uniformLocation = uniforms.get(name);
		if (!checkUniform(name, uniformLocation)) {
			return;
		}
		GL20.glUniform4f(uniforms.get(name), value.getX(), value.getY(), value.getZ(), value.getW());
	}

	public void setUniform(String name, Matrix4f value) {
		Integer uniformLocation = uniforms.get(name);
		if (!checkUniform(name, uniformLocation)) {
			return;
		}
		GL20.glUniformMatrix4(uniforms.get(name), false, BufferUtils.createFloatBuffer(value, true));
	}

	public void setUniform(String name, Matrix4f value, boolean transpose) {
		Integer uniformLocation = uniforms.get(name);
		if (!checkUniform(name, uniformLocation)) {
			return;
		}
		GL20.glUniformMatrix4(uniformLocation, transpose, BufferUtils.createFloatBuffer(value, true));
	}

	private boolean checkUniform(String name, Integer uniformLocation) {
		if (uniformLocation == null) {
			if (logUnrecognizedUniformCalls) {
				Logger.log("Uniform '" + name + "' is unrecognized.", "Shader-" + shaderName, Logger.MESSAGE_LEVEL_DEBUG);
				if (!checkedUniforms.contains(name)) {
					checkedUniforms.add(name);
				}
			}

			return false;
		}

		return true;
	}

	public static void unbind() {
		GL20.glUseProgram(0);
	}
}
