package com.hsifeulbhsifder.sigma.engine.math;

/**\
 * Class representing a two dimensional vector
 * @author Zaeem
 * @version 1.0
 */
public class Vec2f implements Vector<Vec2f> {

	/**
	 * This Vector represents the axis of X, 
	 * which is (1,0)
	 * @Warning Never under any circumstances ever, 
	 * do any methods that modify the value of this vector
	 * on this instance. This is used as an easy way to represent
	 * the X axis
	 */
	public static final Vec2f X = new Vec2f(1,0);
	/**
	 * This Vector represents the axis of Y, 
	 * which is (0,1)
	 * @Warning Never under any circumstances ever, 
	 * do any methods that modify the value of this vector
	 * on this instance. This is used as an easy way to represent
	 * the Y axis
	 */
	public static final Vec2f Y = new Vec2f(0,1);
	/**
	 * This Vector represents a vector with the value of zero, 
	 * which is (0,0)
	 * @Warning Never under any circumstances ever, 
	 * do any methods that modify the value of this vector
	 * on this instance. This is used as an easy way to represent zero
	 */
	public static final Vec2f ZERO = new Vec2f();

	private float x;
	private float y;

	/** Constructs a new vector at (0,0) */
	public Vec2f() {
	}
	/**
	 * Constructs a new vector with both components set to a scalar quantity
	 * @param s - the scalar
	 */
	public Vec2f(float s) {
		this.set(s);
	}
	
	/** Constructs a vector with the given components
	 * @param x - The x-component
	 * @param y - The y-component */
	public Vec2f(float x, float y) {
		this.x = x;
		this.y = y;
	}
	/** Constructs a vector from the given vector
	 * @param v - The vector */
	public Vec2f(final Vec2f v) {
		this.set(v);
	}
	
	@Override
	public Vec2f copy() {
		return new Vec2f(this);
	}

	@Override
	public float length() {
		return (float) Math.sqrt(x * x + y * y);
	}

	@Override
	public float length2() {
		return x * x + y * y;
	}

	@Override
	public Vec2f limit(float limit) {
		if (length2() > limit * limit) {
			normalize();
			mul(limit);
		}
		return this;
	}

	@Override
	public Vec2f clamp(float min, float max) {
		final float l2 = length2();
		if (l2 == 0f)
			return this;
		if (l2 > max * max)
			return normalize().mul(max);
		if (l2 < min * min)
			return normalize().mul(min);
		return this;
	}

	@Override
	public Vec2f set(final Vec2f v) {
		x = v.x;
		y = v.y;
		return this;
	}

	@Override
	public Vec2f set(float s) {
		x = s;
		y = s;
		return this;
	}
	/** Sets the components of this vector
	 * @param x -  The x-component
	 * @param y -  The y-component
	 * @return This vector */
	public Vec2f set(float x, float y) {
		this.x = x;
		this.y = y;
		return this;
	}

	@Override
	public float max() {
		return Math.max(x, y);
	}

	@Override
	public float min() {
		return Math.min(x, y);
	}

	@Override
	public Vec2f normalize() {
		float length = length();
		if (length != 0) {
			x /= length;
			y /= length;
		}
		return this;
	}

	@Override
	public Vec2f add(final Vec2f v) {
		x += v.x;
		y += v.y;
		return this;
	}

	@Override
	public Vec2f sub(final Vec2f v) {
		x -= v.x;
		y -= v.y;
		return this;
	}

	@Override
	public Vec2f mul(final Vec2f v) {
		x *= v.x;
		y *= v.y;
		return this;
	}

	@Override
	public Vec2f div(final Vec2f v) {
		float divx = MC.isZero(v.x) ? 1f : v.x;
		float divy = MC.isZero(v.y) ? 1f : v.y;
		x /= divx;
		y /= divy;
		return this;
	}

	@Override
	public Vec2f add(float s) {
		x += s;
		y += s;
		return this;
	}

	@Override
	public Vec2f sub(float s) {
		x -= s;
		y -= s;
		return this;
	}

	@Override
	public Vec2f mul(float s) {
		x *= s;
		y *= s;
		return this;
	}

