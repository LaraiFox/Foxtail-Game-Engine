package laraifox.foxtail.testing.fontMeshCreator;

import java.io.File;

import laraifox.foxtail.rendering.Texture2D;

/**
 * Represents a font. It holds the font's texture atlas as well as having the ability to create the quad vertices for any text using this font.
 * 
 * @author Karl
 *
 */
public class FontType {

	private Texture2D texture;
	private TextMeshGenerator loader;

	/**
	 * Creates a new font and loads up the data about each character from the font file.
	 * 
	 * @param texture
	 *            - the ID of the font atlas texture.
	 * @param fontFile
	 *            - the font file containing information about each character in the texture atlas.
	 */
	public FontType(String textureFilepath, String metadataFilepath) {
		this.texture = new Texture2D(textureFilepath);
		this.loader = new TextMeshGenerator(new File(metadataFilepath));
	}

	/**
	 * @return The font texture atlas.
	 */
	public int getTexture() {
		return texture.getTextureID();
	}

	/**
	 * Takes in an unloaded text and calculate all of the vertices for the quads on which this text will be rendered. The vertex positions and texture coords
	 * and calculated based on the information from the font file.
	 * 
	 * @param text
	 *            - the unloaded text.
	 * @return Information about the vertices of all the quads.
	 */
	public void createTextMesh(GUIText text) {
		loader.createTextMesh(text);
	}

}
