package com.hsifeulbhsifder.sigma.engine.math;

import java.util.Random;

/**
 * Performance enhanced math class
 * @author Zaeem
 * @version 1.0
 */
public class MC {
	// Sin and Cos
		static public final float FLOAT_ROUNDING_ERROR = 0.000001f; // 32 bits
		static public final float PI = 3.141592653589793238462643383279502884197169399375105820974944592307816406286208998628034825342117067982148086513282306647093844609550582231725359408128481117450284102701938521105559644622948954930381964428810975665933446128475648233786783165271201909145648566923460348610454326648213393607260249141273724587006606315588174881520920962829254091715364367892590360011330530548820466521384146951941511609f;
		static public final float PI2 = PI * 2;

		static public final float E = 2.71828182845904523536028747135266249775724709369995f;

		static private final int SIN_BITS = 14; // 16KB. Adjust for accuracy.
		static private final int SIN_MASK = ~(-1 << SIN_BITS);
		static private final int SIN_COUNT = SIN_MASK + 1;

		static private final float radFull = PI2;
		static private final float degFull = 360;
		static private final float radToIndex = SIN_COUNT / radFull;
		static private final float degToIndex = SIN_COUNT / degFull;

		static public final float radiansToDegrees = 180f / PI;
		static public final float radDeg = radiansToDegrees;
		static public final float degreesToRadians = PI / 180;
		static public final float degRad = degreesToRadians;
		
		static private class Sin {
			static final float[] table = new float[SIN_COUNT];
			static {
				for (int i = 0; i < SIN_COUNT; i++)
					table[i] = (float) Math.sin((i + 0.5f) / SIN_COUNT * radFull);
				for (int i = 0; i < 360; i += 90)
					table[(int) (i * degToIndex) & SIN_MASK] = (float) Math.sin(i
							* degreesToRadians);
			}
		}
		/** @return the sine in radians from a lookup table. */
		static public float sin(float radians) {
			return Sin.table[(int) (radians * radToIndex) & SIN_MASK];
		}
		/** @return the cosine in radians from a lookup table. */
		static public float cos(float radians) {
			return Sin.table[(int) ((radians + PI / 2) * radToIndex) & SIN_MASK];
		}
		/** @return the sine in degrees from a lookup table. */
		static public float sinDeg(float degrees) {
			return Sin.table[(int) (degrees * degToIndex) & SIN_MASK];
		}
		/** @return the cosine in degrees from a lookup table. */
		static public float cosDeg(float degrees) {
			return Sin.table[(int) ((degrees + 90) * degToIndex) & SIN_MASK];
		}
		/** @return the angle in radians from degrees. */
		static public final float toR(float deg) {
			return deg * degreesToRadians;
		}
		/** @return the angle in degrees from radians. */
		static public final float toD(float rad) {
			return rad * radiansToDegrees;
		}
		
		// Atan2

		static private final int ATAN2_BITS = 7; // Adjust for accuracy.
		static private final int ATAN2_BITS2 = ATAN2_BITS << 1;
		static private final int ATAN2_MASK = ~(-1 << ATAN2_BITS2);
		static private final int ATAN2_COUNT = ATAN2_MASK + 1;
		static final int ATAN2_DIM = (int) Math.sqrt(ATAN2_COUNT);
		static private final float INV_ATAN2_DIM_MINUS_1 = 1.0f / (ATAN2_DIM - 1);

		static private class Atan2 {
			static final float[] table = new float[ATAN2_COUNT];
			static {
				for (int i = 0; i < ATAN2_DIM; i++) {
					for (int j = 0; j < ATAN2_DIM; j++) {
						float x0 = (float) i / ATAN2_DIM;
						float y0 = (float) j / ATAN2_DIM;
						table[j * ATAN2_DIM + i] = (float) Math.atan2(y0, x0);
					}
				}
			}
		}
		/** @return atan2 in radians from a lookup table. */
		static public float atan2(float y, float x) {
			float add, mul;
			if (x < 0) {
				if (y < 0) {
					y = -y;
					mul = 1;
				} else
					mul = -1;
				x = -x;
				add = -PI;
			} else {
				if (y < 0) {
					y = -y;
					mul = -1;
				} else
					mul = 1;
				add = 0;
			}
			float invDiv = 1 / ((x < y ? y : x) * INV_ATAN2_DIM_MINUS_1);

			if (invDiv == Float.POSITIVE_INFINITY)
				return ((float) Math.atan2(y, x) + add) * mul;

			int xi = (int) (x * invDiv);
			int yi = (int) (y * invDiv);
			return (Atan2.table[yi * ATAN2_DIM + xi] + add) * mul;
		}
		
