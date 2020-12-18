/**
 * TP  n°: 7 V n°: 1
 * 
 * Titre du TP: AirTable Sort Merge
 * 
 * Date: 7 Janvier 2020
 * 
 * E1: Lechuga Lopez Leopoldo Julian
 * E2: Morakhovski Alexander
 * 
 * email: julian.lechuga305@gmail.com 
 * email: alexmorakhovski@gmail.com
 *
 */

package sortMerge;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.*;

public class Algorithm {
	
	public static void main(String[] args) throws IOException {
		InputStream responseStreamR = API.GET("A00");
		int[] blocA00 = Parser.parseValues(responseStreamR, false);
		
		InputStream responseStreamR1 = API.GET("A01");
		int[] blocA01 = Parser.parseValues(responseStreamR1, false);
		
		Arrays.sort(blocA00);
		Arrays.sort(blocA01);
		System.out.println(Arrays.toString(blocA00));
		System.out.println(Arrays.toString(blocA01));
		
	}
		
	// Function only for visualization purposes during run of algorithm
	// It displays the String value of each ASCII value below them
	public static ArrayList<String> spaceAdder(ArrayList<String> relation){
		for(int i = 0; i < relation.size(); i++) {
			relation.set(i, "  " + relation.get(i));
		}
		return relation;
	}

}
