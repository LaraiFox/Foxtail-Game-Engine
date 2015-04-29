package net.laraifox.libdemo.collision;

import java.util.ArrayList;
import java.util.Iterator;

import net.laraifox.libdemo.interfaces.ICollidable;

import org.lwjgl.opengl.GL11;

public class QuadTree {
	public static final int SPLIT_XY = 0;
	public static final int SPLIT_XZ = 1;
	public static final int SPLIT_ZY = 2;

	/**
	 * The default maximum number of objects that can be present in a node before the node will attempt to subdivide.
	 */
	public static final int DEFAULT_MAX_OBJECTS = 8;
	/**
	 * The default maximum number of subdivisions that the {@link QuadTree} can have before nodes will stop subdividing.
	 */
	public static final int DEFAULT_MAX_LEVELS = 8;

	private final int SPLIT_PLAIN;

	private int level;
	private AABBCollider bounds;
	private ArrayList<ICollidable> objects;
	private QuadTree[] nodes;

	private int maxObjects, maxLevels;

	public QuadTree(AABBCollider bounds, int splitPlain) {
		this(0, bounds, splitPlain, DEFAULT_MAX_OBJECTS, DEFAULT_MAX_LEVELS);
	}

	public QuadTree(AABBCollider bounds, int splitPlain, int maxObjects, int maxLevels) {
		this(0, bounds, splitPlain, maxObjects, maxLevels);
	}

	private QuadTree(int level, AABBCollider bounds, int splitPlain, int maxObjects, int maxLevels) {
		this.SPLIT_PLAIN = splitPlain;

		this.level = level;
		this.bounds = bounds;
		this.objects = new ArrayList<ICollidable>();
		this.nodes = new QuadTree[4];

		this.maxObjects = maxObjects;
		this.maxLevels = maxLevels;
	}

	public void clear() {
		objects.clear();

		for (int i = 0; i < nodes.length; i++) {
			if (nodes[i] != null) {
				nodes[i].clear();
				nodes[i] = null;
			}
		}
	}

	public void insert(ICollidable object) {
		if (nodes[0] != null) {
			int index = getIndex(object);
			if (index != -1) {
				nodes[index].insert(object);
				return;
			}
		}

		objects.add(object);

		if (objects.size() > maxObjects && level < maxLevels) {
			if (nodes[0] == null) {
				split();
			}

			Iterator<ICollidable> iterator = objects.iterator();
			while (iterator.hasNext()) {
				ICollidable currentObject = iterator.next();
				int index = getIndex(currentObject);
				if (index != -1) {
					nodes[index].insert(currentObject);
					iterator.remove();
				}
			}
		}
	}

	/**
	 * 
	 * @param result
	 *            -
	 * @param object
	 * @return
	 */
	public ArrayList<ICollidable> retrieve(ArrayList<ICollidable> result, ICollidable object) {
		int index = getIndex(object);
		if (index != -1 && nodes[0] != null) {
			nodes[index].retrieve(result, object);
		}

		result.addAll(objects);

		return result;
	}

	public void drawQuadtree() {
		if (level == 0) {
			float color = 1.0f - (1.0f / (maxLevels + 1)) * level;
			GL11.glColor4f(color, color, 1.0f, 0.5f);
			GL11.glBegin(GL11.GL_LINE_LOOP);
			GL11.glVertex2f(bounds.getX(), bounds.getY());
			GL11.glVertex2f(bounds.getX() + bounds.getWidth(), bounds.getY());
			GL11.glVertex2f(bounds.getX() + bounds.getWidth(), bounds.getY() + bounds.getHeight());
			GL11.glVertex2f(bounds.getX(), bounds.getY() + bounds.getHeight());
			GL11.glEnd();
		}

		if (nodes[0] != null) {
			float color = 1.0f - (1.0f / (maxLevels + 1)) * level;
			GL11.glColor4f(color, color, 1.0f, 0.5f);
			GL11.glBegin(GL11.GL_LINES);
			GL11.glVertex2f(bounds.getX() + bounds.getWidth() / 2, bounds.getY());
			GL11.glVertex2f(bounds.getX() + bounds.getWidth() / 2, bounds.getY() + bounds.getHeight());
			GL11.glVertex2f(bounds.getX(), bounds.getY() + bounds.getHeight() / 2);
			GL11.glVertex2f(bounds.getX() + bounds.getWidth(), bounds.getY() + bounds.getHeight() / 2);
			GL11.glEnd();

			nodes[0].drawQuadtree();
			nodes[1].drawQuadtree();
			nodes[2].drawQuadtree();
			nodes[3].drawQuadtree();
		}
	}

