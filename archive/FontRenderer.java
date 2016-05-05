package laraifox.foxtail.testing.fontRendering;

import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import laraifox.foxtail.rendering.Shader;
import laraifox.foxtail.testing.fontMeshCreator.FontType;
import laraifox.foxtail.testing.fontMeshCreator.GUIText;
import laraifox.foxtail.testing.shadersandbox.ShaderSandbox;

public class FontRenderer {
	private Shader shader;

	public FontRenderer() {
		this.shader = new Shader("src/laraifox/foxtail/rendering/shaders/TexturedUnlit2D.shader", false);
	}

	public void render(Map<FontType, List<GUIText>> texts) {
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glDisable(GL11.GL_DEPTH_TEST);

		shader.bind();
		shader.setUniform("FOXTAIL_PROJECTION_MATRIX", ShaderSandbox.VP_MATRIX);

		for (FontType font : texts.keySet()) {
			GL13.glActiveTexture(GL13.GL_TEXTURE0);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, font.getTexture());

			for (GUIText text : texts.get(font)) {
				GL30.glBindVertexArray(text.getMesh());

				GL20.glEnableVertexAttribArray(0);
				GL20.glEnableVertexAttribArray(1);

				shader.setUniform("in_BaseColor", text.getColour());
				shader.setUniform("in_Offset", text.getPosition());

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
}
