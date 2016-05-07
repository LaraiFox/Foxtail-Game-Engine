package laraifox.foxtail.testing;

import static org.lwjgl.opencl.CL10.CL_DEVICE_TYPE_ACCELERATOR;
import static org.lwjgl.opencl.CL10.CL_DEVICE_TYPE_CPU;
import static org.lwjgl.opencl.CL10.CL_DEVICE_TYPE_DEFAULT;
import static org.lwjgl.opencl.CL10.CL_DEVICE_TYPE_GPU;

import java.text.DecimalFormat;
import java.util.List;

import org.lwjgl.LWJGLException;
import org.lwjgl.opencl.CL;
import org.lwjgl.opencl.CL10;
import org.lwjgl.opencl.CLDevice;
import org.lwjgl.opencl.CLPlatform;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.PixelFormat;

import laraifox.foxtail.core.Logger;
import laraifox.foxtail.core.OpenGLDisplay;
import laraifox.foxtail.testing.shadersandbox.ShaderSandbox;

public class FoxtailEngineBoot {
	private static int MAX_MULTISAMPLES;

	public static void main(String[] args) {
		Logger.initialize(Logger.MESSAGE_LEVEL_DEBUG, false);

		if (FoxtailEngineBoot.class.getResource("FoxtailEngineBoot.class").toString().startsWith("jar")) {
			Logger.log("Program is running from jar file!", "System", Logger.MESSAGE_LEVEL_DEBUG);
		} else {
			Logger.log("Program is running from workspace!", "System", Logger.MESSAGE_LEVEL_DEBUG);
			//			AssetLoader.useIndevPrefix = true;
		}

		Logger.lineBreak();
		Logger.flush(true);

		try {
			System.setProperty("org.lwjgl.opengl.Window.undecorated", "true");

			Display.setDisplayMode(new DisplayMode(0, 0));
			Display.setFullscreen(false);
			Display.create();

			MAX_MULTISAMPLES = GL11.glGetInteger(GL30.GL_MAX_SAMPLES);

			Display.destroy();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}

		try {
			CL.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}

		for (int platformIndex = 0; platformIndex < CLPlatform.getPlatforms().size(); platformIndex++) {
			CLPlatform platform = CLPlatform.getPlatforms().get(platformIndex);
			Logger.log("\t\tPlatform #" + platformIndex + ": " + platform.getInfoString(CL10.CL_PLATFORM_NAME), "System", Logger.MESSAGE_LEVEL_DEFAULT);

			List<CLDevice> devices = platform.getDevices(CL10.CL_DEVICE_TYPE_ALL);
			for (int deviceIndex = 0; deviceIndex < devices.size(); deviceIndex++) {
				CLDevice device = devices.get(deviceIndex);

				Logger.log("\tDevice #" + deviceIndex + "(" + getDeviceType(device.getInfoInt(CL10.CL_DEVICE_TYPE)) + "): " + device.getInfoString(CL10.CL_DEVICE_NAME), "System",
						Logger.MESSAGE_LEVEL_DEFAULT);

				Logger.log("Compute Units:  " + device.getInfoInt(CL10.CL_DEVICE_MAX_COMPUTE_UNITS) + " @ " + device.getInfoInt(CL10.CL_DEVICE_MAX_CLOCK_FREQUENCY) + " MHz", "System",
						Logger.MESSAGE_LEVEL_DEFAULT);

				Logger.log("Max Work Group: " + device.getInfoInt(CL10.CL_DEVICE_MAX_WORK_GROUP_SIZE), "System", Logger.MESSAGE_LEVEL_DEFAULT);

				Logger.log("Max Work Group: " + device.getInfoInt(CL10.CL_KERNEL_WORK_GROUP_SIZE), "System", Logger.MESSAGE_LEVEL_DEFAULT);

				Logger.log("Local memory:   " + formatMemory(device.getInfoLong(CL10.CL_DEVICE_LOCAL_MEM_SIZE)), "System", Logger.MESSAGE_LEVEL_DEFAULT);

				Logger.log("Global memory:  " + formatMemory(device.getInfoLong(CL10.CL_DEVICE_GLOBAL_MEM_SIZE)), "System", Logger.MESSAGE_LEVEL_DEFAULT);

				Logger.lineBreak();
			}
		}

		Logger.flush(true);

		// Logger.log(Integer.toHexString(new Color3f(1.0f, 1.0f, 0.5f).getHexColor()).toUpperCase(), Logger.MESSAGE_LEVEL_DEBUG);

		try {
			System.setProperty("org.lwjgl.opengl.Window.undecorated", "false");
			OpenGLDisplay display = new OpenGLDisplay(1200, 900, new ShaderSandbox());
			display.setContextAttribs(new ContextAttribs(3, 2).withForwardCompatible(true).withProfileCore(true));
			display.setPixelFormat(new PixelFormat().withSamples(Math.min(2, MAX_MULTISAMPLES)).withDepthBits(24));
			display.initialize();
			display.start();
		} catch (LWJGLException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	private static String formatMemory(long size) {
		if (size <= 0)
			return "0";
		final String[] units = new String[] {
				"B", "KB", "MB", "GB", "TB"
		};
		int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
		return new DecimalFormat("#,##0.#").format(size / Math.pow(1024, digitGroups)) + " " + units[digitGroups];
	}

	private static String getDeviceType(int i) {
		switch (i) {
		case CL_DEVICE_TYPE_DEFAULT:
			return "DEFAULT";
		case CL_DEVICE_TYPE_CPU:
			return "CPU";
		case CL_DEVICE_TYPE_GPU:
			return "GPU";
		case CL_DEVICE_TYPE_ACCELERATOR:
			return "ACCELERATOR";
		}
		return "?";
	}
}
