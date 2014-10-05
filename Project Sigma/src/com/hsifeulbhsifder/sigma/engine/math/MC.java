package com.hsifeulbhsifder.sigma.engine.math;

import java.util.Random;

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

		static public float sin(float radians) {
			return Sin.table[(int) (radians * radToIndex) & SIN_MASK];
		}

		static public float cos(float radians) {
			return Sin.table[(int) ((radians + PI / 2) * radToIndex) & SIN_MASK];
		}

		static public float sinDeg(float degrees) {
			return Sin.table[(int) (degrees * degToIndex) & SIN_MASK];
		}

		static public float cosDeg(float degrees) {
			return Sin.table[(int) ((degrees + 90) * degToIndex) & SIN_MASK];
		}
		
		static public final float toR(float deg) {
			return deg * degreesToRadians;
		}
		
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

		static public int random(int range) {
			return random.nextInt(range + 1);
		}

		static public int random(int start, int end) {
			return start + random.nextInt(end - start + 1);
		}

		static public boolean randomBoolean() {
			return random.nextBoolean();
		}

		static public boolean randomBoolean(float chance) {
			return MC.random() < chance;
		}

		static public float random() {
			return random.nextFloat();
		}

		static public float random(float range) {
			return random.nextFloat() * range;
		}

		static public float random(float start, float end) {
			return start + random.nextFloat() * (end - start);
		}
		
		static public int randomSign () {
			return 1 | (random.nextInt() >> 31);
		}

		public static float randomTriangular () {
			return random.nextFloat() - random.nextFloat();
		}

		public static float randomTriangular (float max) {
			return (random.nextFloat() - random.nextFloat()) * max;
		}

		public static float randomTriangular (float min, float max) {
			return randomTriangular(min, max, (max - min) * .5f);
		}

		public static float randomTriangular (float min, float max, float mode) {
			float u = random.nextFloat();
			float d = max - min;
			if (u <= (mode - min) / d) return min + (float)Math.sqrt(u * d * (mode - min));
			return max - (float)Math.sqrt((1 - u) * d * (max - mode));
		}

		// Powers of two

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

		static public float lerp(float fromValue, float toValue, float progress) {
			return fromValue + (toValue - fromValue) * progress;
		}
		
		// Ceiling, floor, rounding, zero checking, equal checking, logarithms

		static private final int BIG_ENOUGH_INT = 16 * 1024;
		static private final double BIG_ENOUGH_FLOOR = BIG_ENOUGH_INT;
		static private final double CEIL = 0.9999999;
		static private final double BIG_ENOUGH_CEIL = 16384.999999999996;
		static private final double BIG_ENOUGH_ROUND = BIG_ENOUGH_INT + 0.5f;

		static public int floor(float x) {
			return (int) (x + BIG_ENOUGH_FLOOR) - BIG_ENOUGH_INT;
		}

		static public int floorPositive(float x) {
			return (int) x;
		}

		static public int ceil(float x) {
			return (int) (x + BIG_ENOUGH_CEIL) - BIG_ENOUGH_INT;
		}

		static public int ceilPositive(float x) {
			return (int) (x + CEIL);
		}

		static public int round(float x) {
			return (int) (x + BIG_ENOUGH_ROUND) - BIG_ENOUGH_INT;
		}

		static public int roundPositive(float x) {
			return (int) (x + 0.5f);
		}

		static public boolean isZero(float value) {
			return Math.abs(value) <= FLOAT_ROUNDING_ERROR;
		}

		static public boolean isZero(float value, float tolerance) {
			return Math.abs(value) <= tolerance;
		}

		static public boolean isEqual(float a, float b) {
			return Math.abs(a - b) <= FLOAT_ROUNDING_ERROR;
		}

		static public boolean isEqual(float a, float b, float tolerance) {
			return Math.abs(a - b) <= tolerance;
		}

		static public float log(float a, float x) {
			return (float) (Math.log(x) / Math.log(a));
		}

		static public float log2(float x) {
			return log(2, x);
		}
		
		
}
