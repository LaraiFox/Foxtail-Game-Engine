package laraifox.foxtail.testing.thinmatrixfont;

import java.io.File;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import laraifox.foxtail.core.BufferUtils;

public class TextMeshGenerator {

	protected static final double LINE_HEIGHT = 0.03f;
	protected static final int SPACE_ASCII = 32;

	private MetaFile metaData;

	protected TextMeshGenerator(File metaFile) {
		metaData = new MetaFile(metaFile);
	}

	protected void createTextMesh(GUIText text) {
		this.createQuadVertices(text, this.createStructure(text));
	}

	private List<Line> createStructure(GUIText text) {
		char[] chars = text.getTextString().toCharArray();
		List<Line> lines = new ArrayList<Line>();
		Line currentLine = new Line(metaData.getSpaceWidth(), text.getFontSize(), text.getMaxLineSize());
		Word currentWord = new Word(text.getFontSize());
		for (char c : chars) {
			int ascii = (int) c;
			if (ascii == SPACE_ASCII) {
				boolean added = currentLine.attemptToAddWord(currentWord);
				if (!added) {
					lines.add(currentLine);
					currentLine = new Line(metaData.getSpaceWidth(), text.getFontSize(), text.getMaxLineSize());
					currentLine.attemptToAddWord(currentWord);
				}
				currentWord = new Word(text.getFontSize());
				continue;
			}
			Character character = metaData.getCharacter(ascii);
			currentWord.addCharacter(character);
		}
		completeStructure(lines, currentLine, currentWord, text);
		return lines;
	}

	private void completeStructure(List<Line> lines, Line currentLine, Word currentWord, GUIText text) {
		boolean added = currentLine.attemptToAddWord(currentWord);
		if (!added) {
			lines.add(currentLine);
			currentLine = new Line(metaData.getSpaceWidth(), text.getFontSize(), text.getMaxLineSize());
			currentLine.attemptToAddWord(currentWord);
		}
		lines.add(currentLine);
	}

	private void createQuadVertices(GUIText text, List<Line> lines) {
		text.setNumberOfLines(lines.size());
		double curserX = 0f;
		double curserY = 0f;
		List<Float> vertexPositions = new ArrayList<Float>();
		List<Float> VertexTexCoords = new ArrayList<Float>();
		for (Line line : lines) {
			if (text.isCentered()) {
				curserX = (line.getMaxLength() - line.getLineLength()) / 2;
			}
			for (Word word : line.getWords()) {
				for (Character letter : word.getCharacters()) {
					addVerticesForCharacter(curserX, curserY, letter, text.getFontSize(), vertexPositions);
					addTexCoords(VertexTexCoords, letter.getxTextureCoord(), letter.getyTextureCoord(), letter.getXMaxTextureCoord(), letter.getYMaxTextureCoord());
					curserX += letter.getxAdvance() * text.getFontSize();
				}
				curserX += metaData.getSpaceWidth() * text.getFontSize();
			}
			curserX = 0;
			curserY += LINE_HEIGHT * text.getFontSize();
		}

		int vaoID = GL30.glGenVertexArrays();
		GL30.glBindVertexArray(vaoID);

		FloatBuffer buffer = BufferUtils.createFloatBuffer(vertexPositions.size() * 2);
		for (int i = 0; i < vertexPositions.size() / 2; i++) {
			buffer.put(vertexPositions.get(i * 2 + 0));
			buffer.put(vertexPositions.get(i * 2 + 1));

			buffer.put(VertexTexCoords.get(i * 2 + 0));
			buffer.put(VertexTexCoords.get(i * 2 + 1));
		}
		buffer.flip();

		int vboID = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);

		GL20.glVertexAttribPointer(0, 2, GL11.GL_FLOAT, false, 4 * Float.BYTES, 0 * Float.BYTES);
		GL20.glVertexAttribPointer(1, 2, GL11.GL_FLOAT, false, 4 * Float.BYTES, 2 * Float.BYTES);

		GL30.glBindVertexArray(0);

		GL15.glDeleteBuffers(vboID);

		text.setMeshInfo(vaoID, vertexPositions.size() / 2);
	}

	private void addVerticesForCharacter(double curserX, double curserY, Character character, double fontSize, List<Float> vertices) {
		double x = curserX + (character.getxOffset() * fontSize);
		double y = curserY + (character.getyOffset() * fontSize);
		double maxX = x + (character.getSizeX() * fontSize);
		double maxY = y + (character.getSizeY() * fontSize);
		double properX = (2 * x) - 1;
		double properY = (-2 * y) + 1;
		double properMaxX = (2 * maxX) - 1;
		double properMaxY = (-2 * maxY) + 1;
		addVertices(vertices, properX, properY, properMaxX, properMaxY);
	}

	private static void addVertices(List<Float> vertices, double x, double y, double maxX, double maxY) {
		vertices.add((float) x);
		vertices.add((float) y);
		vertices.add((float) maxX);
		vertices.add((float) y);
		vertices.add((float) maxX);
		vertices.add((float) maxY);

		vertices.add((float) x);
		vertices.add((float) y);
		vertices.add((float) maxX);
		vertices.add((float) maxY);
		vertices.add((float) x);
		vertices.add((float) maxY);
	}

	private static void addTexCoords(List<Float> texCoords, double x, double y, double maxX, double maxY) {
		texCoords.add((float) x);
		texCoords.add((float) y);
		texCoords.add((float) maxX);
		texCoords.add((float) y);
		texCoords.add((float) maxX);
		texCoords.add((float) maxY);

		texCoords.add((float) x);
		texCoords.add((float) y);
		texCoords.add((float) maxX);
		texCoords.add((float) maxY);
		texCoords.add((float) x);
		texCoords.add((float) maxY);
	}
}
