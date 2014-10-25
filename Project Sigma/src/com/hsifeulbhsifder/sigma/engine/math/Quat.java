package com.hsifeulbhsifder.sigma.engine.math;

/**
 * A pretty elaborate quaternion class
 * @author Zaeem
 * @version 1.0
 */
public class Quat {
	/**
	 * This Quaternion represents the axis of X, 
	 * which is (1,0,0,0)
	 * @Warning Never under any circumstances ever, 
	 * do any methods that modify the value of this Quaternion
	 * on this instance. This is used as an easy way to represent
	 * the X axis
	 */
	public static final Quat X = new Quat(1, 0, 0, 0);
	/**
	 * This Quaternion represents the axis of Y, 
	 * which is (0,1,0,0)
	 * @Warning Never under any circumstances ever, 
	 * do any methods that modify the value of this Quaternion
	 * on this instance. This is used as an easy way to represent
	 * the Y axis
	 */
	public static final Quat Y = new Quat(0, 1, 0, 0);
	/**
	 * This Quaternion represents the axis of Z, 
	 * which is (0,0,1,0)
	 * @Warning Never under any circumstances ever, 
	 * do any methods that modify the value of this Quaternion
	 * on this instance. This is used as an easy way to represent
	 * the Z axis
	 */
	public static final Quat Z = new Quat(0, 0, 1, 0);
	/**
	 * This Quaternion represents the identity Quaternion, 
	 * which is (0,0,0,1)
	 * @Warning Never under any circumstances ever, 
	 * do any methods that modify the value of this Quaternion
	 * on this instance. This is used as an easy way to represent
	 * the identity Quaternion
	 */
	public static final Quat W = new Quat(0, 0, 0, 1);
	/**
	 * This Quaternion represents a Quaternion with the value of zero, 
	 * which is (0,0,0,0)
	 * @Warning Never under any circumstances ever, 
	 * do any methods that modify the value of this Quaternion
	 * on this instance. This is used as an easy way to represent zero
	 */
	public static final Quat ZERO = new Quat();
	
