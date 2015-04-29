package net.laraifox.libdemo;

import net.laraifox.libdemo.math.Vector3f;

import org.lwjgl.LWJGLException;

public class LibDemoBoot {
	public static void main(String[] args) throws LWJGLException {
		System.out.println(Vector3f.cross(Vector3f.Forward(), Vector3f.Forward()).toString());

		// OpenGLDisplay display = new GraphicsDemo();
		// display.initialize();
		// display.start();
	}
}
