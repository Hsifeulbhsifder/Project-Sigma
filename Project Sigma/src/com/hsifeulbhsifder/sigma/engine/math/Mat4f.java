package com.hsifeulbhsifder.sigma.engine.math;

import com.hsifeulbhsifder.sigma.engine.utils.SigmaException;

/**
 * Encapsulates a <a
 * href="http://en.wikipedia.org/wiki/Row-major_order#Column-major_order">column
 * major</a> 4 by 4 matrix. Like the {@link Vec3f} class it allows the chaining
 * of methods by returning a reference to itself. For example:
 * 
 * <pre>
 * Mat4f mat = new Mat4f().initIdentity().mul(
 * 		new Mat4f().initOrtho(-40, 40, -40, 40, -40, 40));
 * </pre>
 * 
 * @author Zaeem
 * @version 1.0
 */
public class Mat4f {

	/**
	 * XX: Typically the unrotated X component for scaling, also the cosine of
	 * the angle when rotated on the Y and/or Z axis. On Vector3 multiplication
	 * this value is multiplied with the source X component and added to the
	 * target X component.
	 */
	public static final int M00 = 0;
	/**
	 * XY: Typically the negative sine of the angle when rotated on the Z axis.
	 * On Vector3 multiplication this value is multiplied with the source Y
	 * component and added to the target X component.
	 */
	public static final int M01 = 4;
	/**
	 * XZ: Typically the sine of the angle when rotated on the Y axis. On
	 * Vector3 multiplication this value is multiplied with the source Z
	 * component and added to the target X component.
	 */
	public static final int M02 = 8;
	/**
	 * XW: Typically the translation of the X component. On Vector3
	 * multiplication this value is added to the target X component.
	 */
	public static final int M03 = 12;
	/**
	 * YX: Typically the sine of the angle when rotated on the Z axis. On
	 * Vector3 multiplication this value is multiplied with the source X
	 * component and added to the target Y component.
	 */
	public static final int M10 = 1;
	/**
	 * YY: Typically the unrotated Y component for scaling, also the cosine of
	 * the angle when rotated on the X and/or Z axis. On Vector3 multiplication
	 * this value is multiplied with the source Y component and added to the
	 * target Y component.
	 */
	public static final int M11 = 5;
	/**
	 * YZ: Typically the negative sine of the angle when rotated on the X axis.
	 * On Vector3 multiplication this value is multiplied with the source Z
	 * component and added to the target Y component.
	 */
	public static final int M12 = 9;
	/**
	 * YW: Typically the translation of the Y component. On Vector3
	 * multiplication this value is added to the target Y component.
	 */
	public static final int M13 = 13;
	/**
	 * ZX: Typically the negative sine of the angle when rotated on the Y axis.
	 * On Vector3 multiplication this value is multiplied with the source X
	 * component and added to the target Z component.
	 */
	public static final int M20 = 2;
	/**
	 * ZY: Typical the sine of the angle when rotated on the X axis. On Vector3
	 * multiplication this value is multiplied with the source Y component and
	 * added to the target Z component.
	 */
	public static final int M21 = 6;
	/**
	 * ZZ: Typically the unrotated Z component for scaling, also the cosine of
	 * the angle when rotated on the X and/or Y axis. On Vector3 multiplication
	 * this value is multiplied with the source Z component and added to the
	 * target Z component.
	 */
	public static final int M22 = 10;
	/**
	 * ZW: Typically the translation of the Z component. On Vector3
	 * multiplication this value is added to the target Z component.
	 */
	public static final int M23 = 14;
	/**
	 * WX: Typically the value zero. On Vector3 multiplication this value is
	 * ignored.
	 */
	public static final int M30 = 3;
	/**
	 * WY: Typically the value zero. On Vector3 multiplication this value is
	 * ignored.
	 */
	public static final int M31 = 7;
	/**
	 * WZ: Typically the value zero. On Vector3 multiplication this value is
	 * ignored.
	 */
	public static final int M32 = 11;
	/**
	 * WW: Typically the value one. On Vector3 multiplication this value is
	 * ignored.
	 */
	public static final int M33 = 15;

	public final float t[] = new float[16];
	public final float m[] = new float[16];

	/** Constructs an identity matrix */
	public Mat4f() {
		m[M00] = 1f;
		m[M11] = 1f;
		m[M22] = 1f;
		m[M33] = 1f;
	}

	/**
	 * Constructs a matrix from the given matrix.
	 * 
	 * @param m
	 *            - The matrix to copy. (This matrix is not modified)
	 */
	public Mat4f(Mat4f m) {
		this.set(m);
	}

	/**
	 * Constructs a matrix from the given float array. The array must have at
	 * least 16 elements; the first 16 will be copied.
	 * 
	 * @param mA
	 *            - The float array to copy. Remember that this matrix is in <a
	 *            href="http://en.wikipedia.org/wiki/Row-major_order">column
	 *            major</a> order. (The float array is not modified)
	 */
	public Mat4f(float[] mA) {
		this.set(mA);
	}

	/**
	 * Constructs a rotation matrix from the given {@link Quat}.
	 * 
	 * @param q
	 *            - The quaternion to be copied. (The quaternion is not
	 *            modified)
	 */
	public Mat4f(Quat q) {
		this.set(q);
	}

	/**
	 * Construct a matrix from the given translation, rotation and scale.
	 * 
	 * @param pos
	 *            - The translation
	 * @param rot
	 *            - The rotation, must be normalized
	 * @param scl
	 *            - The scale
	 */
	public Mat4f(Vec3f pos, Quat rot, Vec3f scl) {
		this.set(pos, rot, scl);
	}

	/**
	 * Sets the matrix to the given matrix.
	 * 
	 * @param matrix
	 *            - The matrix that is to be copied. (The given matrix is not
	 *            modified)
	 * @return This matrix for the purpose of chaining methods together.
	 */
	public Mat4f set(Mat4f matrix) {
		return set(matrix.m);
	}

	/**
	 * Sets the matrix to the given matrix as a float array. The float array
	 * must have at least 16 elements; the first 16 will be copied.
	 * 
	 * @param values
	 *            - The matrix, in float form, that is to be copied. Remember
	 *            that this matrix is in <a
	 *            href="http://en.wikipedia.org/wiki/Row-major_order">column
	 *            major</a> order.
	 * @return This matrix for the purpose of chaining methods together.
	 */
	public Mat4f set(float[] values) {
		System.arraycopy(values, 0, m, 0, m.length);
		return this;
	}

	/**
	 * Sets the matrix to a rotation matrix representing the quaternion.
	 * 
	 * @param q
	 *            - The quaternion that is to be used to set this matrix.
	 * @return This matrix for the purpose of chaining methods together.
	 */
	public Mat4f set(Quat q) {
		return set(q.x(), q.y(), q.z(), q.w());
	}

	/**
	 * Sets the matrix to a rotation matrix representing the quaternion.
	 * 
	 * @param qX
	 *            - The X component of the quaternion that is to be used to set
	 *            this matrix.
	 * @param qY
	 *            - The Y component of the quaternion that is to be used to set
	 *            this matrix.
	 * @param qZ
	 *            - The Z component of the quaternion that is to be used to set
	 *            this matrix.
	 * @param qW
	 *            - The W component of the quaternion that is to be used to set
	 *            this matrix.
	 * @return This matrix for the purpose of chaining methods together.
	 */
	public Mat4f set(float qX, float qY, float qZ, float qW) {
		return set(0f, 0f, 0f, qX, qY, qZ, qW);
	}

	/**
	 * Set this matrix to the specified translation and rotation.
	 * 
	 * @param pos
	 *            - The translation
	 * @param ori
	 *            - The rotation, must be normalized
	 * @return This matrix for chaining
	 */
	public Mat4f set(Vec3f pos, Quat ori) {
		return set(pos.x(), pos.y(), pos.z(), ori.x(), ori.y(), ori.z(),
				ori.w());
	}

