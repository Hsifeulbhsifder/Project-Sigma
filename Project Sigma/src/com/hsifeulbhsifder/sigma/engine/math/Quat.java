package com.hsifeulbhsifder.sigma.engine.math;


public class Quat {
	public static final Quat X = new Quat(1, 0, 0, 0);
	public static final Quat Y = new Quat(0, 1, 0, 0);
	public static final Quat Z = new Quat(0, 0, 1, 0);
	public static final Quat W = new Quat(0, 0, 0, 1);
	public static final Quat ZERO = new Quat();
	
	private float x;
	private float y;
	private float z;
	private float w;
	
	public Quat() {
		identity();
	}
	
	public Quat (float x, float y, float z, float w) {
		this.set(x, y, z, w);
	}
	
	public Quat (Quat quaternion) {
		this.set(quaternion);
	}
	
	public Quat (Vec3f axis, float angle) {
		this.set(axis, angle);
	}

	public Quat set (float x, float y, float z, float w) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
		return this;
	}
	
	public Quat set (Quat quaternion) {
		return this.set(quaternion.x, quaternion.y, quaternion.z, quaternion.w);
	}
	
	public Quat set (Vec3f axis, float angle) {
		return setFromAxis(axis, angle);
	}
	
	public Quat copy () {
		return new Quat(this);
	}
	
	public float length() {
		return (float) Math.sqrt(x * x + y * y + z * z + w * w);
	}
	
	public float length2 () {
		return x * x + y * y + z * z + w * w;
	}
	
	public final static float length2 (final float x, final float y, final float z, final float w) {
		return x * x + y * y + z * z + w * w;
	}
	
	public Quat setEulerAngles (float yaw, float pitch, float roll) {
		return setEulerAnglesRad(MC.toR(yaw), MC.toR(pitch), MC.toR(roll));
	}
	
	public Quat setEulerAnglesRad (float yaw, float pitch, float roll) {
		final float hr = roll * 0.5f;
		final float shr = (float)Math.sin(hr);
		final float chr = (float)Math.cos(hr);
		final float hp = pitch * 0.5f;
		final float shp = (float)Math.sin(hp);
		final float chp = (float)Math.cos(hp);
		final float hy = yaw * 0.5f;
		final float shy = (float)Math.sin(hy);
		final float chy = (float)Math.cos(hy);
		final float chy_shp = chy * shp;
		final float shy_chp = shy * chp;
		final float chy_chp = chy * chp;
		final float shy_shp = shy * shp;

		x = (chy_shp * chr) + (shy_chp * shr); // cos(yaw/2) * sin(pitch/2) * cos(roll/2) + sin(yaw/2) * cos(pitch/2) * sin(roll/2)
		y = (shy_chp * chr) - (chy_shp * shr); // sin(yaw/2) * cos(pitch/2) * cos(roll/2) - cos(yaw/2) * sin(pitch/2) * sin(roll/2)
		z = (chy_chp * shr) - (shy_shp * chr); // cos(yaw/2) * cos(pitch/2) * sin(roll/2) - sin(yaw/2) * sin(pitch/2) * cos(roll/2)
		w = (chy_chp * chr) + (shy_shp * shr); // cos(yaw/2) * cos(pitch/2) * cos(roll/2) + sin(yaw/2) * sin(pitch/2) * sin(roll/2)
		return this;
	}
	
	public int getGimbalPole() {
		final float t = y*x+z*w;
		return t > 0.499f ? 1 : (t < -0.499f ? -1 : 0);
	}
	
	public float getRollRad() {
		final int pole = getGimbalPole();
		return pole == 0 ? MC.atan2(2f*(w*z + y*x), 1f - 2f * (x*x + z*z)) : (float)pole * 2f * MC.atan2(y, w);
	}
	
	public float getRoll() {
		return MC.toD(getRollRad());
	}
	
	public float getPitchRad() {
		final int pole = getGimbalPole();
		return pole == 0 ? (float)Math.asin(MC.clamp(2f*(w*x-z*y), -1f, 1f)) : (float)pole * MC.PI * 0.5f;
	}

	public float getPitch() {
		return MC.toD(getPitchRad());
	}
	
	public float getYawRad() {
		return getGimbalPole() == 0 ? MC.atan2(2f*(y*w + x*z), 1f - 2f*(y*y+x*x)) : 0f;
	}
	
	public float getYaw() {
		return MC.toD(getYawRad());
	}
	
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
	
	public float dot (final Quat other) {
		return this.x * other.x + this.y * other.y + this.z * other.z + this.w * other.w;
	}
	
	public Quat conjugate () {
		x = -x;
		y = -y;
		z = -z;
		return this;
	}
	
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
	
	public Quat mul(float s) {
		this.x *= s;
		this.y *= s;
		this.z *= s;
		this.w *= s;
		return this;
	}
	
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
	
	public Quat add(Quat q){
		this.x += q.x;
		this.y += q.y;
		this.z += q.z;
		this.w += q.w;
		return this;
	}
	
	public Quat add(float qx, float qy, float qz, float qw){
		this.x += qx;
		this.y += qy;
		this.z += qz;
		this.w += qw;
		return this;
	}
	
	public Quat sub(Quat q){
		this.x -= q.x;
		this.y -= q.y;
		this.z -= q.z;
		this.w -= q.w;
		return this;
	}
	
	public Quat sub(float qx, float qy, float qz, float qw){
		this.x -= qx;
		this.y -= qy;
		this.z -= qz;
		this.w -= qw;
		return this;
	}
	
	public Quat identity() {
		return this.set(0, 0, 0, 1);
	}
	
	public boolean isIdentity () {
		return MC.isZero(x) && MC.isZero(y) && MC.isZero(z) && MC.isEqual(w, 1f);
	}
	
	public boolean isIdentity (final float tolerance) {
		return MC.isZero(x, tolerance) && MC.isZero(y, tolerance) && MC.isZero(z, tolerance)
			&& MC.isEqual(w, 1f, tolerance);
	}
	
	public Quat setFromAxis (final Vec3f axis, final float degrees) {
		return setFromAxis(axis.x(), axis.y(), axis.z(), degrees);
	}	
	
	public Quat setFromAxisRad (final Vec3f axis, final float radians) {
		return setFromAxisRad(axis.x(), axis.y(), axis.z(), radians);
	}
	
	public Quat setFromAxis (final float x, final float y, final float z, final float degrees) {
		return setFromAxisRad(x, y, z, MC.toR(degrees));
	}
	
	public Quat setFromAxisRad (final float x, final float y, final float z, final float radians) {
		float d = Vec3f.length(x, y, z);
		if (d == 0f) return identity();
		d = 1f / d;
		float l_ang = radians;
		float l_sin = (float)Math.sin(l_ang / 2);
		float l_cos = (float)Math.cos(l_ang / 2);
		return this.set(d * x * l_sin, d * y * l_sin, d * z * l_sin, l_cos).normalize();
	}
	
	public float getAxisAngle (Vec3f axis) {
		return MC.toD(getAxisAngleRad(axis));
	}

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

	public float getAngleRad () {
		return (float)(2.0 * Math.acos((this.w > 1) ? (this.w / length()) : this.w));
	}

	public float getAngle () {
		return MC.toD(getAngleRad());
	}
	
	public float getAngleAroundRad (final float axisX, final float axisY, final float axisZ) {
		final float d = Vec3f.dot(this.x, this.y, this.z, axisX, axisY, axisZ);
		final float l2 = Quat.length2(axisX * d, axisY * d, axisZ * d, this.w);
		return MC.isZero(l2) ? 0f : (float)(2.0 * Math.acos(MC.clamp((float) (this.w / Math.sqrt(l2)), -1f, 1f)));
	}

	public float getAngleAroundRad (final Vec3f axis) {
		return getAngleAroundRad(axis.x(), axis.y(), axis.z());
	}

	public float getAngleAround (final float axisX, final float axisY, final float axisZ) {
		return MC.toD(getAngleAroundRad(axisX, axisY, axisZ));
	}

	public float getAngleAround (final Vec3f axis) {
		return getAngleAround(axis.x(), axis.y(), axis.z());
	}
	
	public Quat setFromCross (final Vec3f v1, final Vec3f v2) {
		final float dot = MC.clamp(v1.dot(v2), -1f, 1f);
		final float angle = (float)Math.acos(dot);
		return setFromAxisRad(v1.y() * v2.z() - v1.z() * v2.y(), v1.z() * v2.x() - v1.x() * v2.z(), v1.x() * v2.y() - v1.y() * v2.x(), angle);
	}
	
	public Quat setFromCross (final float x1, final float y1, final float z1, final float x2, final float y2, final float z2) {
		final float dot = MC.clamp(Vec3f.dot(x1, y1, z1, x2, y2, z2), -1f, 1f);
		final float angle = (float)Math.acos(dot);
		return setFromAxisRad(y1 * z2 - z1 * y2, z1 * x2 - x1 * z2, x1 * y2 - y1 * x2, angle);
	}
	
	public Quat setFromMatrix (boolean normalizeAxes, Mat4f matrix) {
		return setFromAxes(normalizeAxes, matrix.m[Mat4f.M00], matrix.m[Mat4f.M01], matrix.m[Mat4f.M02],
			matrix.m[Mat4f.M10], matrix.m[Mat4f.M11], matrix.m[Mat4f.M12], matrix.m[Mat4f.M20],
			matrix.m[Mat4f.M21], matrix.m[Mat4f.M22]);
	}

	public Quat setFromMatrix (Mat4f matrix) {
		return setFromMatrix(false, matrix);
	}
	
	public Quat setFromAxes (float xx, float xy, float xz, float yx, float yy, float yz, float zx, float zy, float zz) {
		return setFromAxes(false, xx, xy, xz, yx, yy, yz, zx, zy, zz);
	}

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
