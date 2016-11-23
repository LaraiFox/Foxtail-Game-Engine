package laraifox.foxtail.core.gui;

import laraifox.foxtail.core.Transform2D;
import laraifox.foxtail.core.math.Vector2f;
import laraifox.foxtail.core.math.Vector4f;
import laraifox.foxtail.rendering.Texture2D;

public abstract class GUIComponent {
	private GUIComponent parent;
	private Transform2D transform;
	private String name;
	private Vector4f color;
	private Texture2D texture;
	// private AABBCollider2D bounds;
	private String toolTipText;
	private boolean visible;

	public GUIComponent() {

	}

	public void render() {

	}

	public GUIComponent getParent() {
		return this.parent;
	}

	public void setParent(GUIComponent parent) {
		this.parent = parent;
	}

	public Transform2D getTransform() {
		return transform;
	}

	public void setTransform(Transform2D transform) {
		this.transform = transform;
	}

	public Vector2f getPosition() {
		return transform.getTranslation();
	}

	public void setPosition(Vector2f position) {
		this.transform.setTranslation(position);
	}

	public float getX() {
		return transform.getTranslation().getX();
	}

	public float getY() {
		return transform.getTranslation().getY();
	}

	public float getRotation() {
		return transform.getRotation();
	}

	public void setRotation(float rotation) {
		this.transform.setRotation(rotation);
	}

	public Vector2f getSize() {
		return transform.getScale();
	}

	public void setSize(Vector2f size) {
		this.transform.setScale(size);
	}

	public float getWidth() {
		return transform.getScale().getX();
	}

	public float getHeight() {
		return transform.getScale().getY();
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Vector4f getOverlayColor() {
		return color;
	}

	public void setOverlayColor(Vector4f overlayColor) {
		this.color = overlayColor;
	}

	public Texture2D getBackgroundTexture() {
		return texture;
	}

	public void setBackgroundTexture(Texture2D backgroundTexture) {
		this.texture = backgroundTexture;
	}

	public String getToolTipText() {
		return toolTipText;
	}

	public void setToolTipText(String toolTipText) {
		this.toolTipText = toolTipText;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}
}
