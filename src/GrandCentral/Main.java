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
		/** Step 1 : Find what each entity CAN & MUST contain, and in what quantity **/
		try {
         File inputFile = new File(fp);

         SAXParserFactory factory = SAXParserFactory.newInstance();
         SAXParser saxParser = factory.newSAXParser();
         UserHandler userhandler = new UserHandler();
         saxParser.parse(inputFile, userhandler);
      } catch (Exception e) {
         e.printStackTrace();
      }

		/**
		 * Print relations established of entityNo, entityParents, & entityChildrenMap
		 **/
		XMLParser.seeRelationships();
		/** Step 2 :Generate classes of entities to "entityTypes" package in src **/
		// Note : You have to refresh for the classes to be seen in your directory
		// The classes all differ a lot and if you read the auto-generated notes they'll
		// give you a good understanding of the xml-organization
		XMLParser.generateBasicClasses();
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

class UserHandler extends DefaultHandler {
    boolean author = false;
    boolean article = false;
    boolean addNext = false;
    List<String> authorsList = new ArrayList<>();

    @Override
    public void startElement(
            String uri, String localName, String qName, Attributes attributes)
            throws SAXException {
        if (qName.equalsIgnoreCase("article")) {
            article = true;
        } else if (qName.equalsIgnoreCase("authors")){
            author = true;
        }
    }

    @Override
    public void endElement(String uri,
                           String localName, String qName) throws SAXException {

        if (qName.equalsIgnoreCase("article")) {
            System.out.println("authors: " + authorsList.toString());
            System.out.println("End Element: " + qName);
            authorsList.clear();
        } else if (qName.equals("authors")){
            addNext = true;
        } else if (addNext){
            authorsList.add(qName);
            addNext = false;
        }
    }

    @Override
    public void characters(char ch[], int start, int length) throws SAXException {
        if(article){

            article = false;

        }
    }
}
