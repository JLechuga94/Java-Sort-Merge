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

public class AirTable{
	
	private static int sizeBloc = 10;
	
	public static void main(String[] args) throws IOException {
		
		ArrayList<String> airTableRelations = new ArrayList<String>(
				Arrays.asList("RD",
						"A00", "A01", "A02", "A03", "A04", "A05","A06", "A07", "A08","A09", 
						"B00", "B01", "B02", "B03", "B04", "B05", "B06", "B07", "B08", "B09"));
		
		List<Integer> numbers = createIntRelation(96,200);
		
		deleteAirTableData(airTableRelations);
		sendAirTableData(numbers, "RD");
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
		

	public static void deleteAirTableData(ArrayList<String> airTableRelations) throws IOException {
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
	public static void sendAirTableData(List<Integer> relation, String relationName) throws IOException {
		System.out.println("----- Sending new Data to AirTable -----\n");
		
		ArrayList<String> dataBloc = new ArrayList<String>();
		ArrayList<String> descriptor = new ArrayList<String>();	
		String json = null;
		
		String blocName = null;
		int counter = 0;
		
		while(relation.size() > 0) {
			
			while(dataBloc.size() < sizeBloc && relation.size() > 0) {
				dataBloc.add(Integer.toString(relation.remove(0)));
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
}
