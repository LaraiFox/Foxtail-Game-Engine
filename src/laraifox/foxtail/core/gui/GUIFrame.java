package laraifox.foxtail.core.gui;

import java.util.ArrayList;
import java.util.List;

import laraifox.foxtail.core.IMouseClickListener;
import laraifox.foxtail.core.IMouseInputListener;
import laraifox.foxtail.core.IMouseMovementListener;
import laraifox.foxtail.core.IMouseWheelListener;
import laraifox.foxtail.core.IUpdatable;
import laraifox.foxtail.core.InputHandler;
import laraifox.foxtail.core.Transform2D;
import laraifox.foxtail.core.math.Vector2f;
import laraifox.foxtail.core.math.Vector4f;
import laraifox.foxtail.rendering.Texture2D;

public class GUIFrame implements IUpdatable {
	private Transform2D transform;
	private Vector4f color;
	private Texture2D texture;
	// private AABBCollider2D bounds;
	private String toolTipText;
	private boolean visible;

	private List<IMouseClickListener> mouseClickListeners;
	private List<IMouseMovementListener> mouseMovementListeners;
	private List<IMouseWheelListener> mouseWheelListeners;

	public GUIFrame() {
		this.mouseClickListeners = new ArrayList<IMouseClickListener>();
		this.mouseMovementListeners = new ArrayList<IMouseMovementListener>();
		this.mouseWheelListeners = new ArrayList<IMouseWheelListener>();
	}

	public boolean addInputListener(IMouseClickListener listener) {
		return this.mouseClickListeners.add(listener);
	}

	public boolean addInputListener(IMouseMovementListener listener) {
		return this.mouseMovementListeners.add(listener);
	}

	public boolean addInputListener(IMouseWheelListener listener) {
		return this.mouseWheelListeners.add(listener);
	}

	public boolean addInputListener(IMouseInputListener listener) {
		return this.mouseClickListeners.add(listener) | this.mouseMovementListeners.add(listener) | this.mouseWheelListeners.add(listener);
	}

	public void update() {
		for (int i = 0; i < InputHandler.MOUSE_BUTTON_COUNT; i++) {
			if (InputHandler.isButtonPressed(i)) {
				for (IMouseClickListener listener : mouseClickListeners) {
					listener.onMouseButtonPressed(i, InputHandler.getMouseX(), InputHandler.getMouseY());
				}
			} else if (InputHandler.isButtonReleased(i)) {
				for (IMouseClickListener listener : mouseClickListeners) {
					listener.onMouseButtonReleased(i, InputHandler.getMouseX(), InputHandler.getMouseY());
				}
			}
		}

		if (InputHandler.getMouseDX() != 0.0f || InputHandler.getMouseDY() != 0.0f) {
			for (IMouseMovementListener listener : mouseMovementListeners) {
				listener.onMouseMoved(InputHandler.getMouseX(), InputHandler.getMouseY(), InputHandler.getMouseDX(), InputHandler.getMouseDY());
			}
		}

		if (InputHandler.getMouseDWheel() != 0.0f) {
			for (IMouseWheelListener listener : mouseWheelListeners) {
				listener.onMouseWheel(InputHandler.getMouseDWheel());
			}
		}
	}

	public void render() {

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