	/**
	 * Sets the matrix to a rotation matrix representing the translation and
	 * quaternion.
	 * 
	 * @param translationX
	 *            - The X component of the translation that is to be used to set
	 *            this matrix.
	 * @param translationY
	 *            - The Y component of the translation that is to be used to set
	 *            this matrix.
	 * @param translationZ
	 *            - The Z component of the translation that is to be used to set
	 *            this matrix.
	 * @param quaternionX
	 *            - The X component of the quaternion that is to be used to set
	 *            this matrix.
	 * @param quaternionY
	 *            - The Y component of the quaternion that is to be used to set
	 *            this matrix.
	 * @param quaternionZ
	 *            - The Z component of the quaternion that is to be used to set
	 *            this matrix.
	 * @param quaternionW
	 *            - The W component of the quaternion that is to be used to set
	 *            this matrix.
	 * @return This matrix for the purpose of chaining methods together.
	 */
	public Mat4f set(float translationX, float translationY,
			float translationZ, float quaternionX, float quaternionY,
			float quaternionZ, float quaternionW) {
		final float xs = quaternionX * 2f, ys = quaternionY * 2f, zs = quaternionZ * 2f;
		final float wx = quaternionW * xs, wy = quaternionW * ys, wz = quaternionW
				* zs;
		final float xx = quaternionX * xs, xy = quaternionX * ys, xz = quaternionX
				* zs;
		final float yy = quaternionY * ys, yz = quaternionY * zs, zz = quaternionZ
				* zs;

		blank();

		m[M00] = (1.0f - (yy + zz));
		m[M01] = (xy - wz);
		m[M02] = (xz + wy);
		m[M03] = translationX;

		m[M10] = (xy + wz);
		m[M11] = (1.0f - (xx + zz));
		m[M12] = (yz - wx);
		m[M13] = translationY;

		m[M20] = (xz - wy);
		m[M21] = (yz + wx);
		m[M22] = (1.0f - (xx + yy));
		m[M23] = translationZ;

		m[M33] = 1.0f;
		return this;
	}

	/**
	 * Set this matrix to the specified translation, rotation and scale.
	 * 
	 * @param position
	 *            - The translation
	 * @param orientation
	 *            - The rotation, must be normalized
	 * @param scale
	 *            - The scale
	 * @return This matrix for chaining
	 */
	public Mat4f set(Vec3f position, Quat orientation, Vec3f scale) {
		return set(position.x(), position.y(), position.z(), orientation.x(),
				orientation.y(), orientation.z(), orientation.w(), scale.x(),
				scale.y(), scale.z());
	}

	/**
	 * Sets the matrix to a rotation matrix representing the translation and
	 * quaternion.
	 * 
	 * @param translationX
	 *            - The X component of the translation that is to be used to set
	 *            this matrix.
	 * @param translationY
	 *            - The Y component of the translation that is to be used to set
	 *            this matrix.
	 * @param translationZ
	 *            - The Z component of the translation that is to be used to set
	 *            this matrix.
	 * @param quaternionX
	 *            - The X component of the quaternion that is to be used to set
	 *            this matrix.
	 * @param quaternionY
	 *            - The Y component of the quaternion that is to be used to set
	 *            this matrix.
	 * @param quaternionZ
	 *            - The Z component of the quaternion that is to be used to set
	 *            this matrix.
	 * @param quaternionW
	 *            - The W component of the quaternion that is to be used to set
	 *            this matrix.
	 * @param scaleX
	 *            - The X component of the scaling that is to be used to set
	 *            this matrix.
	 * @param scaleY
	 *            - The Y component of the scaling that is to be used to set
	 *            this matrix.
	 * @param scaleZ
	 *            - The Z component of the scaling that is to be used to set
	 *            this matrix.
	 * @return This matrix for the purpose of chaining methods together.
	 */
	public Mat4f set(float translationX, float translationY,
			float translationZ, float quaternionX, float quaternionY,
			float quaternionZ, float quaternionW, float scaleX, float scaleY,
			float scaleZ) {
		final float xs = quaternionX * 2f, ys = quaternionY * 2f, zs = quaternionZ * 2f;
		final float wx = quaternionW * xs, wy = quaternionW * ys, wz = quaternionW
				* zs;
		final float xx = quaternionX * xs, xy = quaternionX * ys, xz = quaternionX
				* zs;
		final float yy = quaternionY * ys, yz = quaternionY * zs, zz = quaternionZ
				* zs;

		blank();

		m[M00] = scaleX * (1.0f - (yy + zz));
		m[M01] = scaleY * (xy - wz);
		m[M02] = scaleZ * (xz + wy);
		m[M03] = translationX;

		m[M10] = scaleX * (xy + wz);
		m[M11] = scaleY * (1.0f - (xx + zz));
		m[M12] = scaleZ * (yz - wx);
		m[M13] = translationY;

		m[M20] = scaleX * (xz - wy);
		m[M21] = scaleY * (yz + wx);
		m[M22] = scaleZ * (1.0f - (xx + yy));
		m[M23] = translationZ;

		m[M33] = 1.0f;
		return this;
	}

	/**
	 * Sets the four columns of the matrix which correspond to the x-, y- and
	 * z-axis of the vector space this matrix creates as well as the 4th column
	 * representing the translation of any point that is multiplied by this
	 * matrix.
	 * 
	 * @param xAxis
	 *            - The x-axis.
	 * @param yAxis
	 *            - The y-axis.
	 * @param zAxis
	 *            - The z-axis.
	 * @param pos
	 *            - The translation vector.
	 */
	public Mat4f set(Vec3f xAxis, Vec3f yAxis, Vec3f zAxis, Vec3f pos) {
		blank();
		m[M00] = xAxis.x();
		m[M01] = xAxis.y();
		m[M02] = xAxis.z();
		m[M10] = yAxis.x();
		m[M11] = yAxis.y();
		m[M12] = yAxis.z();
		m[M20] = zAxis.x();
		m[M21] = zAxis.y();
		m[M22] = zAxis.z();
		m[M03] = pos.x();
		m[M13] = pos.y();
		m[M23] = pos.z();
		m[M33] = 1;
		return this;
	}

	/** @return a copy of this matrix */
	public Mat4f copy() {
		return new Mat4f(this);
	}

	/**
	 * Sets the value of all the components of the matrix to zero
	 * 
	 * @return This matrix
	 */
	public Mat4f blank() {
		m[M00] = 0f;
		m[M01] = 0f;
		m[M02] = 0f;
		m[M03] = 0f;

		m[M10] = 0f;
		m[M11] = 0f;
		m[M12] = 0f;
		m[M13] = 0f;

		m[M20] = 0f;
		m[M21] = 0f;
		m[M22] = 0f;
		m[M23] = 0f;

		m[M30] = 0f;
		m[M31] = 0f;
		m[M32] = 0f;
		m[M33] = 0f;
		return this;
	}

	/**
	 * Adds a translational component to the matrix in the 4th column. The other
	 * columns are untouched.
	 * 
	 * @param vector
	 *            - The translation vector to add to the current matrix. (This
	 *            vector is not modified)
	 * @return This matrix for the purpose of chaining methods together.
	 */
	public Mat4f trans(Vec3f vector) {
		m[M03] += vector.x();
		m[M13] += vector.y();
		m[M23] += vector.z();
		return this;
	}

	/**
	 * Adds a translational component to the matrix in the 4th column. The other
	 * columns are untouched.
	 * 
	 * @param x
	 *            - The x-component of the translation vector.
	 * @param y
	 *            - The y-component of the translation vector.
	 * @param z
	 *            - The z-component of the translation vector.
	 * @return This matrix for the purpose of chaining methods together.
	 */
	public Mat4f trans(float x, float y, float z) {
		m[M03] += x;
		m[M13] += y;
		m[M23] += z;
		return this;
	}

