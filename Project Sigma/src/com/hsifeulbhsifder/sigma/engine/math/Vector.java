package com.hsifeulbhsifder.sigma.engine.math;

public interface Vector<T extends Vector<T>> {

	T copy();

	float length();

	float length2();

	T limit(float limit);

	T clamp(float min, float max);

	T set(final T v);
	
	T set(float s);

	T normalize();

	T add(final T v);

	T sub(final T v);

	T mul(final T v);

	T div(final T v);

	T add(float s);

	T sub(float s);

	T mul(float s);

	T div(float s);

	T mulAdd(final T v, float s);

	T mulAdd(final T vA, T vM);

	T setZero();

	float dot(final T v);
	
	float dot(float s);

	T reflect(final T normal);

	float distance(final T v);

	float distance2(final T v);

	T lerp(final T target, float alpha);
	
	float max();

	float min();
	
	boolean isUnit();

	boolean isUnit(final float margin);

	boolean isZero();

	boolean isZero(final float margin);

	boolean isOnLine(final T other, float epsilon);

	boolean isOnLine(final T other);

	boolean isCollinear(final T other, float epsilon);

	boolean isCollinear(final T other);

	boolean isCollinearOpposite(final T other, float epsilon);

	boolean isCollinearOpposite(final T other);

	boolean isPerpendicular(final T other);

	boolean isPerpendicular(final T other, float epsilon);

	boolean hasSameDirection(final T other);

	boolean hasOppositeDirection(final T other);

	boolean epsilonEquals(T other, float epsilon);

	float x();
	
	float y();
	
	float z();
}
