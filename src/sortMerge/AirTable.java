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
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class AirTable{
	
	private static int sizeBloc = 10;
	
	public static void main(String[] args) throws IOException {
		List<Integer> numbers = createIntRelation(96,200);
	}
	
	// Test this method easily
	public static List<Integer> createIntRelation(int size, int maxValue) {
		ArrayList<Integer> numbers = new ArrayList<>(maxValue);
		
		for(int i = 0; i < maxValue;){
		    numbers.add(i);
		    i = i + 1;
		}
		Collections.shuffle(numbers);
		
		List<Integer> relation = numbers.subList(0, size);
		
		System.out.println("----- Relation created from integers in range(0,200) -----");
		System.out.println(relation);
		System.out.println("Size : " + relation.size() + "\n");
		return relation;
	}
		

	public static void clearAirTableData(ArrayList<String> airTableRelations) throws IOException {
		System.out.println("----- Clearing existing Data from AirTable -----");
		String relation = null;
		for(int i = 0; i < airTableRelations.size(); i++) {
			relation = airTableRelations.get(i);
			API.DELETE(relation);
		}
		System.out.println("\n----- Finished clearing existing Data from AirTable -----\n");
	}
	
	/*** This function sends all the data generated from randomly selecting pairs of characters in groups of 10
	 * since AirTable limits each call to 10 records. We need to send R and S in packets of 10 cells.
	 ***/
	public static void sendAirTableData(ArrayList<String> relation, String relationName) throws IOException {
		System.out.println("----- Sending new Data to AirTable -----\n");
		
		ArrayList<String> dataBloc = new ArrayList<String>();
		ArrayList<String> descriptor = new ArrayList<String>();	
		String json = null;
		
		String blocName = null;
		int counter = 0;
		
		while(relation.size() > 0) {
			
			while(dataBloc.size() < sizeBloc && relation.size() > 0) {
				dataBloc.add(relation.remove(0));
			}
			
			if(relationName == "RD")
				blocName = "A0" +  Integer.toString(counter);
			else if(relationName == "SD")
				blocName = "B0" +  Integer.toString(counter);
			else if (relationName == "RSD")
				blocName = "C0" +  Integer.toString(counter);
			
			json = Parser.buildJSON(dataBloc);
			
			System.out.println(String.format("Sending bloc: %s of relation: %s", blocName, relationName));
			API.POST(json, blocName);
			
			dataBloc.clear();
			descriptor.add(Integer.toString(counter));
			counter++;
		}
		
		json = Parser.buildJSON(descriptor);
		System.out.println(String.format("Sending descriptor: %s", relationName));
		API.POST(json, relationName);
		
		System.out.println("----- Finished sending new Data to AirTable -----\n");
	}

	public static ArrayList<String> transformMergeDataToArray(int[][] RS){
		ArrayList<String> mergedData = new ArrayList<String>();
		
		for(int i = 0; i < RS.length; i++) {
			if(Arrays.stream(RS[i]).sum() > 0) {
				ArrayList<String> rsBloc = Parser.asciiTableToStringArray(RS[i]);
				mergedData.addAll(rsBloc);
			}
		}
		System.out.println("Merged data");
		System.out.println(mergedData + "\n");
		return mergedData;
	}
}