	/**
	 * Sets the matrix to an identity matrix.
	 * 
	 * @return This matrix for the purpose of chaining methods together.
	 */
	public Mat4f initIdentity() {
		blank();
		m[M00] = 1f;
		m[M11] = 1f;
		m[M22] = 1f;
		m[M33] = 1f;

		return this;
	}

	/**
	 * Sets the matrix to an translation matrix from a blank matrix.
	 * 
	 * @return This matrix for the purpose of chaining methods together.
	 */
	public Mat4f initTranslation(float x, float y, float z) {
		blank();
		m[M00] = 1f;
		m[M03] = x;
		m[M11] = 1f;
		m[M13] = y;
		m[M22] = 1f;
		m[M23] = z;
		m[M33] = 1f;

		return this;
	}

	/**
	 * Sets the matrix to a projection matrix with a near- and far plane, a
	 * field of view in degrees and an aspect ratio. Note that the field of view
	 * specified is the angle in degrees for the height, the field of view for
	 * the width will be calculated according to the aspect ratio.
	 * 
	 * @param near
	 *            - The near plane
	 * @param far
	 *            - The far plane
	 * @param fovy
	 *            - The field of view of the height in degrees
	 * @param aspectRatio
	 *            - The "width over height" aspect ratio
	 * @return This matrix for the purpose of chaining methods together.
	 */
	public Mat4f initPerspective(float fov, float aspectRatio, float zNear,
			float zFar) {
		initIdentity();
		float l_fd = (float) (1.0 / Math.tan((fov * (Math.PI / 180)) / 2.0));
		float l_a1 = (zFar + zNear) / (zNear - zFar);
		float l_a2 = (2 * zFar * zNear) / (zNear - zFar);
		m[M00] = l_fd / aspectRatio;
		m[M10] = 0;
		m[M20] = 0;
		m[M30] = 0;
		m[M01] = 0;
		m[M11] = l_fd;
		m[M21] = 0;
		m[M31] = 0;
		m[M02] = 0;
		m[M12] = 0;
		m[M22] = l_a1;
		m[M32] = -1;
		m[M03] = 0;
		m[M13] = 0;
		m[M23] = l_a2;
		m[M33] = 0;

		return this;
	}

	/**
	 * Sets this matrix to an orthographic projection matrix with the origin at
	 * (x,y) extending by width and height. The near plane is set to 0, the far
	 * plane is set to 1.
	 * 
	 * @param x
	 *            - The x-coordinate of the origin
	 * @param y
	 *            - The y-coordinate of the origin
	 * @param width
	 *            - The width
	 * @param height
	 *            - The height
	 * @return This matrix for the purpose of chaining methods together.
	 */
	public Mat4f initOrtho2D(float x, float y, float width, float height) {
		initOrtho(x, x + width, y, y + height, 0, 1);
		return this;
	}

	/**
	 * Sets this matrix to an orthographic projection matrix with the origin at
	 * (x,y) extending by width and height, having a near and far plane.
	 * 
	 * @param x
	 *            - The x-coordinate of the origin
	 * @param y
	 *            - The y-coordinate of the origin
	 * @param width
	 *            - The width
	 * @param height
	 *            - The height
	 * @param near
	 *            - The near plane
	 * @param far
	 *            - The far plane
	 * @return This matrix for the purpose of chaining methods together.
	 */
	public Mat4f initOrtho2D(float x, float y, float width, float height,
			float near, float far) {
		initOrtho(x, x + width, y, y + height, near, far);
		return this;
	}

	/**
	 * Sets the matrix to an orthographic projection like glOrtho
	 * (http://www.opengl.org/sdk/docs/man/xhtml/glOrtho.xml) following the
	 * OpenGL equivalent
	 * 
	 * @param left
	 *            - The left clipping plane
	 * @param right
	 *            - The right clipping plane
	 * @param bottom
	 *            - The bottom clipping plane
	 * @param top
	 *            - The top clipping plane
	 * @param near
	 *            - The near clipping plane
	 * @param far
	 *            - The far clipping plane
	 * @return This matrix for the purpose of chaining methods together.
	 */
	public Mat4f initOrtho(float left, float right, float bottom, float top,
			float near, float far) {

		this.initIdentity();
		float x_orth = 2 / (right - left);
		float y_orth = 2 / (top - bottom);
		float z_orth = -2 / (far - near);

		float tx = -(right + left) / (right - left);
		float ty = -(top + bottom) / (top - bottom);
		float tz = -(far + near) / (far - near);

		m[M00] = x_orth;
		m[M10] = 0;
		m[M20] = 0;
		m[M30] = 0;
		m[M01] = 0;
		m[M11] = y_orth;
		m[M21] = 0;
		m[M31] = 0;
		m[M02] = 0;
		m[M12] = 0;
		m[M22] = z_orth;
		m[M32] = 0;
		m[M03] = tx;
		m[M13] = ty;
		m[M23] = tz;
		m[M33] = 1;

		return this;
	}

	/**
	 * Sets the 4th column to the translation vector.
	 * 
	 * @param vector
	 *            - The translation vector
	 * @return This matrix for the purpose of chaining methods together.
	 */
	public Mat4f setTranslation(Vec3f vector) {
		m[M03] = vector.x();
		m[M13] = vector.y();
		m[M23] = vector.z();
		return this;
	}

	/**
	 * Sets the 4th column to the translation vector.
	 * 
	 * @param x
	 *            - The X coordinate of the translation vector
	 * @param y
	 *            - The Y coordinate of the translation vector
	 * @param z
	 *            - The Z coordinate of the translation vector
	 * @return This matrix for the purpose of chaining methods together.
	 */
	public Mat4f setTranslation(float x, float y, float z) {
		m[M03] = x;
		m[M13] = y;
		m[M23] = z;
		return this;
	}

	/**
	 * Sets this matrix to a translation matrix, overwriting it first by an
	 * identity matrix and then setting the 4th column to the translation
	 * vector.
	 * 
	 * @param vector
	 *            - The translation vector
	 * @return This matrix for the purpose of chaining methods together.
	 */
	public Mat4f setToTranslation(Vec3f vector) {
		initIdentity();
		m[M03] = vector.x();
		m[M13] = vector.y();
		m[M23] = vector.z();
		return this;
	}

	/**
	 * Sets this matrix to a translation matrix, overwriting it first by an
	 * identity matrix and then setting the 4th column to the translation
	 * vector.
	 * 
	 * @param x
	 *            - The x-component of the translation vector.
	 * @param y
	 *            - The y-component of the translation vector.
	 * @param z
	 *            - The z-component of the translation vector.
	 * @return This matrix for the purpose of chaining methods together.
	 */
	public Mat4f setToTranslation(float x, float y, float z) {
		initIdentity();
		m[M03] = x;
		m[M13] = y;
		m[M23] = z;
		return this;
	}

	/**
	 * Sets this matrix to a translation and scaling matrix by first overwriting
	 * it with an identity and then setting the translation vector in the 4th
	 * column and the scaling vector in the diagonal.
	 * 
	 * @param translation
	 *            - The translation vector
	 * @param scaling
	 *            - The scaling vector
	 * @return This matrix for the purpose of chaining methods together.
	 */
	public Mat4f setToTranslationAndScaling(Vec3f translation, Vec3f scaling) {
		initIdentity();
		m[M03] = translation.x();
		m[M13] = translation.y();
		m[M23] = translation.z();
		m[M00] = scaling.x();
		m[M11] = scaling.y();
		m[M22] = scaling.z();
		return this;
	}

