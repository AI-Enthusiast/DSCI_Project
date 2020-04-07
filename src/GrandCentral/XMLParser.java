package GrandCentral;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;
import entityTypes.*;
import java.lang.reflect.*;

public class XMLParser {

	/** describes what each entity can be searched by **/
	static HashMap<String, SearchBy> entitySearchBy = new HashMap<String, SearchBy>();
	/** describes their parents in readable format **/
	static HashMap<String, String> entityParentNotes = new HashMap<String, String>();
	/** 3 hashmaps store info on SOLELY the RELATIONSHIPS within the document **/
	/** just the number of each entity **/
	static HashMap<String, Integer> entityNo;
	/** stores a possible set of parents for each entity **/
	static HashMap<String, Set<String>> entityParents;
	/**
	 * its a mouthful but here goes: entityChildren maps each entity(defined by a
	 * string) to a hashmap of that entity's POSSIBLE children, each child is an
	 * entity itself(defined by a string) who is mapped to a List<Integer> of that
	 * child's # of occurrences within different occurrences of the parent entity
	 **/
	static HashMap<String, HashMap<String, List<Integer>>> entityChildrenMap;

	/**
	 * Generates 3-hashmaps of entityNo, entityParents, entityChildrenMap and
	 * thereby establishes relationships between parents and children
	 **/
	public static void establishRelationships(String filePath) {
		
		entityChildrenMap = new HashMap<String, HashMap<String, List<Integer>>>();
		entityParents = new HashMap<String, Set<String>>();
		entityNo = new HashMap<String, Integer>();

		try {
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();

			DefaultHandler handler = new DefaultHandler() {
				LLNode<String> parentNode = new LLNode<String>("root");

				public void startElement(String uri, String localName, String qName, Attributes attributes)
						throws SAXException {
					/**
					 * stage 1. takes down names and quantities of entities, might not be needed
					 **/
					if (entityNo.containsKey(qName)) {
						entityNo.put(qName, entityNo.get(qName) + 1);

					} else {
						entityNo.put(qName, 1);
					}
					/** stage 2. establish below parent node **/
					parentNode.next = new LLNode<String>(parentNode, qName);
					parentNode.children.add(qName);
					parentNode = parentNode.next;
				}

				public void endElement(String uri, String localName, String qName) throws SAXException {

					List<String> children = parentNode.children;
					/** parentNode is set to this element's parent **/
					parentNode = parentNode.prev;
					parentNode.next = null;
					String parent = parentNode.element;
					/**
					 * parentSet refers to the POSSIBLE parents of this entity, which have so far
					 * been recorded
					 **/
					Set<String> parentSet;

					if (entityParents.containsKey(qName)) {
						parentSet = entityParents.get(qName);
					} else {
						parentSet = new HashSet<String>();
					}
					/** hashmap of this entity **/

					HashMap<String, List<Integer>> entityChildren;
					if (entityChildrenMap.containsKey(qName)) {
						entityChildren = entityChildrenMap.get(qName);
					} else {
						entityChildren = new HashMap<String, List<Integer>>();
					}
					/** 1. Record parent to parentSet of this entity **/
					parentSet.add(parent);
					entityParents.put(qName, parentSet);
					/** 2. count up its children and put that in the entityChildren hashmap **/
					/** 2A. Iterate through this entity's children and count them **/
					HashMap<String, Integer> childrenOccurences = new HashMap<String, Integer>();
					for (String child : children) {
						if (childrenOccurences.containsKey(child)) {
							childrenOccurences.put(child, childrenOccurences.get(child) + 1);
						} else {
							childrenOccurences.put(child, 1);
						}
					}
					/**
					 * 2C. Put the childrenOccurences just counted into the entityChildren list<int>
					 **/
					Set<String> keys = childrenOccurences.keySet();
					for (String typeOfChild : keys) {
						int occurences = childrenOccurences.get(typeOfChild);
						List<Integer> childSetOfOccurences;
						if (entityChildren.containsKey(typeOfChild)) {
							childSetOfOccurences = entityChildren.get(typeOfChild);

						} else {
							childSetOfOccurences = new ArrayList<Integer>();
						}
						childSetOfOccurences.add(occurences);
						entityChildren.put(typeOfChild, childSetOfOccurences);
					}
					/** 2D. put entityChildren into entityChildrenMap **/
					if (entityChildren.size() > 0) {
						entityChildrenMap.put(qName, entityChildren);
					}
				}

				public void characters(char ch[], int start, int length) throws SAXException {
					// String contents = new String(ch, start, length);
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
		/**
		 * this last part ensures we know some children do not always exist(we had to
		 * wait for the end to be sure of that
		 **/
		Set<String> keySet = entityNo.keySet();
		for (String key : keySet) {
			int entityOcc = entityNo.get(key);
			HashMap<String, List<Integer>> entityChildren = entityChildrenMap.get(key);
			if (entityChildren != null) {
				Set<String> keySet2 = entityChildren.keySet();
				for (String key2 : keySet2) {
					List<Integer> li = entityChildren.get(key2);
					if (li.size() < (entityOcc - 1)) {
						li.add(0);
					}
				}
			}
		}
		/**
		 * this very last part is to ensure the root has its children recorded in the
		 * event that the XMLParser hit an entity exception limit
		 **/

		System.out.println("Established Relationships.");
	}

	public static void seeRelationships() {
		Set<String> keySet = entityNo.keySet();
		for (String key : keySet) {
			int entityOcc = entityNo.get(key);
			System.out.println("key=" + key + ", no=" + entityNo.get(key));
			HashMap<String, List<Integer>> entityChildren = entityChildrenMap.get(key);
			if (entityChildren != null) {
				Set<String> keySet2 = entityChildren.keySet();
				for (String key2 : keySet2) {
					List<Integer> li = entityChildren.get(key2);
					String percentPresent = (Float.toString(((float) li.size() / (float) (entityOcc)) * 100))
							.substring(0, 5) + "%";
					if (!li.contains(0)) {
						percentPresent = "All";
					}
					System.out.println("         " + percentPresent + " contain " + key2 + ", occuring in quantity of "
							+ Range.toRangeString(entityChildren.get(key2)));
				}
				System.out.println("         parents can be " + entityParents.get(key));
			} else {
				System.out.println("         no children");
				System.out.println("         parents can be " + entityParents.get(key));
			}
		}
		System.out.println(
				"Note that all \"children\" are DIRECT, thats why incollections don't all have authors as children.");
	}

	public static void generateBasicClasses() {
		String imports = "import java.util.Set;\n" + "import java.util.HashSet;\n" + "import GrandCentral.SearchBy;";
		Set<String> keySet = entityNo.keySet();
		StringBuilder sb = new StringBuilder();
		String entityTypesPath = System.getProperty("user.dir");
		entityTypesPath.replace("\\", "/");
		entityTypesPath += "/src/entityTypes";
		File entityTypes = new File(entityTypesPath);
		File[] entityTypesList = entityTypes.listFiles();
		/** string entity and entity are defaults, everything else is removed **/
		for (File f : entityTypesList) {
			if (!(Arrays.asList(new String[] { "StringEntity.java", "Entity.java", "Root.java" }))
					.contains(f.getName())) {
				f.delete();
			}
		}
		String childrenNote;
		String[] childrenDeclaration;
		for (String key : keySet) {
			sb.setLength(0);
			String className = capitalize(key);
			String header = "public class " + className + " extends Entity{";
			boolean hasChildren = false;
			Set<String> parents;
			Set<String> descriptorChildren = null;
			Set<String> requiredChildren = null;
			Set<String> unrequiredChildren = null;
			Set<String> anyChildren = null;
			int entityOcc = entityNo.get(key);
			HashMap<String, List<Integer>> entityChildren = entityChildrenMap.get(key);
			Set<String> keySet2 = null;
			if (entityChildren != null) {
				requiredChildren = new HashSet<String>();
				unrequiredChildren = new HashSet<String>();
				descriptorChildren = new HashSet<String>();
				anyChildren = new HashSet<String>();
				keySet2 = entityChildren.keySet();
				childrenNote = toNote("children = " + keySet2);
				childrenDeclaration = new String[keySet2.size()];
				int index = 0;
				for (String key2 : keySet2) {
					/** list of #of occurences **/
					List<Integer> li = entityChildren.get(key2);
					/** range in #of occurences **/
					Range r = new Range(li);
					if (r.min > 0) {
						requiredChildren.add(key2);
						anyChildren.add(key2);
						if (r.hasInput && r.min == 1 && r.max == 1) {
							descriptorChildren.add(key2);
							anyChildren.add(key2);
						}

					} else {
						unrequiredChildren.add(key2);
						anyChildren.add(key2);
					}
					String childClass = capitalize(key2);
					if (r.max > 1) {
						childrenDeclaration[index] = "public Set<" + childClass + "> " + key2 + ";";
					} else {
						childrenDeclaration[index] = "public " + childClass + " " + key2 + ";";
					}
					index += 1;
				}
				childrenNote = toNote("children = " + anyChildren);
				hasChildren = true;
			} else {
				childrenNote = toNote("no children, only contents");
				childrenDeclaration = new String[2];
				childrenDeclaration[0] = "public final boolean hasChildren = false;";
				childrenDeclaration[1] = "public StringEntity contents;";
			}
			Set<String> parentSet = entityParents.get(key);
			String parentNote = "";
			String parentDeclaration = "";
			if (parentSet == null) {
				parentNote = toNote("no parents, this is the root");
			} else {
				parentNote = toNote("possible parents = " + entityParents.get(key));
				if (parentSet.size() == 1) {
					String parentClass = capitalize(parentSet.iterator().next());
					parentDeclaration = "Set<" + parentClass + "> parent;";
				} else { // parentSet.size() > 1
					parentDeclaration = "Set<Entity>parent;";
				}
			}
			entityParentNotes.put(key, parentNote);
			String descriptorNote = "";
			/** establish the uniqueDescriptor **/
			String descriptorDeclaration = "";
			/** origin refers to diff definitions of origin of "descriptorChildren" **/
			SearchBy origin = SearchBy.DescriptorChildren;
			if (descriptorChildren == null || descriptorChildren.size() == 0) {
				if (requiredChildren == null || requiredChildren.size() == 0) {
					descriptorChildren = anyChildren;
					origin = SearchBy.AllChildren;
				} else {
					descriptorChildren = requiredChildren;
					origin = SearchBy.RequiredChildren;
				}
			}
			if (descriptorChildren == null || descriptorChildren.size() == 0) {
				descriptorDeclaration = "StringEntity";
				descriptorDeclaration = "{\"" + descriptorDeclaration + "\"}";
				origin = SearchBy.StringEntity;
				descriptorNote = toNote("the contents that defines this entity");
			} else if (descriptorChildren.size() == 1) {
				descriptorDeclaration = capitalize(descriptorChildren.iterator().next());
				descriptorDeclaration = "{\"" + descriptorDeclaration + "\"}";
				descriptorNote = toNote("the child that defines this entity");
			} else {
				int index = 0;
				for (String dc : descriptorChildren) {
					descriptorDeclaration += (index == descriptorChildren.size() - 1) ? ("\"" + dc + "\"")
							: ("\"" + dc + "\",");
					index += 1;
				}
				descriptorDeclaration = "{" + descriptorDeclaration + "}";
				descriptorNote = toNote("the children that define this entity");
			}
			descriptorDeclaration = "public static final String[] uniqueDescriptor = " + descriptorDeclaration + ";";
			parents = entityParents.get(key);
			entitySearchBy.put(key, origin);
			String methodNote;
			String methodHeader = "public boolean equals(" + className + " " + key + ") {\n";
			String methodReturn = "\t" + "return ";
			if (!hasChildren) {
				methodNote = toNote("equals depends on StringEntity contents");
				methodReturn += "this.contents.equals(" + key + ".contents);\n\t}";
			} else {
				methodNote = toNote("Equality depends on " + origin.toString());
				boolean first = true;
				for (String dc : descriptorChildren) {
					methodReturn += first ? ("this." + dc + ".equals(" + key + "." + dc + ")")
							: (" && this." + dc + ".equals(" + key + "." + dc + ")");
					first = false;
				}
				methodReturn += ";\n" + "\t" + "}";
			}
			String fileContents = "package entityTypes;\n" + imports + "\n\n" + header + "\n\n\t" + parentNote + "\n\t"
					+ parentDeclaration + "\n\n\t" + descriptorNote + "\n\t" + descriptorDeclaration + "\n\n\t"
					+ childrenNote;
			for (String s : childrenDeclaration) {
				fileContents += "\n\t" + s;
			}
			fileContents += "\n\n\t" + methodNote + "\n\t" + methodHeader + "\t" + methodReturn;
			fileContents += "\n\n\t" + "public static final SearchBy searchTier = SearchBy." + origin + ";";
			fileContents += "\n\n" + "}";
			Formatting.printToFile(fileContents, entityTypesPath + "/" + className + ".java");

		}
		System.out.println("Generated Basic Classes to entityTypes package (you have to refresh).");
	}

	public static void generateCustomHashMaps() {
		String imports = "package GrandCentral;\n" + "import java.util.HashMap;\n" + "import entityTypes.*;\n"
				+ "import entityTypes.Number;\n" + ""+"import java.util.HashSet;\n" + 
						"import java.util.Set;";
		String srcPath = System.getProperty("user.dir");
		srcPath.replace("\\", "/");
		srcPath += "/src/";
		Set<String> keySet = entitySearchBy.keySet();
		List<String> hmDeclarations = new ArrayList<String>();
		
		List<String> classDeclarations = new ArrayList<String>();
		List<String>mapLocationDeclarations = new ArrayList<String>();
		List<String>putMethod = new ArrayList<String>();
		List<String>getMethod = new ArrayList<String>();
		putMethod.add("\npublic static void put(String name, StringEntity key,Entity value) {\n\tif (false) {}");
		getMethod.add("public static Entity get(String keyName, StringEntity key) {\nif (false) {}");
		classDeclarations.add("\npublic static void load() {");
		hmDeclarations.add(imports);
		hmDeclarations.add("public class All{");
		/** make single hashmap for mapping all keys to a class **/
		hmDeclarations.add("public static HashMap<String,Class>classMap = new HashMap<String,Class>();");
		hmDeclarations.add("public static HashMap<String,HashMap<StringEntity,? extends Entity>>allHashMaps = new HashMap<String,HashMap<StringEntity,? extends Entity>>();\npublic static Set<String> entitiesWithStrings = new HashSet<String>();");
		/** make hashmaps for all keys with stringEntity as descriptor **/
		for (String key : keySet) {
			String cName = capitalize(key);
			SearchBy sb = entitySearchBy.get(key);
			int no = entityNo.get(key);
			// Here we're only going to make hashmaps for entities that can be searched for
			// using StringEntity
			if (no > 1 && sb.equals(SearchBy.StringEntity)) {
				String pNote = "\n" + entityParentNotes.get(key);
				hmDeclarations.add(pNote);
				String keyWithS = key
						+ ((key.charAt(key.length() - 1) == 's') ? "" : "s");
				hmDeclarations.add("public static HashMap<StringEntity," + cName + ">" + keyWithS + " = new HashMap<StringEntity," + cName
						+ ">();");
				mapLocationDeclarations.add("allHashMaps.put(\""+key+"\", "+keyWithS+");");
				getMethod.add("else if (keyName.equals(\""+key+"\")) {\n" + 
						"	return ("+cName+")"+keyWithS+".get(key);\n" + 
						"}");
				putMethod.add("else if (name.equals(\""+key+"\")) {"+keyWithS+".put(key, ("+cName+")value);}");
			}
			classDeclarations.add("classMap.put(\"" + key + "\",(new " + cName + "()).getClass());");
		}
		
		putMethod.add("\n}");
		getMethod.add("\nreturn null;\n}");
		hmDeclarations.addAll(classDeclarations);
		hmDeclarations.addAll(mapLocationDeclarations);
		hmDeclarations.add("entitiesWithStrings = allHashMaps.keySet();");	
		hmDeclarations.add("\n}");
		hmDeclarations.addAll(putMethod);
		hmDeclarations.addAll(getMethod);
		hmDeclarations.add("\n}");
		String grandCentral = srcPath + "GrandCentral/All.java";
		Formatting.recreateFile(grandCentral);
		Formatting.printToFile(hmDeclarations, grandCentral);
		System.out.println("Custom hashmaps generated to GrandCentral.All class");
		try {
			Method m = (new All()).getClass().getMethod("load");
			m.invoke(null, null);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void fillDataStructures(String filePath) {

		try {
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();

			DefaultHandler handler = new DefaultHandler() {
				LLNodeEntity parentNode = new LLNodeEntity(new StringEntity("root"));
				String contents = "";

				public void startElement(String uri, String localName, String qName, Attributes attributes)
						throws SAXException {
					Class<? extends Entity> cqe = All.classMap.get(qName);
					try {
						Entity thisEntity = cqe.getConstructor().newInstance();
						parentNode.next = new LLNodeEntity(parentNode, thisEntity);
						parentNode = parentNode.next;
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

				public void endElement(String uri, String localName, String qName) throws SAXException {
					parentNode = parentNode.prev;
					Entity parent = parentNode.element;
					Entity thisElement = parentNode.next.element;
					try {
						if (All.entitiesWithStrings.contains(qName)) {
							StringEntity se;
							if (contents.length() < 50) {
								se = new StringEntity(contents);
							} else {
								se = new StringEntity("n");
							}
							StringEntity seExisting = (StringEntity) All.get(qName,se);
							Field f = thisElement.getClass().getField("contents");
							if (seExisting == null) {
								f.set(thisElement, se);
								All.put(qName,seExisting, thisElement);
							}
							else {		
								System.out.println("f");
								f.set(thisElement, seExisting);
							}
						}
						Field f = parent.getClass().getField(qName);
						if (f.getType().getName().equals("java.util.Set")) {
							Class ccc = f.getType();
							var v = f.get(parent);
							if (v == null) {
								Set<Entity> newSet = new HashSet<Entity>();
								newSet.add(thisElement);
								f.set(parent, newSet);
							} else {
								Set<Entity> newSet = (Set<Entity>) f.get(parent);
								newSet.add(thisElement);
								f.set(parent, newSet);
							}

						} else {
							f.set(parent, thisElement);
						}

					} catch (Exception e) {
						e.printStackTrace();
					}
					parentNode.next = null;
					contents = "";
				}

				public void characters(char ch[], int start, int length) throws SAXException {
					contents = new String(ch, start, length);
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
		System.out.println("Data Loaded.");
	}

	public static void compressData() {
		System.out.println("Nothing written to compress data.");
	}

	public static String capitalize(String key) {
		return Character.toUpperCase(key.charAt(0)) + key.substring(1, key.length());
	}

	public static String toNote(String s) {
		return "/** " + s + " **/";
	}

}
