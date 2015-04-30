package net.laraifox.libdev;

import net.laraifox.lib.display.OpenGLDisplay;
import net.laraifox.libdev._demos.GraphicsDemo;

import org.lwjgl.LWJGLException;

public class LibDemoBoot {
	public static void main(String[] args) throws LWJGLException {
		// System.out.println(Vector3f.cross(Vector3f.Forward(), Vector3f.Forward()).toString());

		OpenGLDisplay display = new GraphicsDemo();
		display.initialize();
		display.start();
	}
}
