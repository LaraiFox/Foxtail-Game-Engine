package laraifox.foxtail.rendering;

import org.lwjgl.opengl.GL11;

import laraifox.foxtail.core.math.MathUtils;
import laraifox.foxtail.core.math.Vector4f;

public class Color4f {
	/***
	 * Numeric index of the X component of the color.
	 */
	public static final int COMPONENT_INDEX_RED = 0;
	/***
	 * Numeric index of the Y component of the color.
	 */
	public static final int COMPONENT_INDEX_GREEN = 1;
	/***
	 * Numeric index of the Z component of the color.
	 */
	public static final int COMPONENT_INDEX_BLUE = 2;
	/***
	 * Numeric index of the W component of the color.
	 */
	public static final int COMPONENT_INDEX_ALPHA = 3;

	/***
	 * The number of components in the color.
	 */
	public static final int COMPONENT_COUNT = 4;
	/***
	 * The total size in bytes of all components in the color.
	 */
	public static final int BYTE_COUNT = COMPONENT_COUNT * Float.BYTES;

	private float r, g, b, a;

	/**
	 * Constructs a new color setting all components to zero.
	 */
	public Color4f() {
		this(0.0f, 0.0f, 0.0f, 0.0f);
	}

	public Color4f(float value) {
		this(value, value, value, value);
	}

	public Color4f(float r, float g, float b, float a) {
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
	}

	public Color4f(Vector4f vector) {
		this(vector.getX(), vector.getY(), vector.getZ(), vector.getW());
	}

	public Color4f(Color4f color) {
		this(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
	}

	public void bind() {
		GL11.glColor4f(this.r, this.g, this.b, this.a);
	}

	public Color4f add(Color4f color) {
		return this.add(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
	}

	public Color4f add(float r, float g, float b, float a) {
		this.r += r;
		this.g += g;
		this.b += b;
		this.a += a;

		return this;
	}

	public Color4f subtract(Color4f color) {
		return this.subtract(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
	}

	public Color4f subtract(float r, float g, float b, float a) {
		this.r -= r;
		this.g -= g;
		this.b -= b;
		this.a -= a;

		return this;
	}

	public Color4f multiply(Color4f color) {
		return this.multiply(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
	}

	public Color4f multiply(float r, float g, float b, float a) {
		this.r *= r;
		this.g *= g;
		this.b *= b;
		this.a *= a;

		return this;
	}

	public Color4f divide(Color4f color) {
		return this.divide(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
	}

	public Color4f divide(float r, float g, float b, float a) {
		this.r /= r;
		this.g /= g;
		this.b /= b;
		this.a /= a;

		return this;
	}

	public Color4f clamp() {
		this.r = MathUtils.clamp(this.r, 0.0f, 1.0f);
		this.g = MathUtils.clamp(this.g, 0.0f, 1.0f);
		this.b = MathUtils.clamp(this.b, 0.0f, 1.0f);
		this.a = MathUtils.clamp(this.a, 0.0f, 1.0f);

		return this;
	}

	@Override
	public boolean equals(Object object) {
		if (object instanceof Color4f) {
			return this.isEqual((Color4f) object);
		}

		return false;
	}

	public boolean isEqual(Color4f color) {
		return (this.r == color.getRed() && this.g == color.getGreen() && this.b == color.getBlue() && this.a == color.getAlpha());
	}

	@Override
	public String toString() {
		return new String("[ " + r + ", " + g + ", " + b + ", " + a + " ]");
	}

	public String toHexString() {
		return new String("0x" + Integer.toHexString(this.getHexColor()));
	}

	public int getHexColor() {
		return (((int) (this.a * 0xFF)) << 24) | (((int) (this.r * 0xFF)) << 16) | (((int) (this.g * 0xFF)) << 8) | ((int) (this.b * 0xFF));
	}

	public Color4f get() {
		return new Color4f(this);
	}

	public float get(int i) {
		if (i == COMPONENT_INDEX_RED)
			return r;
		else if (i == COMPONENT_INDEX_GREEN)
			return g;
		else if (i == COMPONENT_INDEX_BLUE)
			return b;
		else if (i == COMPONENT_INDEX_ALPHA)
			return a;
		else
			throw new ArrayIndexOutOfBoundsException("No component exists at index " + i + ".");
	}

	public float getRed() {
		return r;
	}

	public float getGreen() {
		return g;
	}

	public float getBlue() {
		return b;
	}

	public float getAlpha() {
		return a;
	}

	public Color4f set(Color4f color) {
		this.r = color.getRed();
		this.g = color.getGreen();
		this.b = color.getBlue();
		this.a = color.getAlpha();

		return this;
	}

	public Color4f set(int i, float value) {
		if (i == COMPONENT_INDEX_RED)
			this.r = value;
		else if (i == COMPONENT_INDEX_GREEN)
			this.g = value;
		else if (i == COMPONENT_INDEX_BLUE)
			this.b = value;
		else if (i == COMPONENT_INDEX_ALPHA)
			this.a = value;
		else
			throw new ArrayIndexOutOfBoundsException("No component exists at index " + i + ".");

		return this;
	}

	public Color4f setX(float r) {
		this.r = r;

		return this;
	}

	public Color4f setY(float g) {
		this.g = g;

		return this;
	}

	public Color4f setZ(float b) {
		this.b = b;

		return this;
	}

	public Color4f setW(float a) {
		this.a = a;

		return this;
	}

}
