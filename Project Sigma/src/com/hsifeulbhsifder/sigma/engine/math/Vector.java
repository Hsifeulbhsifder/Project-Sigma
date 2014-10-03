package com.hsifeulbhsifder.sigma.engine.math;

public interface Vector<T extends Vector<T>> {

	T copy ();

	float length ();

	float length2 ();

	T limit (float limit);

	T clamp (float min, float max);

	T set (T v);

	T normalize ();

	T add (T v);
	
	T sub (T v);

	T mul (T v);
	
	T div (T v);
	
	T add (float v);
	
	T sub (float v);
	
	T mul (float v);
	
	T div (float v);
	
	float dot (T v);

	float cross (T v);

	T reflect (T v);

	float dst (T v);

	float dst2 (T v);

	T lerp (T target, float alpha);

	boolean isUnit ();

	boolean isUnit (final float margin);

	boolean isZero ();

	boolean isZero (final float margin);

	boolean isOnLine (T other, float epsilon);

	boolean isOnLine (T other);

	boolean isCollinear (T other, float epsilon);

	boolean isCollinear (T other);

	boolean isCollinearOpposite (T other, float epsilon);

	boolean isCollinearOpposite (T other);

	boolean isPerpendicular (T other);

	boolean isPerpendicular (T other, float epsilon);

	boolean hasSameDirection (T other);

	boolean hasOppositeDirection (T other);

	boolean epsilonEquals (T other, float epsilon);

	T mulAdd (T v, float scalar);

	T mulAdd (T v, T mulVec);

	T setZero ();
	
	
}
