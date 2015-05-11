package net.laraifox.libdev;

import net.laraifox.lib.display.OpenGLDisplay;
import net.laraifox.libdev._demos.GraphicsDemo;
import net.laraifox.libdev._old.Vector3f_old;
import net.laraifox.libdev.math.Vector3f;

import org.lwjgl.LWJGLException;

public class LibDemoBoot {
	public static void main(String[] args) throws LWJGLException {
		 System.out.println(new Vector3f_old(0.0000000000001f, 0.0f, 0.0f).length());

//		OpenGLDisplay display = new GraphicsDemo();
//		display.initialize();
//		display.start();
	}
}
