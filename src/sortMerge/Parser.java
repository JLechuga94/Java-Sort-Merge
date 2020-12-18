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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Parser {
	
	private static String valueRgx = "\"Value\":\"([^\"]*)\"";
	private static String idRgx = "\"id\":\"([^\"]*)\"";
	
	public static String buildJSON(ArrayList<String> relation) {
		
		StringBuilder json = new StringBuilder();
		json.append("{\"records\":[");
		
		for(int i = 0; i < relation.size()-1; i++) {
			String element = String.format("{\"fields\": {\"Value\":\"%s\"}},", relation.get(i));
			json.append(element);
			}
		json.append(String.format("{\"fields\": {\"Value\":\"%s\"}}", relation.get(relation.size()-1)));
		json.append("]}");
		System.out.println(json.toString());
		
		return json.toString();
	}
	
	public static int[] parseValues(InputStream responseStream, boolean descriptor){
		
		String json = convertStreamToString(responseStream);
		
		ArrayList<String> values = getAllMatches(json, valueRgx);
		
		int[] table = new int[values.size()];
		
		for(int i = 0; i < values.size(); i++) {
			table[i] = Integer.parseInt(values.get(i));  
		}
		
        return table;
	}
	
	public static ArrayList<String> parseIDs(InputStream responseStream){
		
		String json = convertStreamToString(responseStream);
		
		ArrayList<String> ids = getAllMatches(json, idRgx);
        
        return ids;
	}
	
	private static String convertStreamToString(InputStream is) {

		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();

		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
				}
			}
		catch (IOException e) {
			e.printStackTrace();
			} 
		finally {
		    try {
		        is.close();
		    } 
		    catch (IOException e) {
		        e.printStackTrace();
		        }
		    }
		return sb.toString();
		}
	
	// Function to find all matches of regex string in JSON response
	private static ArrayList<String> getAllMatches(String text, String regex) {
        ArrayList<String> matches = new ArrayList<String>();
        Matcher m = Pattern.compile(regex).matcher(text);
        while(m.find()) {
            matches.add(m.group(1));
        }
        return matches;
    }
}
