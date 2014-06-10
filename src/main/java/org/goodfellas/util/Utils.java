package org.goodfellas.util;

import java.lang.Math;


public final class Utils {

	// make the class static
	private Utils() {}

	public static final double euclideanDistance(int x1, int x2, int y1, int y2) {

		return Math.sqrt( Math.pow((x1 - x2), 2) + Math.pow((y1 - y2), 2) );

	}

    public static int[] copyArray(int[] adjacent) {
        int[] newArray = new int[adjacent.length];
        for(int i = 0 ; i < adjacent.length ; i++) {
            newArray[i] = adjacent[i];
        }
        return newArray;
    }

}