	/**
	 * Initializes the four child nodes contained within the current node.
	 */
	private void split() {
		float x = bounds.getX();
		float y = bounds.getY();
		float z = bounds.getZ();
		float width = bounds.getWidth();
		float height = bounds.getHeight();
		float length = bounds.getLength();
		int childLevel = level + 1;

		switch (SPLIT_PLAIN) {
		case SPLIT_XY:
			width = width / 2.0f;
			height = height / 2.0f;

			nodes[0] = new QuadTree(childLevel, new AABBCollider(x - width, y - height, z, width, height, length, null), SPLIT_PLAIN, maxObjects, maxLevels);
			nodes[1] = new QuadTree(childLevel, new AABBCollider(x + width, y - height, z, width, height, length, null), SPLIT_PLAIN, maxObjects, maxLevels);
			nodes[2] = new QuadTree(childLevel, new AABBCollider(x + width, y + height, z, width, height, length, null), SPLIT_PLAIN, maxObjects, maxLevels);
			nodes[3] = new QuadTree(childLevel, new AABBCollider(x - width, y + height, z, width, height, length, null), SPLIT_PLAIN, maxObjects, maxLevels);
			break;
		case SPLIT_XZ:
			width = width / 2.0f;
			length = length / 2.0f;

			nodes[0] = new QuadTree(childLevel, new AABBCollider(x - width, y, z - length, width, height, length, null), SPLIT_PLAIN, maxObjects, maxLevels);
			nodes[1] = new QuadTree(childLevel, new AABBCollider(x + width, y, z - length, width, height, length, null), SPLIT_PLAIN, maxObjects, maxLevels);
			nodes[2] = new QuadTree(childLevel, new AABBCollider(x + width, y, z + length, width, height, length, null), SPLIT_PLAIN, maxObjects, maxLevels);
			nodes[3] = new QuadTree(childLevel, new AABBCollider(x - width, y, z + length, width, height, length, null), SPLIT_PLAIN, maxObjects, maxLevels);
			break;
		case SPLIT_ZY:
			height = height / 2.0f;
			length = length / 2.0f;

			nodes[0] = new QuadTree(childLevel, new AABBCollider(x, y - height, z - length, width, height, length, null), SPLIT_PLAIN, maxObjects, maxLevels);
			nodes[1] = new QuadTree(childLevel, new AABBCollider(x, y - height, z + length, width, height, length, null), SPLIT_PLAIN, maxObjects, maxLevels);
			nodes[2] = new QuadTree(childLevel, new AABBCollider(x, y + height, z + length, width, height, length, null), SPLIT_PLAIN, maxObjects, maxLevels);
			nodes[3] = new QuadTree(childLevel, new AABBCollider(x, y + height, z - length, width, height, length, null), SPLIT_PLAIN, maxObjects, maxLevels);
			break;
		default:
			break;
		}
	}

	/**
	 * Returns the index of the child {@link QuadTree} which fully contains the given rectangle. If no child fully contains the rectangle then a value of -1 is
	 * returned.
	 * 
	 * @param object
	 *            - the rectangle which this function finds the owner of.
	 * @return the index of the child {@link QuadTree}.
	 */
	private int getIndex(ICollidable collidable) {
		if (nodes[0] != null) {
			if (collidable instanceof AABBCollider) {
				AABBCollider castCollider = (AABBCollider) collidable;
				for (int i = 0; i < nodes.length; i++) {
					if (nodes[i].bounds.collides(castCollider).isContained()) {
						return i;
					}
				}
			} else if (collidable instanceof CapsuleCollider) {
				CapsuleCollider castCollider = (CapsuleCollider) collidable;
				for (int i = 0; i < nodes.length; i++) {
					if (nodes[i].bounds.collides(castCollider).isContained()) {
						return i;
					}
				}
			} else if (collidable instanceof ConvexMeshCollider) {
				ConvexMeshCollider castCollider = (ConvexMeshCollider) collidable;
				for (int i = 0; i < nodes.length; i++) {
					if (nodes[i].bounds.collides(castCollider).isContained()) {
						return i;
					}

				}
			} else if (collidable instanceof OBBCollider) {
				OBBCollider castCollider = (OBBCollider) collidable;
				for (int i = 0; i < nodes.length; i++) {
					if (nodes[i].bounds.collides(castCollider).isContained()) {
						return i;

					}
				}
			} else if (collidable instanceof PlainCollider) {
				PlainCollider castCollider = (PlainCollider) collidable;
				for (int i = 0; i < nodes.length; i++) {
					if (nodes[i].bounds.collides(castCollider).isContained()) {
						return i;

					}
				}
			} else if (collidable instanceof SphereCollider) {
				SphereCollider castCollider = (SphereCollider) collidable;
				for (int i = 0; i < nodes.length; i++) {
					if (nodes[i].bounds.collides(castCollider).isContained()) {
						return i;

					}
				}
			}
		}

		// if (object.getX() + object.getWidth() < xCenter) {
		// if (object.getY() + object.getHeight() < yCenter) {
		// return 0;
		// } else if (object.getY() > yCenter) {
		// return 3;
		// }
		// } else if (object.getX() > xCenter) {
		// if (object.getY() + object.getHeight() < yCenter) {
		// return 1;
		// } else if (object.getY() > yCenter) {
		// return 2;
		// }
		// }

		return -1;
	}
}
