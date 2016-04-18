package laraifox.foxtail.testing;

import laraifox.foxtail.core.Logger;
import laraifox.foxtail.core.OpenGLDisplay;
import laraifox.foxtail.testing.shadersandbox.ShaderSandbox;

public class FoxtailEngineBoot {
	public static void main(String[] args) {
		Logger.initialize(Logger.MESSAGE_LEVEL_DEBUG, false);

//		Logger.log(Integer.toHexString(new Color3f(1.0f, 1.0f, 0.5f).getHexColor()).toUpperCase(), Logger.MESSAGE_LEVEL_DEBUG);

		OpenGLDisplay display = new OpenGLDisplay(1200, 900, new ShaderSandbox());
		display.start();
	}
}
