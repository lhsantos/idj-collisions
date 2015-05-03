package br.unb.idj.collisions.util;

public class Vector2 {
	public float x;
	public float y;

	public Vector2() {
		this(0, 0);
	}

	public Vector2(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public static Vector2 zero() {
		return new Vector2(0, 0);
	}

	public static Vector2 right() {
		return new Vector2(1, 0);
	}

	public static Vector2 up() {
		return new Vector2(0, 1);
	}

	public static Vector2 left() {
		return new Vector2(-1, 0);
	}

	public static Vector2 down() {
		return new Vector2(0, -1);
	}

	public Vector2 add(Vector2 other) {
		return new Vector2(x + other.x, y + other.y);
	}

	public float sqrMagnitude() {
		return x * x + y * y;
	}

	public float magnitude() {
		return (float) Math.sqrt(sqrMagnitude());
	}

	@Override
	public int hashCode() {
		return Float.hashCode(x) ^ Float.hashCode(y);
	}

	@Override
	public boolean equals(Object obj) {
		if ((obj == null) || !(obj instanceof Vector2))
			return false;

		Vector2 v2 = (Vector2) obj;
		return (Float.compare(x, v2.x) == 0) && (Float.compare(y, v2.y) == 0);
	}

	@Override
	public String toString() {
		return "Vector2[" + x + "," + y + "]";
	}
}
