package GrandCentral;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class Main {

	public static void main(String[] args) throws InterruptedException {
		// IMPORTANT : Add to "Run-configuration VM-arguments the following
		// '-DentityExpansionLimit=2500000' "

        /** path of xml file **/
		String fp = "/Users/cdacker/Downloads/Dblp/dblp.xml";

		XMLParser2.fillAuthorTable(fp);

		/** Step 2 :Generate classes of entities to "entityTypes" package in src **/
		// Note : You have to refresh for the classes to be seen in your directory
		// The classes all differ a lot and if you read the auto-generated notes they'll
		// give you a good understanding of the xml-organization

        /**
		 * Step 3 : Create HashMaps to serve as different paths of "getting-into" data
		 * structure(not really that great of an idea)
		 **/

		//XMLParser.generateCustomHashMaps();
		// All of the hashmaps with be in the "All" class in package "GrandCentral"
		// Calls will be like this "All.Authors.keySet()";
		/**
		 * Step 4 : Load XML-Data into data structures (needs to be completely redone,
		 * poorly executed)
		 **/

		// XMLParser.fillDataStructures(filePath);
		/**
		 * Step 5 : Compress dataStructure and write to readable file (for easy loading
		 * in future)
		 **/
		// haven't got around to this yet
		// XMLParser.compressData();

		/**
		 * Step 6 : Entire database will be easy to deal with, we can switch to a
		 * different database if we want as long as its in xml and still traverse it
		 * with ease
		 **/

	}
}
