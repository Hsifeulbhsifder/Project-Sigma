package com.hsifeulbhsifder.sigma.engine.math;

public class Vec3f implements Vector<Vec3f> {

	/**
	 * This Vector represents the axis of X, 
	 * which is (1,0,0)
	 * @Warning Never under any circumstances ever, 
	 * do any methods that modify the value of this vector
	 * on this instance. This is used as an easy way to represent
	 * the X axis
	 */
	public final static Vec3f X = new Vec3f(1, 0, 0);
	/**
	 * This Vector represents the axis of Y, 
	 * which is (0,1,0)
	 * @Warning Never under any circumstances ever, 
	 * do any methods that modify the value of this vector
	 * on this instance. This is used as an easy way to represent
	 * the Y axis
	 */
	public final static Vec3f Y = new Vec3f(0, 1, 0);
	/**
	 * This Vector represents the axis of Z, 
	 * which is (0,0,1)
	 * @Warning Never under any circumstances ever, 
	 * do any methods that modify the value of this vector
	 * on this instance. This is used as an easy way to represent
	 * the Z axis
	 */
	public final static Vec3f Z = new Vec3f(0, 0, 1);
	/**
	 * This Vector represents a vector with the value of zero, 
	 * which is (0,0,0)
	 * @Warning Never under any circumstances ever, 
	 * do any methods that modify the value of this vector
	 * on this instance. This is used as an easy way to represent zero
	 */
	public final static Vec3f Zero = new Vec3f();

	private float x;
	private float y;
	private float z;

	/** Constructs a new vector at (0,0,0) */
	public Vec3f() {
	}
	/**
	 * Constructs a new vector with all components set to a scalar quantity
	 * @param s - the scalar
	 */
	public Vec3f(float s) {
		this.set(s);
	}
	/** Constructs a vector with the given components
	 * @param x - The x-component
	 * @param y - The y-component
	 * @param z - The z-component */
	public Vec3f(float x, float y, float z) {
		this.set(x, y, z);
	}
	/** Constructs a vector from the given vector
	 * @param v - The vector */
	public Vec3f(final Vec3f v) {
		this.set(v);
	}
	/**
	 * Constructs a vector from a float array, with a minimum of three values
	 * @param v - An array of floats, of which the first three values 
	 * is taken as the components of the vector	 */
	public Vec3f(final float[] v) {
		this.set(v[0], v[1], v[2]);
	}
	/**
	 * Constructs a vector from a Vec2f instance and a z component
	 * @param v - a Vec2f that provides the x and y components of the vector
	 * @param z - the z component    */
	public Vec3f(final Vec2f v, float z) {
		this.set(v, z);
	}
	/** Sets the vector to the given components
	 * 
	 * @param x - The x-component
	 * @param y - The y-component
	 * @param z - The z-component
	 * @return This vector */
	public Vec3f set(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
		return this;
	}

	@Override
	public Vec3f set(final Vec3f v) {
		return this.set(v.x, v.y, v.z);
	}
	/** Sets the components from the array. The array must have at least 3 elements
	 * 
	 * @param v - The array
	 * @return This vector */
	public Vec3f set(final float[] v) {
		return this.set(v[0], v[1], v[2]);
	}
	/** Sets the components of the given vector and z-component
	 * 
	 * @param v - The vector
	 * @param z - The z-component
	 * @return This vector */
	public Vec3f set(final Vec2f v, float z) {
		return this.set(v.z(), v.y(), z);
	}

	@Override
	public Vec3f set(float s) {
		return this.set(s, s, s);
	}

	@Override
	public Vec3f copy() {
		return new Vec3f(this);
	}

	@Override
	public float length() {
		return (float) Math.sqrt(x * x + y * y + z * z);
	}
	/** @return The length */
	public static float length(final float x, final float y, final float z) {
		return (float) Math.sqrt(x * x + y * y + z * z);
	}

	@Override
	public float length2() {
		return x * x + y * y + z * z;
	}
	/** @return The length squared*/
	public static float length2(final float x, final float y, final float z) {
		return x * x + y * y + z * z;
	}
	/** @param vector - The other vector
	 * @return Whether this and the other vector are equal */
	public boolean identify(final Vec3f v) {
		return x == v.x && y == v.y && z == v.z;
	}

