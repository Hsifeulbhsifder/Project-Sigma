package com.hsifeulbhsifder.sigma.engine.math;

/**
 * This is an interface for vector classes to follow
 * Involves chaining
 * @author Zaeem
 * @version 1.0
 * @param <T> T extends Vector - the vector class
 *  that the methods would act under
 */
public interface Vector<T extends Vector<T>> {
	/**
	 * Creates a new Vector<T extends Vector<T>> object 
	 * from existing vector
	 * @return A copy of the vector
	 */
	T copy();
	/** @return length */
	float length();
	/** This method is faster than {@link Vector#length()} because it avoids calculating a square root. It is useful for comparisons,
	 * but not for getting accurate lengths, as the return value is the square of the actual length.
	 * @return The squared length */
	float length2();
	/** Limits this vector's length to given value
	 * @return This vector */
	T limit(float limit);
	/** Clamps this vector's length to given min and max values
	 * @param min - Min length
	 * @param max - Max length
	 * @return This vector */
	T clamp(float min, float max);
	/** Sets this vector from the given vector
	 * @param v - The vector
	 * @return This vector */
	T set(final T v);
	/** Sets this vector from the given float,
	 *  making all components for the vector equal to it
	 * @param s - The scalar float
	 * @return This vector */
	T set(float s);
	/** Normalizes this vector. Does nothing if it is zero.
	 * @return This vector */
	T normalize();
	/** Adds the given vector to this vector
	 * @param v - The vector
	 * @return This vector */
	T add(final T v);
	/** Subtracts the given vector from this vector
	 * @param v - The vector
	 * @return This vector */
	T sub(final T v);
	/** Multiplies the given vector by this vector
	 * @param v - The vector
	 * @return This vector */
	T mul(final T v);
	/** Divides the given vector from this vector,
	 *  does nothing to component if divisor is zero
	 * @param v - The vector
	 * @return This vector */
	T div(final T v);
	/** Adds the given float to all components of this vector
	 * @param s - The scalar float
	 * @return This vector */
	T add(float s);
	/** Subtracts the given float from all components of this vector
	 * @param s - The scalar float
	 * @return This vector */
	T sub(float s);
	/** Multiplies the given float by all components of this vector
	 * @param s - The scalar float
	 * @return This vector */
	T mul(float s);
	/** Divides the given float from all components of this vector,
	 * does nothing to a component if it is equal to zero
	 * @param s - The scalar float
	 * @return This vector */
	T div(float s);
	/** First scale a supplied vector, then add it to this vector.
	 * @param v - addition vector
	 * @param s - scalar for scaling the addition vector 
	 * @return This vector*/
	T mulAdd(final T v, float s);
	/** First scale a supplied vector, then add it to this vector.
	 * @param vA - addition vector
	 * @param vM - vector for scaling the addition vector 
	 * @return This vector*/
	T mulAdd(final T vA, T vM);
	/** Sets the components of this vector to 0
	 * @return This vector */
	T setZero();
	/** @param v - The other vector
	 * @return The dot product between this and the other vector */
	float dot(final T v);
	/** @param s - The scalar value to find dot pruduct with
	 * @return The dot product between this and the scalar value */
	float dot(float s);
	/**
	 * Calculates the reflection of this vector and normal
	 * @param normal - the normal vector to be reflected on
	 * @return This vector
	 */
	T reflect(final T normal);
	/** @param v - The other vector
	 * @return the distance between this and the other vector */
	float distance(final T v);
	/** This method is faster than {@link Vector#distance(Vector)} because it avoids calculating a square root. It is useful for
	 * comparisons, but not for getting accurate distances, as the return value is the square of the actual distance.
	 * @param v - The other vector
	 * @return the squared distance between this and the other vector */
	float distance2(final T v);
	/** Linearly interpolates between this vector and the target vector by alpha which is in the range [0,1]. The result is stored
	 * in this vector.
	 * @param target - The target vector
	 * @param alpha - The interpolation coefficient
	 * @return This vector */
	T lerp(final T target, float alpha);
	/**
	 * Returns the highest component of this vector
	 * @return The highest component
	 */
	float max();
	/**
	 * Returns the lowest component of this vector
	 * @return The lowest component
	 */
	float min();
	/** @return Whether this vector is a unit length vector */
	boolean isUnit();
	/** @param margin - margin of uncertainty in unit calculation bias
	 *  @return Whether this vector is a unit length vector within the given margin. */
	boolean isUnit(final float margin);
	/** @return Whether this vector is a zero vector */
	boolean isZero ();
	/** @param margin - margin of uncertainty in unit calculation bias 
	 *  @return Whether the length of this vector is smaller than the given margin */
	boolean isZero (final float margin);
	/** @return true if this vector is in line with the other vector (either in the same or the opposite direction) */
	boolean isOnLine (T other, float epsilon);
	/** @return true if this vector is in line with the other vector (either in the same or the opposite direction) */
	boolean isOnLine (T other);
	/** @return true if this vector is collinear with the other vector ({@link #isOnLine(Vector, float)} &&
	 *         {@link #hasSameDirection(Vector)}). */
	boolean isCollinear (T other, float epsilon);
	/** @return true if this vector is collinear with the other vector ({@link #isOnLine(Vector)} &&
	 *         {@link #hasSameDirection(Vector)}). */
	boolean isCollinear (T other);
	/** @return true if this vector is opposite collinear with the other vector ({@link #isOnLine(Vector, float)} &&
	 *         {@link #hasOppositeDirection(Vector)}). */
	boolean isCollinearOpposite (T other, float epsilon);
	/** @return true if this vector is opposite collinear with the other vector ({@link #isOnLine(Vector)} &&
	 *         {@link #hasOppositeDirection(Vector)}). */
	boolean isCollinearOpposite (T other);
	/** @return Whether this vector is perpendicular with the other vector. True if the dot product is 0. */
	boolean isPerpendicular (T other);
	/** @return Whether this vector is perpendicular with the other vector. True if the dot product is 0.
	 * @param epsilon a positive small number close to zero */
	boolean isPerpendicular (T other, float epsilon);
	/** @return Whether this vector has similar direction compared to the other vector. True if the normalized dot product is > 0. */
	boolean hasSameDirection (T other);
	/** @return Whether this vector has opposite direction compared to the other vector. True if the normalized dot product is < 0. */
	boolean hasOppositeDirection (T other);
	/** Compares this vector with the other vector, using the supplied epsilon for fuzzy equality testing.
	 * @param other - other vector to compare to
	 * @param epsilon - margin of error for equality testing
	 * @return whether the vectors have fuzzy equality. */
	boolean epsilonEquals (T other, float epsilon);
	/**
	 * @return x component of vector
	 */
	float x();
	/**
	 * @return y component of vector
	 */
	float y();
	/**
	 * @return z component of vector if exists, other wise throws an exception
	 */
	float z();
}
