package GrandCentral;


import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;


public class XMLParser2 {

	public static String contents = "";
	public static HashTable fillAuthorTable(String filePath) {
		HashTable authorTable = new HashTable();
		try {
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();

			DefaultHandler handler = new DefaultHandler() {
				LLNode<String> parentNode = new LLNode<String>("root");
				boolean insideArticle = false;
				public void startElement(String uri, String localName, String qName, Attributes attributes)
						throws SAXException {
					parentNode.next = new LLNode(parentNode,qName);
					parentNode = parentNode.next;

				}

				public void endElement(String uri, String localName, String qName) throws SAXException {
					if (parentNode.element.equalsIgnoreCase("article")) {
						List<String>authorsOfArticle = parentNode.children;
						//System.out.print(authorsOfArticle.toString()+"\n");
						try {
							authorTable.putList(authorsOfArticle.toArray(String[]::new));
						} catch (OutOfMemoryError e){
							return;
						};

					}
					else if (parentNode.element.equalsIgnoreCase("author")) {
						if (parentNode.prev.element.equalsIgnoreCase("article")) {
							//System.out.println(contents);
							parentNode.prev.children.add(contents);
							contents = "";
						}
					}
					parentNode = parentNode.prev;
					parentNode.next = null;
				}

				public void characters(char ch[], int start, int length) throws SAXException {
					if (parentNode.element.equalsIgnoreCase("author")) {
						contents += new String(ch, start, length);
					}

				}

			};

			File file = new File(filePath);
			InputStream inputStream = new FileInputStream(file);
			Reader reader = new InputStreamReader(inputStream, "ISO-8859-1");

			InputSource is = new InputSource(reader);
			is.setEncoding("ISO-8859-1");

			saxParser.parse(is, handler);

		} catch (SAXParseException e2) {

			System.out.println("Entity expansion limit reached (have to fix this later");
		} catch (Exception e) {

			e.printStackTrace();
		}
		return authorTable;
	}
}