	@Override
	public Vec3f limit(float limit) {
		if (length2() > limit * limit)
			normalize().mul(limit);
		return this;
	}

	@Override
	public Vec3f clamp(float min, float max) {
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
	public Vec3f normalize() {
		final float len2 = this.length2();
		if (len2 == 0f || len2 == 1f)
			return this;
		return this.mul(1f / (float) Math.sqrt(len2));
	}

	@Override
	public Vec3f add(final Vec3f v) {
		return this.add(v.x, v.y, v.z);
	}

	@Override
	public Vec3f sub(final Vec3f v) {
		return this.sub(v.x, v.y, v.z);
	}

	@Override
	public Vec3f mul(final Vec3f v) {
		return this.mul(v.x, v.y, v.z);
	}

	@Override
	public Vec3f div(final Vec3f v) {
		return this.div(v.x, v.y, v.z);
	}

	@Override
	public Vec3f add(float s) {
		return this.add(s, s, s);
	}

	@Override
	public Vec3f sub(float s) {
		return this.sub(s, s, s);
	}

	@Override
	public Vec3f mul(float s) {
		return this.mul(s, s, s);
	}

	@Override
	public Vec3f div(float s) {
		return this.div(s, s, s);
	}
	/** Adds the given vector to this component
	 * @param x - The x-component of the other vector
	 * @param y - The y-component of the other vector
	 * @param z - The z-component of the other vector
	 * @return This vector. */
	public Vec3f add(float x, float y, float z) {
		return this.set(this.x + x, this.y + y, this.z + z);
	}
	/** Subtracts the given vector from this component
	 * @param x - The x-component of the other vector
	 * @param y - The y-component of the other vector
	 * @param z - The z-component of the other vector
	 * @return This vector. */
	public Vec3f sub(float x, float y, float z) {
		return this.set(this.x - x, this.y - y, this.z - z);
	}
	/** Multiplies the given vector by this component
	 * @param x - The x-component of the other vector
	 * @param y - The y-component of the other vector
	 * @param z - The z-component of the other vector
	 * @return This vector. */
	public Vec3f mul(float x, float y, float z) {
		return this.set(this.x * x, this.y * y, this.z * z);
	}
	/** Divides the given vector from this component, does nothing
	 * to a component if the divisors component is equal to zero
	 * @param x - The x-component of the other vector
	 * @param y - The y-component of the other vector
	 * @param z - The z-component of the other vector
	 * @return This vector. */
	public Vec3f div(float x, float y, float z) {
		float dx = MC.isZero(x)? 1f : x;
		float dy = MC.isZero(y)? 1f : y;
		float dz = MC.isZero(z)? 1f : z;
		return this.set(this.x / dx, this.y / dy, this.z / dz);
	}

	@Override
	public Vec3f mulAdd(final Vec3f v, float s) {
		this.x += v.x * s;
		this.y += v.y * s;
		this.z += v.z * s;
		return this;
	}

	@Override
	public Vec3f mulAdd(final Vec3f vA, final Vec3f vM) {
		this.x += vA.x * vM.x;
		this.y += vA.y * vM.y;
		this.z += vA.z * vM.z;
		return this;
	}

	@Override
	public Vec3f setZero() {
		this.x = 0;
		this.y = 0;
		this.z = 0;
		return this;
	}
	/** @return The dot product between the two vectors */
	public static float dot(float x1, float y1, float z1, float x2, float y2,
			float z2) {
		return x1 * x2 + y1 * y2 + z1 * z2;
	}

	@Override
	public float dot(final Vec3f v) {
		return x * v.x + y * v.y + z * v.z;
	}

	@Override
	public float dot(float s) {
		return this.x * s + this.y * s + this.z * z;
	}
	/** Returns the dot product between this and the given vector.
	 * @param x - The x-component of the other vector
	 * @param y - The y-component of the other vector
	 * @param z - The z-component of the other vector
	 * @return The dot product */
	public float dot(float x, float y, float z) {
		return this.x * x + this.y * y + this.z * z;
	}
	/** Sets this vector to the cross product between it and the other vector.
	 * @param vector - The other vector
	 * @return This vector */
	public Vec3f cross(final Vec3f v) {
		return this
				.set(y * v.z - z * v.y, z * v.x - x * v.z, x * v.y - y * v.x);
	}
	/** Sets this vector to the cross product between it and the other vector.
	 * @param x - The x-component of the other vector
	 * @param y - The y-component of the other vector
	 * @param z - The z-component of the other vector
	 * @return This vector */
	public Vec3f cross(float x, float y, float z) {
		return this.set(this.y * z - this.z * y, this.z * x - this.x * z,
				this.x * y - this.y * x);
	}

	@Override
	public Vec3f reflect(final Vec3f normal) {
		return this.sub(normal.mul(this.dot(normal) * 2));
		
		//immutable version
		
//		Vec3f res = new Vec3f(this);
//		Vec3f op = new Vec3f(normal);
//		return res.sub(op.mul(this.dot(normal) * 2));
		
		
	}

	@Override
	public float distance(final Vec3f v) {
		final float a = v.x - x;
		final float b = v.y - y;
		final float c = v.z - z;
		return (float) Math.sqrt(a * a + b * b + c * c);
	}
	/** @return the distance between this point and the given point */
	public float distance(float x, float y, float z) {
		final float a = x - this.x;
		final float b = y - this.y;
		final float c = z - this.z;
		return (float) Math.sqrt(a * a + b * b + c * c);
	}

	@Override
	public float distance2(final Vec3f v) {
		final float a = v.x - x;
		final float b = v.y - y;
		final float c = v.z - z;
		return a * a + b * b + c * c;
	}
	/** Returns the squared distance between this point and the given point
	 * @param x - The x-component of the other point
	 * @param y - The y-component of the other point
	 * @param z - The z-component of the other point
	 * @return The squared distance */
	public float distance2(float x, float y, float z) {
		final float a = x - this.x;
		final float b = y - this.y;
		final float c = z - this.z;
		return a * a + b * b + c * c;
	}

	public Vec3f rotate(final Quat rotation) {
		Quat conjugate = rotation.conjugate();
		Quat w = rotation.mul(this).mul(conjugate);

		x = w.x();
		y = w.y();
		z = w.z();

		return this;
	}

	public Vec3f rotate(final Vec3f axis, float angle) {
		float sinAngle = (float) MC.sin(-angle);
		float cosAngle = (float) MC.cos(-angle);

		Vec3f rotVector = new Vec3f(axis);

		return this.cross(rotVector.mul(sinAngle))
				.add((this.mul(cosAngle)).add(rotVector.mul(this.dot(rotVector
						.mul(1 - cosAngle)))));
	}

	@Override
	public Vec3f lerp(final Vec3f target, float alpha) {
		mul(1.0f - alpha);
		add(target.x * alpha, target.y * alpha, target.z * alpha);
		return this;
	}
	/** Spherically interpolates between this vector and the target vector by alpha which is in the range [0,1]. The result is
	 * stored in this vector.
	 * 
	 * @param target - The target vector
	 * @param alpha - The interpolation coefficient
	 * @return This vector. */
	public Vec3f slerp(final Vec3f target, float alpha) {
		final float dot = dot(target);
		// If the inputs are too close for comfort, simply linearly interpolate.
		if (dot > 0.9995 || dot < -0.9995)
			return lerp(target, alpha);

		final float theta0 = (float) Math.acos(dot);
		final float theta = theta0 * alpha;

		final float st = (float) Math.sin(theta);
		final float tx = target.x - x * dot;
		final float ty = target.y - y * dot;
		final float tz = target.z - z * dot;
		final float l2 = tx * tx + ty * ty + tz * tz;
		final float dl = st
				* ((l2 < 0.0001f) ? 1f : 1f / (float) Math.sqrt(l2));

		return mul((float) Math.cos(theta)).add(tx * dl, ty * dl, tz * dl)
				.normalize();
	}

	@Override
	public float max() {
		return Math.max(x, Math.max(y, z));
	}

	@Override
	public float min() {
		return Math.max(x, Math.max(y, z));
	}

	public static Vec3f max(Vec3f a, Vec3f b) {
		return new Vec3f(Math.max(a.x, b.x), Math.max(a.y, b.y), Math.max(a.z,
				b.z));
	}

	@Override
	public boolean isUnit() {
		return isUnit(0.000000001f);
	}

	@Override
	public boolean isUnit(final float margin) {
		return Math.abs(length2() - 1f) < margin;
	}

	@Override
	public boolean isZero() {
		return x == 0 && y == 0 && z == 0;
	}

	@Override
	public boolean isZero(float margin) {
		return length2() < margin;
	}

	@Override
	public boolean isOnLine(final Vec3f other, float epsilon) {
		return length2(y * other.z - z * other.y, z * other.x - x * other.z, x
				* other.y - y * other.x) <= epsilon;
	}

	@Override
	public boolean isOnLine(final Vec3f other) {
		return length2(y * other.z - z * other.y, z * other.x - x * other.z, x
				* other.y - y * other.x) <= MC.FLOAT_ROUNDING_ERROR;
	}

	@Override
	public boolean isCollinear(final Vec3f other, float epsilon) {
		return isOnLine(other, epsilon) && hasSameDirection(other);
	}

	@Override
	public boolean isCollinear(final Vec3f other) {
		return isOnLine(other) && hasSameDirection(other);
	}

	@Override
	public boolean isCollinearOpposite(final Vec3f other, float epsilon) {
		return isOnLine(other, epsilon) && hasOppositeDirection(other);
	}

	@Override
	public boolean isCollinearOpposite(final Vec3f other) {
		return isOnLine(other) && hasOppositeDirection(other);
	}

	@Override
	public boolean isPerpendicular(final Vec3f other) {
		return MC.isZero(dot(other));
	}

	@Override
	public boolean isPerpendicular(final Vec3f other, float epsilon) {
		return MC.isZero(dot(other), epsilon);
	}

	@Override
	public boolean hasSameDirection(final Vec3f other) {
		return dot(other) > 0;
	}

	@Override
	public boolean hasOppositeDirection(final Vec3f other) {
		return dot(other) < 0;
	}

	@Override
	public float x() {
		return x;
	}

	@Override
	public float y() {
		return z;
	}

	@Override
	public float z() {
		return z;
	}

	public Vec2f xy() {
		return new Vec2f(x, y);
	}

	public Vec2f yz() {
		return new Vec2f(y, z);
	}

	public Vec2f zx() {
		return new Vec2f(z, x);
	}

	public Vec2f yx() {
		return new Vec2f(y, x);
	}

	public Vec2f zy() {
		return new Vec2f(z, y);
	}

	public Vec2f xz() {
		return new Vec2f(x, z);
	}

	@Override
	public String toString() {
		return "(" + x + ", " + y + ", " + z + ")";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 17;
		result = prime * result + Float.floatToIntBits(x);
		result = prime * result + Float.floatToIntBits(y);
		result = prime * result + Float.floatToIntBits(z);
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
		Vec3f other = (Vec3f) obj;
		if (Float.floatToIntBits(x) != Float.floatToIntBits(other.x))
			return false;
		if (Float.floatToIntBits(y) != Float.floatToIntBits(other.y))
			return false;
		if (Float.floatToIntBits(z) != Float.floatToIntBits(other.z))
			return false;
		return true;
	}

	@Override
	public boolean epsilonEquals(Vec3f other, float epsilon) {
		if (other == null)
			return false;
		if (Math.abs(other.x - x) > epsilon)
			return false;
		if (Math.abs(other.y - y) > epsilon)
			return false;
		if (Math.abs(other.z - z) > epsilon)
			return false;
		return true;
	}
	/** Compares this vector with the other vector, using the supplied epsilon for fuzzy equality testing.
	 * @return whether the vectors are the same. */
	public boolean epsilonEquals(float x, float y, float z, float epsilon) {
		if (Math.abs(x - this.x) > epsilon)
			return false;
		if (Math.abs(y - this.y) > epsilon)
			return false;
		if (Math.abs(z - this.z) > epsilon)
			return false;
		return true;
	}
}
