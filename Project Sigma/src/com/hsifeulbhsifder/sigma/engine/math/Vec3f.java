package com.hsifeulbhsifder.sigma.engine.math;

public class Vec3f implements Vector<Vec3f> {

	public final static Vec3f X = new Vec3f(1, 0, 0);
	public final static Vec3f Y = new Vec3f(0, 1, 0);
	public final static Vec3f Z = new Vec3f(0, 0, 1);
	public final static Vec3f Zero = new Vec3f();

	private float x;
	private float y;
	private float z;

	public Vec3f() {
	}

	public Vec3f(float s) {
		this.set(s);
	}

	public Vec3f(float x, float y, float z) {
		this.set(x, y, z);
	}

	public Vec3f(final Vec3f v) {
		this.set(v);
	}

	public Vec3f(float[] v) {
		this.set(v[0], v[1], v[2]);
	}

	public Vec3f(final Vec2f v, float z) {
		this.set(v, z);
	}

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

	public Vec3f set(float[] v) {
		return this.set(v[0], v[1], v[2]);
	}

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
	
	public static float length(final float x, final float y, final float z) {
		return (float) Math.sqrt(x * x + y * y + z * z);
	}

	@Override
	public float length2() {
		return x * x + y * y + z * z;
	}

	public static float length2(final float x, final float y, final float z) {
		return x * x + y * y + z * z;
	}

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

	public Vec3f add(float x, float y, float z) {
		return this.set(this.x + x, this.y + y, this.z + z);
	}

	public Vec3f sub(float x, float y, float z) {
		return this.set(this.x - x, this.y - y, this.z - z);
	}

	public Vec3f mul(float x, float y, float z) {
		return this.set(this.x * x, this.y * y, this.z * z);
	}

	public Vec3f div(float x, float y, float z) {
		if (x == 0 || y == 0 || z == 0) {
			// TODO: add exception handling
			return this;
		} else {
			return this.set(this.x / x, this.y / y, this.z / z);
		}
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

	public static float dot (float x1, float y1, float z1, float x2, float y2, float z2) {
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

	public float dot(float x, float y, float z) {
		return this.x * x + this.y * y + this.z * z;
	}

	public Vec3f cross(final Vec3f v) {
		return this
				.set(y * v.z - z * v.y, z * v.x - x * v.z, x * v.y - y * v.x);
	}

	public Vec3f cross(float x, float y, float z) {
		return this.set(this.y * z - this.z * y, this.z * x - this.x * z,
				this.x * y - this.y * x);
	}

	@Override
	public Vec3f reflect(final Vec3f normal) {
		return this.sub(normal.mul(this.dot(normal) * 2));
	}

	@Override
	public float distance(final Vec3f v) {
		final float a = v.x - x;
		final float b = v.y - y;
		final float c = v.z - z;
		return (float) Math.sqrt(a * a + b * b + c * c);
	}

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

	public float distance2(float x, float y, float z) {
		final float a = x - this.x;
		final float b = y - this.y;
		final float c = z - this.z;
		return a * a + b * b + c * c;
	}
	
	public Vec3f rotate(Quat rotation) {
		Quat conjugate = rotation.conjugate();
		Quat w = rotation.mul(this).mul(conjugate);
		
		x = w.x();
		y = w.y();
		z = w.z();
		
		return this;
	}

	@Override
	public Vec3f lerp(final Vec3f target, float alpha) {
		mul(1.0f - alpha);
		add(target.x * alpha, target.y * alpha, target.z * alpha);
		return this;
	}

	public Vec3f slerp(final Vec3f target, float alpha) {
		final float dot = dot(target);
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
		return new Vec3f(Math.max(a.x, b.x), Math.max(a.y, b.y), Math.max(
				a.z, b.z));
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
