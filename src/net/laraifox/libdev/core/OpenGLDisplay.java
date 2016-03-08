package net.laraifox.lib.display;

import net.laraifox.lib.graphics.OrthographicProjection;
import net.laraifox.lib.math.MathHelper;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.PixelFormat;

public class ProgramDisplay {
	private static final String DEFAULT_TITLE = new String("OpenGL Display");

	protected String title;
	protected float width, height;
	protected boolean isFullscreen;
	protected boolean isResizable;
	protected boolean isVSyncEnabled;

	protected PixelFormat pixelFormat;
	protected ContextAttribs contextAttribs;

	protected OrthographicProjection orthographicProjection;

	private boolean isInitialized;
	private boolean isRunning;

	private int framerate, updaterate, tickrate;
	private int updates, ups;
	private int frames, fps;

	public OpenGLDisplay() {
		this(DEFAULT_TITLE, 800, 600, false, false, 60, 60, 1);
	}

	public OpenGLDisplay(String title) {
		this(title, 800, 600, false, false, 60, 60, 1);
	}

	public OpenGLDisplay(float width, float height) {
		this(DEFAULT_TITLE, width, height, false, false, 60, 60, 1);
	}

	public OpenGLDisplay(String title, float width, float height) {
		this(title, width, height, false, false, 60, 60, 1);
	}

	public OpenGLDisplay(String title, float width, float height, boolean fullscreen) {
		this(title, width, height, fullscreen, false, 60, 60, 1);
	}

	public OpenGLDisplay(String title, float width, float height, boolean fullscreen, boolean vSync) {
		this(title, width, height, fullscreen, vSync, 60, 60, 1);
	}

	public OpenGLDisplay(String title, float width, float height, boolean fullscreen, boolean vSync, int framerate) {
		this(title, width, height, fullscreen, vSync, framerate, 60, 1);
	}

	public OpenGLDisplay(String title, float width, float height, boolean fullscreen, boolean vSync, int framerate, int updaterate) {
		this(title, width, height, fullscreen, vSync, framerate, updaterate, 1);
	}

	public OpenGLDisplay(String title, float width, float height, boolean fullscreen, boolean vSync, int framerate, int updaterate, int tickrate) {
		this.title = title;
		this.width = width;
		this.height = height;
		this.isFullscreen = fullscreen;
		this.isResizable = false;
		this.isVSyncEnabled = vSync;

		this.pixelFormat = new PixelFormat();
		this.contextAttribs = new ContextAttribs();

		this.orthographicProjection = new OrthographicProjection();

		this.isInitialized = false;
		this.isRunning = false;

		this.framerate = framerate;
		this.updaterate = updaterate;
		this.tickrate = tickrate;
		this.updates = 0;
		this.frames = 0;
		this.ups = 0;
		this.fps = 0;
	}

	public final void initialize() throws LWJGLException {
		if (isInitialized)
			return;

		isInitialized = true;
		initializeDisplay();
		initializeOpenGL();
		initializeVariables();
	}

	protected void initializeDisplay() throws LWJGLException {
		Display.setTitle(title);
		Display.setDisplayMode((new DisplayMode((int) width, (int) height)));
		Display.setFullscreen(isFullscreen);
		Display.setResizable(isResizable);
		Display.setVSyncEnabled(isVSyncEnabled);
		Display.create(pixelFormat, contextAttribs);
	}

	protected void initializeOpenGL() {
		initializeDefaultGLProjection();
		initializeOpenGLDefaults();
	}

	protected final void initializeDefaultGLProjection() {
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		orthographicProjection.glApplyProjection();
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
	}

	protected final void initializeOpenGLDefaults() {
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

		GL11.glDisable(GL11.GL_DEPTH_TEST);

		GL11.glEnable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
	}

	protected abstract void initializeVariables();

	public final void start() {
		if (isRunning)
			return;

		if (isInitialized) {
			isRunning = true;
			gameLoop();
		}
	}

	public final void stop() {
		if (!isRunning)
			return;

		isRunning = false;
	}

	private final void gameLoop() {
		long previousDeltaTime = System.nanoTime();
		long previousTick = System.nanoTime();
		long previousUpdate = System.nanoTime();
		long nanosecondsPerTick = (long) ((float) MathHelper.MILLIARD / (float) tickrate);
		long nanosecondsPerUpdate = (long) ((float) MathHelper.MILLIARD / (float) updaterate);

		while (!Display.isCloseRequested() && isRunning) {
			long currentTime = System.nanoTime();

			if (currentTime - previousTick >= nanosecondsPerTick) {
				previousTick += nanosecondsPerTick;
				ups = (int) (updates / (1.0f / tickrate));
				updates = 0;
				fps = (int) (frames / (1.0f / tickrate));
				frames = 0;
				tick();
			}

			if (currentTime - previousUpdate >= nanosecondsPerUpdate) {
				previousUpdate += nanosecondsPerUpdate;
				float delta = (float) (currentTime - previousDeltaTime) / (float) nanosecondsPerUpdate;
				previousDeltaTime = currentTime;
				update(delta);
				updates++;
			}

			render();
			frames++;

			Display.update();
			Display.sync(framerate);
		}

		isInitialized = false;
		cleanUp();
		Display.destroy();
	}

	protected abstract void cleanUp();

	protected abstract void tick();

	protected abstract void update(float delta);

	protected abstract void render();

	public final String getTitle() {
		return title;
	}

	public final void setTitle(String title) {
		this.title = title;
	}

	public final float getWidth() {
		return width;
	}

	public final void setWidth(float width) {
		this.width = width;
	}

	public final float getHeight() {
		return height;
	}

	public final void setHeight(float height) {
		this.height = height;
	}

	public final boolean isFullscreen() {
		return isFullscreen;
	}

	public final void setFullscreen(boolean isFullscreen) {
		this.isFullscreen = isFullscreen;
	}

	public final boolean isResizable() {
		return isResizable;
	}

	public final void setResizable(boolean isResizable) {
		this.isResizable = isResizable;
	}

	public final boolean isVSyncEnabled() {
		return isVSyncEnabled;
	}

	public final void setVSyncEnabled(boolean isVSyncEnabled) {
		this.isVSyncEnabled = isVSyncEnabled;
	}

	public final PixelFormat getPixelFormat() {
		return pixelFormat;
	}

	public final void setPixelFormat(PixelFormat pixelFormat) {
		this.pixelFormat = pixelFormat;
	}

	public final ContextAttribs getContextAttribs() {
		return contextAttribs;
	}

	public final void setContextAttribs(ContextAttribs contextAttribs) {
		this.contextAttribs = contextAttribs;
	}

	public final OrthographicProjection getOrthographicProjection() {
		return orthographicProjection;
	}

	public final void setOrthographicProjection(OrthographicProjection orthographicProjection) {
		this.orthographicProjection = orthographicProjection;
	}

	public final boolean isInitialized() {
		return isInitialized;
	}

	public final void setInitialized(boolean isInitialized) {
		this.isInitialized = isInitialized;
	}

	public final boolean isRunning() {
		return isRunning;
	}

	public final void setRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}

	public final int getFramerate() {
		return framerate;
	}

	public final void setFramerate(int framerate) {
		this.framerate = framerate;
	}

	public final int getUpdaterate() {
		return updaterate;
	}

	public final void setUpdaterate(int updaterate) {
		this.updaterate = updaterate;
	}

	public final int getCurrentUPS() {
		return ups;
	}

	public final int getCurrentFPS() {
		return fps;
	}
}