	/**
	 * Sets this matrix to a translation and scaling matrix by first overwriting
	 * it with an identity and then setting the translation vector in the 4th
	 * column and the scaling vector in the diagonal.
	 * 
	 * @param translationX
	 *            - The x-component of the translation vector
	 * @param translationY
	 *            - The y-component of the translation vector
	 * @param translationZ
	 *            - The z-component of the translation vector
	 * @param scalingX
	 *            - The x-component of the scaling vector
	 * @param scalingY
	 *            - The x-component of the scaling vector
	 * @param scalingZ
	 *            - The x-component of the scaling vector
	 * @return This matrix for the purpose of chaining methods together.
	 */
	public Mat4f setToTranslationAndScaling(float traX, float traY, float traZ,
			float sclX, float sclY, float sclZ) {
		initIdentity();
		m[M03] = traX;
		m[M13] = traY;
		m[M23] = traZ;
		m[M00] = sclX;
		m[M11] = sclY;
		m[M22] = sclZ;
		return this;
	}

	private static final Mat4f rx = new Mat4f().blank();
	private static final Mat4f ry = new Mat4f().blank();
	private static final Mat4f rz = new Mat4f().blank();

	/**
	 * Creates a matrix that is rotated from given x, y, z values,
	 * 
	 * @param x
	 *            - x coordinate
	 * @param y
	 *            - y coordinate
	 * @param z
	 *            - z coordinate
	 * @return this matrix
	 */
	public Mat4f initRotation(float x, float y, float z) {

		x = (float) MC.toR(x);
		y = (float) MC.toR(y);
		z = (float) MC.toR(z);

		rz.m[M00] = (float) MC.cos(z);
		rz.m[M01] = -(float) MC.sin(z);
		rz.m[M10] = (float) MC.sin(z);
		rz.m[M11] = (float) MC.cos(z);
		rz.m[M22] = 1;
		rz.m[M33] = 1;

		rx.m[M00] = 1;
		rx.m[M11] = (float) MC.cos(x);
		rx.m[M12] = -(float) MC.sin(x);
		rx.m[M21] = (float) MC.sin(x);
		rx.m[M22] = (float) MC.cos(x);
		rx.m[M33] = 1;

		ry.m[M00] = (float) MC.cos(y);
		ry.m[M02] = -(float) MC.sin(y);
		ry.m[M11] = 1;
		ry.m[M20] = (float) MC.sin(y);
		ry.m[M22] = (float) MC.cos(y);
		ry.m[M33] = 1;

		System.arraycopy(rz.mul(ry.mul(rx)).m, 0, m, 0, 16);

		return this;
	}

	private static Quat quat1 = new Quat();
	private static Quat quat2 = new Quat();

	/**
	 * Sets the matrix to a rotation matrix around the given axis.
	 * 
	 * @param axis
	 *            - The axis
	 * @param degrees
	 *            - The angle in degrees
	 * @return This matrix for the purpose of chaining methods together.
	 */
	public Mat4f setToRotation(Vec3f axis, float degrees) {
		if (degrees == 0) {
			initIdentity();
			return this;
		}
		return set(quat1.set(axis, degrees));
	}

	/**
	 * Sets the matrix to a rotation matrix around the given axis.
	 * 
	 * @param axis
	 *            - The axis
	 * @param radians
	 *            - The angle in radians
	 * @return This matrix for the purpose of chaining methods together.
	 */
	public Mat4f setToRotationRad(Vec3f axis, float radians) {
		if (radians == 0) {
			initIdentity();
			return this;
		}
		return set(quat1.setFromAxisRad(axis, radians));
	}

	/**
	 * Sets the matrix to a rotation matrix around the given axis.
	 * 
	 * @param axisX
	 *            - The x-component of the axis
	 * @param axisY
	 *            - The y-component of the axis
	 * @param axisZ
	 *            - The z-component of the axis
	 * @param degrees
	 *            - The angle in degrees
	 * @return This matrix for the purpose of chaining methods together.
	 */
	public Mat4f setToRotation(float axisX, float axisY, float axisZ,
			float degrees) {
		if (degrees == 0) {
			initIdentity();
			return this;
		}
		return set(quat1.setFromAxis(axisX, axisY, axisZ, degrees));
	}

	/**
	 * Sets the matrix to a rotation matrix around the given axis.
	 * 
	 * @param axisX
	 *            - The x-component of the axis
	 * @param axisY
	 *            - The y-component of the axis
	 * @param axisZ
	 *            - The z-component of the axis
	 * @param radians
	 *            - The angle in radians
	 * @return This matrix for the purpose of chaining methods together.
	 */
	public Mat4f setToRotationRad(float axisX, float axisY, float axisZ,
			float radians) {
		if (radians == 0) {
			initIdentity();
			return this;
		}
		return set(quat1.setFromAxisRad(axisX, axisY, axisZ, radians));
	}

	/**
	 * Set the matrix to a rotation matrix between two vectors.
	 * 
	 * @param v1
	 *            - The base vector
	 * @param v2
	 *            - The target vector
	 * @return This matrix for the purpose of chaining methods together
	 */
	public Mat4f setToRotation(final Vec3f v1, final Vec3f v2) {
		return set(quat1.setFromCross(v1, v2));
	}

	/**
	 * Set the matrix to a rotation matrix between two vectors.
	 * 
	 * @param x1
	 *            - The base vectors x value
	 * @param y1
	 *            - The base vectors y value
	 * @param z1
	 *            - The base vectors z value
	 * @param x2
	 *            - The target vector x value
	 * @param y2
	 *            - The target vector y value
	 * @param z2
	 *            - The target vector z value
	 * @return This matrix for the purpose of chaining methods together
	 */
	public Mat4f setToRotation(final float x1, final float y1, final float z1,
			final float x2, final float y2, final float z2) {
		return set(quat1.setFromCross(x1, y1, z1, x2, y2, z2));
	}

	/**
	 * Sets this matrix to a rotation matrix from the given euler angles.
	 * 
	 * @param yaw
	 *            - the yaw in degrees
	 * @param pitch
	 *            - the pitch in degrees
	 * @param roll
	 *            - the roll in degrees
	 * @return This matrix
	 */
	public Mat4f setFromEulerAngles(float yaw, float pitch, float roll) {
		quat1.setEulerAngles(yaw, pitch, roll);
		return set(quat1);
	}

	/**
	 * Creates a Matrix from a 3d vector
	 * 
	 * @param x
	 *            - x-coordinate
	 * @param y
	 *            - y-coordinate
	 * @param z
	 *            - z-coordinate
	 * @return This matrix
	 */
	public Mat4f initScale(float x, float y, float z) {
		blank();
		m[M00] = x;
		m[M11] = y;
		m[M22] = z;
		m[M33] = 1;
		return this;
	}

	/**
	 * Creates a Matrix from a 3d vector
	 * 
	 * @param v
	 *            - this 3d vector
	 * @return This Vector
	 */
	public Mat4f initScale(Vec3f v) {
		blank();
		m[M00] = v.x();
		m[M11] = v.y();
		m[M22] = v.z();
		m[M33] = 1;
		return this;
	}

	/**
	 * Sets this matrix to a scaling matrix
	 * 
	 * @param vector
	 *            - The scaling vector
	 * @return This matrix for chaining.
	 */
	public Mat4f setToScaling(Vec3f vector) {
		initIdentity();
		m[M00] = vector.x();
		m[M11] = vector.y();
		m[M22] = vector.z();
		return this;
	}

	/**
	 * Sets this matrix to a scaling matrix
	 * 
	 * @param x
	 *            - The x-component of the scaling vector
	 * @param y
	 *            - The y-component of the scaling vector
	 * @param z
	 *            - The z-component of the scaling vector
	 * @return This matrix for chaining.
	 */
	public Mat4f setToScaling(float x, float y, float z) {
		initIdentity();
		m[M00] = x;
		m[M11] = y;
		m[M22] = z;
		return this;
	}

