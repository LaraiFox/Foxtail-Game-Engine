package laraifox.foxtail.testing.thinmatrixfont;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import laraifox.foxtail.core.ResourceManager;
import laraifox.foxtail.core.math.Matrix4f;
import laraifox.foxtail.rendering.Shader;
import laraifox.foxtail.testing.shadersandbox.ShaderSandbox;

public class TextManager {
	private static Map<FontType, List<GUIText>> texts = new HashMap<FontType, List<GUIText>>();
	private static Shader shader;

	public static void initialize() {
		TextManager.shader = new Shader(ResourceManager.getFoxtailResourcePath("shaders/TexturedUnlit2D.shader"), false);
	}

	public static void render() {
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glDisable(GL11.GL_DEPTH_TEST);

		shader.bind();
		shader.setUniform("FOXTAIL_PROJECTION_MATRIX", Matrix4f.Identity());
		shader.setUniform("FOXTAIL_PROJECTION_MATRIX", ShaderSandbox.VP_MATRIX);

		for (FontType font : texts.keySet()) {
			GL13.glActiveTexture(GL13.GL_TEXTURE0);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, font.getTexture());

			for (GUIText text : texts.get(font)) {
				GL30.glBindVertexArray(text.getMesh());

				GL20.glEnableVertexAttribArray(0);
				GL20.glEnableVertexAttribArray(1);

				shader.setUniform("in_BaseColor", text.getColour());
				shader.setUniform("in_Offset", text.getTransform().getTranslation().getXY());

				GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, text.getVertexCount());

				GL20.glDisableVertexAttribArray(1);
				GL20.glDisableVertexAttribArray(0);
			}
		}

		GL30.glBindVertexArray(0);

		Shader.unbind();

		GL11.glDisable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
	}

	public static void loadText(GUIText text) {
		FontType font = text.getFont();

		font.createTextMesh(text);

		List<GUIText> textBatch = texts.get(font);
		if (textBatch == null) {
			textBatch = new ArrayList<GUIText>();
			texts.put(font, textBatch);
		}
		textBatch.add(text);
	}

	//	private static int loadToVAO(float[] vertexPositions, float[] textureCoords) {
	//		int vaoID = GL30.glGenVertexArrays();
	//		GL30.glBindVertexArray(vaoID);
	//
	//		FloatBuffer buffer = BufferUtils.createFloatBuffer(vertexPositions.length * 2);
	//		for (int i = 0; i < vertexPositions.length / 2; i++) {
	//			buffer.put(vertexPositions[i * 2 + 0]);
	//			buffer.put(vertexPositions[i * 2 + 1]);
	//
	//			buffer.put(textureCoords[i * 2 + 0]);
	//			buffer.put(textureCoords[i * 2 + 1]);
	//		}
	//		buffer.flip();
	//
	//		int vboID = GL15.glGenBuffers();
	//		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);
	//		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
	//
	//		GL20.glVertexAttribPointer(0, 2, GL11.GL_FLOAT, false, 4 * Float.BYTES, 0 * Float.BYTES);
	//		GL20.glVertexAttribPointer(1, 2, GL11.GL_FLOAT, false, 4 * Float.BYTES, 2 * Float.BYTES);
	//
	//		GL30.glBindVertexArray(0);
	//
	//		return vaoID;
	//	}

	public static void removeText(GUIText text) {
		List<GUIText> textBatch = texts.get(text.getFont());
		text.cleanUp();
		textBatch.remove(text);
		if (textBatch.isEmpty()) {
			texts.remove(texts.get(text.getFont()));
		}
	}
}
