package laraifox.foxtail.rendering;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL33;
import org.lwjgl.opengl.GL43;

import laraifox.foxtail.core.BufferUtils;
import laraifox.foxtail.core.math.Vector4f;

public class TextureFilter {
	public static final TextureFilter DEFAULT_FILTER = new TextureFilter();

	private int textureFormat;
	private int textureInternalFormat;
	private int depthStencilTextureMode;
	private int textureBaseLevel;
	private Vector4f textureBorderColor;
	private int textureCompareFunc;
	private int textureCompareMode;
	private float textureLODBias;
	private int textureMinFilter;
	private int textureMagFilter;
	private int textureMinLOD;
	private int textureMaxLOD;
	private int textureMaxLevel;
	private int textureSwizzleR;
	private int textureSwizzleG;
	private int textureSwizzleB;
	private int textureSwizzleA;
	private int textureWrapS;
	private int textureWrapT;
	private int textureWrapR;
	private float textureAnisotropy;

	public TextureFilter() {
		this(GL11.GL_RGBA, GL11.GL_RGBA, GL11.GL_DEPTH_COMPONENT, 0, Vector4f.Zero(), GL11.GL_ALWAYS, GL11.GL_NONE, 0.0f, //
				GL11.GL_LINEAR, GL11.GL_LINEAR, -1000, 1000, 1000, GL11.GL_RED, GL11.GL_GREEN, GL11.GL_BLUE, //
				GL11.GL_ALPHA, GL11.GL_REPEAT, GL11.GL_REPEAT, GL11.GL_REPEAT, 0.0f);
	}

	public TextureFilter(int textureFormat, int textureInternalFormat, int depthStencilTextureMode, int textureBaseLevel, Vector4f textureBorderColor, int textureCompareFunc,
			int textureCompareMode, float textureLODBias, int textureMinFilter, int textureMagFilter, int textureMinLOD, int textureMaxLOD, int textureMaxLevel, int textureSwizzleR,
			int textureSwizzleG, int textureSwizzleB, int textureSwizzleA, int textureWrapS, int textureWrapT, int textureWrapR, float textureAnisotropy) {
		this.textureFormat = textureFormat;
		this.textureInternalFormat = textureInternalFormat;
		this.depthStencilTextureMode = depthStencilTextureMode;
		this.textureBaseLevel = textureBaseLevel;
		this.textureBorderColor = textureBorderColor;
		this.textureCompareFunc = textureCompareFunc;
		this.textureCompareMode = textureCompareMode;
		this.textureLODBias = textureLODBias;
		this.textureMinFilter = textureMinFilter;
		this.textureMagFilter = textureMagFilter;
		this.textureMinLOD = textureMinLOD;
		this.textureMaxLOD = textureMaxLOD;
		this.textureMaxLevel = textureMaxLevel;
		this.textureSwizzleR = textureSwizzleR;
		this.textureSwizzleG = textureSwizzleG;
		this.textureSwizzleB = textureSwizzleB;
		this.textureSwizzleA = textureSwizzleA;
		this.textureWrapS = textureWrapS;
		this.textureWrapT = textureWrapT;
		this.textureWrapR = textureWrapR;
		this.textureAnisotropy = textureAnisotropy;
	}

	public void apply(int target) {
		GL11.glTexParameteri(target, GL43.GL_DEPTH_STENCIL_TEXTURE_MODE, depthStencilTextureMode);
		GL11.glTexParameteri(target, GL12.GL_TEXTURE_BASE_LEVEL, textureBaseLevel);
		GL11.glTexParameter(target, GL11.GL_TEXTURE_BORDER_COLOR, BufferUtils.createFloatBuffer(textureBorderColor, true));
		GL11.glTexParameteri(target, GL14.GL_TEXTURE_COMPARE_FUNC, textureCompareFunc);
		GL11.glTexParameteri(target, GL14.GL_TEXTURE_COMPARE_MODE, textureCompareMode);
		GL11.glTexParameterf(target, GL14.GL_TEXTURE_LOD_BIAS, textureLODBias);
		GL11.glTexParameteri(target, GL11.GL_TEXTURE_MIN_FILTER, textureMinFilter);
		GL11.glTexParameteri(target, GL11.GL_TEXTURE_MAG_FILTER, textureMagFilter);
		GL11.glTexParameteri(target, GL12.GL_TEXTURE_MIN_LOD, textureMinLOD);
		GL11.glTexParameteri(target, GL12.GL_TEXTURE_MAX_LOD, textureMaxLOD);
		GL11.glTexParameteri(target, GL12.GL_TEXTURE_MAX_LEVEL, textureMaxLevel);
		GL11.glTexParameteri(target, GL33.GL_TEXTURE_SWIZZLE_R, textureSwizzleR);
		GL11.glTexParameteri(target, GL33.GL_TEXTURE_SWIZZLE_G, textureSwizzleG);
		GL11.glTexParameteri(target, GL33.GL_TEXTURE_SWIZZLE_B, textureSwizzleB);
		GL11.glTexParameteri(target, GL33.GL_TEXTURE_SWIZZLE_A, textureSwizzleA);
		GL11.glTexParameteri(target, GL11.GL_TEXTURE_WRAP_S, textureWrapS);
		GL11.glTexParameteri(target, GL11.GL_TEXTURE_WRAP_T, textureWrapT);
		GL11.glTexParameteri(target, GL12.GL_TEXTURE_WRAP_R, textureWrapR);
	}

