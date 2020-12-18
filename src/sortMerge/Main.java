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
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {

	public static void main(String[] args) throws IOException {
		System.out.println(Parser.asciiTableToStringArray(Parser.parseValues(API.GET("A01"), false)));
	}
		

}