	static final Vec3f l_vez = new Vec3f();
	static final Vec3f l_vex = new Vec3f();
	static final Vec3f l_vey = new Vec3f();

	/**
	 * Sets the matrix to a look at matrix with a direction and an up vector.
	 * Multiply with a translation matrix to get a camera model view matrix.
	 * 
	 * @param direction
	 *            - The direction vector
	 * @param up
	 *            - The up vector
	 * @return This matrix for the purpose of chaining methods together.
	 */
	public Mat4f setToLookAt(Vec3f direction, Vec3f up) {
		l_vez.set(direction).normalize();
		l_vex.set(direction).normalize();
		l_vex.cross(up).normalize();
		l_vey.set(l_vex).cross(l_vez).normalize();
		initIdentity();
		m[M00] = l_vex.x();
		m[M01] = l_vex.y();
		m[M02] = l_vex.z();
		m[M10] = l_vey.x();
		m[M11] = l_vey.y();
		m[M12] = l_vey.z();
		m[M20] = -l_vez.x();
		m[M21] = -l_vez.y();
		m[M22] = -l_vez.z();

		return this;
	}

	static final Vec3f tmpVec = new Vec3f();
	static final Mat4f tmpMat = new Mat4f();

	/**
	 * Sets this matrix to a look at matrix with the given position, target and
	 * up vector.
	 * 
	 * @param position
	 *            - the position
	 * @param target
	 *            - the target
	 * @param up
	 *            - the up vector
	 * @return This matrix
	 */
	public Mat4f setToLookAt(Vec3f position, Vec3f target, Vec3f up) {
		tmpVec.set(target).sub(position);
		setToLookAt(tmpVec, up);
		this.mul(tmpMat.setToTranslation(-position.x(), -position.y(),
				-position.z()));

		return this;
	}

	static final Vec3f right = new Vec3f();
	static final Vec3f tmpForward = new Vec3f();
	static final Vec3f tmpUp = new Vec3f();

	public Mat4f setToWorld(Vec3f position, Vec3f forward, Vec3f up) {
		tmpForward.set(forward).normalize();
		right.set(tmpForward).cross(up).normalize();
		tmpUp.set(right).cross(tmpForward).normalize();

		this.set(right, tmpUp, tmpForward.mul(-1), position);
		return this;
	}

	/**
	 * Linearly interpolates between this matrix and the given matrix mixing by
	 * alpha
	 * 
	 * @param matrix
	 *            - the matrix
	 * @param alpha
	 *            - the alpha value in the range [0,1]
	 * @return This matrix for the purpose of chaining methods together.
	 */
	public Mat4f lerp(Mat4f matrix, float alpha) {
		for (int i = 0; i < 16; i++)
			this.m[i] = this.m[i] * (1 - alpha) + matrix.m[i] * alpha;
		return this;
	}

	/**
	 * Averages the given transform with this one and stores the result in this
	 * matrix. Translations and scales are lerped while rotations are slerped.
	 * 
	 * @param other
	 *            - The other transform
	 * @param w
	 *            - Weight of this transform; weight of the other transform is
	 *            (1 - w)
	 * @return This matrix for chaining
	 */
	public Mat4f average(Mat4f other, float w) {

		getScale(tmpVec);
		other.getScale(tmpForward);

		getRotation(quat1);
		other.getRotation(quat2);

		getTranslation(tmpUp);
		other.getTranslation(right);

		setToScaling(tmpVec.mul(w).add(tmpForward.mul(1 - w)));

		rotate(quat1.slerp(quat2, 1 - w));

		setTranslation(tmpUp.mul(w).add(right.mul(1 - w)));

		return this;
	}

	/**
	 * Averages the given transforms and stores the result in this matrix.
	 * Translations and scales are lerped while rotations are slerped. Does not
	 * destroy the data contained in t.
	 * 
	 * @param t
	 *            - List of transforms
	 * @return This matrix for chaining
	 */
	public Mat4f avg(Mat4f[] t) {
		final float w = 1.0f / t.length;

		tmpVec.set(t[0].getScale(tmpUp).mul(w));

		quat1.set(t[0].getRotation(quat2).exp(w));

		tmpForward.set(t[0].getTranslation(tmpUp).mul(w));

		for (int i = 1; i < t.length; i++) {

			tmpVec.add(t[i].getScale(tmpUp).mul(w));

			quat1.mul(t[i].getRotation(quat2).exp(w));

			tmpForward.add(t[i].getTranslation(tmpUp).mul(w));
		}
		quat1.normalize();

		setToScaling(tmpVec);
		rotate(quat1);
		setTranslation(tmpForward);

		return this;
	}

	/**
	 * Averages the given transforms with the given weights and stores the
	 * result in this matrix. Translations and scales are lerped while rotations
	 * are slerped. Does not destroy the data contained in t or w; Sum of w_i
	 * must be equal to 1, or unexpected results will occur.
	 * 
	 * @param t
	 *            - List of transforms
	 * @param w
	 *            - List of weights
	 * @return This matrix for chaining
	 */
	public Mat4f avg(Mat4f[] t, float[] w) {

		tmpVec.set(t[0].getScale(tmpUp).mul(w[0]));

		quat1.set(t[0].getRotation(quat2).exp(w[0]));

		tmpForward.set(t[0].getTranslation(tmpUp).mul(w[0]));

		for (int i = 1; i < t.length; i++) {

			tmpVec.add(t[i].getScale(tmpUp).mul(w[i]));

			quat1.mul(t[i].getRotation(quat2).exp(w[i]));

			tmpForward.add(t[i].getTranslation(tmpUp).mul(w[i]));
		}
		quat1.normalize();

		setToScaling(tmpVec);
		rotate(quat1);
		setTranslation(tmpForward);

		return this;
	}

	public Vec3f getTranslation(Vec3f pos) {
		pos.set(m[M03], m[M13], m[M23]);
		return pos;
	}

	/**
	 * Gets the rotation of this matrix.
	 * 
	 * @param rotation
	 *            - The {@link Quat} to receive the rotation
	 * @param normalizeAxes
	 *            - True to normalize the axes, necessary when the matrix might
	 *            also include scaling.
	 * @return The provided {@link Quat} for chaining.
	 */
	public Quat getRotation(Quat rot, boolean normalizeAxes) {
		return rot.setFromMatrix(normalizeAxes, this);
	}

	/**
	 * Gets the rotation of this matrix.
	 * 
	 * @param rotation
	 *            - The {@link Quat} to receive the rotation
	 * @return The provided {@link Quat} for chaining.
	 */
	public Quat getRotation(Quat rotation) {
		return rotation.setFromMatrix(this);
	}

	/** @return the squared scale factor on the X axis */
	public float getScaleXSquared() {
		return m[M00] * m[M00] + m[M01] * m[M01] + m[M02] * m[M02];
	}

	/** @return the squared scale factor on the Y axis */
	public float getScaleYSquared() {
		return m[M10] * m[M10] + m[M11] * m[M11] + m[M12] * m[M12];
	}

	/** @return the squared scale factor on the Z axis */
	public float getScaleZSquared() {
		return m[M20] * m[M20] + m[M21] * m[M21] + m[M22] * m[M22];
	}

	/** @return the scale factor on the X axis (non-negative) */
	public float getScaleX() {
		return (MC.isZero(m[M01]) && MC.isZero(m[M02])) ? Math.abs(m[M00])
				: (float) Math.sqrt(getScaleXSquared());
	}

	/** @return the scale factor on the Y axis (non-negative) */
	public float getScaleY() {
		return (MC.isZero(m[M10]) && MC.isZero(m[M12])) ? Math.abs(m[M11])
				: (float) Math.sqrt(getScaleYSquared());
	}