		// Random

		static public Random random = new Randomizer();
		/** @return a random number between 0 (inclusive) and the specified value (inclusive). */
		static public int random(int range) {
			return random.nextInt(range + 1);
		}
		/** @return a random number between start (inclusive) and end (inclusive). */
		static public int random(int start, int end) {
			return start + random.nextInt(end - start + 1);
		}
		/** @return a random boolean value. */
		static public boolean randomBoolean() {
			return random.nextBoolean();
		}
		/** @return true if a random value between 0 and 1 is less than the specified value. */
		static public boolean randomBoolean(float chance) {
			return MC.random() < chance;
		}
		/** @return random number between 0.0 (inclusive) and 1.0 (exclusive). */
		static public float random() {
			return random.nextFloat();
		}
		/** @return a random number between 0 (inclusive) and the specified value (exclusive). */
		static public float random(float range) {
			return random.nextFloat() * range;
		}
		/** @return a random number between start (inclusive) and end (exclusive). */
		static public float random(float start, float end) {
			return start + random.nextFloat() * (end - start);
		}
		/** @return -1 or 1, randomly. */
		static public int randomSign () {
			return 1 | (random.nextInt() >> 31);
		}
		/** @return a triangularly distributed random number 
		 * between -1.0 (exclusive) and 1.0 (exclusive), where values around zero are
		 * more likely.
		 * <p>
		 * This is an optimized version of 
		 * {@link #randomTriangular(float, float, float) randomTriangular(-1, 1, 0)} */
		public static float randomTriangular () {
			return random.nextFloat() - random.nextFloat();
		}
		/** @return a triangularly distributed random number 
		 * between {@code -max} (exclusive) and {@code max} (exclusive), where values
		 * around zero are more likely.
		 * <p>
		 * This is an optimized version of 
		 * {@link #randomTriangular(float, float, float) randomTriangular(-max, max, 0)}
		 * @param max - the upper limit */
		public static float randomTriangular (float max) {
			return (random.nextFloat() - random.nextFloat()) * max;
		}
		/** @return a triangularly distributed random number between {@code min} (inclusive) and {@code max} (exclusive), where the
		 * {@code mode} argument defaults to the midpoint between the bounds, giving a symmetric distribution.
		 * <p>
		 * This method is equivalent of {@link #randomTriangular(float, float, float) randomTriangular(min, max, (max - min) * .5f)}
		 * @param min - the lower limit
		 * @param max - the upper limit */
		public static float randomTriangular (float min, float max) {
			return randomTriangular(min, max, (max - min) * .5f);
		}
		/** @return a triangularly distributed random number between {@code min} (inclusive) and {@code max} (exclusive), where values
		 * around {@code mode} are more likely.
		 * @param min - the lower limit
		 * @param max - the upper limit
		 * @param mode the point around which the values are more likely */
		public static float randomTriangular (float min, float max, float mode) {
			float u = random.nextFloat();
			float d = max - min;
			if (u <= (mode - min) / d) return min + (float)Math.sqrt(u * d * (mode - min));
			return max - (float)Math.sqrt((1 - u) * d * (max - mode));
		}

		// Powers of two
		/** @return the next power of two. Returns the specified value if the value is already a power of two. */
		static public int nextPowerOfTwo(int value) {
			if (value == 0)
				return 1;
			value--;
			value |= value >> 1;
			value |= value >> 2;
			value |= value >> 4;
			value |= value >> 8;
			value |= value >> 16;
			return value + 1;
		}

