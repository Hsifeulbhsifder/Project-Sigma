package com.hsifeulbhsifder.sigma.game;

import com.hsifeulbhsifder.sigma.engine.math.Mat4f;

public class Main {

	public static void main(String[] args) {
		System.out.println(new Mat4f().scl(2).mul(new Mat4f().scl(10)));
	}

}