	/** @return the scale factor on the X axis (non-negative) */
	public float getScaleZ() {
		return (MC.isZero(m[M20]) && MC.isZero(m[M21])) ? Math.abs(m[M22])
				: (float) Math.sqrt(getScaleZSquared());
	}

	/**
	 * @param scale
	 *            - The vector which will receive the (non-negative) scale
	 *            components on each axis.
	 * @return The provided vector for chaining.
	 */
	public Vec3f getScale(Vec3f scale) {
		return scale.set(getScaleX(), getScaleY(), getScaleZ());
	}

	/** removes the translational part and transposes the matrix. */
	public Mat4f toNormalMatrix() {
		m[M03] = 0;
		m[M13] = 0;
		m[M23] = 0;
		return inverse().transpose();
	}

	public Mat4f transpose() {
		t[M00] = m[M00];
		t[M01] = m[M10];
		t[M02] = m[M20];
		t[M03] = m[M30];
		t[M10] = m[M01];
		t[M11] = m[M11];
		t[M12] = m[M21];
		t[M13] = m[M31];
		t[M20] = m[M02];
		t[M21] = m[M12];
		t[M22] = m[M22];
		t[M23] = m[M32];
		t[M30] = m[M03];
		t[M31] = m[M13];
		t[M32] = m[M23];
		t[M33] = m[M33];
		return set(t);
	}

	/**
	 * Postmultiplies this matrix by a translation matrix. Postmultiplication is
	 * also used by OpenGL ES' glTranslate/glRotate/glScale
	 * 
	 * @param translation
	 *            - the translation vector
	 * @return This matrix for the purpose of chaining methods together.
	 */
	public Mat4f translate(Vec3f translation) {
		return translate(translation.x(), translation.y(), translation.z());
	}

	/**
	 * Postmultiplies this matrix by a translation matrix. Postmultiplication is
	 * also used by OpenGL ES' 1.x glTranslate/glRotate/glScale.
	 * 
	 * @param x
	 *            - Translation in the x-axis.
	 * @param y
	 *            - Translation in the y-axis.
	 * @param z
	 *            - Translation in the z-axis.
	 * @return This matrix for the purpose of chaining methods together.
	 */
	public Mat4f translate(float x, float y, float z) {
		t[M00] = 1;
		t[M01] = 0;
		t[M02] = 0;
		t[M03] = x;
		t[M10] = 0;
		t[M11] = 1;
		t[M12] = 0;
		t[M13] = y;
		t[M20] = 0;
		t[M21] = 0;
		t[M22] = 1;
		t[M23] = z;
		t[M30] = 0;
		t[M31] = 0;
		t[M32] = 0;
		t[M33] = 1;

		mul(m, t);
		return this;
	}

	/**
	 * Postmultiplies this matrix with a (counter-clockwise) rotation matrix.
	 * Postmultiplication is also used by OpenGL ES' 1.x
	 * glTranslate/glRotate/glScale.
	 * 
	 * @param axis
	 *            - The vector axis to rotate around.
	 * @param degrees
	 *            - The angle in degrees.
	 * @return This matrix for the purpose of chaining methods together.
	 */
	public Mat4f rotate(Vec3f axis, float degrees) {
		if (degrees == 0)
			return this;
		quat1.set(axis, degrees);
		return rotate(quat1);
	}

	/**
	 * Postmultiplies this matrix with a (counter-clockwise) rotation matrix.
	 * Postmultiplication is also used by OpenGL ES' 1.x
	 * glTranslate/glRotate/glScale.
	 * 
	 * @param axis
	 *            - The vector axis to rotate around.
	 * @param radians
	 *            - The angle in radians.
	 * @return This matrix for the purpose of chaining methods together.
	 */
	public Mat4f rotateRad(Vec3f axis, float radians) {
		if (radians == 0)
			return this;
		quat1.setFromAxisRad(axis, radians);
		return rotate(quat1);
	}

	/**
	 * Postmultiplies this matrix with a (counter-clockwise) rotation matrix.
	 * Postmultiplication is also used by OpenGL ES' 1.x
	 * glTranslate/glRotate/glScale
	 * 
	 * @param axisX
	 *            - The x-axis component of the vector to rotate around.
	 * @param axisY
	 *            - The y-axis component of the vector to rotate around.
	 * @param axisZ
	 *            - The z-axis component of the vector to rotate around.
	 * @param degrees
	 *            - The angle in degrees
	 * @return This matrix for the purpose of chaining methods together.
	 */
	public Mat4f rotate(float axisX, float axisY, float axisZ, float degrees) {
		if (degrees == 0)
			return this;
		quat1.setFromAxis(axisX, axisY, axisZ, degrees);
		return rotate(quat1);
	}

	/**
	 * Postmultiplies this matrix with a (counter-clockwise) rotation matrix.
	 * Postmultiplication is also used by OpenGL ES' 1.x
	 * glTranslate/glRotate/glScale
	 * 
	 * @param axisX
	 *            - The x-axis component of the vector to rotate around.
	 * @param axisY
	 *            - The y-axis component of the vector to rotate around.
	 * @param axisZ
	 *            - The z-axis component of the vector to rotate around.
	 * @param radians
	 *            - The angle in radians
	 * @return This matrix for the purpose of chaining methods together.
	 */
	public Mat4f rotateRad(float axisX, float axisY, float axisZ, float radians) {
		if (radians == 0)
			return this;
		quat1.setFromAxisRad(axisX, axisY, axisZ, radians);
		return rotate(quat1);
	}

	/**
	 * Postmultiplies this matrix with a (counter-clockwise) rotation matrix.
	 * Postmultiplication is also used by OpenGL ES' 1.x
	 * glTranslate/glRotate/glScale.
	 * 
	 * @param rotation
	 *            - the rotation vector
	 * @return This matrix for the purpose of chaining methods together.
	 */
	public Mat4f rotate(Quat rotation) {
		rotation.toMatrix(t);
		mul(m, t);
		return this;
	}

	/**
	 * Postmultiplies this matrix by the rotation between two vectors.
	 * 
	 * @param v1
	 *            - The base vector
	 * @param v2
	 *            - The target vector
	 * @return This matrix for the purpose of chaining methods together
	 */
	public Mat4f rotate(final Vec3f v1, final Vec3f v2) {
		return rotate(quat1.setFromCross(v1, v2));
	}

	/**
	 * Postmultiplies this matrix with a scale matrix. Postmultiplication is
	 * also used by OpenGL ES' 1.x glTranslate/glRotate/glScale.
	 * 
	 * @param scaleX
	 *            - The scale in the x-axis.
	 * @param scaleY
	 *            - The scale in the y-axis.
	 * @param scaleZ
	 *            - The scale in the z-axis.
	 * @return This matrix for the purpose of chaining methods together.
	 */
	public Mat4f scale(float scaleX, float scaleY, float scaleZ) {
		t[M00] = scaleX;
		t[M01] = 0;
		t[M02] = 0;
		t[M03] = 0;
		t[M10] = 0;
		t[M11] = scaleY;
		t[M12] = 0;
		t[M13] = 0;
		t[M20] = 0;
		t[M21] = 0;
		t[M22] = scaleZ;
		t[M23] = 0;
		t[M30] = 0;
		t[M31] = 0;
		t[M32] = 0;
		t[M33] = 1;

		mul(m, t);
		return this;
	}

	/**
	 * Copies the 4x3 upper-left sub-matrix into float array. The destination
	 * array is supposed to be a column major matrix.
	 * 
	 * @param dst
	 *            - the destination matrix
	 */
	public void extract4x3Matrix(float[] dst) {
		dst[0] = m[M00];
		dst[1] = m[M10];
		dst[2] = m[M20];
		dst[3] = m[M01];
		dst[4] = m[M11];
		dst[5] = m[M21];
		dst[6] = m[M02];
		dst[7] = m[M12];
		dst[8] = m[M22];
		dst[9] = m[M03];
		dst[10] = m[M13];
		dst[11] = m[M23];
	}

