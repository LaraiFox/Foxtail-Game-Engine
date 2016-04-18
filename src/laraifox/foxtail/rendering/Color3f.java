package laraifox.foxtail.rendering;

import org.lwjgl.opengl.GL11;

import laraifox.foxtail.core.math.MathUtils;
import laraifox.foxtail.core.math.Vector3f;

public class Color3f {
	/***
	 * Numeric index of the Red component of the color.
	 */
	public static final int COMPONENT_INDEX_RED = 0;
	/***
	 * Numeric index of the Green component of the color.
	 */
	public static final int COMPONENT_INDEX_GREEN = 1;
	/***
	 * Numeric index of the Blue component of the color.
	 */
	public static final int COMPONENT_INDEX_BLUE = 2;
	/***
	 * The number of components in the color.
	 */
	public static final int COMPONENT_COUNT = 3;
	/***
	 * The total size in bytes of all components in the color.
	 */
	public static final int BYTE_COUNT = COMPONENT_COUNT * Float.BYTES;

	private float r, g, b;

	public Color3f() {
		this(0.0f, 0.0f, 0.0f);
	}

	public Color3f(float value) {
		this(value, value, value);
	}

	public Color3f(Vector3f vector) {
		this(vector.getX(), vector.getY(), vector.getZ());
	}

	public Color3f(float r, float g, float b) {
		this.r = r;
		this.g = g;
		this.b = b;
	}

	public Color3f(Color3f color) {
		this(color.getRed(), color.getGreen(), color.getBlue());
	}

	public void glBindColor() {
		GL11.glColor3f(this.r, this.g, this.b);
	}

	public Color3f add(Color3f color) {
		return this.add(color.getRed(), color.getGreen(), color.getBlue());
	}

	public Color3f add(float r, float g, float b) {
		this.r += r;
		this.g += g;
		this.b += b;

		return this;
	}

	public Color3f subtract(Color3f color) {
		return this.subtract(color.getRed(), color.getGreen(), color.getBlue());
	}

	public Color3f subtract(float r, float g, float b) {
		this.r -= r;
		this.g -= g;
		this.b -= b;

		return this;
	}

	public Color3f multiply(Color3f color) {
		return this.multiply(color.getRed(), color.getGreen(), color.getBlue());
	}

	public Color3f multiply(float r, float g, float b) {
		this.r *= r;
		this.g *= g;
		this.b *= b;

		return this;
	}

	public Color3f divide(Color3f color) {
		return this.divide(color.getRed(), color.getGreen(), color.getBlue());
	}

	public Color3f divide(float r, float g, float b) {
		this.r /= r;
		this.g /= g;
		this.b /= b;

		return this;
	}

	public Color3f clamp() {
		this.r = MathUtils.clamp(this.r, 0.0f, 1.0f);
		this.g = MathUtils.clamp(this.g, 0.0f, 1.0f);
		this.b = MathUtils.clamp(this.b, 0.0f, 1.0f);

		return this;
	}

	@Override
	public boolean equals(Object object) {
		if (object instanceof Color3f) {
			return this.isEqual((Color3f) object);
		}

		return false;
	}

	public boolean isEqual(Color3f color) {
		return (this.r == color.getRed() && this.g == color.getGreen() && this.b == color.getBlue());
	}

	@Override
	public String toString() {
		return new String("[ " + r + ", " + g + ", " + b + " ]");
	}

	public String toHexString() {
		return new String("0x" + Integer.toHexString(this.getHexColor()));
	}

	public int getHexColor() {
		return 0xFF000000 | (((int) (this.r * 0xFF)) << 16) | (((int) (this.r * 0xFF)) << 8) | ((int) (this.b * 0xFF));
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

	public void setRed(float r) {
		this.r = r;
	}

	public void setGreen(float g) {
		this.g = g;
	}

	public void setBlue(float b) {
		this.b = b;
	}
}
