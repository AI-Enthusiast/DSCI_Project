package GrandCentral;

import java.util.HashMap;

import entityTypes.*;
import entityTypes.Number;

import java.util.HashSet;
import java.util.Set;

public class All {
    public static HashMap<String, Class> classMap = new HashMap<String, Class>();
    public static HashMap<String, HashMap<StringEntity, ? extends Entity>> allHashMaps = new HashMap<String, HashMap<StringEntity, ? extends Entity>>();
    public static Set<String> entitiesWithStrings = new HashSet<String>();


    /**
     * possible parents = [incollection, www, book, mastersthesis, proceedings, inproceedings, phdthesis, article]
     **/
    public static HashMap<StringEntity, Author> authors = new HashMap<StringEntity, Author>();


    public static void load() {

        allHashMaps.put("author", authors);
        entitiesWithStrings = allHashMaps.keySet();

    }

    public static void put(String name, StringEntity key, Entity value) {
        if (false) {
        } else if (name.equals("author")) {
            authors.put(key, (Author) value);
        }
    }

    public static Entity get(String keyName, StringEntity key) {
        if (false) {
        } else if (keyName.equals("author")) {
            return (Author) authors.get(key);
        }


        return null;
    }

}