		static public boolean isPowerOfTwo(int value) {
			return value != 0 && (value & value - 1) == 0;
		}
		
		// Clamping

		static public int clamp(int value, int min, int max) {
			if (value < min)
				return min;
			if (value > max)
				return max;
			return value;
		}

		static public short clamp(short value, short min, short max) {
			if (value < min)
				return min;
			if (value > max)
				return max;
			return value;
		}

		static public float clamp(float value, float min, float max) {
			if (value < min)
				return min;
			if (value > max)
				return max;
			return value;
		}
		
		// Linear Interpolation
		/** Linearly interpolates between fromValue to toValue on progress position. */
		static public float lerp(float fromValue, float toValue, float progress) {
			return fromValue + (toValue - fromValue) * progress;
		}
		
		// Ceiling, floor, rounding, zero checking, equal checking, logarithms

		static private final int BIG_ENOUGH_INT = 16 * 1024;
		static private final double BIG_ENOUGH_FLOOR = BIG_ENOUGH_INT;
		static private final double CEIL = 0.9999999;
		static private final double BIG_ENOUGH_CEIL = 16384.999999999996;
		static private final double BIG_ENOUGH_ROUND = BIG_ENOUGH_INT + 0.5f;
		/** @return the largest integer less than or equal to the specified float. This method will only properly floor floats from
		 * -(2^14) to (Float.MAX_VALUE - 2^14). */
		static public int floor(float x) {
			return (int) (x + BIG_ENOUGH_FLOOR) - BIG_ENOUGH_INT;
		}
		/** @return the largest integer less than or equal to the specified float. This method will only properly floor floats that are
		 * positive. Note this method simply casts the float to int. */
		static public int floorPositive(float x) {
			return (int) x;
		}

		/** @return the smallest integer greater than or equal to the specified float. This method will only properly ceil floats from
		 * -(2^14) to (Float.MAX_VALUE - 2^14). */
		static public int ceil (float x) {
			return (int)(x + BIG_ENOUGH_CEIL) - BIG_ENOUGH_INT;
		}

		/** @return the smallest integer greater than or equal to the specified float. This method will only properly ceil floats that
		 * are positive. */
		static public int ceilPositive (float x) {
			return (int)(x + CEIL);
		}

		/** @return the closest integer to the specified float. This method will only properly round floats from -(2^14) to
		 * (Float.MAX_VALUE - 2^14). */
		static public int round (float x) {
			return (int)(x + BIG_ENOUGH_ROUND) - BIG_ENOUGH_INT;
		}

		/** @return the closest integer to the specified float. This method will only properly round floats that are positive. */
		static public int roundPositive (float x) {
			return (int)(x + 0.5f);
		}

		/** @return true if the value is zero (using the default tolerance as upper bound) */
		static public boolean isZero (float value) {
			return Math.abs(value) <= FLOAT_ROUNDING_ERROR;
		}

		/** @return true if the value is zero.
		 * @param tolerance - represent an upper bound below which the value is considered zero. */
		static public boolean isZero (float value, float tolerance) {
			return Math.abs(value) <= tolerance;
		}

		/** @return true if a is nearly equal to b. The function uses the default floating error tolerance.
		 * @param a - the first value.
		 * @param b - the second value. */
		static public boolean isEqual (float a, float b) {
			return Math.abs(a - b) <= FLOAT_ROUNDING_ERROR;
		}

		/** @return true if a is nearly equal to b.
		 * @param a - the first value.
		 * @param b - the second value.
		 * @param tolerance represent an upper bound below which the two values are considered equal. */
		static public boolean isEqual (float a, float b, float tolerance) {
			return Math.abs(a - b) <= tolerance;
		}

		/** @return the logarithm of x with base a */
		static public float log (float a, float x) {
			return (float)(Math.log(x) / Math.log(a));
		}

		/** @return the logarithm of x with base 2 */
		static public float log2 (float x) {
			return log(2, x);
		}
		
		
}
