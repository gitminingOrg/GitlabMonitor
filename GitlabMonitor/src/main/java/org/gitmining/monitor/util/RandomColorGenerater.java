package org.gitmining.monitor.util;

import java.util.Random;

public class RandomColorGenerater {
	public static String getRandomColor(){
		Random random = new Random();
		int r = random.nextInt(255);
		int g = random.nextInt(255);
		int b = random.nextInt(255);
		String color = "rgba("+r+", "+g+", "+b+", .2)";
		return color;
	}
}