	@Override
	public Vec2f div(float s) {
		float div = MC.isZero(s) ? 1f : s;
		x /= div;
		y /= div;
		return this;
	}
	/** Adds the other vector to this vector.
	 * @param x - The x-component of the other vector
	 * @param y - The y-component of the other vector
	 * @return This vector */
	public Vec2f add(float x, float y) {
		this.x += x;
		this.y += y;
		return this;
	}
	/** Subtracts the other vector from this vector.
	 * @param x - The x-component of the other vector
	 * @param y - The y-component of the other vector
	 * @return This vector */
	public Vec2f sub(float x, float y) {
		this.x -= x;
		this.y -= y;
		return this;
	}
	/** Multiplies the other vector by this vector.
	 * @param x - The x-component of the other vector
	 * @param y - The y-component of the other vector
	 * @return This vector */
	public Vec2f mul(float x, float y) {
		this.x *= x;
		this.y *= y;
		return this;
	}
	/** Divides the other vector from this vector.
	 *  Does Nothing to a component if it is equal to zero
	 * @param x - The x-component of the other vector
	 * @param y - The y-component of the other vector
	 * @return This vector */
	public Vec2f div(float x, float y) {
		float divx = MC.isZero(x) ? 1f : x;
		float divy = MC.isZero(y) ? 1f : y;
		x /= divx;
		y /= divy;
		return this;
	}

	@Override
	public Vec2f mulAdd(final Vec2f v, float s) {
		this.x += v.x * s;
		this.y += v.y * s;
		return this;
	}

	@Override
	public Vec2f mulAdd(final Vec2f vA, final Vec2f vM) {
		this.x += vA.x * vM.x;
		this.y += vA.y * vM.y;
		return this;
	}

	@Override
	public Vec2f setZero() {
		this.x = 0;
		this.y = 0;
		return this;
	}

	@Override
	public float dot(final Vec2f v) {
		return x * v.x + y * v.y;
	}

	@Override
	public float dot(float s) {
		return x * s + y * s;
	}

	public float dot(float ox, float oy) {
		return x * ox + y * oy;
	}
	/** Calculates the 2D cross product between this and the given vector.
	 * @param v - the other vector
	 * @return the cross product */
	public float cross(final Vec2f v) {
		return this.x * v.y - this.y * v.x;
	}
	/** Calculates the 2D cross product between this and the given vector components.
	 * @param x - the x-coordinate of the other vector
	 * @param y - the y-coordinate of the other vector
	 * @return the cross product */
	public float cross(float x, float y) {
		return this.x * y - this.y * x;
	}
	/** @return the angle in degrees of this vector (point) relative to the x-axis. 
	 * Angles are towards the positive y-axis (typically
	 *         counter-clockwise) and between 0 and 360. */
	public float angle() {
		float angle = MC.toR((float) MC.atan2(y, x));
		if (angle < 0)
			angle += 360;
		return angle;
	}
	/** @return the angle in degrees of this vector (point) relative to the given vector.
	 *  Angles are towards the positive y-axis
	 *         (typically counter-clockwise.) between -180 and +180 */
	public float angle(final Vec2f v) {
		return MC.toR((float) MC.atan2(cross(v), dot(v)));
	}
	/** @return the angle in radians of this vector (point) relative to the x-axis.
	 *  Angles are towards the positive y-axis.
	 *         (typically counter-clockwise) */
	public float angleRad() {
		return (float) MC.atan2(y, x);
	}
	/** @return the angle in radians of this vector (point) relative to the given vector
	 * . Angles are towards the positive y-axis.
	 *         (typically counter-clockwise.) */
	public float angleRad(final Vec2f reference) {
		return (float) MC.atan2(cross(reference), dot(reference));
	}
	/** Sets the angle of the vector in degrees relative to the x-axis,
	 *  towards the positive y-axis (typically counter-clockwise).
	 * @param degrees The angle in degrees to set. */
	public Vec2f setAngle(float degrees) {
		return setAngleRad(MC.toR(degrees));
	}
	/** Sets the angle of the vector in radians relative to the x-axis,
	 *  towards the positive y-axis (typically counter-clockwise).
	 * @param radians The angle in radians to set. */
	public Vec2f setAngleRad(float radians) {
		this.set(length(), 0f);
		this.rotateRad(radians);

		return this;
	}
	/** Rotates the Vector2 by the given angle, counter-clockwise
	 *  assuming the y-axis points up.
	 * @param degrees the angle in degrees */
	public Vec2f rotate(float degrees) {
		return rotateRad(MC.toR(degrees));
	}
	/** Rotates the Vector2 by the given angle, counter-clockwise
	 *  assuming the y-axis points up.
	 * @param radians the angle in radians */
	public Vec2f rotateRad(float radians) {
		float cos = (float) MC.cos(radians);
		float sin = (float) MC.sin(radians);

		float newX = this.x * cos - this.y * sin;
		float newY = this.x * sin + this.y * cos;

		this.x = newX;
		this.y = newY;

		return this;
	}
	/** Rotates the Vector2 by 90 degrees in the specified direction,
	 *  where true is counter-clockwise and false is clockwise. */
	public Vec2f rotate90(boolean dir) {
		float x = this.x;
		if (dir) {
			this.x = -y;
			y = x;
		} else {
			this.x = y;
			y = -x;
		}
		return this;
	}

