package net.laraifox.libdev;

import net.laraifox.libdev.math.Vector3f;

import org.lwjgl.LWJGLException;

public class LibDemoBoot {
	public static void main(String[] args) throws LWJGLException {
		testFunction();

		// OpenGLDisplay display = new GraphicsDemo();
		// display.initialize();
		// display.start();
	}

	private static void testFunction() {
		final float increment = 0.1f;

		float percentComplete = 0.0f;
		float alpha = increment / (1000 - -1000);

		float longestLengthSqN = 0.0f;
		float longestLengthSqP = 0.0f;
		float longestLengthN = 0.0f;
		float longestLengthP = 0.0f;

		for (float i = -1000; i < 1000; i += increment) {
			for (float j = -1000; j < 1000; j += increment) {
				for (float k = -1000; k < 1000; k += increment) {
					Vector3f vector = new Vector3f(k, j, i);

					if (!vector.isZero()) {
						vector.normalize();

						float lengthSq = vector.lengthSq() - 1.0f;
						float length = vector.length() - 1.0f;

						longestLengthSqN = Math.min(longestLengthSqN, lengthSq);
						longestLengthSqP = Math.max(longestLengthSqP, lengthSq);
						longestLengthN = Math.min(longestLengthN, length);
						longestLengthP = Math.max(longestLengthP, length);
					}
				}
			}

			percentComplete += alpha;
			System.out.println((percentComplete * 100.0f) + "% Complete");
		}

		System.out.println("");
		System.out.println(longestLengthSqN + ", " + longestLengthSqP + "  Sq Lengths");
		System.out.println(longestLengthN + ", " + longestLengthP + "  Lengths");
	}
}