	@Override
	public String toString() {
		StringBuilder buider = new StringBuilder();

		buider.append("[ ");
		buider.append(depthStencilTextureMode + ", ");
		buider.append(textureBaseLevel + ", ");
		buider.append(textureBorderColor.toString() + ", ");
		buider.append(textureCompareFunc + ", ");
		buider.append(depthStencilTextureMode + ", ");
		buider.append(textureCompareMode + ", ");
		buider.append(textureLODBias + ", ");
		buider.append(textureMinFilter + ", ");
		buider.append(textureMagFilter + ", ");
		buider.append(textureMinLOD + ", ");
		buider.append(textureMaxLOD + ", ");
		buider.append(textureMaxLevel + ", ");
		buider.append(textureSwizzleR + ", ");
		buider.append(textureSwizzleG + ", ");
		buider.append(textureSwizzleB + ", ");
		buider.append(textureSwizzleA + ", ");
		buider.append(textureWrapS + ", ");
		buider.append(textureWrapT + ", ");
		buider.append(textureWrapR);
		buider.append(" ]");

		return buider.toString();
	}

	public int getTextureFormat() {
		return textureFormat;
	}

	public int getTextureInternalFormat() {
		return textureInternalFormat;
	}

	public int getDepthStencilTextureMode() {
		return depthStencilTextureMode;
	}

	public int getTextureBaseLevel() {
		return textureBaseLevel;
	}

	public Vector4f getTextureBorderColor() {
		return textureBorderColor;
	}

	public int getTextureCompareFunc() {
		return textureCompareFunc;
	}

	public int getTextureCompareMode() {
		return textureCompareMode;
	}

	public float getTextureLODBias() {
		return textureLODBias;
	}

	public int getTextureMinFilter() {
		return textureMinFilter;
	}

	public int getTextureMagFilter() {
		return textureMagFilter;
	}

	public int getTextureMinLOD() {
		return textureMinLOD;
	}

	public int getTextureMaxLOD() {
		return textureMaxLOD;
	}

	public int getTextureMaxLevel() {
		return textureMaxLevel;
	}

	public int getTextureSwizzleR() {
		return textureSwizzleR;
	}

	public int getTextureSwizzleG() {
		return textureSwizzleG;
	}

	public int getTextureSwizzleB() {
		return textureSwizzleB;
	}

	public int getTextureSwizzleA() {
		return textureSwizzleA;
	}

	public int getTextureWrapS() {
		return textureWrapS;
	}

	public int getTextureWrapT() {
		return textureWrapT;
	}

	public int getTextureWrapR() {
		return textureWrapR;
	}

	public float getTextureAnisotropy() {
		return textureAnisotropy;
	}

	public TextureFilter setTextureFormat(int textureFormat) {
		this.textureFormat = textureFormat;
		return this;
	}

	public TextureFilter setTextureInternalFormat(int textureInternalFormat) {
		this.textureInternalFormat = textureInternalFormat;
		return this;
	}

	public TextureFilter setDepthStencilTextureMode(int glDepthStencilTextureMode) {
		this.depthStencilTextureMode = glDepthStencilTextureMode;
		return this;
	}

	public TextureFilter setTextureBaseLevel(int textureBaseLevel) {
		this.textureBaseLevel = textureBaseLevel;
		return this;
	}

	public TextureFilter setTextureBorderColor(Vector4f textureBorderColor) {
		this.textureBorderColor = textureBorderColor;
		return this;
	}

	public TextureFilter setTextureCompareFunc(int textureCompareFunc) {
		this.textureCompareFunc = textureCompareFunc;
		return this;
	}

	public TextureFilter setTextureCompareMode(int textureCompareMode) {
		this.textureCompareMode = textureCompareMode;
		return this;
	}

	public TextureFilter setTextureLODBias(float textureLODBias) {
		this.textureLODBias = textureLODBias;
		return this;
	}

	public TextureFilter setTextureMinFilter(int textureMinFilter) {
		this.textureMinFilter = textureMinFilter;
		return this;
	}

	public TextureFilter setTextureMagFilter(int textureMagFilter) {
		this.textureMagFilter = textureMagFilter;
		return this;
	}

	public TextureFilter setTextureMinLOD(int textureMinLOD) {
		this.textureMinLOD = textureMinLOD;
		return this;
	}

	public TextureFilter setTextureMaxLOD(int textureMaxLOD) {
		this.textureMaxLOD = textureMaxLOD;
		return this;
	}

	public TextureFilter setTextureMaxLevel(int textureMaxLevel) {
		this.textureMaxLevel = textureMaxLevel;
		return this;
	}

	public TextureFilter setTextureSwizzleR(int textureSwizzleR) {
		this.textureSwizzleR = textureSwizzleR;
		return this;
	}

	public TextureFilter setTextureSwizzleG(int textureSwizzleG) {
		this.textureSwizzleG = textureSwizzleG;
		return this;
	}

	public TextureFilter setTextureSwizzleB(int textureSwizzleB) {
		this.textureSwizzleB = textureSwizzleB;
		return this;
	}

	public TextureFilter setTextureSwizzleA(int textureSwizzleA) {
		this.textureSwizzleA = textureSwizzleA;
		return this;
	}

	public TextureFilter setTextureWrapS(int textureWrapS) {
		this.textureWrapS = textureWrapS;
		return this;
	}

	public TextureFilter setTextureWrapT(int textureWrapT) {
		this.textureWrapT = textureWrapT;
		return this;
	}

	public TextureFilter setTextureWrapR(int textureWrapR) {
		this.textureWrapR = textureWrapR;
		return this;
	}

	public TextureFilter setTextureAnisotropy(float textureAnisotropy) {
		this.textureAnisotropy = textureAnisotropy;
		return this;
	}
}