	/**
	 * Inverts the matrix. Stores the result in this matrix.
	 * 
	 * @return This matrix for the purpose of chaining methods together.
	 * @throws SigmaException
	 *             if the matrix is singular (not invertible)
	 */
	public Mat4f inverse() {
		float l_det = m[M30] * m[M21] * m[M12] * m[M03] - m[M20] * m[M31]
				* m[M12] * m[M03] - m[M30] * m[M11] * m[M22] * m[M03] + m[M10]
				* m[M31] * m[M22] * m[M03] + m[M20] * m[M11] * m[M32] * m[M03]
				- m[M10] * m[M21] * m[M32] * m[M03] - m[M30] * m[M21] * m[M02]
				* m[M13] + m[M20] * m[M31] * m[M02] * m[M13] + m[M30] * m[M01]
				* m[M22] * m[M13] - m[M00] * m[M31] * m[M22] * m[M13] - m[M20]
				* m[M01] * m[M32] * m[M13] + m[M00] * m[M21] * m[M32] * m[M13]
				+ m[M30] * m[M11] * m[M02] * m[M23] - m[M10] * m[M31] * m[M02]
				* m[M23] - m[M30] * m[M01] * m[M12] * m[M23] + m[M00] * m[M31]
				* m[M12] * m[M23] + m[M10] * m[M01] * m[M32] * m[M23] - m[M00]
				* m[M11] * m[M32] * m[M23] - m[M20] * m[M11] * m[M02] * m[M33]
				+ m[M10] * m[M21] * m[M02] * m[M33] + m[M20] * m[M01] * m[M12]
				* m[M33] - m[M00] * m[M21] * m[M12] * m[M33] - m[M10] * m[M01]
				* m[M22] * m[M33] + m[M00] * m[M11] * m[M22] * m[M33];
		if (l_det == 0f)
			throw new SigmaException("non-invertible matrix");
		float inv_det = 1.0f / l_det;
		t[M00] = m[M12] * m[M23] * m[M31] - m[M13] * m[M22] * m[M31] + m[M13]
				* m[M21] * m[M32] - m[M11] * m[M23] * m[M32] - m[M12] * m[M21]
				* m[M33] + m[M11] * m[M22] * m[M33];
		t[M01] = m[M03] * m[M22] * m[M31] - m[M02] * m[M23] * m[M31] - m[M03]
				* m[M21] * m[M32] + m[M01] * m[M23] * m[M32] + m[M02] * m[M21]
				* m[M33] - m[M01] * m[M22] * m[M33];
		t[M02] = m[M02] * m[M13] * m[M31] - m[M03] * m[M12] * m[M31] + m[M03]
				* m[M11] * m[M32] - m[M01] * m[M13] * m[M32] - m[M02] * m[M11]
				* m[M33] + m[M01] * m[M12] * m[M33];
		t[M03] = m[M03] * m[M12] * m[M21] - m[M02] * m[M13] * m[M21] - m[M03]
				* m[M11] * m[M22] + m[M01] * m[M13] * m[M22] + m[M02] * m[M11]
				* m[M23] - m[M01] * m[M12] * m[M23];
		t[M10] = m[M13] * m[M22] * m[M30] - m[M12] * m[M23] * m[M30] - m[M13]
				* m[M20] * m[M32] + m[M10] * m[M23] * m[M32] + m[M12] * m[M20]
				* m[M33] - m[M10] * m[M22] * m[M33];
		t[M11] = m[M02] * m[M23] * m[M30] - m[M03] * m[M22] * m[M30] + m[M03]
				* m[M20] * m[M32] - m[M00] * m[M23] * m[M32] - m[M02] * m[M20]
				* m[M33] + m[M00] * m[M22] * m[M33];
		t[M12] = m[M03] * m[M12] * m[M30] - m[M02] * m[M13] * m[M30] - m[M03]
				* m[M10] * m[M32] + m[M00] * m[M13] * m[M32] + m[M02] * m[M10]
				* m[M33] - m[M00] * m[M12] * m[M33];
		t[M13] = m[M02] * m[M13] * m[M20] - m[M03] * m[M12] * m[M20] + m[M03]
				* m[M10] * m[M22] - m[M00] * m[M13] * m[M22] - m[M02] * m[M10]
				* m[M23] + m[M00] * m[M12] * m[M23];
		t[M20] = m[M11] * m[M23] * m[M30] - m[M13] * m[M21] * m[M30] + m[M13]
				* m[M20] * m[M31] - m[M10] * m[M23] * m[M31] - m[M11] * m[M20]
				* m[M33] + m[M10] * m[M21] * m[M33];
		t[M21] = m[M03] * m[M21] * m[M30] - m[M01] * m[M23] * m[M30] - m[M03]
				* m[M20] * m[M31] + m[M00] * m[M23] * m[M31] + m[M01] * m[M20]
				* m[M33] - m[M00] * m[M21] * m[M33];
		t[M22] = m[M01] * m[M13] * m[M30] - m[M03] * m[M11] * m[M30] + m[M03]
				* m[M10] * m[M31] - m[M00] * m[M13] * m[M31] - m[M01] * m[M10]
				* m[M33] + m[M00] * m[M11] * m[M33];
		t[M23] = m[M03] * m[M11] * m[M20] - m[M01] * m[M13] * m[M20] - m[M03]
				* m[M10] * m[M21] + m[M00] * m[M13] * m[M21] + m[M01] * m[M10]
				* m[M23] - m[M00] * m[M11] * m[M23];
		t[M30] = m[M12] * m[M21] * m[M30] - m[M11] * m[M22] * m[M30] - m[M12]
				* m[M20] * m[M31] + m[M10] * m[M22] * m[M31] + m[M11] * m[M20]
				* m[M32] - m[M10] * m[M21] * m[M32];
		t[M31] = m[M01] * m[M22] * m[M30] - m[M02] * m[M21] * m[M30] + m[M02]
				* m[M20] * m[M31] - m[M00] * m[M22] * m[M31] - m[M01] * m[M20]
				* m[M32] + m[M00] * m[M21] * m[M32];
		t[M32] = m[M02] * m[M11] * m[M30] - m[M01] * m[M12] * m[M30] - m[M02]
				* m[M10] * m[M31] + m[M00] * m[M12] * m[M31] + m[M01] * m[M10]
				* m[M32] - m[M00] * m[M11] * m[M32];
		t[M33] = m[M01] * m[M12] * m[M20] - m[M02] * m[M11] * m[M20] + m[M02]
				* m[M10] * m[M21] - m[M00] * m[M12] * m[M21] - m[M01] * m[M10]
				* m[M22] + m[M00] * m[M11] * m[M22];
		m[M00] = t[M00] * inv_det;
		m[M01] = t[M01] * inv_det;
		m[M02] = t[M02] * inv_det;
		m[M03] = t[M03] * inv_det;
		m[M10] = t[M10] * inv_det;
		m[M11] = t[M11] * inv_det;
		m[M12] = t[M12] * inv_det;
		m[M13] = t[M13] * inv_det;
		m[M20] = t[M20] * inv_det;
		m[M21] = t[M21] * inv_det;
		m[M22] = t[M22] * inv_det;
		m[M23] = t[M23] * inv_det;
		m[M30] = t[M30] * inv_det;
		m[M31] = t[M31] * inv_det;
		m[M32] = t[M32] * inv_det;
		m[M33] = t[M33] * inv_det;
		return this;
	}

