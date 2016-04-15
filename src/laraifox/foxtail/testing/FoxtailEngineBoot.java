package laraifox.foxtail.testing;

import laraifox.foxtail.core.OpenGLDisplay;
import laraifox.foxtail.testing.shadersandbox.ShaderSandbox;

public class FoxtailEngineBoot {
	public static void main(String[] args) {
		OpenGLDisplay display = new OpenGLDisplay(1200, 900, new ShaderSandbox());
		display.start();
	}
}