	private float x;
	private float y;
	private float z;
	private float w;
	/**
	 * Constructs a quaternion with identity values
	 */
	public Quat() {
		identity();
	}
	/** Constructor, sets the four components of the quaternion.
	 * @param x - The x-component
	 * @param y - The y-component
	 * @param z - The z-component
	 * @param w - The w-component */
	public Quat (float x, float y, float z, float w) {
		this.set(x, y, z, w);
	}
	/** Constructor, sets the quaternion components from the given quaternion.
	 * @param quaternion - The quaternion to copy. */
	public Quat (Quat quaternion) {
		this.set(quaternion);
	}
	/** Constructor, sets the quaternion from the given axis vector and the angle around that axis in degrees.
	 * @param axis - The axis
	 * @param angle - The angle in degrees. */
	public Quat (Vec3f axis, float angle) {
		this.set(axis, angle);
	}
	/** From Ken Shoemake's "Quaternion Calculus and Fast Animation" article
	 * Constructs a quaternion from a matrix
	 * @param rot - the rotation matrix
	 */
	public Quat(Mat4f rot) {
		float trace = rot.get(0, 0) + rot.get(1, 1) + rot.get(2, 2);

		if (trace > 0) {
			float s = 0.5f / (float) Math.sqrt(trace + 1.0f);
			w = 0.25f / s;
			x = (rot.get(1, 2) - rot.get(2, 1)) * s;
			y = (rot.get(2, 0) - rot.get(0, 2)) * s;
			z = (rot.get(0, 1) - rot.get(1, 0)) * s;
		} else {
			if (rot.get(0, 0) > rot.get(1, 1) && rot.get(0, 0) > rot.get(2, 2)) {
				float s = 2.0f * (float) Math.sqrt(1.0f + rot.get(0, 0)
						- rot.get(1, 1) - rot.get(2, 2));
				w = (rot.get(1, 2) - rot.get(2, 1)) / s;
				x = 0.25f * s;
				y = (rot.get(1, 0) + rot.get(0, 1)) / s;
				z = (rot.get(2, 0) + rot.get(0, 2)) / s;
			} else if (rot.get(1, 1) > rot.get(2, 2)) {
				float s = 2.0f * (float) Math.sqrt(1.0f + rot.get(1, 1)
						- rot.get(0, 0) - rot.get(2, 2));
				w = (rot.get(2, 0) - rot.get(0, 2)) / s;
				x = (rot.get(1, 0) + rot.get(0, 1)) / s;
				y = 0.25f * s;
				z = (rot.get(2, 1) + rot.get(1, 2)) / s;
			} else {
				float s = 2.0f * (float) Math.sqrt(1.0f + rot.get(2, 2)
						- rot.get(0, 0) - rot.get(1, 1));
				w = (rot.get(0, 1) - rot.get(1, 0)) / s;
				x = (rot.get(2, 0) + rot.get(0, 2)) / s;
				y = (rot.get(1, 2) + rot.get(2, 1)) / s;
				z = 0.25f * s;
			}
		}

		float length = (float) Math.sqrt(x * x + y * y + z * z + w * w);
		x /= length;
		y /= length;
		z /= length;
		w /= length;
	}
	/** Sets the components of the quaternion
	 * @param x - The x-component
	 * @param y - The y-component
	 * @param z - The z-component
	 * @param w - The w-component
	 * @return this quaternion */
	public Quat set (float x, float y, float z, float w) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
		return this;
	}
	/** Sets the quaternion components from the given quaternion.
	 * @param quaternion - The quaternion.
	 * @return this quaternion. */
	public Quat set (Quat quaternion) {
		return this.set(quaternion.x, quaternion.y, quaternion.z, quaternion.w);
	}
	/** Sets the quaternion components from the given axis and angle around that axis.
	 * 
	 * @param axis - The axis
	 * @param angle - The angle in degrees
	 * @return this quaternion. */
	public Quat set (Vec3f axis, float angle) {
		return setFromAxis(axis, angle);
	}
	/** @return a copy of this quaternion */
	public Quat copy () {
		return new Quat(this);
	}
	/** @return the length of this quaternion */
	public float length() {
		return (float) Math.sqrt(x * x + y * y + z * z + w * w);
	}
	/** @return the squared length of this quaternion */
	public float length2 () {
		return x * x + y * y + z * z + w * w;
	}
	
	public final static float length2 (final float x, final float y, final float z, final float w) {
		return x * x + y * y + z * z + w * w;
	}
	/** Sets the quaternion to the given euler angles in degrees.
	 * @param yaw - the rotation around the y axis in degrees
	 * @param pitch - the rotation around the x axis in degrees
	 * @param roll - the rotation around the z axis degrees
	 * @return this quaternion */
	public Quat setEulerAngles (float yaw, float pitch, float roll) {
		return setEulerAnglesRad(MC.toR(yaw), MC.toR(pitch), MC.toR(roll));
	}
	/** Sets the quaternion to the given euler angles in radians.
	 * @param yaw - the rotation around the y axis in radians
	 * @param pitch - the rotation around the x axis in radians
	 * @param roll - the rotation around the z axis in radians
	 * @return this quaternion */
	public Quat setEulerAnglesRad (float yaw, float pitch, float roll) {
		final float hr = roll/2f;
		final float shr = MC.sin(hr);
		final float chr = MC.cos(hr);
		final float hp = pitch/2f;
		final float shp = MC.sin(hp);
		final float chp = MC.cos(hp);
		final float hy = yaw/2f;
		final float shy = MC.sin(hy);
		final float chy = MC.cos(hy);
		final float chy_shp = chy * shp;
		final float shy_chp = shy * chp;
		final float chy_chp = chy * chp;
		final float shy_shp = shy * shp;

		x = (chy_shp * chr) + (shy_chp * shr); 
		y = (shy_chp * chr) - (chy_shp * shr); 
		z = (chy_chp * shr) - (shy_shp * chr); 
		w = (chy_chp * chr) + (shy_shp * shr); 
		return this;
	}
	/** Get the pole of the gimbal lock, if any. 
	 * @return positive (+1) for north pole,
	 *  negative (-1) for south pole, zero (0) when no gimbal lock */ 
	public int getGimbalPole() {
		final float t = y*x+z*w;
		return t > 0.499f ? 1 : (t < -0.499f ? -1 : 0);
	}
	/** Get the roll euler angle in radians, which is the rotation around the z axis.
	 *  Requires that this quaternion is normalized. 
	 * @return the rotation around the z axis in radians (between -PI and +PI) */
	public float getRollRad() {
		final int pole = getGimbalPole();
		return pole == 0 ? MC.atan2(2f*(w*z + y*x), 1f - 2f * (x*x + z*z)) : (float)pole * 2f * MC.atan2(y, w);
	}
	/** Get the roll euler angle in degrees, which is the rotation around the z axis.
	 *  Requires that this quaternion is normalized. 
	 * @return the rotation around the z axis in degrees (between -180 and +180) */
	public float getRoll() {
		return MC.toD(getRollRad());
	}
	/** Get the pitch euler angle in radians, which is the rotation around the x axis.
	 *  Requires that this quaternion is normalized. 
	 * @return the rotation around the x axis in radians (between -(PI/2) and +(PI/2)) */
	public float getPitchRad() {
		final int pole = getGimbalPole();
		return pole == 0 ? (float)Math.asin(MC.clamp(2f*(w*x-z*y), -1f, 1f)) : (float)pole * MC.PI * 0.5f;
	}
	/** Get the pitch euler angle in degrees, which is the rotation around the x axis. Requires that this quaternion is normalized. 
	 * @return the rotation around the x axis in degrees (between -90 and +90) */
	public float getPitch() {
		return MC.toD(getPitchRad());
	}
	/** Get the yaw euler angle in radians, which is the rotation around the y axis. Requires that this quaternion is normalized. 
	 * @return the rotation around the y axis in radians (between -PI and +PI) */
	public float getYawRad() {
		return getGimbalPole() == 0 ? MC.atan2(2f*(y*w + x*z), 1f - 2f*(y*y+x*x)) : 0f;
	}
	/** Get the yaw euler angle in degrees, which is the rotation around the y axis. Requires that this quaternion is normalized. 
	 * @return the rotation around the y axis in degrees (between -180 and +180) */
	public float getYaw() {
		return MC.toD(getYawRad());
	}
	/** Normalizes this quaternion to unit length
	 * @return this quaternion */
	public Quat normalize() {
		float len = length2();
		if (len != 0.f && !MC.isEqual(len, 1f)) {
			len = (float)Math.sqrt(len);
			w /= len;
			x /= len;
			y /= len;
			z /= len;
		}
		return this;
	}
	/** Get the dot product between this and the other quaternion (commutative).
	 * @param other the other quaternion.
	 * @return the dot product of this and the other quaternion. */
	public float dot (final Quat other) {
		return this.x * other.x + this.y * other.y + this.z * other.z + this.w * other.w;
	}
	/** Conjugate the quaternion.
	 * 
	 * @return this quaternion */
	public Quat conjugate () {
		x = -x;
		y = -y;
		z = -z;
		return this;
	}
	/** Multiplies this quaternion with another one in the form of this = this * other
	 * 
	 * @param other - Quaternion to multiply with
	 * @return this quaternion */
	public Quat mul (final Quat q) {
		final float newX = this.w * q.x + this.x * q.w + this.y * q.z - this.z * q.y;
		final float newY = this.w * q.y + this.y * q.w + this.z * q.x - this.x * q.z;
		final float newZ = this.w * q.z + this.z * q.w + this.x * q.y - this.y * q.x;
		final float newW = this.w * q.w - this.x * q.x - this.y * q.y - this.z * q.z;
		this.x = newX;
		this.y = newY;
		this.z = newZ;
		this.w = newW;
		return this;
	}
	/**
	 * Multiplies all components of this quaternion by a scalar value
	 * @param s - scalar value, to which the quaternion is multiplied to
	 * @return this quaternion
	 */
	public Quat mul(float s) {
		this.x *= s;
		this.y *= s;
		this.z *= s;
		this.w *= s;
		return this;
	}
	/** Multiplies this quaternion with another one in the form of this = this * other
	 * 
	 * @param x the x component of the other quaternion to multiply with
	 * @param y the y component of the other quaternion to multiply with
	 * @param z the z component of the other quaternion to multiply with
	 * @param w the w component of the other quaternion to multiply with
	 * @return this quaternion */
	public Quat mul (final float x, final float y, final float z, final float w) {
		final float newX = this.w * x + this.x * w + this.y * z - this.z * y;
		final float newY = this.w * y + this.y * w + this.z * x - this.x * z;
		final float newZ = this.w * z + this.z * w + this.x * y - this.y * x;
		final float newW = this.w * w - this.x * x - this.y * y - this.z * z;
		this.x = newX;
		this.y = newY;
		this.z = newZ;
		this.w = newW;
		return this;
	}
	
	public Quat mul(Vec3f r) {
		final float newX = w * r.x() + y * r.z() - z * r.y();
		final float newY = w * r.y() + z * r.x() - x * r.z();
		final float newZ = w * r.z() + x * r.y() - y * r.x();
		final float newW = -x * r.x() - y * r.y() - z * r.z();
		this.x = newX;
		this.y = newY;
		this.z = newZ;
		this.w = newW;
		return this;
	}
	/** Add the x,y,z,w components of the passed in quaternion to the ones of this quaternion 
	 * @return this quaternion*/
	public Quat add(Quat q){
		this.x += q.x;
		this.y += q.y;
		this.z += q.z;
		this.w += q.w;
		return this;
	}
	/** Add the x,y,z,w components of the passed in quaternion to the ones of this quaternion 
	 * @return this quaternion*/
	public Quat add(float qx, float qy, float qz, float qw){
		this.x += qx;
		this.y += qy;
		this.z += qz;
		this.w += qw;
		return this;
	}
	/** Subtract the x,y,z,w components of the passed in quaternion from the ones of this quaternion 
	 * @return this quaternion*/
	public Quat sub(Quat q){
		this.x -= q.x;
		this.y -= q.y;
		this.z -= q.z;
		this.w -= q.w;
		return this;
	}
	/** Subtract the x,y,z,w components of the passed in quaternion from the ones of this quaternion 
	 * @return this quaternion*/
	public Quat sub(float qx, float qy, float qz, float qw){
		this.x -= qx;
		this.y -= qy;
		this.z -= qz;
		this.w -= qw;
		return this;
	}
	/** Sets the quaternion to an identity Quaternion
	 * @return this quaternion */
	public Quat identity() {
		return this.set(0, 0, 0, 1);
	}
	/** @return If this quaternion is an identity Quaternion */
	public boolean isIdentity () {
		return MC.isZero(x) && MC.isZero(y) && MC.isZero(z) && MC.isEqual(w, 1f);
	}
	/** @return If this quaternion is an identity Quaternion */
	public boolean isIdentity (final float tolerance) {
		return MC.isZero(x, tolerance) && MC.isZero(y, tolerance) && MC.isZero(z, tolerance)
			&& MC.isEqual(w, 1f, tolerance);
	}
	/** Sets the quaternion components from the given axis and angle around that axis.
	 * 
	 * @param axis - The axis
	 * @param degrees - The angle in degrees
	 * @return this quaternion. */
	public Quat setFromAxis (final Vec3f axis, final float degrees) {
		return setFromAxis(axis.x(), axis.y(), axis.z(), degrees);
	}	
	/** Sets the quaternion components from the given axis and angle around that axis.
	 * 
	 * @param axis - The axis
	 * @param radians - The angle in radians
	 * @return this quaternion. */
	public Quat setFromAxisRad (final Vec3f axis, final float radians) {
		return setFromAxisRad(axis.x(), axis.y(), axis.z(), radians);
	}
	/** Sets the quaternion components from the given axis and angle around that axis.
	 * @param x - X direction of the axis
	 * @param y - Y direction of the axis
	 * @param z - Z direction of the axis
	 * @param degrees - The angle in degrees
	 * @return this quaternion. */
	public Quat setFromAxis (final float x, final float y, final float z, final float degrees) {
		return setFromAxisRad(x, y, z, MC.toR(degrees));
	}
	/** Sets the quaternion components from the given axis and angle around that axis.
	 * @param x - X direction of the axis
	 * @param y - Y direction of the axis
	 * @param z - Z direction of the axis
	 * @param radians - The angle in radians
	 * @return this quaternion. */
	public Quat setFromAxisRad (final float x, final float y, final float z, final float radians) {
		float d = Vec3f.length(x, y, z);
		if (d == 0f) return identity();
		d = 1f / d;
		float l_ang = radians;
		float l_sin = (float)Math.sin(l_ang / 2);
		float l_cos = (float)Math.cos(l_ang / 2);
		return this.set(d * x * l_sin, d * y * l_sin, d * z * l_sin, l_cos).normalize();
	}
	/** Get the axis angle representation of the rotation in degrees. The supplied vector will receive the axis (x, y and z values)
	 * of the rotation and the value returned is the angle in degrees around that axis. Note that this method will alter the
	 * supplied vector, the existing value of the vector is ignored. </p> This will normalize this quaternion if needed. The
	 * received axis is a unit vector. However, if this is an identity quaternion (no rotation), then the length of the axis may be
	 * zero.
	 * 
	 * @param axis - vector which will receive the axis
	 * @return the angle in degrees
	 * @see <a href="http://en.wikipedia.org/wiki/Axis%E2%80%93angle_representation">wikipedia</a>
	 * @see <a href="http://www.euclideanspace.com/maths/geometry/rotations/conversions/quaternionToAngle">calculation</a> */
	public float getAxisAngle (Vec3f axis) {
		return MC.toD(getAxisAngleRad(axis));
	}
	/** Get the axis-angle representation of the rotation in radians. The supplied vector will receive the axis (x, y and z values)
	 * of the rotation and the value returned is the angle in radians around that axis. Note that this method will alter the
	 * supplied vector, the existing value of the vector is ignored. </p> This will normalize this quaternion if needed. The
	 * received axis is a unit vector. However, if this is an identity quaternion (no rotation), then the length of the axis may be
	 * zero.
	 * 
	 * @param axis - vector which will receive the axis
	 * @return the angle in radians
	 * @see <a href="http://en.wikipedia.org/wiki/Axis%E2%80%93angle_representation">wikipedia</a>
	 * @see <a href="http://www.euclideanspace.com/maths/geometry/rotations/conversions/quaternionToAngle">calculation</a> */
	public float getAxisAngleRad (Vec3f axis) {
		if (this.w > 1) this.normalize(); 
		float angle = (float)(2.0 * Math.acos(this.w));
		double s = Math.sqrt(1 - this.w * this.w); 
		if (s < MC.FLOAT_ROUNDING_ERROR) { 
			axis.set(this.x, this.y, this.z);
		} else {
			axis.set((float)(this.x / s), (float)(this.y / s), (float)(this.z / s));
		}

		return angle;
	}
	/** Get the angle in radians of the rotation this quaternion represents. Does not normalize the quaternion. Use
	 * {@link #getAxisAngleRad(Vec3f)} to get both the axis and the angle of this rotation. Use
	 * {@link #getAngleAroundRad(Vec3f)} to get the angle around a specific axis.
	 * @return the angle in radians of the rotation */
	public float getAngleRad () {
		return (float)(2.0 * Math.acos((this.w > 1) ? (this.w / length()) : this.w));
	}
	/** Get the angle in degrees of the rotation this quaternion represents. Use {@link #getAxisAngle(Vec3f)} to get both the axis
	 * and the angle of this rotation. Use {@link #getAngleAround(Vec3f)} to get the angle around a specific axis.
	 * @return the angle in degrees of the rotation */
	public float getAngle () {
		return MC.toD(getAngleRad());
	}
	/** Get the angle in radians of the rotation around the specified axis. The axis must be normalized.
	 * @param axisX - the x component of the normalized axis for which to get the angle
	 * @param axisY - the y component of the normalized axis for which to get the angle
	 * @param axisZ - the z component of the normalized axis for which to get the angle
	 * @return the angle in radians of the rotation around the specified axis */
	public float getAngleAroundRad (final float axisX, final float axisY, final float axisZ) {
		final float d = Vec3f.dot(this.x, this.y, this.z, axisX, axisY, axisZ);
		final float l2 = Quat.length2(axisX * d, axisY * d, axisZ * d, this.w);
		return MC.isZero(l2) ? 0f : (float)(2.0 * Math.acos(MC.clamp((float) (this.w / Math.sqrt(l2)), -1f, 1f)));
	}
	/** Get the angle in radians of the rotation around the specified axis. The axis must be normalized.
	 * @param axis - the normalized axis for which to get the angle
	 * @return the angle in radians of the rotation around the specified axis */
	public float getAngleAroundRad (final Vec3f axis) {
		return getAngleAroundRad(axis.x(), axis.y(), axis.z());
	}
	/** Get the angle in degrees of the rotation around the specified axis. The axis must be normalized.
	 * @param axisX - the x component of the normalized axis for which to get the angle
	 * @param axisY - the y component of the normalized axis for which to get the angle
	 * @param axisZ - the z component of the normalized axis for which to get the angle
	 * @return the angle in degrees of the rotation around the specified axis */
	public float getAngleAround (final float axisX, final float axisY, final float axisZ) {
		return MC.toD(getAngleAroundRad(axisX, axisY, axisZ));
	}
	/** Get the angle in degrees of the rotation around the specified axis. The axis must be normalized.
	 * @param axis - the normalized axis for which to get the angle
	 * @return the angle in degrees of the rotation around the specified axis */
	public float getAngleAround (final Vec3f axis) {
		return getAngleAround(axis.x(), axis.y(), axis.z());
	}
	/** Set this quaternion to the rotation between two vectors.
	 * @param v1 - The base vector, which should be normalized.
	 * @param v2 - The target vector, which should be normalized.
	 * @return this quaternion */
	public Quat setFromCross (final Vec3f v1, final Vec3f v2) {
		final float dot = MC.clamp(v1.dot(v2), -1f, 1f);
		final float angle = (float)Math.acos(dot);
		return setFromAxisRad(v1.y() * v2.z() - v1.z() * v2.y(), v1.z() * v2.x() - v1.x() * v2.z(), v1.x() * v2.y() - v1.y() * v2.x(), angle);
	}
	/** Set this quaternion to the rotation between two vectors.
	 * @param x1 - The base vectors x value, which should be normalized.
	 * @param y1 - The base vectors y value, which should be normalized.
	 * @param z1 - The base vectors z value, which should be normalized.
	 * @param x2 - The target vector x value, which should be normalized.
	 * @param y2 - The target vector y value, which should be normalized.
	 * @param z2 - The target vector z value, which should be normalized.
	 * @return this quaternion */
	public Quat setFromCross (final float x1, final float y1, final float z1, final float x2, final float y2, final float z2) {
		final float dot = MC.clamp(Vec3f.dot(x1, y1, z1, x2, y2, z2), -1f, 1f);
		final float angle = (float)Math.acos(dot);
		return setFromAxisRad(y1 * z2 - z1 * y2, z1 * x2 - x1 * z2, x1 * y2 - y1 * x2, angle);
	}
	/** Sets the Quaternion from the given matrix, optionally removing any scaling. 
	 * @return this quaternion*/
	public Quat setFromMatrix (boolean normalizeAxes, Mat4f matrix) {
		return setFromAxes(normalizeAxes, matrix.m[Mat4f.M00], matrix.m[Mat4f.M01], matrix.m[Mat4f.M02],
			matrix.m[Mat4f.M10], matrix.m[Mat4f.M11], matrix.m[Mat4f.M12], matrix.m[Mat4f.M20],
			matrix.m[Mat4f.M21], matrix.m[Mat4f.M22]);
	}
	/** Sets the Quaternion from the given rotation matrix, which must not contain scaling.
	 * @return this quaternion */
	public Quat setFromMatrix (Mat4f matrix) {
		return setFromMatrix(false, matrix);
	}
	/** <p>
	 * Sets the Quaternion from the given x-, y- and z-axis which have to be orthonormal.
	 * </p>
	 * 
	 * <p>
	 * Taken from Bones framework for JPCT, see http://www.aptalkarga.com/bones/ which in turn took it from Graphics Gem code at
	 * ftp://ftp.cis.upenn.edu/pub/graphics/shoemake/quatut.ps.Z.
	 * </p>
	 * 
	 * @param xx - x-axis x-coordinate
	 * @param xy - x-axis y-coordinate
	 * @param xz - x-axis z-coordinate
	 * @param yx - y-axis x-coordinate
	 * @param yy - y-axis y-coordinate
	 * @param yz - y-axis z-coordinate
	 * @param zx - z-axis x-coordinate
	 * @param zy - z-axis y-coordinate
	 * @param zz - z-axis z-coordinate */
	public Quat setFromAxes (float xx, float xy, float xz, float yx, float yy, float yz, float zx, float zy, float zz) {
		return setFromAxes(false, xx, xy, xz, yx, yy, yz, zx, zy, zz);
	}
	/** <p>
	 * Sets the Quaternion from the given x-, y- and z-axis.
	 * </p>
	 * 
	 * <p>
	 * Taken from Bones framework for JPCT, see http://www.aptalkarga.com/bones/ which in turn took it from Graphics Gem code at
	 * ftp://ftp.cis.upenn.edu/pub/graphics/shoemake/quatut.ps.Z.
	 * </p>
	 * 
	 * @param normalizeAxes whether to normalize the axes (necessary when they contain scaling)
	 * @param xx - x-axis x-coordinate
	 * @param xy - x-axis y-coordinate
	 * @param xz - x-axis z-coordinate
	 * @param yx - y-axis x-coordinate
	 * @param yy - y-axis y-coordinate
	 * @param yz - y-axis z-coordinate
	 * @param zx - z-axis x-coordinate
	 * @param zy - z-axis y-coordinate
	 * @param zz - z-axis z-coordinate */
	public Quat setFromAxes (boolean normalizeAxes, float xx, float xy, float xz, float yx, float yy, float yz, float zx,
		float zy, float zz) {
		if (normalizeAxes) {
			final float lx = 1f / Vec3f.length(xx, xy, xz);
			final float ly = 1f / Vec3f.length(yx, yy, yz);
			final float lz = 1f / Vec3f.length(zx, zy, zz);
			xx *= lx;
			xy *= lx;
			xz *= lx;
			yz *= ly;
			yy *= ly;
			yz *= ly;
			zx *= lz;
			zy *= lz;
			zz *= lz;
		}
		final float t = xx + yy + zz;

		if (t >= 0) { 
			float s = (float)Math.sqrt(t + 1); 
			w = 0.5f * s;
			s = 0.5f / s; 
			x = (zy - yz) * s;
			y = (xz - zx) * s;
			z = (yx - xy) * s;
		} else if ((xx > yy) && (xx > zz)) {
			float s = (float)Math.sqrt(1.0 + xx - yy - zz);
			x = s * 0.5f; 
			s = 0.5f / s;
			y = (yx + xy) * s;
			z = (xz + zx) * s;
			w = (zy - yz) * s;
		} else if (yy > zz) {
			float s = (float)Math.sqrt(1.0 + yy - xx - zz); 
			y = s * 0.5f;
			s = 0.5f / s;
			x = (yx + xy) * s;
			z = (zy + yz) * s;
			w = (xz - zx) * s;
		} else {
			float s = (float)Math.sqrt(1.0 + zz - xx - yy); 
			z = s * 0.5f;
			s = 0.5f / s;
			x = (xz + zx) * s;
			y = (zy + yz) * s;
			w = (yx - xy) * s;
		}

		return this;
	}
	/** Fills a 4x4 matrix with the rotation matrix represented by this quaternion.
	 * @param matrix - Matrix to fill */
	public void toMatrix (final float[] matrix) {
		final float xx = x * x;
		final float xy = x * y;
		final float xz = x * z;
		final float xw = x * w;
		final float yy = y * y;
		final float yz = y * z;
		final float yw = y * w;
		final float zz = z * z;
		final float zw = z * w;

		matrix[Mat4f.M00] = 1 - 2 * (yy + zz);
		matrix[Mat4f.M01] = 2 * (xy - zw);
		matrix[Mat4f.M02] = 2 * (xz + yw);
		matrix[Mat4f.M03] = 0;
		matrix[Mat4f.M10] = 2 * (xy + zw);
		matrix[Mat4f.M11] = 1 - 2 * (xx + zz);
		matrix[Mat4f.M12] = 2 * (yz - xw);
		matrix[Mat4f.M13] = 0;
		matrix[Mat4f.M20] = 2 * (xz - yw);
		matrix[Mat4f.M21] = 2 * (yz + xw);
		matrix[Mat4f.M22] = 1 - 2 * (xx + yy);
		matrix[Mat4f.M23] = 0;
		matrix[Mat4f.M30] = 0;
		matrix[Mat4f.M31] = 0;
		matrix[Mat4f.M32] = 0;
		matrix[Mat4f.M33] = 1;
	}
	/**
	 * Nonspherical linear interpolation between this quaternion and the other quaternion,
	 * based on the alpha value of range [0,1] 
	 * @param end - the end quaternion
	 * @param alpha - alpha in range of [0,1]
	 * @return this quaternion
	 */
	public Quat nlerp(Quat end, float alpha) {
		final float dot = dot(end);

		float scale0 = 1 - alpha;
		float scale1 = alpha;

		if (dot < 0.f) scale1 = -scale1;
		
		x = (scale0 * x) + (scale1 * end.x);
		y = (scale0 * y) + (scale1 * end.y);
		z = (scale0 * z) + (scale1 * end.z);
		w = (scale0 * w) + (scale1 * end.w);

		return this;
	}
	/** Non-spherical linear interpolation between this quaternion and the other quaternion, based on the alpha value in the range
	 * [0,1]. Taken from Bones framework for JPCT, see http://www.aptalkarga.com/bones/
	 * @param end - the end quaternion
	 * @param alpha - alpha in the range [0,1]
	 * @return this quaternion */
	public Quat slerp (Quat end, float alpha) {
		final float dot = dot(end);
		float absDot = dot < 0.f ? -dot : dot;

		float scale0 = 1 - alpha;
		float scale1 = alpha;

		if ((1 - absDot) > 0.1) {
			final double angle = Math.acos(absDot);
			final double invSinTheta = 1f / Math.sin(angle);

			scale0 = (float)(Math.sin((1 - alpha) * angle) * invSinTheta);
			scale1 = (float)(Math.sin((alpha * angle)) * invSinTheta);
		}

		if (dot < 0.f) scale1 = -scale1;

		x = (scale0 * x) + (scale1 * end.x);
		y = (scale0 * y) + (scale1 * end.y);
		z = (scale0 * z) + (scale1 * end.z);
		w = (scale0 * w) + (scale1 * end.w);

		return this;
	}
	/**
	 * Calculates (this quaternion)^alpha where alpha is a real number and stores the result in this quaternion.
	 * See http://en.wikipedia.org/wiki/Quaternion#Exponential.2C_logarithm.2C_and_power
	 * @param alpha - Exponent
	 * @return this quaternion */
	public Quat exp (float alpha) {

		float norm = length();
		float normExp = (float)Math.pow(norm, alpha);

		float theta = (float)Math.acos(w / norm);

		float coeff = 0;
		if(Math.abs(theta) < 0.001) 
			coeff = normExp*alpha / norm;
		else
			coeff = (float)(normExp*Math.sin(alpha*theta) / (norm*Math.sin(theta)));

		w = (float)(normExp*Math.cos(alpha*theta));
		x *= coeff;
		y *= coeff;
		z *= coeff;

		normalize();

		return this;
	}
	
	public float x() {
		return x;
	}

	public float y() {
		return y;
	}

	public float z() {
		return z;
	}

	public float w() {
		return w;
	}

	@Override
	public String toString () {
		return "(" + x + ", " + y + ", " + z + ", " + w + ")";
	}
	
	@Override
	public int hashCode () {
		final int prime = 31;
		int result = 17;
		result = prime * result + Float.floatToRawIntBits(w);
		result = prime * result + Float.floatToRawIntBits(x);
		result = prime * result + Float.floatToRawIntBits(y);
		result = prime * result + Float.floatToRawIntBits(z);
		return result;
	}

	@Override
	public boolean equals (Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Quat)) {
			return false;
		}
		Quat other = (Quat)obj;
		return (Float.floatToRawIntBits(w) == Float.floatToRawIntBits(other.w))
			&& (Float.floatToRawIntBits(x) == Float.floatToRawIntBits(other.x))
			&& (Float.floatToRawIntBits(y) == Float.floatToRawIntBits(other.y))
			&& (Float.floatToRawIntBits(z) == Float.floatToRawIntBits(other.z));
	}
}
