package net.laraifox.libdev;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.GL11;

import net.laraifox.lib.display.OpenGLDisplay;
import net.laraifox.libdev.math.Vector3f;

public class LibDemoBoot extends OpenGLDisplay {
	Vector3f lerp = new Vector3f(-1, 0.5f, 0);
	Vector3f nlerp = new Vector3f(-1, 0.25f, 0);
	Vector3f slerp = new Vector3f(-1, 0, 0);
	Vector3f movto = new Vector3f(-1, -0.5f, 0);
	
	public static void main(String[] args) throws LWJGLException {
		LibDemoBoot test = new LibDemoBoot();
		test.initialize();
		test.start();
		//		testFunction();

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

	@Override
	protected void cleanUp() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void initializeVariables() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void render() {
//		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
		
		GL11.glPointSize(2.0f);

		GL11.glBegin(GL11.GL_POINTS);
		GL11.glVertex2f(lerp.getX(), lerp.getY());
		GL11.glVertex2f(nlerp.getX(), nlerp.getY());
		GL11.glVertex2f(slerp.getX(), slerp.getY());
		GL11.glVertex2f(movto.getX(), movto.getY());
		GL11.glEnd();
	}

	@Override
	protected void tick() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void update(float arg0) {
		lerp.lerp(new Vector3f(1, 0.5f, 0), 0.01f);
		nlerp.nlerp(new Vector3f(1, 0.25f, 0), 0.01f);
		slerp.slerp(new Vector3f(1, 0.0f, 0), 0.01f);
		movto.moveTowards(new Vector3f(1, -0.5f, 0), 0.01f);
	}
}
