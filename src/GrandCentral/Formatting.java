package GrandCentral;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;




public class Formatting {
	public static ArrayList<String>pathsInUse = new ArrayList<String>();

	public static void printToFile(List<String>lines, String path) {
		while (pathsInUse.contains(path)) {
		}
		pathsInUse.add(path);
		try {
			printToFileBuffer(lines, path);
		} catch (Exception e) {
		System.out.println("Unable to print to "+path);
		}
		pathsInUse.remove(path);
		
	}
	public static void printToFile(String etf, String path) {
		while (pathsInUse.contains(path)) {
		}
		pathsInUse.add(path);
		try {
			printToFileBuffer(etf, path);
		} catch (Exception e) {
		System.out.println("Unable to print "+etf+" to "+path);
		}
		pathsInUse.remove(path);
	}
    public static void printToFileBuffer(String etf, String path) throws Exception {
    	try(PrintWriter output = new PrintWriter(new FileWriter(path,true))) 
    	{
    	    output.printf("%s\r\n", etf);
    	} 
    	catch (Exception e) {
    		System.out.println("broken313425");
    	}
    }
    public static void printToFileBuffer(List<String>lines, String path) throws Exception {
    	try(PrintWriter output = new PrintWriter(new FileWriter(path,true))) 
    	{
    		for (int x = 0; x < lines.size(); x ++) {
    			   output.printf("%s\r\n", lines.get(x));
    		}
    	} 
    	catch (Exception e) {
    		System.out.println("Can't print in printToFileBuffer.");
    	}
    }
    

	public static void recreateFile(String path) {
		File f1 = new File(path);
		f1.delete();
		try {
			f1.createNewFile();
		} catch (IOException e) {
		System.out.println("Couldn't create the file "+path);
		}
	}


	public static List<String> gla(String path, int lineStart, int lineEnd) {
		while (pathsInUse.contains(path)) {
		}
		pathsInUse.add(path);
	ArrayList<String>arrayOfLines = new ArrayList<String>();	
	try {
		File fileInput = new File(path);
		FileReader fileInputReader = new FileReader(fileInput);
		BufferedReader fileInputBuffer = new BufferedReader(fileInputReader);
		String line;
		int count = 0;
		boolean emergencyStop = false;
		try {
		while (((line = fileInputBuffer.readLine()) != null) && (!emergencyStop)) {
			count += 1;
			if ((count >= lineStart && count <= lineEnd)) {
			arrayOfLines.add(line);
			}
			else if (count > lineEnd) {
				emergencyStop = true;
			}
		}
		}
		catch (IOException e) {	
			System.out.println("While the file "+path+" has been opened sucessfully, it can not be read properly.");
		}
		fileInputBuffer.close();
	} catch (IOException e) {
		System.out.println("An attempt to open the file "+path+" has failed.");
	}
	pathsInUse.remove(path);
	String[]uniformArray = new String[arrayOfLines.size()];//2nd Version
	for (int x = 0; x < arrayOfLines.size(); x++) {
		uniformArray[x] = arrayOfLines.get(x);
	}
	return Arrays.asList(uniformArray);
}
  	public static List<String> gla(String path) {
  		while (pathsInUse.contains(path)) {
		}
		pathsInUse.add(path);
		List<String>arrayOfLines = new ArrayList<String>();	
		try {
			File fileInput = new File(path);
			FileReader fileInputReader = new FileReader(fileInput);
			BufferedReader fileInputBuffer = new BufferedReader(fileInputReader);
			String line;
			try {
			while ((line = fileInputBuffer.readLine()) != null) {
				arrayOfLines.add(line);
			}
			}
			catch (IOException e) {	
				System.out.println("While the file "+path+" has been opened sucessfully, it can not be read properly.");
			}
			fileInputBuffer.close();
		} catch (IOException e) {
			System.out.println("An attempt to open the file "+path+" has failed.");
			e.printStackTrace();
		}
		pathsInUse.remove(path);
	
		return arrayOfLines;
	}
  	public static List<String>justIndex(List<String>original,String seperator,int index,int totalIndex,int startLine,int endLine) {
  		//seperator = "," or "~" or really anything
  		//index = 0,1,2,3 (thing to collect ofc)
  		//totalIndex = size of individual lists(NOT of "original"
  		//startLine = 1,2,3,4,5
  		//endLine = 1,2,3,4,5
  	
  		List<String>returnList = new ArrayList<String>();
  		ListIterator originalL = original.listIterator();
  	int line = 0;
  		while (originalL.hasNext()) {
  			line +=1; 
  			if (line >= startLine && (!(line > endLine))) {
  	  			String[]parsed = ((String)originalL.next()).split(seperator);
  	  			if (!(parsed.length < totalIndex)) { //MUST BE THIS EXACT SPECIFICATION,SO THAT LENGTH CAN INCREASE IN OLD DATA STRUCTURES
  	  			returnList.add(parsed[index]);
  	  			}
  			}
  			else {
  			originalL.next();
  			}
  		}
	
		return returnList;
  	}
  	public static List<String>unscramableGraph(List<String>map,int nighSpan){
  		
  		return new ArrayList<String>();
  	}

  


	public static List<String> gla(String path, String spacer) { // For spacing out commands or lines that got too long
		String lines = "";
		try {
			File fileInput = new File(path);
			FileReader fileInputReader = new FileReader(fileInput);
			BufferedReader fileInputBuffer = new BufferedReader(fileInputReader);
			String line;
			try {
			while ((line = fileInputBuffer.readLine()) != null) {
				lines += line;
			}
			}
			catch (IOException e) {	
				System.out.println("While the file "+path+" has been opened sucessfully, it can not be read properly.");
			}
			fileInputBuffer.close();
		} catch (IOException e) {
			System.out.println("An attempt to open the file "+path+" has failed.");
		}
		if (lines.equals("")) {
			List<String>nothingArray = new ArrayList<String>();
			return nothingArray;
		}
		else {
		return Arrays.asList(lines.split(spacer));
		}
	}
	


	public static List<String>removeDuplicates(List<String>input) {
		List<String>output = new ArrayList<String>();
		Set<String> set = new HashSet<>(input);
		output.addAll(set);
		return output;
	}
	public static void removeDuplicatesOfFile(String path) {
		List<String>input = gla(path);
		int size1 = input.size();
		recreateFile(path);
		List<String>output = new ArrayList<String>();
		Set<String> set = new HashSet<>(input);
		output.addAll(set);
		int size2 = output.size();
		printToFile(output,path);
	}

}