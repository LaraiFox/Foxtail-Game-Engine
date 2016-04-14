package laraifox.foxtail.testing;

import laraifox.foxtail.core.OpenGLDisplay;

public class FoxtailEngineBoot {
	public static void main(String[] args) {
		OpenGLDisplay display = new OpenGLDisplay(new ShaderSandbox());
		display.start();
	}
}
