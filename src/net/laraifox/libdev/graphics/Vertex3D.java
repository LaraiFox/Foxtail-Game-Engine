package net.laraifox.libdev.graphics;

import net.laraifox.libdev.math.Vector2f;
import net.laraifox.libdev.math.Vector3f;

import org.lwjgl.opengl.GL11;

public class Vertex3D {
	public static final int SIZE = 8;

	private Vector3f position;
	private Vector2f texCoord;
	private Vector3f normal;

	public Vertex3D() {
		this(Vector3f.Zero(), Vector2f.Zero(), Vector3f.Zero());
	}

	public Vertex3D(Vector3f position) {
		this(position, Vector2f.Zero(), Vector3f.Zero());
	}

	public Vertex3D(Vector3f position, Vector2f textureCoord) {
		this(position, textureCoord, Vector3f.Zero());
	}

	public Vertex3D(Vector3f position, Vector2f textureCoord, Vector3f normal) {
		this.position = position;
		this.texCoord = textureCoord;
		this.normal = normal;
	}

	public void drawGLVertex() {
		GL11.glVertex3f(position.getX(), position.getY(), position.getZ());
	}

	public void drawGLVertexWithUV() {
		GL11.glTexCoord2f(texCoord.getX(), texCoord.getY());
		GL11.glVertex3f(position.getX(), position.getY(), position.getZ());
	}

	public void drawGLVertexWithNormal() {
		GL11.glNormal3f(normal.getX(), normal.getY(), normal.getZ());
		GL11.glVertex3f(position.getX(), position.getY(), position.getZ());
	}

	public void drawGLVertexWithUVAndNormal() {
		GL11.glNormal3f(normal.getX(), normal.getY(), normal.getZ());
		GL11.glTexCoord2f(texCoord.getX(), texCoord.getY());
		GL11.glVertex3f(position.getX(), position.getY(), position.getZ());
	}

	public Vector3f getPosition() {
		return position;
	}

	public void setPosition(Vector3f position) {
		this.position = position;
	}

	public Vector2f getTextureCoord() {
		return texCoord;
	}

	public void setTextureCoord(Vector2f texCoord) {
		this.texCoord = texCoord;
	}

	public Vector3f getNormal() {
		return normal;
	}

	public void setNormal(Vector3f normal) {
		this.normal = normal;
	}
}