	@Override
	public Vec2f reflect(final Vec2f normal) {
		return this.sub(normal.mul(this.dot(normal) * 2));
	}

	@Override
	public float distance(final Vec2f v) {
		final float x_d = v.x - x;
		final float y_d = v.y - y;
		return (float) Math.sqrt(x_d * x_d + y_d * y_d);
	}
	/** @param x - The x-component of the other vector
	 * @param  y - The y-component of the other vector
	 * @return the distance between this and the other vector */
	public float distance(float x, float y) {
		final float x_d = x - this.x;
		final float y_d = y - this.y;
		return (float) Math.sqrt(x_d * x_d + y_d * y_d);
	}

	@Override
	public float distance2(final Vec2f v) {
		final float x_d = v.x - x;
		final float y_d = v.y - y;
		return x_d * x_d + y_d * y_d;
	}
	/** @param x - The x-component of the other vector
	 * @param  y - The y-component of the other vector
	 * @return the squared distance between this and the other vector */
	public float distance2(float x, float y) {
		final float x_d = x - this.x;
		final float y_d = y - this.y;
		return x_d * x_d + y_d * y_d;
	}

	@Override
	public Vec2f lerp(final Vec2f target, float alpha) {
		final float invAlpha = 1.0f - alpha;
		this.x = (x * invAlpha) + (target.x * alpha);
		this.y = (y * invAlpha) + (target.y * alpha);
		return this;
	}

	@Override
	public boolean isUnit() {
		return isUnit(0.000000001f);
	}

	@Override
	public boolean isUnit(float margin) {
		return Math.abs(length2() - 1f) < margin;
	}

	@Override
	public boolean isZero() {
		return x == 0 && y == 0;
	}

	@Override
	public boolean isZero(float margin) {
		return length2() < margin;
	}

	@Override
	public boolean isOnLine(final Vec2f other, float epsilon) {
		return MC.isZero(x * other.y - y * other.x, epsilon);
	}

	@Override
	public boolean isOnLine(final Vec2f other) {
		return MC.isZero(x * other.y - y * other.x);
	}

	@Override
	public boolean isCollinear(final Vec2f other, float epsilon) {
		return isOnLine(other, epsilon) && dot(other) > 0f;
	}

	@Override
	public boolean isCollinear(final Vec2f other) {
		return isOnLine(other) && dot(other) > 0f;
	}

	@Override
	public boolean isCollinearOpposite(final Vec2f other, float epsilon) {
		return isOnLine(other, epsilon) && dot(other) < 0f;
	}

	@Override
	public boolean isCollinearOpposite(final Vec2f other) {
		return isOnLine(other) && dot(other) < 0f;
	}

	@Override
	public boolean isPerpendicular(final Vec2f other) {
		return MC.isZero(dot(other));
	}

	@Override
	public boolean isPerpendicular(final Vec2f other, float epsilon) {
		return MC.isZero(dot(other), epsilon);
	}

	@Override
	public boolean hasSameDirection(final Vec2f other) {
		return dot(other) > 0;
	}

	@Override
	public boolean hasOppositeDirection(final Vec2f other) {
		return dot(other) < 0;
	}

	@Override
	public String toString() {
		return "(" + x + ", " + y + ")";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 17;
		result = prime * result + Float.floatToIntBits(x);
		result = prime * result + Float.floatToIntBits(y);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vec2f other = (Vec2f) obj;
		if (Float.floatToIntBits(x) != Float.floatToIntBits(other.x))
			return false;
		if (Float.floatToIntBits(y) != Float.floatToIntBits(other.y))
			return false;
		return true;
	}

	@Override
	public boolean epsilonEquals(Vec2f other, float epsilon) {
		if (other == null)
			return false;
		if (Math.abs(other.x - x) > epsilon)
			return false;
		if (Math.abs(other.y - y) > epsilon)
			return false;
		return true;
	}
	/** Compares this vector with the other vector, using the supplied epsilon for fuzzy equality testing.
	 * @return whether the vectors are the same. */
	public boolean epsilonEquals(float x, float y, float epsilon) {
		if (Math.abs(x - this.x) > epsilon)
			return false;
		if (Math.abs(y - this.y) > epsilon)
			return false;
		return true;
	}

	@Override
	public float x() {
		return x;
	}

	@Override
	public float y() {
		return y;
	}

	@Override
	public float z() {
		System.err.println("ERROR!");
		new AssertionError().printStackTrace();
		System.exit(0);
		return 0;
	}

}