	/** @return The determinant of this matrix */
	public float determinant() {
		return m[M30] * m[M21] * m[M12] * m[M03] - m[M20] * m[M31] * m[M12]
				* m[M03] - m[M30] * m[M11] * m[M22] * m[M03] + m[M10] * m[M31]
				* m[M22] * m[M03] + m[M20] * m[M11] * m[M32] * m[M03] - m[M10]
				* m[M21] * m[M32] * m[M03] - m[M30] * m[M21] * m[M02] * m[M13]
				+ m[M20] * m[M31] * m[M02] * m[M13] + m[M30] * m[M01] * m[M22]
				* m[M13] - m[M00] * m[M31] * m[M22] * m[M13] - m[M20] * m[M01]
				* m[M32] * m[M13] + m[M00] * m[M21] * m[M32] * m[M13] + m[M30]
				* m[M11] * m[M02] * m[M23] - m[M10] * m[M31] * m[M02] * m[M23]
				- m[M30] * m[M01] * m[M12] * m[M23] + m[M00] * m[M31] * m[M12]
				* m[M23] + m[M10] * m[M01] * m[M32] * m[M23] - m[M00] * m[M11]
				* m[M32] * m[M23] - m[M20] * m[M11] * m[M02] * m[M33] + m[M10]
				* m[M21] * m[M02] * m[M33] + m[M20] * m[M01] * m[M12] * m[M33]
				- m[M00] * m[M21] * m[M12] * m[M33] - m[M10] * m[M01] * m[M22]
				* m[M33] + m[M00] * m[M11] * m[M22] * m[M33];
	}

	/**
	 * Scales the matrix by a given vector
	 * 
	 * @param scale
	 *            - scale vector
	 * @return this matrix
	 */
	public Mat4f scale(Vec3f scale) {
		m[M00] *= scale.x();
		m[M11] *= scale.y();
		m[M22] *= scale.z();
		return this;
	}

	/**
	 * Scales the matrix by a given vector
	 * 
	 * @param x
	 *            - x-component of vector
	 * @param y
	 *            - y-component of vector
	 * @param z
	 *            - z-component of vector
	 * @return This Matrix
	 */
	public Mat4f scl(float x, float y, float z) {
		m[M00] *= x;
		m[M11] *= y;
		m[M22] *= z;
		return this;
	}

	/**
	 * Scales the matrix by a given scalar quantity
	 * 
	 * @param scale
	 *            - scalar quantity
	 * @return This Matrix
	 */
	public Mat4f scl(float scale) {
		m[M00] *= scale;
		m[M11] *= scale;
		m[M22] *= scale;
		return this;
	}

	/**
	 * Multiplies all the values of the matrix by a given scalar quantity
	 * 
	 * @param s
	 *            -the scalar quantity
	 * @return this matrix
	 */
	public Mat4f mul(float s) {
		m[M00] *= s;
		m[M01] *= s;
		m[M02] *= s;
		m[M03] *= s;

		m[M10] *= s;
		m[M11] *= s;
		m[M12] *= s;
		m[M13] *= s;

		m[M20] *= s;
		m[M21] *= s;
		m[M22] *= s;
		m[M23] *= s;

		m[M30] *= s;
		m[M31] *= s;
		m[M32] *= s;
		m[M33] *= s;

		return this;
	}

	/**
	 * Postmultiplies this matrix with the given matrix, storing the result in
	 * this matrix. For example:
	 * 
	 * <pre>
	 * A.mul(B) results in A := AB.
	 * </pre>
	 * 
	 * @param matrix
	 *            - The other matrix to multiply by.
	 * @return This matrix for the purpose of chaining operations together.
	 */
	public Mat4f mul(Mat4f matrix) {
		mul(m, matrix.m);
		return this;
	}

	static final float[] res = new float[16];

	/**
	 * Multiplies the matrix a with matrix b, storing the result in a. The
	 * arrays are assumed to hold 4x4 column major matrices as you can get from
	 * {@link Mat4f#m}. This is the same as {@link Mat4f#mul(Mat4f)}.
	 *
	 * @param a
	 *            - the first matrix.
	 * @param b
	 *            - the second matrix.
	 */
	public static void mul(float[] a, float[] b) {
		res[M00] = (a[M00] * b[M00]) + (a[M01] * b[M10]) + (a[M02] * b[M20])
				+ (a[M03] * b[M30]);
		res[M01] = (a[M00] * b[M01]) + (a[M01] * b[M11]) + (a[M02] * b[M21])
				+ (a[M03] * b[M31]);
		res[M02] = (a[M00] * b[M02]) + (a[M01] * b[M12]) + (a[M02] * b[M22])
				+ (a[M03] * b[M32]);
		res[M03] = (a[M00] * b[M03]) + (a[M01] * b[M13]) + (a[M02] * b[M23])
				+ (a[M03] * b[M33]);

		res[M10] = (a[M10] * b[M00]) + (a[M11] * b[M10]) + (a[M12] * b[M20])
				+ (a[M13] * b[M30]);
		res[M11] = (a[M10] * b[M01]) + (a[M11] * b[M11]) + (a[M12] * b[M21])
				+ (a[M13] * b[M31]);
		res[M12] = (a[M10] * b[M02]) + (a[M11] * b[M12]) + (a[M12] * b[M22])
				+ (a[M13] * b[M32]);
		res[M13] = (a[M10] * b[M03]) + (a[M11] * b[M13]) + (a[M12] * b[M23])
				+ (a[M13] * b[M33]);

		res[M20] = (a[M20] * b[M00]) + (a[M21] * b[M10]) + (a[M22] * b[M20])
				+ (a[M23] * b[M30]);
		res[M21] = (a[M20] * b[M01]) + (a[M21] * b[M11]) + (a[M22] * b[M21])
				+ (a[M23] * b[M31]);
		res[M22] = (a[M20] * b[M02]) + (a[M21] * b[M12]) + (a[M22] * b[M22])
				+ (a[M23] * b[M32]);
		res[M23] = (a[M20] * b[M03]) + (a[M21] * b[M13]) + (a[M22] * b[M23])
				+ (a[M23] * b[M33]);

		res[M30] = (a[M30] * b[M30]) + (a[M31] * b[M10]) + (a[M32] * b[M20])
				+ (a[M33] * b[M30]);
		res[M31] = (a[M30] * b[M31]) + (a[M31] * b[M11]) + (a[M32] * b[M21])
				+ (a[M33] * b[M31]);
		res[M32] = (a[M30] * b[M32]) + (a[M31] * b[M12]) + (a[M32] * b[M22])
				+ (a[M33] * b[M32]);
		res[M33] = (a[M30] * b[M33]) + (a[M31] * b[M13]) + (a[M32] * b[M23])
				+ (a[M33] * b[M33]);
		System.arraycopy(res, 0, a, 0, 16);
	}

	/**
	 * @return the matrix float array, which stores all its values
	 */
	public float[] getMatrix() {
		return m;
	}

	/**
	 * Returns the value of a component of the array, specified by x and y
	 * 
	 * @param x
	 *            - the x coordinate of the array
	 * @param y
	 *            - the y coordinate of the array
	 * @return a float value, equal to the corresponding value in the array at
	 *         that value
	 */
	public float get(int x, int y) {
		return m[x + y * 4];
	}

	@Override
	public String toString() {
		return "[" + m[M00] + "|" + m[M01] + "|" + m[M02] + "|" + m[M03]
				+ "]\n" + "[" + m[M10] + "|" + m[M11] + "|" + m[M12] + "|"
				+ m[M13] + "]\n" + "[" + m[M20] + "|" + m[M21] + "|" + m[M22]
				+ "|" + m[M23] + "]\n" + "[" + m[M30] + "|" + m[M31] + "|"
				+ m[M32] + "|" + m[M33] + "]\n";
	}

}
