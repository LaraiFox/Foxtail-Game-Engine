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

	private int glTextureFormat;
	private int glTextureInternalFormat;
	private int glDepthStencilTextureMode;
	private int glTextureBaseLevel;
	private Vector4f glTextureBorderColor;
	private int glTextureCompareFunc;
	private int glTextureCompareMode;
	private float glTextureLODBias;
	private int glTextureMinFilter;
	private int glTextureMagFilter;
	private int glTextureMinLOD;
	private int glTextureMaxLOD;
	private int glTextureMaxLevel;
	private int glTextureSwizzleR;
	private int glTextureSwizzleG;
	private int glTextureSwizzleB;
	private int glTextureSwizzleA;
	private int glTextureWrapS;
	private int glTextureWrapT;
	private int glTextureWrapR;
	private float glTextureAnisotropy;

	public TextureFilter() {
		this(GL11.GL_RGBA, GL11.GL_RGBA, GL11.GL_DEPTH_COMPONENT, 0, Vector4f.Zero(), GL11.GL_ALWAYS, GL11.GL_NONE, 0.0f, //
				GL11.GL_LINEAR, GL11.GL_LINEAR, -1000, 1000, 1000, GL11.GL_RED, GL11.GL_GREEN, GL11.GL_BLUE, //
				GL11.GL_ALPHA, GL11.GL_REPEAT, GL11.GL_REPEAT, GL11.GL_REPEAT, 0.0f);
	}

	public TextureFilter(int glTextureFormat, int glTextureInternalFormat, int glDepthStencilTextureMode, int glTextureBaseLevel, Vector4f glTextureBorderColor, int glTextureCompareFunc,
			int glTextureCompareMode, float glTextureLODBias, int glTextureMinFilter, int glTextureMagFilter, int glTextureMinLOD, int glTextureMaxLOD, int glTextureMaxLevel, int glTextureSwizzleR,
			int glTextureSwizzleG, int glTextureSwizzleB, int glTextureSwizzleA, int glTextureWrapS, int glTextureWrapT, int glTextureWrapR, float glTextureAnisotropy) {
		this.glTextureFormat = glTextureFormat;
		this.glTextureInternalFormat = glTextureInternalFormat;
		this.glDepthStencilTextureMode = glDepthStencilTextureMode;
		this.glTextureBaseLevel = glTextureBaseLevel;
		this.glTextureBorderColor = glTextureBorderColor;
		this.glTextureCompareFunc = glTextureCompareFunc;
		this.glTextureCompareMode = glTextureCompareMode;
		this.glTextureLODBias = glTextureLODBias;
		this.glTextureMinFilter = glTextureMinFilter;
		this.glTextureMagFilter = glTextureMagFilter;
		this.glTextureMinLOD = glTextureMinLOD;
		this.glTextureMaxLOD = glTextureMaxLOD;
		this.glTextureMaxLevel = glTextureMaxLevel;
		this.glTextureSwizzleR = glTextureSwizzleR;
		this.glTextureSwizzleG = glTextureSwizzleG;
		this.glTextureSwizzleB = glTextureSwizzleB;
		this.glTextureSwizzleA = glTextureSwizzleA;
		this.glTextureWrapS = glTextureWrapS;
		this.glTextureWrapT = glTextureWrapT;
		this.glTextureWrapR = glTextureWrapR;
		this.glTextureAnisotropy = glTextureAnisotropy;
	}

	public void apply(int target) {
		GL11.glTexParameteri(target, GL43.GL_DEPTH_STENCIL_TEXTURE_MODE, glDepthStencilTextureMode);
		GL11.glTexParameteri(target, GL12.GL_TEXTURE_BASE_LEVEL, glTextureBaseLevel);
		GL11.glTexParameter(target, GL11.GL_TEXTURE_BORDER_COLOR, BufferUtils.createFloatBuffer(glTextureBorderColor, true));
		GL11.glTexParameteri(target, GL14.GL_TEXTURE_COMPARE_FUNC, glTextureCompareFunc);
		GL11.glTexParameteri(target, GL14.GL_TEXTURE_COMPARE_MODE, glTextureCompareMode);
		GL11.glTexParameterf(target, GL14.GL_TEXTURE_LOD_BIAS, glTextureLODBias);
		GL11.glTexParameteri(target, GL11.GL_TEXTURE_MIN_FILTER, glTextureMinFilter);
		GL11.glTexParameteri(target, GL11.GL_TEXTURE_MAG_FILTER, glTextureMagFilter);
		GL11.glTexParameteri(target, GL12.GL_TEXTURE_MIN_LOD, glTextureMinLOD);
		GL11.glTexParameteri(target, GL12.GL_TEXTURE_MAX_LOD, glTextureMaxLOD);
		GL11.glTexParameteri(target, GL12.GL_TEXTURE_MAX_LEVEL, glTextureMaxLevel);
		GL11.glTexParameteri(target, GL33.GL_TEXTURE_SWIZZLE_R, glTextureSwizzleR);
		GL11.glTexParameteri(target, GL33.GL_TEXTURE_SWIZZLE_G, glTextureSwizzleG);
		GL11.glTexParameteri(target, GL33.GL_TEXTURE_SWIZZLE_B, glTextureSwizzleB);
		GL11.glTexParameteri(target, GL33.GL_TEXTURE_SWIZZLE_A, glTextureSwizzleA);
		GL11.glTexParameteri(target, GL11.GL_TEXTURE_WRAP_S, glTextureWrapS);
		GL11.glTexParameteri(target, GL11.GL_TEXTURE_WRAP_T, glTextureWrapT);
		GL11.glTexParameteri(target, GL12.GL_TEXTURE_WRAP_R, glTextureWrapR);
	}

	@Override
	public String toString() {
		StringBuilder buider = new StringBuilder();

		buider.append("[ ");
		buider.append(glDepthStencilTextureMode + ", ");
		buider.append(glTextureBaseLevel + ", ");
		buider.append(glTextureBorderColor.toString() + ", ");
		buider.append(glTextureCompareFunc + ", ");
		buider.append(glDepthStencilTextureMode + ", ");
		buider.append(glTextureCompareMode + ", ");
		buider.append(glTextureLODBias + ", ");
		buider.append(glTextureMinFilter + ", ");
		buider.append(glTextureMagFilter + ", ");
		buider.append(glTextureMinLOD + ", ");
		buider.append(glTextureMaxLOD + ", ");
		buider.append(glTextureMaxLevel + ", ");
		buider.append(glTextureSwizzleR + ", ");
		buider.append(glTextureSwizzleG + ", ");
		buider.append(glTextureSwizzleB + ", ");
		buider.append(glTextureSwizzleA + ", ");
		buider.append(glTextureWrapS + ", ");
		buider.append(glTextureWrapT + ", ");
		buider.append(glTextureWrapR);
		buider.append(" ]");

		return buider.toString();
	}

	public int getGLTextureFormat() {
		return glTextureFormat;
	}

	public int getGLTextureInternalFormat() {
		return glTextureInternalFormat;
	}

	public int getGLDepthStencilTextureMode() {
		return glDepthStencilTextureMode;
	}

	public int getGLTextureBaseLevel() {
		return glTextureBaseLevel;
	}

	public Vector4f getGLTextureBorderColor() {
		return glTextureBorderColor;
	}

	public int getGLTextureCompareFunc() {
		return glTextureCompareFunc;
	}

	public int getGLTextureCompareMode() {
		return glTextureCompareMode;
	}

	public float getGLTextureLODBias() {
		return glTextureLODBias;
	}

	public int getGLTextureMinFilter() {
		return glTextureMinFilter;
	}

	public int getGLTextureMagFilter() {
		return glTextureMagFilter;
	}

	public int getGLTextureMinLOD() {
		return glTextureMinLOD;
	}

	public int getGLTextureMaxLOD() {
		return glTextureMaxLOD;
	}

	public int getGLTextureMaxLevel() {
		return glTextureMaxLevel;
	}

	public int getGLTextureSwizzleR() {
		return glTextureSwizzleR;
	}

	public int getGLTextureSwizzleG() {
		return glTextureSwizzleG;
	}

	public int getGLTextureSwizzleB() {
		return glTextureSwizzleB;
	}

	public int getGLTextureSwizzleA() {
		return glTextureSwizzleA;
	}

	public int getGLTextureWrapS() {
		return glTextureWrapS;
	}

	public int getGLTextureWrapT() {
		return glTextureWrapT;
	}

	public int getGLTextureWrapR() {
		return glTextureWrapR;
	}

	public float getGLTextureAnisotropy() {
		return glTextureAnisotropy;
	}

	public TextureFilter setGLTextureFormat(int glTextureFormat) {
		this.glTextureFormat = glTextureFormat;
		return this;
	}

	public TextureFilter setGLTextureInternalFormat(int glTextureInternalFormat) {
		this.glTextureInternalFormat = glTextureInternalFormat;
		return this;
	}

	public TextureFilter setGLDepthStencilTextureMode(int glDepthStencilTextureMode) {
		this.glDepthStencilTextureMode = glDepthStencilTextureMode;
		return this;
	}

	public TextureFilter setGLTextureBaseLevel(int glTextureBaseLevel) {
		this.glTextureBaseLevel = glTextureBaseLevel;
		return this;
	}

	public TextureFilter setGLTextureBorderColor(Vector4f glTextureBorderColor) {
		this.glTextureBorderColor = glTextureBorderColor;
		return this;
	}

	public TextureFilter setGLTextureCompareFunc(int glTextureCompareFunc) {
		this.glTextureCompareFunc = glTextureCompareFunc;
		return this;
	}

	public TextureFilter setGLTextureCompareMode(int glTextureCompareMode) {
		this.glTextureCompareMode = glTextureCompareMode;
		return this;
	}

	public TextureFilter setGLTextureLODBias(float glTextureLODBias) {
		this.glTextureLODBias = glTextureLODBias;
		return this;
	}

	public TextureFilter setGLTextureMinFilter(int glTextureMinFilter) {
		this.glTextureMinFilter = glTextureMinFilter;
		return this;
	}

	public TextureFilter setGLTextureMagFilter(int glTextureMagFilter) {
		this.glTextureMagFilter = glTextureMagFilter;
		return this;
	}

	public TextureFilter setGLTextureMinLOD(int glTextureMinLOD) {
		this.glTextureMinLOD = glTextureMinLOD;
		return this;
	}

	public TextureFilter setGLTextureMaxLOD(int glTextureMaxLOD) {
		this.glTextureMaxLOD = glTextureMaxLOD;
		return this;
	}

	public TextureFilter setGLTextureMaxLevel(int glTextureMaxLevel) {
		this.glTextureMaxLevel = glTextureMaxLevel;
		return this;
	}

	public TextureFilter setGLTextureSwizzleR(int glTextureSwizzleR) {
		this.glTextureSwizzleR = glTextureSwizzleR;
		return this;
	}

	public TextureFilter setGLTextureSwizzleG(int glTextureSwizzleG) {
		this.glTextureSwizzleG = glTextureSwizzleG;
		return this;
	}

	public TextureFilter setGLTextureSwizzleB(int glTextureSwizzleB) {
		this.glTextureSwizzleB = glTextureSwizzleB;
		return this;
	}

	public TextureFilter setGLTextureSwizzleA(int glTextureSwizzleA) {
		this.glTextureSwizzleA = glTextureSwizzleA;
		return this;
	}

	public TextureFilter setGLTextureWrapS(int glTextureWrapS) {
		this.glTextureWrapS = glTextureWrapS;
		return this;
	}

	public TextureFilter setGLTextureWrapT(int glTextureWrapT) {
		this.glTextureWrapT = glTextureWrapT;
		return this;
	}

	public TextureFilter setGLTextureWrapR(int glTextureWrapR) {
		this.glTextureWrapR = glTextureWrapR;
		return this;
	}

	public TextureFilter setGLTextureAnisotropy(float glTextureAnisotropy) {
		this.glTextureAnisotropy = glTextureAnisotropy;
		return this;
	}
}
