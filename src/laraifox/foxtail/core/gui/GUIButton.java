package laraifox.foxtail.core.gui;

import laraifox.foxtail.core.IMouseClickListener;
import laraifox.foxtail.core.IMouseMovementListener;
import laraifox.foxtail.core.math.Vector4f;
import laraifox.foxtail.rendering.Texture2D;

public abstract class GUIButton extends GUILabel implements IMouseClickListener, IMouseMovementListener {
	private Texture2D hoveredTexture, clickedTexture;
	private Vector4f hoveredColor, clickedColor;
	private boolean hovered, clicked;

	public GUIButton() {}

	public void onButtonClicked() {}

	public void onMouseMoved(float x, float y, float dx, float dy) {
		// this.hovered = bounds.contains(x, y);
	}

	public void onMouseButtonPressed(int button, float x, float y) {
		// if (bounds.contains(x, y)) {
		// clicked = true;
		// }
	}

	public void onMouseButtonReleased(int button, float x, float y) {
		// if (bounds.contains(x, y) && clicked) {
		// this.onButtonClicked();
		// }
	}
}
